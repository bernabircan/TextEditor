/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ege;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Stack;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MetinManager implements DosyaInterface, Element {

    public static MetinManager instance; //singleton
    public File seciliDosya;//o an ki acilan dosyayi tutar
    public List<Yazi> kelimeler = new ArrayList<>();
    public List<Kelime> sozluk = new ArrayList<>();
    public Stack keyStrokes = new Stack();//command leri tutar
    @FXML
    public TextArea textArea;//textarea daki metni tutar

    //singleton almak icin
    public static MetinManager getInstance() {
        if (instance == null) {
            instance = new MetinManager();
        }
        return instance;
    }

    //command i calıştırır
    public void doCommand(Command command){
        if(command != null){
            keyStrokes.push(command);
            command.execute();
        }
    }
    
    //gerialma işlevselliğini sağlar
    public void undoCommand(){
        if(!keyStrokes.empty()){
            Object top = keyStrokes.peek(); //top elemente bakarız
            if(top instanceof UndoableCommand){
                keyStrokes.pop();
                UndoableCommand undoableCommand = (UndoableCommand) top;
                undoableCommand.undo();//geri alırız
            }
        }
    }
    
    @Override
    public void accept(Visitor v) { //sözlük okumak ve yenile metodu için kullanılır.
        v.visit(this);
    }
    
    //algoritmaSecimi: true ise üclü transposition seçer, false ise ikili transposition
    void HataKontrolEt(boolean algoritmaSecimi) {
        MetinManager metinManager = MetinManager.getInstance();

        metinManager.MetinOku(textArea.getText());
        try{
            SozlukOkuVisitor sozluk = new SozlukOkuVisitor(); //visitor oluştulur
            metinManager.accept(sozluk);//sozluk okuma yapılır.
        }catch(Exception e){
            System.out.println("Sozluk dosyasi bulunamadi");
        }
        
        
        //gelen isteğe göre adapter patterni bağlıyoruz
        List<String> hataliKelimeler;
        Strategy strategy; //uygulayacağımız strateji
        if(algoritmaSecimi == false){ //2'li yer değistirme hatası bulur
            strategy = new ikiHarfDegisimi();
        }else{ //3'lu yer değistirme hatası bulur
            strategy = new ucHarfDegisimi();
        }
        StrategyChooser strategyChooser = new StrategyChooser(strategy);
        //seçili stratejiyi uygular
        hataliKelimeler = strategyChooser.executeStrategy(metinManager.kelimeler, metinManager.sozluk);
       
        String toplamYazi = "";
        //iterator ile dolaşma yöntemi
        ListIterator<Yazi> iterator = metinManager.kelimeler.listIterator();
        while (iterator.hasNext()) { 
            String gelen = iterator.next().GetData();
            toplamYazi += gelen;
        }
        
        //eski dolaşma yöntemi
        /*
        for (Yazi kelim : metinManager.kelimeler) {
            String gelen = kelim.GetData();
            toplamYazi += gelen;
        }
        */
        
        System.out.println("**********");
        ListIterator<String> hatalilar_iterator = hataliKelimeler.listIterator();
        while (iterator.hasNext()) { 
            System.out.println("Hatalı Kelime: " + hatalilar_iterator.next());
        }

        textArea.setText(toplamYazi);
        
    }

    //kelime arar, bulursa kelimeyi dondurur
    public Yazi kelimeAra(String aranankelime) {
        //iterator ile dolaşma yöntemi
        ListIterator<Yazi> iterator = kelimeler.listIterator();
        while (iterator.hasNext()) { 
            Yazi gelen = iterator.next();
            if (aranankelime.equals(gelen.GetData())) {
                return gelen;
            }
        }
        
        //eski dolaşma yöntemi
        /*
        for (int x = 0; x < kelimeler.size(); x++) {
            if (aranankelime.equals(kelimeler.get(x).GetData())) {
                return kelimeler.get(x);
            }
        }
        */
        return null;
    }

    //kelime degisitmrek icin kullanılır
    public void kelimeDegistir(Kelime eski, Kelime yeni) {
        String eskikelime = eski.GetData();
        String yeniKelime = yeni.GetData();
        //aranan kelimeyi listeden bul ve yeni eklime nin data sı ile değiştir
        //iterator ile dolaşma yöntemi
        ListIterator<Yazi> iterator = kelimeler.listIterator();
        while (iterator.hasNext()) { 
            Yazi gelen = iterator.next();
            if (eskikelime.equals(gelen.GetData())) {
                Kelime kelime = (Kelime) gelen;
                kelime.SetData(yeniKelime);
            }
        }
        
        //eski dolaşma yöntemi
        /*
        for (int x = 0; x < kelimeler.size(); x++) {
            if (eskikelime.equals(kelimeler.get(x))) {
                Kelime kelime = (Kelime) kelimeler.get(x);
                kelime.SetData(yeniKelime);
            }
        }
        */
    }

    //dosyaAdi ile yeni txt olusturur
    @Override
    public void DosyaOlustur(String dosyaAdi, String path) {
        try {
            File myObj = new File(path + "\\" + dosyaAdi + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("Dosya Oluşturuldu: " + myObj.getName());
            } else {
                System.out.println("Dosya zaten mevcut!");
            }
        } catch (IOException e) {
            System.out.println("Hata: Dosya oluşturulamıyor.");
        }
    }

    //yazi degiskenini açılmış dosyaya yazar
    @Override
    public void DosyaKaydet(String yazi, Stage stage) {
        if (seciliDosya == null && stage != null) { //dosya seçili değilse yeni dosya oluşturmak için klasor seç
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Dosya Seç...");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
            //klasor seçme yerini proje konumundan başlatır
            File workingDirectory = new File(System.getProperty("user.dir"));
            fileChooser.setInitialDirectory(workingDirectory);
            //klasor seçme ekranını gösterir
            File selectedFile = fileChooser.showSaveDialog(stage);
            if (selectedFile != null) {
                seciliDosya = selectedFile;
            }
        }
        try {
            FileWriter myWriter = new FileWriter(seciliDosya);
            myWriter.write(yazi);
            myWriter.close();
        } catch (Exception e) {
            System.out.println("Hata: Dosya kaydedilemiyor.");
        }
    }

    //dosya seçmek için pencere açar
    @Override
    public void DosyaAc(Stage stage) {
        DosyaKapat();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Dosya Seç...");
        //dosya seçme yerini proje konumundan başlatır
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(workingDirectory);
        //seçilcek dosya türlerini sadece txt olarak filtreler
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
        //dosya seçme ekranını gösterir
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            seciliDosya = selectedFile;
        }
    }

    //açılmış dosyayı okur ve okudugunu geri döndürür
    @Override
    public String DosyaOku() {
        kelimeler = new ArrayList<>();
        String dosyaYazisi = "";
        try {
            Scanner myReader = new Scanner(seciliDosya);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                dosyaYazisi += data;
                dosyaYazisi += System.getProperty("line.separator");
            }

            String temp = "";
            String punctuations = ".,:;";
            char[] letterArray = dosyaYazisi.toCharArray();
            for (int i = 1; i < letterArray.length+1; i++) {//parsing işlemi yapılır
                if (i == letterArray.length) {
                    temp += letterArray[i - 1];
                    if(Character.isLetter(letterArray[i-1])){
                        kelimeler.add(new Kelime(temp));
                    }else{
                        kelimeler.add(new KelimeDegil(temp));
                    }
                    break;
                }
                if ((Character.isLetter(letterArray[i]) || letterArray[i] == '-')
                        && (Character.isLetter(letterArray[i - 1]) || letterArray[i - 1] == '-')) {
                    temp += letterArray[i - 1];
                    continue;
                } else if (!(Character.isLetter(letterArray[i]) || letterArray[i] == '-')
                        && (Character.isLetter(letterArray[i - 1]) || letterArray[i - 1] == '-')) {
                    temp += letterArray[i - 1];
                    kelimeler.add(new Kelime(temp));
                    temp = "";
                }

                if (Character.isDigit(letterArray[i])) {
                    temp += letterArray[i];
                } else if (punctuations.contains(String.valueOf(letterArray[i]))) {
                    temp += letterArray[i];
                } else if (Character.isWhitespace(letterArray[i])) {
                    temp += letterArray[i];
                } else {
                    kelimeler.add(new KelimeDegil(temp));
                    temp = "";
                }
            }

            myReader.close();
        } catch (Exception e) {
            System.out.println("Dosya Bulunamadı!");
        }
        return dosyaYazisi;
    }

    //metini okur ve listeye atar
    public void MetinOku(String dosyaYazisi) {
        kelimeler = new ArrayList<>();
        String temp = "";
        String punctuations = ".,:;";
        char[] letterArray = dosyaYazisi.toCharArray();
        for (int i = 1; i < letterArray.length + 1; i++) {
            if (i == letterArray.length) {
                temp += letterArray[i - 1];
                if(Character.isLetter(letterArray[i-1])){
                    kelimeler.add(new Kelime(temp));
                }else{
                    kelimeler.add(new KelimeDegil(temp));
                }
                break;
            }
            if ((Character.isLetter(letterArray[i]) || letterArray[i] == '-')
                    && (Character.isLetter(letterArray[i - 1]) || letterArray[i - 1] == '-')) {
                temp += letterArray[i - 1];

                continue;
            } else if (!(Character.isLetter(letterArray[i]) || letterArray[i] == '-')
                    && (Character.isLetter(letterArray[i - 1]) || letterArray[i - 1] == '-')) {
                temp += letterArray[i - 1];
                kelimeler.add(new Kelime(temp));
                temp = "";
            }

            if (Character.isDigit(letterArray[i])) {
                temp += letterArray[i];
            } else if (punctuations.contains(String.valueOf(letterArray[i]))) {
                temp += letterArray[i];
            } else if (Character.isWhitespace(letterArray[i])) {
                temp += letterArray[i];
            } else {
                kelimeler.add(new KelimeDegil(temp));
                temp = "";
            }
        }


    }

    //açılmış dosyayı null yaparak okunmasını engeller
    @Override
    public void DosyaKapat() {
        seciliDosya = null;
        kelimeler = new ArrayList<>();
    }

}
