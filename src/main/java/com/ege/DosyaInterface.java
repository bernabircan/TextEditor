/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ege;

import javafx.stage.Stage;

/**
 *
 * @author EmreŞahin
 */
public interface DosyaInterface {
    public void DosyaOlustur(String dosyaAdi, String path);
    public void DosyaKaydet(String yazi, Stage stage);
    public void DosyaAc(Stage stage);
    public String DosyaOku();
    public void DosyaKapat();
}
