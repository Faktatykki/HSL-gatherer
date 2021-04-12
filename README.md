# HSLGatherer


Sovellus mahdollistaa Helsingin seudun liikenteen julkisten liikennevälineiden reaaliaikaisten saapumisaikojen tarkkailun pysäkkikohtaisesti. Sovellus käyttää hyödyksi Reittioppaan tarjoamaa rajapintaa, joka päivittää reaaliaikaisesti tietoa Helsingin metrojen, raitiovaunujen ja linja-autojen pysäkkikohtaisista saapumisajoista ja mahdollisista viivästyksistä. 

Sovelluksen valmiissa versiossa käyttäjä pystyy tallentamaan pysäkkejä ja kulkuvälineitä tietokantaa ja pitämään esimerkiksi yllä reaaliaikaista koontia käyttäjälle relevanteista pysäkeistä ja kulkuvälineistä.

## Kieli ja versio

Sovellus on kirjoitettu kokonaan Javan versiolla 11.

## Dokumentaatio

Käyttöohje

[Vaatimuusmäärittely](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/vaatimuusmaarittely.md)  

[Työaikakirjanpito](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/tuntikirjanpito.md)

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

     
