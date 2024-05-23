import models.Airport;
import models.Route;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import parsers.AirportParser;
import parsers.RouteParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class mainApp {

    static ArrayList<Airport> airport = new ArrayList<>();
    static ArrayList<Route> route = new ArrayList<>();


    public static void main (String[] arg) throws IOException {

        boolean finishApp=false;

        airport=AirportParser.airportList("airports.txt");
        route = RouteParser.airportList("routes.txt");

        while (!finishApp){

            String airportSource = getAirportSource();
            String airportDestination = getAirportDestination();

            SingleGraph graph =new SingleGraph("Grafo");

            graph.setAttribute("ui.stylesheet", getCss());

            fillWithNodes(graph);

            createDikkstra(graph,airportSource,airportDestination);

            System.setProperty("org.graphstream.ui", "swing");
            Viewer view = graph.display(false);

            finishApp= getUserDecision();

            if(!finishApp)view.close();
        }

        System.exit(0);

    }
    private static String getAirportSource() {

        Scanner entradaScanner = new Scanner(System.in);

        System.out.println("Aeropuerto de origen:");
        String airportSource = entradaScanner.nextLine();

        if(correctNameAirport(airportSource)){
            return airportSource;
        }
        else {
            System.out.println("No coincide el nombre del aeropuerto.");
            getAirportSource();
        }
        return null;
    }

    private static String getAirportDestination() {

        Scanner entradaScanner = new Scanner(System.in);

        System.out.println("Aeropuerto de destino:");
        String airportDestination = entradaScanner.nextLine();

        if(correctNameAirport(airportDestination)){
            return airportDestination;
        }
        else {
            System.out.println("No coincide el nombre del aeropuerto.");
            getAirportDestination();
        }
        return null;
    }

    private static boolean correctNameAirport(String airportName) {
        for (Airport value : airport) {
            if (airportName.equals(value.getName())) {
                return true;
            }
        }

        return false;
    }

    private static String getCss() {
        return """
graph{
  fill-mode: plain;
  fill-color: #00b3ff;
}
                
node{
  text-size: 2;
  text-alignment: under;
  size: 3;
  fill-color:white;
  stroke-mode: plain;
  stroke-width: 2;
  stroke-color: red;
}

edge{
  text-size: 0;
  size:0;
}
                                
node.best_path{
  size: 8;
  text-size: 10;
  text-color:white;
  text-alignment: under;
  fill-color: green;
  stroke-color: white;
}
                
edge.best_path{
  fill-color: yellow;
  size: 3;
}
""";
    }

    private static void fillWithNodes(SingleGraph graph) {

        addNodes(graph);
        addEdges(graph);

    }

    private static void addNodes(SingleGraph graph) {

        for (Airport value : airport) {
            String nodeName = value.getName() + ", " + value.getIATA() + "-" + value.getICAO();
            Node node = graph.addNode(nodeName);
            node.setAttribute("ui.label", value.getName());
            node.setAttribute("x", value.getLongitude());
            node.setAttribute("y", value.getLatitude());
        }

    }

    private static void addEdges(SingleGraph graph) {
        ArrayList<Route> edges = new ArrayList<>();

        for (Route value : route) {

            String iataSource = value.getAirportCodeSource();
            String iataDestination = value.getAirportCodeDestination();
            String nodeName1 = "";
            String nodeName2 = "";

            Airport airportSource = null, airportDestination = null;


            for (Airport item : airport) {
                if (iataSource.equals(item.getIATA())) {
                    nodeName1 = item.getName() + ", " + item.getIATA() + "-" + item.getICAO();
                    airportSource = item;
                }
                if (iataDestination.equals(item.getIATA())) {
                    nodeName2 = item.getName() + ", " + item.getIATA() + "-" + item.getICAO();
                    airportDestination=item;
                }
            }

            if ((!repeatEdge(edges, iataSource, iataDestination) && !nodeName2.equals("") && !nodeName1.equals("")) || edges.isEmpty()) {
                Edge edge = graph.addEdge(value.getAirportCodeSource() + "-"
                                + value.getAirportCodeDestination() + value.getCode()
                        , nodeName1, nodeName2);

                edge.setAttribute("ui.label", getDistance(airportSource,airportDestination));
                edge.setAttribute("length", getDistance(airportSource,airportDestination));
                edge.setAttribute("weight", value.getNumberOfStops()+1);
                edge.setAttribute("ui.hide");
            }
            edges.add(value);
        }
    }

    private static boolean repeatEdge(ArrayList<Route> edges, String iataSource, String iataDestination) {

        for (Route edge : edges) {
            if (edge.getAirportCodeSource().equals(iataDestination) &&
                    edge.getAirportCodeDestination().equals(iataSource)) {
                return true;
            }

            if (edge.getAirportCodeSource().equals(iataSource) && edge.getAirportCodeDestination().equals(iataDestination)) {
                return true;
            }
        }
        return false;
    }

    private static double getDistance(Airport airportSource, Airport airportDestination) {

        final double radioTierra = 6371;
        final double lat1 = airportSource.getLatitude();
        final double lat2 = airportDestination.getLatitude();
        final double lng1 = airportSource.getLongitude();
        final double lng2= airportDestination.getLongitude();

        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat/2);
        double sindLon = Math.sin(dLon/2);

        double va1 = Math.pow(sindLat,2) + (Math.pow(sindLon,2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)));

        double va2 = 2 * Math.atan2(Math.sqrt(va1),Math.sqrt(1 - va1));
        double distance = radioTierra * va2;

        return distance;
    }

    private static void createDikkstra(SingleGraph graph, String airportSource, String airportDestination) {

        Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE,
                null, "length" );

        dijkstra.init(graph);
        dijkstra.setSource(getNodeAirport(airportSource,graph));
        dijkstra.compute();

        Node finalNode = getNodeAirport(airportDestination, graph);

        for(Node node: dijkstra.getPathNodes(finalNode)){
            node.setAttribute("ui.class", "best_path");
        }

        for(Edge edge: dijkstra.getPathEdges(finalNode)){
            edge.setAttribute("ui.class", "visible");
            edge.removeAttribute("ui.hide");
            edge.setAttribute("ui.class", "best_path");
        }

    }

    private static Node getNodeAirport(String airportName, SingleGraph graph) {

        for (Airport value : airport) {
            if (airportName.equals(value.getName())) {
                return graph.getNode(airportName + ", " + value.getIATA() + "-" + value.getICAO());
            }
        }
        return null;
    }

    private static boolean getUserDecision() {
        Scanner entradaScanner = new Scanner(System.in);

        System.out.println("¿Terminar aplicación? (1-Si)(2-No)");
        String userDecision = entradaScanner.nextLine();

        if(userDecision.equals("1")) return true;

        return false;
    }

}
