package models;

public class Route {

    private String code;
    private int id;
    private String airportCodeSource;
    private int idAirportSource;
    private String airportCodeDestination;
    private int idAirportDestination;
    private String codeshare;
    private int numberOfStops;
    private String airplaneType;

    public Route(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAirportCodeSource() {
        return airportCodeSource;
    }

    public void setAirportCodeSource(String airportCodeSource) {
        this.airportCodeSource = airportCodeSource;
    }

    public int getIdAirportSource() {
        return idAirportSource;
    }

    public void setIdAirportSource(int idAirportSource) {
        this.idAirportSource = idAirportSource;
    }

    public String getAirportCodeDestination() {
        return airportCodeDestination;
    }

    public void setAirportCodeDestination(String airportCodeDestination) {
        this.airportCodeDestination = airportCodeDestination;
    }

    public int getIdAirportDestination() {
        return idAirportDestination;
    }

    public void setIdAirportDestination(int idAirportDestination) {
        this.idAirportDestination = idAirportDestination;
    }

    public String getCodeshare() {
        return codeshare;
    }

    public void setCodeshare(String codeshare) {
        this.codeshare = codeshare;
    }

    public int getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(int numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public String getAirplaneType() {
        return airplaneType;
    }

    public void setAirplaneType(String airplaneType) {
        this.airplaneType = airplaneType;
    }
}
