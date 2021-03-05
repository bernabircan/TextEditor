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
public class Kelime extends Yazi{
    
    private String data;
    
    public Kelime(String kelime){
        this.data = kelime;
    }
    
    @Override
    public String GetData(){
        return data;
    }
    
    @Override
    public void SetData(String yeniKelime){
        data = yeniKelime;
    }
    
    public String toString(){
         return data;
     }
}
