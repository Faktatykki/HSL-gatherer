# Käyttöohje

## Käynnistäminen

Ohjelma käynnistetään seuraavalla komennolla:

```
mvn compile exec:java -Dexec.mainClass=main.Main
```

Komento käynnistää ohjelman tekstikäyttöliittymän.

## Päänäkymä

Kun ohjelma käynnistyy, tulee käyttäjälle ensimmäisenä vastaan päänäkymä. Navigoiminen tapahtuu syöttämällä halutun toiminnallisuuden numero ja painamalla 'Enter'. Esimerkkinä kun halutaan siirtyä etsimään saapumisaikoja:

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/paanakyma.png)

## Saapumisaikojen etsiminen

Saapumisaikoja haetaan tekstimuotoisesti syöttämällä pysäkin nimi ja painamalla 'Enter'. Hakuparametrin ei tarvitse olla eksakti, mutta se tarkentaa hakua. Kirjainkoolla ei ole väliä:

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/matkahaku.png)

Tuloksena tulee tiedot pysäkille saapuvista kulkuvälineistä järjestyksessä:  

- Sign = linjan koodi 
- Route = linjan nimi ja suunta
- Departure = lähtöaika pysäkiltä
- Delay = mahdollinen viivästys
- Updated = tieto siitä, että onko saapumisaikaa päivitetty vai ei

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/matkahakutulos.png)

## Pysäkkihaku

Pysäkkejä haetaan tekstimuotoisesti syöttämällä pysäkin nimi ja painamalla 'Enter'. Hakuparametrin ei tarvitse olla eksakti. Hakutoiminto onkin hyvä sitä varten, että hakee pysäkkejä hieman laajemmalla hakusanalla, jonka tulosten myötä käyttäjä osaa tehdä tarkemman haun saapumisaikojen suhteen:

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/pysakkihaku.png)  

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/pysakkihakutulos.png)

## Sovelluksen sulkeminen

Sovellus suljetaan syöttämällä päänäkymässä parametriksi numero kolme (3) ja painamalla 'Enter'.

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/sulku.png)

