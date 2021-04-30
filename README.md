# HSLGatherer


Sovellus mahdollistaa Helsingin seudun liikenteen julkisten liikennevälineiden reaaliaikaisten saapumisaikojen tarkkailun pysäkkikohtaisesti. Sovellus käyttää hyödyksi Reittioppaan tarjoamaa rajapintaa, joka päivittää reaaliaikaisesti tietoa Helsingin metrojen, raitiovaunujen ja linja-autojen pysäkkikohtaisista saapumisajoista ja mahdollisista viivästyksistä. 

Sovelluksen valmiissa versiossa käyttäjä pystyy tallentamaan pysäkkejä sekä kulkuvälineitä tietokantaan ja pitämään yllä reaaliaikaista koontia käyttäjälle relevanteista pysäkeistä ja kulkuvälineistä.

## Kieli ja versio

Sovellus on kirjoitettu kokonaan Javan versiolla 11 ja vaati toimiakseen kyseisen version (mahdollisesti toimii Javan versiolla 8, mutta ei ole testattu). 

## Rajapinta

Ohjelma käyttää hyväksi Reittiopaan tarjoamaa avointa rajapintaa kulkuvälineiden reaaliaikaisista sijainneista: 

**https://api.digitransit.fi/routing/v1/routers/hsl/index/graphql**

## Dokumentaatio

[Käyttöohje](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kayttoohje.md)

[Vaatimuusmäärittely](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/vaatimusmaarittely.md)  

[Työaikakirjanpito](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuuri](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/arkkitehtuuri.md) 

## Releaset

[Viikon 5 release](https://github.com/Faktatykki/ot-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot 

Kaikki komennot suoritetaan samasta tiedostosijainnista, missä pom.xml sijaitsee.

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
### Jarin generointi

Suoritettavan jarin saa generoitua komennolla: 

```
mvn package
```
Komento luo kansioon *target* suoritettavan tiedoston *HSLGatherer-1.0-SNAPSHOT.jar*, joka suoritetaan komennolla: 

```
java -jar HSLGatherer-1.0-SNAPSHOT.jar
````
### JavaDoc

JavaDoc luodaan komennolla
```
mvn javadoc:javadoc
```
Tarkasteltava JavaDoc löytyy avaamalla tiedosto
*target/site/apidocs/index.html*

### Checkstyle 

Checkstyle-tarkistukset saa suoritettua komennolla: 

```
mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset löytyvät tiedostosta *target/site/checkstyle.html*
