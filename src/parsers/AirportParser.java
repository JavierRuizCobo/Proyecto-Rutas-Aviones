package parsers;

import models.Airport;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AirportParser {

    static final int idIndex = 0;
    static final int nameIndex = 1;
    static final int cityIndex = 2;
    static final int countryIndex = 3;
    static final int iataIndex = 4;
    static final int icaoIndex = 5;
    static final int latitudeIndex = 6;
    static final int longitudeIndex = 7;
    static final int altitudeIndex = 8;
    static final int timezoneIndex = 9;
    static final int dstIndex = 10;
    static final int sourceIndex = 13;

    public static ArrayList<Airport> airportList(String file) throws IOException {
        ArrayList<Airport> out = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(file));

        String line;

        while (null !=(line = in.readLine())){

            Airport output = AirportParser.parserLine(line);
            out.add(output);
        }
        return out;
    }

    public static Airport parserLine(String line) {

        String[] parts = splitCsvLine(line);

        final int id = Integer.parseInt(parts[idIndex]);
        Airport output = new Airport(id);

        final String name = parts[nameIndex].replace("\"", "");
        output.setName(name);

        final String city = parts[cityIndex].replace("\"", "");
        output.setCity(city);

        final String country = parts[countryIndex].replace("\"", "");
        output.setCounty(country);

        String iata = parts[iataIndex].replace("\"", "");
        if (iata.equals("\\N")) iata="";
        output.setIATA(iata);

        final String icao = parts[icaoIndex].replace("\"", "");
        output.setICAO(icao);

        final double latitude = Double.parseDouble(parts[latitudeIndex] );
        output.setLatitude(latitude);

        final double longitude = Double.parseDouble(parts[longitudeIndex]);
        output.setLongitude(longitude);

        final int altitude = Integer.parseInt(parts[altitudeIndex]);
        output.setAltitude(altitude);

        final Float timeZone = converseToFloat(parts[timezoneIndex]);
        if(timeZone != null )output.setTimeZone(timeZone);

        final String dst = parts[dstIndex].replace("\"", "");
        output.setDST(dst);

        final String source = parts[sourceIndex].replace("\"", "");
        output.setInfoSource(source);

        return output;
    }

    private static Float converseToFloat(String part) {
        if(part.equals("\\N")){
            return null;
        }
        return Float.parseFloat(part);
    }


    private static String[] splitCsvLine(String line) {

        String[] parts = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", 14);

        return parts;
    }



}
