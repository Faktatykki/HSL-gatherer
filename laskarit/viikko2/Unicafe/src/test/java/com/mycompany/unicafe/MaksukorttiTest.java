package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void lataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(10);
        assertEquals("saldo: 0.20", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikeinKunTarpeeksi() {
        assertEquals(kortti.otaRahaa(5), true);
        assertEquals(kortti.saldo(), 5);
    }
    
    @Test
    public void saldoVaheneeOikeinKunEiTarpeeksi() {
        assertEquals(kortti.otaRahaa(7000), false);
        assertEquals(kortti.saldo(), 10);
    }
}
