package LogicTests;

import logic.APIhandle;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import java.net.MalformedURLException;

import static org.junit.Assert.*;

public class APIhandleTest {

    APIhandle a;

    @Before
    public void setUp() throws MalformedURLException {
        a = new APIhandle();
    }

    @Test
    public void httpRequestHandlesBadRequest() throws IOException {
        List<String[]> response = a.makeHttpRequest(true, ".*?=,!#¤%&/()=   ");

        assertEquals(response.size(), 0);

        response = a.makeHttpRequest(false, ".*?=,!#¤%&/()=   ");

        assertEquals(response.size(), 0);
    }

    @Test
    public void stopHttpRequestWontReturnNullValues() throws IOException {
        List<String[]> response = a.makeHttpRequest(true, "Hämeenapajantie");

        boolean anyNulls = false;

        for (String[] s: response) {
            if (s[0] == null) {
                anyNulls = true;
            }
        }

        assertFalse(anyNulls);
    }

    @Test
    public void tripHttpRequestWontReturnNullValues() throws IOException {
        List<String[]> response = a.makeHttpRequest(false, "Hämeenapajantie");

        boolean anyNulls = false;

        for (String[] s: response) {
            if (s[0] == null || s[1] == null || s[2] == null || s[3] == null || s[4] == null) {
                anyNulls = true;
            }
        }

        assertFalse(anyNulls);
    }

    @Test
    public void secondConvertsToTime() {
        String time = a.convertSeconds("28799");

        assertEquals("07:59:59", time);

        time = a.convertSeconds("0");

        assertEquals("00:00:00", time);

        time = a.convertSeconds("86400");

        assertEquals("00:00:00", time);

        time = a.convertSeconds("86405");

        assertEquals("00:00:05", time);
    }
}
