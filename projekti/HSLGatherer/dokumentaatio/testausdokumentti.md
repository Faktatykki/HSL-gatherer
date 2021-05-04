# Testausdokumentti

Ohjelmaa on testattu automaattisin yksikkö- ja integraatiotestein JUnitilla. Manuaalista testausta on tehty etenkin käyttöliittymän kohdalla. 

## Yksikkö- ja integraatiotestaus 

### Luokat 

[*Stop*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/classes/Stop.java)- ja [*Trip*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/classes/Trip.java)-luokkaa on kokeiltu omin testein, vaikka luokkien toiminnallisuus on olennainen osa integraatio- ja yksikkötestauksen toimivuutta. 
Luokkien *equals*, *hashCode* ja *compareTo*-metodeihin on kiinnitetty erityisesti huomiota, eikä niinkään yksiselitteisiin gettereihin. 

[*Stop-luokan testit*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/test/java/ClassTests/StopTest.java) 
[*Trip-luokan testit*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/test/java/ClassTests/TripTest.java)

## Service 

[*Service*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/services/Service.java)-luokan myötä tapahtuu suurimmaksi osaksi integraatiotestaus, sillä luokka liittyy epäsuorasti melkein kaikkeen toiminnallisuuteen, mitä ohjelmassa tapahtuu. 
[*ServiceTest*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/test/java/ServiceTests/ServiceTest.java) kautta testataan miltei kaikkea ohjelmassa tapahtuvaa toiminnallisuutta. Testiluokassa käydään läpi hyvin yleistä toiminnallisuutta ilman sen kummempia
rajatapauksia, sillä tarkempiin rajatapauksiin keskitytään enemmän yksikkötesteissä. 

## Sovelluslogiikka 

Sovelluslogiikan testaamiseen liittyy olennaisesti integraatiotestausta, mutta pääpainona on rajatapaukset ja luokkien omien 
"erikoisuuksien" testaus. Esimerkiksi [*Logic*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/logic/Logic.java)-luokan metodeissa tapahtuva järjestäminen ja tietorakenteiden muuttaminen, 
[*APIhandler*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/logic/APIhandle.java)-luokassa tapahtuva "huonojen" hakuparametrien syöttäminen ja [*DBConnector*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/main/java/logic/DBConnector.java)-luokassa ResultSet-tulosten 
oikeellisuus.

[*Logic-luokan testit*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/test/java/LogicTests/LogicTest.java)
[*APIhandler-luokan testit*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/test/java/LogicTests/APIhandleTest.java)
[*DBConnector-luokan testit*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/src/test/java/LogicTests/DBConnectorTest.java)

*DBConnector*-luokan testejä varten luotiin väliaikainen testattava tietokanta, joka testien jälkeen poistuu. 

## Kattavuus 

Käyttöliittymäkerrosta ei oteta huomioon testauksessa. Täten rivikattavuus on 95% ja haaraumakattavuus 86%. 

![alt text](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kuvat/jacocoreport.png) 

Rivikattavuudesta jää 6% paljolti siksi, että luokkien yksinkertaisimpia gettereitä ei testattu. Haaraumakattavuuden alhaisuus johtuu paljolti siitä, että 
testaamatta jäivät tilanteet, joissa tietokantaa ei ole olemassa. 

# Järjestelmätestaus 

Järjestelmätestaus on suoritettu manuaalisesti. 

## Asennus 

Sovelluksesta on haettu viimeisin *release*. [*README.md*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/README.md):tä ja [*käyttöohjetta*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/kayttoohje.md) seuraten 
on käyty läpi ohjeet ohjelman asentamiseksi, testaamiseksi ja suorittamiseksi. 

## Toiminnallisuudet 

Edellä mainittuja dokumentteja, sekä [*vaatimusmäärittelyä*](https://github.com/Faktatykki/ot-harjoitustyo/blob/master/projekti/HSLGatherer/dokumentaatio/vaatimusmaarittely.md) seuraten on käyty läpi 
kaikki ohjelman tarjoamat toiminnallisuudet ja yritetty etsiä ohjelmasta heikkouksia käyden läpi manuaalisesti rajatapauksia (esim. syötetty hakuparametriksi erikoismerkkejä ja poistettu tietokanta kesken ohjelman suorituksen). 
