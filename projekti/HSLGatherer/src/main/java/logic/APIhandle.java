
package logic;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *  Luokka käsittelee kaiken rajapinnan ja ohjelman välillä
 *  tapahtuvan kommunikoinnin
 */
public class APIhandle {

    private URL url;

    public APIhandle() throws MalformedURLException {
        this.url = new URL("https://api.digitransit.fi/routing/v1/routers/hsl/index/graphql");
    }

    /**
     * Tekee Http-pyynnön rajapintaan luomalla DataOutputStreamin
     * johon kirjoitetaan pyyntö parametrina saadusta merkkijonosta.
     * Riippuen stopQueryn boolean arvosta, saadaan toinen kahdesta
     * valmiista hakurakenteesta (riippuu, että haetaanko pelkkää pysäkkiä
     * vai reittejä). Metodi kutsuu closeConnections-metodia joka lähettää
     * pyynnön rajapinnalle. Vastauksella kutsutaan readFromStream-metodia
     * (parametrina InputStream). Lopulta saadaan vastaukseksi graphQL-vastauksesta
     * jäsennelty lista taulukoita, josta löytyy (riippuen boolean arvosta)
     * rajapinnan vastauksena antamasta tuloksesta pysäkkejä tai pysäkin reittejä.
     *
     * @param stopQuery boolean arvo, joka indikoi, että onko kyseessä reitti- vai pysäkkihaku
     * @param stop pysäkin nimi jota käytetään hakuparametrina
     *
     * @see logic.APIhandle#setConnection()
     * @see logic.APIhandle#getParam(boolean, String)
     * @see logic.APIhandle#endDataoutput(DataOutputStream, String)
     * @see logic.APIhandle#closeConnections(HttpURLConnection, InputStream)
     *
     * @return lista haetuista pysäkeistä tai reiteistä
     *
     * @throws IOException
     */
    public List<String[]> makeHttpRequest(boolean stopQuery, String stop) throws IOException {
        List<String[]> jsonResponse = null;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        
        try {
            urlConnection = setConnection();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());

            String param = getParam(stopQuery, stop);

            endDataoutput(wr, param);

            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(stopQuery, inputStream);
        } catch (Exception e) {
        } finally {
            closeConnections(urlConnection, inputStream);
        }

        return jsonResponse;
    }

    /**
     * Luo yhteyden oliomuuttujana määriteltyyn Reittioppaan rajapintaan
     * ja varmistaa kommunikoinnin oikeellisuuden (UTF-8, graphQL)
     *
     * @see logic.APIhandle#makeHttpRequest(boolean, String)
     *
     * @return valmiiksi alustettu yhteys rajapintaan
     *
     * @throws IOException
     */
    public HttpURLConnection setConnection() throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setRequestProperty("charset", "utf-8");
        urlConnection.setRequestProperty("Content-Type", "application/graphql");

        return urlConnection;
    }

    /**
     * Sulkee yhteyden rajapintaan turvallisesti
     *
     * @param urlConnection yhteys rajapintaan
     * @param inputStream rajapinnan palauttama datavirta
     *
     * @see logic.APIhandle#makeHttpRequest(boolean, String)
     *
     * @throws IOException
     */
    public void closeConnections(HttpURLConnection urlConnection, InputStream inputStream) throws IOException {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }

    /**
     * Lähettää datavirran (graphQL-kyselyn) rajapintaan käyttäjän haluamalla parametrilla
     *
     * @param wr rajapintaan lähetettävä datavirta
     * @param param graphQL-kysely
     *
     * @see logic.APIhandle#makeHttpRequest(boolean, String)
     *
     * @throws IOException
     */
    public void endDataoutput(DataOutputStream wr, String param) throws IOException {
        wr.writeUTF(param);
        wr.flush();
        wr.close();
    }

    /**
     * Määritellään, että onko kyseessä pysäkki- vai reittihaku
     *
     * @param stopQuery true, jos pysäkkihaku, muuten reittihaku
     * @param stop käyttäjän syöttämä hakuparametri
     *
     * @see logic.APIhandle#makeHttpRequest(boolean, String)
     * @see logic.APIhandle#getStops(String)
     * @see logic.APIhandle#getTrips(String)
     *
     * @return riippuen stopQuery-parametrista, oikeanmallinen graphQL-kysely
     *
     * @throws IOException
     */
    public String getParam(boolean stopQuery, String stop) throws IOException {
        String param = null;

        if (stopQuery) {
            param = getStops(stop);
        } else {
            param = getTrips(stop);
        }

        return param;
    }

    /**
     * Luo pysäkkihakua varten graphQL-mallisen kyselyn
     *
     * @param stop käyttäjän syöttämä hakuparametri
     *
     * @see logic.APIhandle#getParam(boolean, String)
     *
     * @return pysäkkihakua varten graphQL-mallinen kysely
     *
     * @throws IOException
     */
    private String getTrips(String stop) throws IOException {
        String s = "{" +
                "                      stops(name: \"" + stop + "\") {" +
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

    /**
     * Luo reittihakua varten graphQL-mallisen kyselyn
     *
     * @param stop käyttäjän syöttämä hakuparametri
     *
     * @see logic.APIhandle#getParam(boolean, String)
     *
     * @return reittihakua varten graphQL-mallinen kysely
     *
     * @throws IOException
     */
    private String getStops(String stop) throws IOException {
        String s = "{" +
                "                      stops(name:\"" + stop + "\") {" +
                "                        name" +
                "                          }" +
                "                           }";
        return s;
    }

    /**
     * Lukee rajapinnan palauttaman datavirran liittyen graphQL-kyselyyn
     * ja kutsumalla jäsentely-metodeja palauttaa listan taulukoita hakutuloksista
     *
     * @param stopQuery true, jos pysäkkihaku, muuten reittihaku
     * @param stream rajapinnan palauttama datavirta
     *
     * @see logic.APIhandle#makeHttpRequest(boolean, String)
     * @see logic.APIhandle#parseGraphQLResult(Scanner)
     * @see logic.APIhandle#parseStopGraphQLResult(Scanner)
     *
     * @return lista taulukoita, jotka ovat jäsennelty helposti läpikäytäväksi
     */
    private List<String[]> readFromStream(boolean stopQuery, InputStream stream) {
        Scanner s = new Scanner(stream).useDelimiter("},\\{").useDelimiter("name").useDelimiter("trip");

        List<String[]> tempList;

        if (stopQuery) {
            tempList = parseStopGraphQLResult(s);
        } else {
            tempList = parseGraphQLResult(s);
        }

        return tempList;
    }

    /**
     * Lukee Scanner-oliosta rajapinnan palauttaman datavirran
     * ja jäsentelee sen taulukoiksi, jonka elementit
     * ovat haettujen pysäkkien tietoja
     *
     * @param s Scanner-olio, joka sisältää rajapinnan palauttaman datavirran
     *
     * @see logic.APIhandle#readFromStream(boolean, InputStream)
     *
     * @return jäsennelty lista taulukoita, jonka elementit ovat pysäkkien tietoja
     */
    private List<String[]> parseStopGraphQLResult(Scanner s) {
        List<String[]> tempList = new ArrayList<>();

        while (s.hasNext()) {
            String[] tempSplit = s.next().split("name");

            for (int i = 1; i < tempSplit.length; i++) {
                tempSplit[i] = tempSplit[i].replaceAll("[^a-zäöåA-ZÖÄÅ0-9.' ']", "");
                String[] added = new String[1];
                added[0] = tempSplit[i];
                tempList.add(added);
            }
        }

        return tempList;
    }

    /**
     * Jäsentelee Scanner-olion datan luettavaan muotoon taulukoiksi listaan.
     * [0] = linjakoodi, [1] = lähtöaika, [2] = viivästyksen määrä, [3] = päivityksen tila
     * [4] = linjan nimi
     *
     * @param s Scanner-olio, joka sisältää rajapinnan palauttaman datavirran
     *
     * @see logic.APIhandle#readFromStream(boolean, InputStream)
     *
     * @return jäsennelty lista taulukoita, jonka elementit ovat reittien tietoja
     */
    private List<String[]> parseGraphQLResult(Scanner s) {
        List<String[]> tempList = new ArrayList<>();

        while (s.hasNext()) {

            String[] tempSplit = s.next().split("[:,]");

            for (int i = 0; i < tempSplit.length; i++) {
                if (i == 10) {
                    tempSplit[i] = tempSplit[i].replaceAll("[\"\\]\\}]", "");
                    continue;
                }

                tempSplit[i] = tempSplit[i].replaceAll("[^a-zäöåA-ZÖÄÅ0-9.' ']", "");
            }

            if (tempSplit.length < 10) {
                continue;
            }

            String[] finArr = {tempSplit[2], convertSeconds(tempSplit[4]), convertSeconds(tempSplit[6]), tempSplit[8], tempSplit[10]};

            tempList.add(finArr);
        }

        return tempList;
    }

    /**
     * Rajapinta palauttaa tarkat ajat muodossa "sekuntia jälkeen keskiyön",
     * joten metodi muuttaa sekunnit kellonajoiksi muotoon "hh:mm:ss"
     *
     * @param s merkkijono-muodossa sekuntimäärän
     *
     * @see logic.APIhandle#parseGraphQLResult(Scanner) 
     * @return tarkka kellonaika muodossa "hh:mm:ss"
     */
    public String convertSeconds(String s) {
        int totalSec = Integer.valueOf(s);

        if (totalSec >= 86400) {
            totalSec = totalSec - 86400;
        }

        int seconds = totalSec % 60;
        int hours = totalSec / 60;
        int minutes = hours % 60;
        hours = hours / 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
