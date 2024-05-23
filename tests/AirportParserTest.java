import models.Airport;
import org.junit.jupiter.api.Test;
import parsers.AirportParser;

import static org.junit.jupiter.api.Assertions.*;

public class AirportParserTest {


    final static String oneLine = "641,\"Harstad/Narvik Airport, Evenes\",\"Harstad/Narvik\",\"Norway\",\"EVE\",\"ENEV\",68.491302490234,16.678100585938,84,1,\"E\",\"Europe/Oslo\",\"airport\",\"OurAirports\"";

    @Test
    void ParseOneLine(){

        Airport airport = AirportParser.parserLine(oneLine);

        assertEquals(641, airport.getId());
        assertEquals("Harstad/Narvik Airport, Evenes", airport.getName());
        assertEquals("Harstad/Narvik", airport.getCity());
        assertEquals("Norway", airport.getCounty());
        assertEquals("EVE", airport.getIATA());
        assertEquals("ENEV", airport.getICAO());
        assertEquals(68.491302490234, airport.getLatitude());
        assertEquals(16.678100585938, airport.getLongitude());
        assertEquals(84, airport.getAltitude());
        assertEquals(1, airport.getTimeZone());
        assertEquals("E", airport.getDST());
        assertEquals("OurAirports", airport.getInfoSource());

    }
}
