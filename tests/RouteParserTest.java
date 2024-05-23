import models.Route;
import org.junit.jupiter.api.Test;
import parsers.RouteParser;

import static org.junit.jupiter.api.Assertions.*;

public class RouteParserTest {

    final static String oneLine = "2B,410,TGK,\\N,DME,4029,,0,CR2";


    @Test
    void ParseOneLine(){

        Route route = RouteParser.parserLine(oneLine);

        assertEquals("2B", route.getCode());
        assertEquals(410, route.getId());
        assertEquals("TGK", route.getAirportCodeSource() );
        assertEquals(0, route.getIdAirportSource());
        assertEquals("DME", route.getAirportCodeDestination() );
        assertEquals(4029, route.getIdAirportDestination());
        assertEquals("", route.getCodeshare());
        assertEquals(0, route.getNumberOfStops());
        assertEquals("CR2", route.getAirplaneType());

    }
}
