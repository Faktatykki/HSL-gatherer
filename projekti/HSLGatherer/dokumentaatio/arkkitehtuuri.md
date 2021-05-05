# Arkkitehtuurikuvaus
## Rakenne 

Ohjelma noudattaa kolmitasoista kerrosarkkitehtuuria. Jokainen kerros vastaa omasta osa-alueestaan. Pakkausrakenne on seuraavanlainen: 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/packages.png) 

Pakkaus *ui* sisältää käyttöliittymän rakentavan koodin luokassa [*GUI*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/ui/GUI.java). Kyseinen arkkitehtuuritaso vastaa ainoastaan käyttäjän kanssa kommunikoinnista. 

Pakkaus *services* sisältää palveluluokan [*Service*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/services/Service.java). Arkkitehtuuritason vastuulla on käyttöliittymän ja sovelluslogiikan välillä kommunikointi. 

Pakkaus *logic* sisältää luokat *APIhandle*, *DBConnector* ja *Logic*. [*APIhandle*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/logic/APIhandle.java) vastaa rajapinnan kanssa kommunikoinnista. [*DBConnector*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/logic/DBConnector.java) vastaa tietokantaan liittyvistä toiminnoista. [*Logic*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/logic/Logic.java) vastaa yleisestä sovelluslogiikasta. Arkkitehtuuritason vastuulla on sovelluslogiikka ja ulkoisten tekijöiden (rajapinta, tietokanta) kanssa kommunikointi.

## Käyttöliittymä

Graafinen käyttöliittymä on toteutettu JavaFX:llä ja sisältää karkeasti viisi näkymää: 

- Pysäkkien haku ja tallentaminen 
- Linjan lisääminen tallennetulle pysäkille 
- Tallennettujen pysäkkien ja linjojen ohitusaikojen reaaliaikaisesti päivittyvä koonti
- Tallennetun pysäkin poistaminen 
- Tallennetun linjan poistaminen pysäkkikohtaisesti 

Tarkempi kuvaus näkymistä löytyy [käyttöohjeista](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kayttoohje.md). Jokainen näkymistä on toteutettu omana [Scene](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html)-oliona. Jokaista Scene-oliota ja sen rakentamista edustaa oma metodi. Metodi rakentaa Scene-olion ja palauttaa sen globaalina muuttujana toimivalle [Stage](https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html)-oliolle. 

Käyttöliittymä kommunikoi ainoastaan Service-luokan metodien kautta sovelluslogiikan kanssa. Täten sovelluslogiikka on eriytetty käyttöliittymästä. Tarkempi kuvaus toiminnallisuudesta löytyy kohdasta "Päätoiminnallisuudet". 

## Sovelluslogiikka ja palveluluokka 

Ohjelmassa pääosassa ovat HSL:n pysäkit ja niihin liittyvät julkisten kulkuvälineiden ohitusajat. Pysäkkejä edustaa luokka [*Stop*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/classes/Stop.java). Linjoja ja aikatauluja edustaa luokka [*Trip*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/classes/Trip.java). Näitä luokkia luovat ja käsittelevät ja ohjelman päätoiminnallisuuteen liittyvät metodit tulevat helposti ilmi *Service*-luokan metodeista. Näitä ovat: 

- searchForStops(String stop)
- addStop(String stop)
- addTrip(Trip trip, String stop)
- searchForTrips(String stop)
- searchForRoutes(String stop) (ero searchForTrips-metodiin on, että searchForRoutes luo listan linjoja kokonaisten aikataulujen sijaan)
- getSavedStops()
- getSavedRoutes()
- deleteStop(String stop)
- deleteRoute(Trip trip)

*Service*-luokka ohjaa käyttöliittymästä tulleet metodikutsut ja parametrit edelleen *Logic*-luokalle. *Logic*-luokka suorittaa omaa toiminnallisuutta ja ohjaa kutsuja edelleen *DBConnector*- tai *APIhandle*-luokalle, riippuen käyttäjän toiminnallisuudesta.

Eri arkkitehtuuritasojen välisiä suhteita kuvaava kaavio: 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/luokkakaavio.png)

## Tiedon talletus 

Pakkauksen *logic* luokka *DBConnector* hoitaa tietokantaan liittyvät toiminnallisuudet. Ohjelman tarvitseman tiedon pysyväistalletuksen volyymi on verrattain pientä, joten ohjelma käyttää kevyttä *SQLiteä*. Tietokantaan tallennetaan tiedot käyttäjän valitsemista pysäkeistä ja linjoista. Taulujen rakenne on seuraavanlainen: 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/tables.png) 

*Stops*-tauluun talletetaan pysäkkien nimet. 

*Trips*-tauluun talletetaan linjan tunnus, nimi ja viittaus tallennettuun pysäkkiin. Jos tallennettu pysäkki poistetaan johon tallennettu linja viittaa, poistuu myös linja tietokannasta. 

Tietokannan tallennettuja tietoja käytetään hyväksi päivittyvässä tiedossa. Tietokantaan tallennetut tiedot ovatkin paljolti tallennettuja hakuparametreja, minkä mukaan rajapinnasta haetaan tietoa yhä uudelleen, kun tietoa halutaan päivittää. 

## Päätoiminnallisuudet 

Sekvenssikaavioiden kautta on helppo havannoida päätoiminnallisuutta ja luokkien välistä riippuvuutta. 

### Pysäkin ja linjan lisääminen

Käyttöliittymässä käyttäjä syöttää hakuparametrin ja painaa nappia, joka kutsuu *Service*-luokan metodia *searchForStops(String stop)*. Metodi kutsuu edelleen *Logic*-luokan metodia *searchStops(boolean true, String stop)*, joka kutsuu *APIhandle*-luokan metodia *makeHttpRequest(boolean true, String stop)*, jota kautta tehdään rajapintaan http-pyyntö. Vastauksena rajapinnalta saadaan [*graphQL*](https://graphql.org/learn/)-kyselyn tulos, jonka *Logic*-luokka palauttaa *Service*-luokan kautta *Stop*-luokkia sisältävänä listana takaisin käyttöliittymään näkyville. Näistä käyttäjä voi valita tallennettavaksi tietokantaan minkä tahansa pysäkin tai enemmän. Käyttäjän painettua *Save*-näppäintä *GUI*-luokka kutsuu *Service*-luokan *addStop(String stop)*-metodia, joka kutsuu *Logic*-luokan metodia *addStop(String stop)*, joka kutsuu *DBConnector*-luokan metodia *addStopToDb(String stop)*, joka lisää pysäkin tietokantaan. 

Prosessi on miltei identtinen pysäkille pysähtyvän linjan tallentamisen kanssa. Käyttöliittymään liittyvien ratkaisujen myötä käyttäjä ei pysty tallentamaan pysäkkiin liittyviä linjoja ilman pysäkin tallentamista. 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/sekvenssi.png) 

### Pysäkin ja linjan poistaminen

Käyttöliittymässä käyttäjä näkee ensin tallentamansa pysäkit ja niihin liittyvät tallennetut linjat. Käyttäjän painaessa *Delete*-nappia kutsuu *Service*- ja *Logic*-luokan kautta *DBConnector*-luokan *deleteStop(String stop)*-metodia, joka poistaa pysäkin tietokannasta. Pysäkin poistaessa poistuu myös pysäkkiin viittaavat tallennetut linjat tietokannasta. 

Prosessi on miltei identtinen pysäkille pysähtyvän linjan poistamisen kanssa. 

### Yleinen toiminnallisuus 

Kaiken toiminnon periaate on käytännössä sama. Käyttäjä kommunikoi ainoastaan käyttöliittymäkerroksen kanssa, käyttöliittymä kerros kommunikoi ainoastaan palvelukerroksen kanssa, palvelukerros ainoastaan logiikkakerroksen kanssa. Täten yleinen toiminnallisuuden muuttaminen ja jatkokehittäminen on suhteellisen suoraviivaista, kun vastuualueet ovat selvät. 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/sekvenssiDelete.png) 

## Parannettavaa 

### Käyttöliittymä 

Graafisen käyttöliittymän vahvuus on näkymien jako metodeiksi, mutta metodit sisältävät toisiin metodeihin nähden paljon toisteista koodia. Toisteista koodia löytyy erityisen paljon asettelun ja eri komponenttien koon suhteen. Tämän voisi korvata luomalla asettelua varten oman metodin, jota kutsumalla eri näkymien komponenttien asettelut olisivat identtiset toisten näkymien välillä. Tästä syystä metodien pituudet ovat todella pitkiä ja täten vaikuttaa koodin ymmärrettävyyteen. 

### Logiikan ja palveluluokan välinen suhde 

Palveluluokkaan on jäänyt yksi tuloksien järjestäminen, joka periaatteessa pitäisi hoitua logiikassa. Toisaalta tämä päätös lisää sovelluslogiikan ymmärrettävyyttä, joten kyse on enemmänkin makuasiasta.

