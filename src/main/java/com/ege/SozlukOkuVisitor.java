/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ege;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author 90554
 */
public class SozlukOkuVisitor implements Visitor {

    //dosyadan gelen kelimeleri okuyup listeye ekler
    @Override
    public void visit(MetinManager aThis) {
        try {
            File file = new File("words.txt");
            Scanner input = new Scanner(file);
            while (input.hasNext()) {//sonraki satır mevcutsa
                String word = input.next();
                //kelime nesnesi oluştur
                Kelime kelime = new Kelime(word);
                //listeye ekle
                aThis.sozluk.add(kelime);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
   
    
}
