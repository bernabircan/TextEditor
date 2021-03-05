/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ege;

import java.util.ListIterator;

/**
 *
 * @author 90554
 */
public class YenileVisitor implements Visitor{

    //textarea daki yazıyı kelimeler listesindeki kelimeler ile yeniler
    @Override
    public void visit(MetinManager aThis) { 
         String toplamYazi = "";
        //iterator ile dolaşma yöntemi
        ListIterator<Yazi> iterator = aThis.kelimeler.listIterator();
        while (iterator.hasNext()) { 
            String gelen = iterator.next().GetData();
            toplamYazi += gelen;
        }
        aThis.textArea.setText(toplamYazi);
    }
        
}

     
   
    

