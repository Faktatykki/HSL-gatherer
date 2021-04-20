# HSLGatherer


Sovellus mahdollistaa Helsingin seudun liikenteen julkisten liikennevälineiden reaaliaikaisten saapumisaikojen tarkkailun pysäkkikohtaisesti. Sovellus käyttää hyödyksi Reittioppaan tarjoamaa rajapintaa, joka päivittää reaaliaikaisesti tietoa Helsingin metrojen, raitiovaunujen ja linja-autojen pysäkkikohtaisista saapumisajoista ja mahdollisista viivästyksistä. 

Sovelluksen valmiissa versiossa käyttäjä pystyy tallentamaan pysäkkejä sekä kulkuvälineitä tietokantaan ja pitämään yllä reaaliaikaista koontia käyttäjälle relevanteista pysäkeistä ja kulkuvälineistä.

## Kieli ja versio

Sovellus on kirjoitettu kokonaan Javan versiolla 11.

## Rajapinta

Ohjelma käyttää hyväksi Reittiopaan tarjoamaa avointa rajapintaa kulkuvälineiden reaaliaikaisista sijainneista: 

**https://api.digitransit.fi/routing/v1/routers/hsl/index/graphql**

## Dokumentaatio

[Käyttöohje](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kayttoohje.md)

[Vaatimuusmäärittely](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/vaatimusmaarittely.md)  

[Työaikakirjanpito](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuuri](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/arkkitehtuuri.md)

## Komentorivitoiminnot

### Ohjelman suorittaminen

Ohjelman käynnistäminen tapahtuu komennolla:

```
mvn compile exec:java -Dexec.mainClass=main.Main
```
Komento käynnistää sovelluksen tekstikäyttöliittymän
 

### Testaaminen

Testien suorittaminen tapahtuu komennolla: 

```
mvn test
```
  
Jacoco-testikattavuusraportin luominen tapahtuu komennolla:

```
mvn jacoco:report
```
Jos ylläoleva komento ei toimi, kannattaa kokeilla seuraavaa: 

```
mvn clean jacoco:prepare-agent install jacoco:report
```
