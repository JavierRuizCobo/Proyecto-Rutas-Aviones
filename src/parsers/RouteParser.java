package parsers;

import models.Route;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RouteParser {

    static final int codeIndex = 0;
    static final int idIndex = 1;
    static final int airportCodeSourceIndex = 2;
    static final int idAirportCodeSourceIndex = 3;
    static final int airportCodeDestinationIndex = 4;
    static final int idAirportCodeDestinationIndex = 5;
    static final int codeshareIndex = 6;
    static final int numberOfStopsIndex = 7;
    static final int airplaneTypeIndex = 8;

    public static ArrayList<Route> airportList(String file) throws IOException {
        ArrayList<Route> out = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(file));

        String line;

        while (null !=(line = in.readLine())){

            Route output = RouteParser.parserLine(line);
            out.add(output);
        }
        return out;
    }

    public static Route parserLine(String line){

        String[] parts = splitCsvLine(line);

        final String code = parts[codeIndex];
        Route route = new Route(code);

        final Integer id = convertToInteger(parts[idIndex]);
        if(id != null )route.setId(id);

        final String airportCodeSource = parts[airportCodeSourceIndex];
        route.setAirportCodeSource(airportCodeSource);

        final Integer idAirportSource = convertToInteger(parts[idAirportCodeSourceIndex]);
        if(idAirportSource !=null )route.setIdAirportSource(idAirportSource);

        final String airportCodeDestination = parts[airportCodeDestinationIndex];
        route.setAirportCodeDestination(airportCodeDestination);

        final Integer idAirportDestination = convertToInteger(parts[idAirportCodeDestinationIndex]);
        if (idAirportDestination != null )route.setIdAirportDestination(idAirportDestination);

        final String codeshare = parts[codeshareIndex];
        if(codeshare != null ) route.setCodeshare(codeshare);

        final int numberOfStops = Integer.parseInt(parts[numberOfStopsIndex]);
        route.setNumberOfStops(numberOfStops);

        final String airplaneType = parts[airplaneTypeIndex];
        route.setAirplaneType(airplaneType);

        return route;
    }

    private static Integer convertToInteger(String part) {
        if(part.equals("") || part.equals("\\N")) return null;
        return Integer.parseInt(part);
    }

    private static String[] splitCsvLine(String line) {

        String[] parts = line.split(",", 9);

        return parts;
    }
}
