
package Logic;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.*;

public class APIhandle {

    private URL url;

    public APIhandle() throws MalformedURLException {

        this.url = new URL("https://api.digitransit.fi/routing/v1/routers/hsl/index/graphql");
    }

    //tekee httprequestin apiin
    // dataoutputstream luodaan urlia varten
    //getQueryStopissa löytyy valmis hakurakenne, parametrina pysäkki
    //writeUTF lähettää pyynnön apiin
    //vastaus annetaan readFromStream-metodille, parametrina inputstream
    //metodi kutsuu parseGraphQL-metodia jolle viedään inputstream luettavaan muotoon muokattavaksi
    //joka palautetaan listana arrayta
    public List<String[]> makeHttpRequest(String stop) throws IOException {
        List<String[]> jsonResponse = null;

        if(url == null) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Type", "application/graphql");

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());

            String param = getQueryByStop(stop);
            wr.writeUTF(param);
            wr.flush();
            wr.close();

            int rc = urlConnection.getResponseCode();

            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private String getQueryByStop(String stop) throws IOException {
        String s = "{" +
                "                      stops(name: \""+ stop +"\") {" +
                "                        name" +
                "                          stoptimesWithoutPatterns {" +
                "                          trip {" +
                "                            routeShortName" +
                "                          }     " +
                "                          realtimeDeparture" +
                "                          departureDelay" +
                "                          realtimeState" +
                "                          headsign" +
                "                        }" +
                "                      }" +
                "                    }";

        return s;
    }

    private List<String[]> readFromStream(InputStream stream) throws IOException {
        Scanner s = new Scanner(stream).useDelimiter("},\\{").useDelimiter("name").useDelimiter("trip");

        List<String[]> tempList = parseGraphQLResult(s);

        return tempList;
    }

    private List<String[]> parseGraphQLResult(Scanner s) {
        List<String[]> tempList = new ArrayList<>();

        while(s.hasNext()) {
            String[] split = s.next().split("[:,]");

            for(int i = 0; i < split.length; i++) {
                if(i == 10) {
                    continue;
                }

                split[i] = split[i].replaceAll("[^a-zA-Z0-9]", "");
            }

            tempList.add(split);
        }

        for(int i = 1; i < tempList.size(); i++) {
            System.out.println(Arrays.toString(tempList.get(i)));
        }

        return tempList;
    }

}
