
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void kassapaatteenAlkuasetelmaOikein() {
        assertEquals(kassa.kassassaRahaa(), 1000);
        assertEquals(kassa.maukkaitaLounaitaMyyty(), 0);
        assertEquals(kassa.edullisiaLounaitaMyyty(), 0);
    }
    
    @Test
    public void maksukortinAlkuasetelmaOikein() {
        assertEquals(kortti.saldo(), 1000);
    }
    
    @Test
    public void edullisetLounaatToimiiKunRahaaTarpeeksi() {
        assertEquals(kassa.syoEdullisesti(250), 10);
        assertEquals(kassa.kassassaRahaa(), 1240);
        assertEquals(kassa.edullisiaLounaitaMyyty(), 1);
    }
    
    @Test
    public void maukkaatLounaatToimiiKunRahaaTarpeeksi() {
        assertEquals(kassa.syoMaukkaasti(420), 20);
        assertEquals(kassa.kassassaRahaa(), 1400);
        assertEquals(kassa.maukkaitaLounaitaMyyty(), 1);
    }
    
    @Test
    public void riittamatonRahamaaraKateisella() {
        assertEquals(kassa.syoEdullisesti(230), 230);
        assertEquals(kassa.kassassaRahaa(), 1000);
        assertEquals(kassa.edullisiaLounaitaMyyty(), 0);
        
        assertEquals(kassa.syoMaukkaasti(399), 399);
        assertEquals(kassa.kassassaRahaa(), 1000);
        assertEquals(kassa.maukkaitaLounaitaMyyty(), 0);
    }
    
    @Test
    public void edullinenLounasKorttiostona() {
        assertEquals(kassa.syoEdullisesti(kortti), true);
        assertEquals(kassa.edullisiaLounaitaMyyty(), 1);
        assertEquals(kassa.kassassaRahaa(), 1000);
    }
    
    @Test
    public void maukasLounasKorttiostona() {
        assertEquals(kassa.syoMaukkaasti(kortti), true);
        assertEquals(kassa.maukkaitaLounaitaMyyty(), 1);
        assertEquals(kassa.kassassaRahaa(), 1000);
    }
    
    @Test
    public void kortinRahamaaraEiMuutuKunKortillaEiTarpeeksiRahaa() {
        kortti.otaRahaa(800);
        
        assertEquals(kortti.saldo(), 200);
        assertEquals(kassa.syoEdullisesti(kortti), false);
        assertEquals(kassa.edullisiaLounaitaMyyty(), 0);
        assertEquals(kassa.kassassaRahaa(), 1000);
        
        assertEquals(kortti.saldo(), 200);
        assertEquals(kassa.syoMaukkaasti(kortti), false);
        assertEquals(kassa.maukkaitaLounaitaMyyty(), 0);
        assertEquals(kassa.kassassaRahaa(), 1000);
        
        assertEquals(kortti.saldo(), 200);
    }
    
    @Test
    public void kortilleRahaaLadattaessaKortinSaldoMuuttuuJaKassassaOlevaRahamaaraKasvaaLadatullaSummalla() {
        kassa.lataaRahaaKortille(kortti, 1000);
        
        assertEquals(kortti.saldo(), 2000);
        assertEquals(kassa.kassassaRahaa(), 2000);
    }
    
    @Test
    public void kortilleRahaaLiianVahan() {
        kassa.lataaRahaaKortille(kortti, -10);
        
        assertEquals(kortti.saldo(), 1000);
        assertEquals(kassa.kassassaRahaa(), 1000);
    }
}
