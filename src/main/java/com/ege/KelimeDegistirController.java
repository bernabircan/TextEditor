/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ege;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author EmreŞahin
 */
public class KelimeDegistirController implements Initializable {

      @FXML
    private BorderPane DegistirKelime;

    @FXML
    private TextField degistirilcekInput;

    @FXML
    private TextField neyleDegistirilcekInput;

    @FXML
    private Button degistirButon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void DegistirButon(ActionEvent event) {
        MetinManager metinManager = MetinManager.getInstance();//singleton ornegini alir
        metinManager.MetinOku(metinManager.textArea.getText());//guncel yazıyı listeye cevir
        Yazi arananKelime = metinManager.kelimeAra(degistirilcekInput.getText());//aranan kelimeyi bul
        System.out.println("aranan: " + degistirilcekInput.getText() + " degistirilcek: " + degistirilcekInput.getText());
        System.out.println("bulunan: " + arananKelime);
        if(arananKelime == null){
            return;
        }
        arananKelime.SetData(neyleDegistirilcekInput.getText());//degistir
        YenileVisitor yenile=new YenileVisitor(); //visitor oluşturulur.
        metinManager.accept(yenile);//text area yi degistirilmis listeden yazdir
    }

}
