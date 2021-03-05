/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ege;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

class GeriAl {

    public int boyut = 0;
    public int baslangic = 0;
}

public class FXMLDocumentController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MetinManager.getInstance().textArea = textArea;
        KeyListener();
    }
    
    public void KeyListener(){
        //herhangi bir tuşa basıldığında cagrılır
        textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                //ignore edilecek tuslar
                if(ke.getCode() == KeyCode.LEFT || ke.getCode() == KeyCode.RIGHT || 
                        ke.getCode() == KeyCode.UP || ke.getCode() == KeyCode.DOWN ||
                        ke.getCode()== KeyCode.CONTROL){
                    return;
                }
                
                //textarea ya değişiklik olmadan önce command in içine eski text i atariz
                Command cmd = new KeyPressedCommand(textArea.getText());
                //command i stack e at
                MetinManager.getInstance().doCommand(cmd);
            }
        });
    }

    @FXML
    private BorderPane borderPane;

    @FXML
    public TextArea textArea;

    @FXML
    private MenuItem yeniDosyaOlusturButon;

    @FXML
    private MenuItem dosyaAcButon;

    @FXML
    private MenuItem dosyaKaydetButon;

    @FXML
    private MenuItem dosyaKapatButon;

    @FXML
    private MenuItem hataKontrolEtButon;

    @FXML
    private MenuItem kelimeBulButon;

    @FXML
    private MenuItem kelimeDegistirButon;

    @FXML
    private MenuItem geriAlButon;

    @FXML
    void DosyaAc(ActionEvent event) {
        MetinManager metinManager = MetinManager.getInstance();
        Stage stage = (Stage) borderPane.getScene().getWindow();
        metinManager.DosyaAc(stage);
        textArea.setText(metinManager.DosyaOku());
        if (metinManager.seciliDosya != null) {
            stage.setTitle(metinManager.seciliDosya.getName() + " - Notepad");
        }
    }

    @FXML
    void DosyaKapat(ActionEvent event) {
        MetinManager metinManager = MetinManager.getInstance();
        //dosyayı kapat
        metinManager.DosyaKapat();
        //yazı bölgesini temizle
        textArea.setText("");
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setTitle("Notepad");
    }

    @FXML
    void DosyaKaydet(ActionEvent event) {
        MetinManager metinManager = MetinManager.getInstance();
        Stage stage = (Stage) borderPane.getScene().getWindow();
        metinManager.DosyaKaydet(textArea.getText(), stage);
        if (metinManager.seciliDosya != null) {
            stage.setTitle(metinManager.seciliDosya.getName() + " - Notepad");
        }
    }

    @FXML
    void GeriAl(ActionEvent event) {
        //harf harf geri alır
        MetinManager.getInstance().undoCommand();
        
        
    }

    

    @FXML
    void KelimeBul(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("KelimeAra.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 431, 66);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Arama Yap");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Hata! Yeni pencere oluşturulamadı");
        }
    }

    @FXML
    void KelimeDegistir(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("KelimeDegistir.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 431, 100);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Kelime Değiştir");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Hata! Yeni pencere oluşturulamadı");
        }
    }

    @FXML
    void YeniDosyaOlustur(ActionEvent event) {
        //textArea yi temizler
        textArea.setText("");
        Stage stage = (Stage) borderPane.getScene().getWindow();
        //basligi Notepad yapiyoruz
        stage.setTitle("Notepad");
        //singleton ornegini aldik
        MetinManager metinManager = MetinManager.getInstance();
        //dosyayi kapat
        metinManager.DosyaKapat();
    }
    
    @FXML
    void IkiliKontrol(ActionEvent event) {
        MetinManager.getInstance().HataKontrolEt(false);
       
    }
    
    @FXML
    void UcluKontrol(ActionEvent event) {
        MetinManager.getInstance().HataKontrolEt(true);
       
    }

}
