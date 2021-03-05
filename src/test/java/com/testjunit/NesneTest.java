/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testjunit;

import com.ege.Kelime;
import com.ege.MetinManager;
import com.ege.Yazi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author EmreŞahin
 */
public class NesneTest {
    
    public NesneTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws IOException {
        System.out.println("Cleaning the screen...");
        if (System.getProperty("os.name").startsWith("Window"))
            Runtime.getRuntime().exec("cls");
        else
            try {
                Runtime.getRuntime().exec("clear");
            } catch (IOException ex) {
            }
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
        Runtime r=Runtime.getRuntime();
        System.out.println("Collecting the garbage....");
        r.gc();
    }
    
    
    
    @org.junit.jupiter.api.Test
    public void testKelimeDegistir(){
        List<Yazi> kelimeler = new ArrayList<>();
        assertNotNull(kelimeler);
        Kelime yazi = new Kelime("hello");
        assertNotNull(yazi);
        kelimeler.add(yazi);
        
        MetinManager metinManager = MetinManager.getInstance();
        assertNotNull(metinManager);
        metinManager.kelimeler = kelimeler;
        
        Kelime yeniKelime = new Kelime("merhaba");
        assertNotNull(yeniKelime);
        metinManager.kelimeDegistir(yazi,yeniKelime );
        Yazi gelenYazi = metinManager.kelimeler.get(0);
        assertNotNull(gelenYazi);
        Kelime gelenKelime = (Kelime)gelenYazi;//downcasting
        assertNotNull(gelenKelime);
        String gelenString = gelenKelime.GetData();
        assertNotNull(gelenString);
        System.out.println("Sonuc: " + gelenString);
    }

}
