package com.ege;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ikiHarfDegisimi implements Strategy {

    @Override
    public List<String> executeHataBulma(List<Yazi> metin, List<Kelime> sozluk) {
        List<String> hataliKelimeler = new ArrayList();
        
        //iterator ları tanımladık
        ListIterator<Yazi> metinIterator = metin.listIterator();
        
        //metindeki her kelimeyi sozlukteki ile karsılastır
        while (metinIterator.hasNext()) {
            Yazi metinKelimesiYazi = metinIterator.next();
            String metinKelimesi = metinKelimesiYazi.GetData();
            metinKelimesi = metinKelimesi.toLowerCase();
            
            boolean hataliKelime = true;
            //iterator ları tanımladık
            ListIterator<Kelime> sozlukIterator = sozluk.listIterator();
            while (sozlukIterator.hasNext()) {
                Kelime sozlukKelimesiKelime = sozlukIterator.next();
                String sozlukKelimesi = sozlukKelimesiKelime.GetData();

                if (metinKelimesi.equals(sozlukKelimesi)) { //kelimse sözlükteki bir kelimeyse okey veriyo
                    hataliKelime = false;
                    break;
                }
                
                else if (metinKelimesi.length() == sozlukKelimesi.length()) { //sözlükte kontrol edilen kelimeyle aynı değil ise ama uzunlukları eşitse kontrole giriyor
                    
                    int hataliHarfSayisi = 0;
                    char hataliHarfler[] = new char[99];
                    char dogruHarfler[] = new char[99];
                    
                    for (int i = 0; i < metinKelimesi.length(); i++) { //tek tek harfleri kontrol ediyor
                        
                        if (metinKelimesi.charAt(i) != sozlukKelimesi.charAt(i)) { //farklı harf tespit edildiğinde farklı harfleri tut
                            hataliHarfler[hataliHarfSayisi] = metinKelimesi.charAt(i);
                            dogruHarfler[hataliHarfSayisi] = sozlukKelimesi.charAt(i);
                            hataliHarfSayisi++;
                        }
                    }
                    
                    if (hataliHarfSayisi == 2) { // sadece 2 harf farklıysa
                        
                        if (hataliHarfler[0] == dogruHarfler[1] && hataliHarfler[1] == dogruHarfler[0]) { //ve o 2 harf sadece kendi içinde yer değiştirmişse
                            hataliKelime = false;
                            //metin.set(metin.indexOf(metinKelimesi), sozlukKelimesi);
                            //kelimeyi sözlükteki kelimeyle değiştir
                            Kelime kelimee = (Kelime) metin.get(metin.indexOf(metinKelimesiYazi));
                            System.out.println("Eski Hatalı Kelime: " + kelimee.GetData() + ", Yenisi: " + sozlukKelimesi);
                            kelimee.SetData(sozlukKelimesi);
                            break;
                        }
                    }
                }
            }
            if (hataliKelime) //kelime hatalıysa ekle
            {
                //gelen eleman kelime degilse bi sonraki elemana bak
                if (metinKelimesiYazi instanceof Kelime) {
                    hataliKelimeler.add(metinKelimesi);
                }
            }
        }
        return hataliKelimeler;
    }
    
}
