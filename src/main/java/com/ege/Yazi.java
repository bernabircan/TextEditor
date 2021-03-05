/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ege;

/**
 *
 * @author EmreŞahin
 */
public abstract class Yazi {
    //kelimeler listesinde tutulması icin olsuturulmus bir ust sinif
    
    //alt siniflari bu metodu override ediyorlar
    public String GetData(){
        return "Hata!";
    }
    
    //alt siniflari bu metodu override ediyorlar
    public void SetData(String yeniKelime){
    }
}
