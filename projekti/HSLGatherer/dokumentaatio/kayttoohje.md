# Käyttöohje

## Ohjelman käynnistäminen 

Ohjelman saa käynnistettyä komennolla 
 
```
mvn compile exec:java -Dexec.mainClass=main.Main
```

Komento käynnistää sovelluksen tekstikäyttöliittymän. 

## Ohjelmassa navigointi 

Navigointi tapahtuu koko sovelluksessa syöttämällä kenttään numeron ja painamalla 'Enter'. Paluu tapahtuu aina painamalla 'Enter' ilman parametria. Seuraava ohje näyttää kuvin esimerkin pysäkin ja linjan lisäämisestä sekä niiden poistamisesta. 

## Päänäkymä 

Päänäkymässä valitaan, että mihin toimintoon halutaan siirtyä (syöttämällä numero 1-4) tai poistua (syöttämällä numero viisi (5))

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/paanakyma.png) 

## Pysäkin lisääminen
### (1. Add Stop) 

Näkymässä syötetään halutun pysäkin nimi ja painetaan 'Enter'.

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/haku.png)

Seuraavassa näkymässä näytetään hakutulokset ja mahdollisuus tallentaa haluttu pysäkki hakutuloksista. Valitseminen tapahtuu taas syöttämällä numero. 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/hakutallennus.png) 

Paluu takaisin päänäkymään tapahtuu painamalla 'Enter'. 

## Linjan lisääminen
### (2. Add Trip)  

Kun käyttäjä on tallentanut vähintään yhden pysäkin, on mahdollista tallentaa pysäkkikohtaisia linjoja valitsemalla ensin pysäkki

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/tallennetutpysakit.png) 

ja sitten valitsemalla haluttu linja tallennettavaksi (parametrina ei toimi linjan numero vaan vasemmassa laidassa näkyvä indeksinumero)

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/kuvatallentamisesta.png)

Paluu takaisin päänäkymään tapahtuu painamalla 'Enter'. 

## Tallennettujen pysäkkien ja linjojen koonti 
### (3. Show Stops) 

Kohdassa "Show Stops" käyttäjä voi nähdä kaikki tallentamansa pysäkit ja linjat. Koonti näyttää kaikki tallennetut linjat pysäkkikohtaisesti. 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/kuvakoonnista.png)

## Tallennettujen pysäkkien ja linjojen poistaminen 
### (4. Delete Stops or Routes) 

Kohdassa "Delete Stops or Routes" on mahdollista poistaa jo tallennettuja pysäkkejä ja linjoja. Näkymässä käyttäjä valitsee, että haluaako poistaa pysäkkejä vai linjoja. 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/kuvavalinnasta.png)

Poistovalinnan tekeminen toimii syöttämällä poistettavan linjan tai pysäkin indeksinumero. 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/linjapoisto.png)

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/pysakkipoisto.png)

Poistamalla pysäkki poistuu myös pysäkille pysähtyvät käyttäjän tallettamat linjat. 

Paluu takaisin päänäkymään tapahtuu painamalla 'Enter'.  

## Ohjelman sulkeminen 
### (5. Exit) 

Ohjelma sulkeutuu antamalla päänäkymässä parametriksi numeron viisi (5).  




