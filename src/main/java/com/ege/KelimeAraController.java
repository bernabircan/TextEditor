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
public class KelimeAraController implements Initializable {

    @FXML
    private BorderPane hataAyiklamaBorder;

    @FXML
    private TextField aramaKutusu;

    @FXML
    private Button araButon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    static int aramaBaslangicIndex = 0;
    @FXML
    void AraButonTikla(ActionEvent event) throws IOException {
        //arama kutusunun icindeki yazi bos degilse
        if (aramaKutusu.getText() != null && !aramaKutusu.getText().isEmpty()) {
            //metin icinde arar
            int index = MetinManager.getInstance().textArea.getText().indexOf(aramaKutusu.getText(), aramaBaslangicIndex);
            if (index == -1) {
                //aranan kelime bulunamadı
                aramaBaslangicIndex = 0;
                System.out.println("Hata! Aranan Kelime yok...");
            } else {
                //kelime bulundu
                MetinManager.getInstance().textArea.selectRange(index, index + aramaKutusu.getLength());
                aramaBaslangicIndex = index + aramaKutusu.getLength();//aranacak indeksi arttir
            }
        }

    }

}
