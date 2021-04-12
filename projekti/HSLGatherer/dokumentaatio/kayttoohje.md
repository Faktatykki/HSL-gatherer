# Käyttöohje

## Käynnistäminen

Ohjelma käynnistetään seuraavalla komennolla:

```
mvn compile exec:java -Dexec.mainClass=main.Main
```

Komento käynnistää ohjelman tekstikäyttöliittymän.

## Päänäkymä

Kun ohjelma käynnistyy, tulee käyttäjälle ensimmäisenä vastaan päänäkymä. Navigoiminen tapahtuu syöttämällä halutun toiminnallisuuden numero ja painamalla 'Enter'. Esimerkkinä kun halutaan siirtyä etsimään saapumisaikoja:

KUVA PÄÄNÄKYMÄSTÄ

## Saapumisaikojen etsiminen

Saapumisaikoja haetaan tekstimuotoisesti syöttämällä pysäkin nimi ja painamalla 'Enter'. Hakuparametrin ei tarvitse olla eksakti, mutta se tarkentaa hakua. Kirjainkoolla ei ole väliä:

KUVA SAAPUMISAIKOJEN ETSIMISISTÄ 

## Pysäkkihaku

Pysäkkejä haetaan tekstimuotoisesti syöttämällä pysäkin nimi ja painamalla 'Enter'. Hakuparametrin ei tarvitse olla eksakti. Hakutoiminto onkin hyvä sitä varten, että hakee pysäkkejä hieman laajemmalla hakusanalla, jonka tulosten myötä käyttäjä osaa tehdä tarkemman haun saapumisaikojen suhteen:

KUVA PYSÄKKIHAUSTA  


## Sovelluksen sulkeminen

Sovellus suljetaan syöttämällä päänäkymässä parametriksi numero kolme (3) ja painamalla 'Enter'.

KUVAT SULKEMISESTA

