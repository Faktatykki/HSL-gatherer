# Käyttöohje

## Ohjelman käynnistäminen 

Ohjelman saa käynnistettyä komennolla 
 
```
mvn compile exec:java -Dexec.mainClass=main.Main
```

Komento käynnistää sovelluksen graafisen käyttöliittymän. 

## Päänäkymä 

Päänäkymässä näkyy kaikki sovelluksen tarjoamat toiminnot. Navigointi tapahtuu painamalla haluamaansa toimintoa. 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/mainMenu.png) 

## Pysäkin etsiminen ja tallentaminen
### (Add stop)
Näkymässä syötetään halutun pysäkin nimi ja painetaan 'Search'.

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/searchStop.png)

Sovellus näyttää hakutulokset. Pysäkin tallentaminen tapahtuu painamalla hakutulosten vierellä olevaa nappia 'Save'. 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/searchResult.png) 

Paluu takaisin päänäkymään tapahtuu painamalla 'Return'. 

## Linjan lisääminen
### (Add route) 
Kun käyttäjä on tallentanut vähintään yhden pysäkin, on mahdollista tallentaa pysäkkikohtaisia linjoja. Linjan tallentamiseen siirrytään painamalla haluamaansa pysäkkiä.

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/addRoute.png) 

Tämän jälkeen valitaan tallennettava linja painamalla linjan vierellä olevaa nappia 'Save'.

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/routeResult.png)

Paluu takaisin päänäkymään tapahtuu painamalla 'Return'. 

## Tallennettujen pysäkkien ja linjojen koonti ​
23
![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/searchStop.png)
24
​
25
Sovellus näyttää hakutulokset. Pysäkin tallentaminen tapahtuu painamalla hakutulosten vierellä olevaa nappia 'Save'. 
26
​
27
![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/searchResult.png) 
28
​
29
Paluu takaisin päänäkymään tapahtuu painamalla 'Return'. 
30
​
31
## Linjan lisääminen
32
### (Add route) 
33
Kun käyttäjä on tallentanut vähintään yhden pysäkin, on mahdollista tallentaa pysäkkikohtaisia linjoja. Linjan tallentamiseen siirrytään painamalla haluamaansa pysäkkiä.
34
​
35
![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/addRoute.png) 
36
​
37
Tämän jälkeen valitaan tallennettava linja painamalla linjan vierellä olevaa nappia 'Save'.
38
​
39
![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/routeResult.png)
40
​
41
Paluu takaisin päänäkymään tapahtuu painamalla 'Return'. 
42
​
43
## Tallennettujen pysäkkien ja linjojen koonti 
44
### (Show saved stops and routes)
45
​
46
Käyttäjä näkee kaikki tallentamansa pysäkit ja linjat. Linjoista näkee lähtöajan ja mahdollisen viivästyksen. Koonti näyttää kaikki tallennetut linjat pysäkkikohtaisesti. 
47
​
48
![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/savedRoutes.png)
49
​
50
## Tallennettujen pysäkkien ja linjojen poistaminen 
51
### (Delete stops or routes)
52
​
53
Käyttäjä voi poistaa jo tallennettuja pysäkkejä ja linjoja. Näkymässä käyttäjä valitsee, että haluaako poistaa pysäkkejä vai linjoja. 
54
​
55
![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/deleteMenu.png)
56
​
57
Poistaminen tapahtuu painamalla linjan tai pysäkin viereistä 'Delete'-nappia.
58
​
59
![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/deleteRoute.png)
60
​
61
![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/deleteStop.png)
62
​
63
Poistamalla pysäkin poistuu myös pysäkille pysähtyvät käyttäjän tallettamat linjat. 
64
​
65
Paluu takaisin päänäkymään tapahtuu painamalla 'Return'.  
66
​
67
## Ohjelman sulkeminen 
68
### (5. Exit) 
69
​
70
Ohjelma sulkeutuu painamalla päänäkymässä 'Exit'-nappia.
### (Show saved stops and routes)

Käyttäjä näkee kaikki tallentamansa pysäkit ja linjat. Linjoista näkee lähtöajan ja mahdollisen viivästyksen. Koonti näyttää kaikki tallennetut linjat pysäkkikohtaisesti. 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/savedRoutes.png)

## Tallennettujen pysäkkien ja linjojen poistaminen 
### (Delete stops or routes)

Käyttäjä voi poistaa jo tallennettuja pysäkkejä ja linjoja. Näkymässä käyttäjä valitsee, että haluaako poistaa pysäkkejä vai linjoja. 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/deleteMenu.png)

Poistaminen tapahtuu painamalla linjan tai pysäkin viereistä 'Delete'-nappia.

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/deleteRoute.png)

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/deleteStop.png)

Poistamalla pysäkin poistuu myös pysäkille pysähtyvät käyttäjän tallettamat linjat. 

Paluu takaisin päänäkymään tapahtuu painamalla 'Return'.  

## Ohjelman sulkeminen 
### (5. Exit) 

Ohjelma sulkeutuu painamalla päänäkymässä 'Exit'-nappia.
