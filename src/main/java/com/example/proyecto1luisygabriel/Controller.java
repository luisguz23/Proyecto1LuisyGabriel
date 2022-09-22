package com.example.proyecto1luisygabriel;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Integer.parseInt;

public class Controller implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playbutton, pauseButton,resetButton,previousButton,nextButton;

    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;

    private Media media;
    private MediaPlayer mediaPlayer;

    ///sirve para utilizar una direccion en la compu
    private File directory;
    private File[] files;

    ///private Biblioteca;


    private ArrayList<File> songs;
    private int songNumber;
    ///private int[] speeds = {25,50,75,100,125,150,175,200};

    private Timer timer;
    private TimerTask task;
    private boolean running;
    private Listas prueba;

    private com.example.proyecto1luisygabriel.File cancion;


    public void XReader(int idv){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        try {
            Document documento = builder.parse(new java.io.File("musica.xml"));
            NodeList listaCanciones = documento.getElementsByTagName("cancion");


            int i;
            for (i = 0; 1 < listaCanciones.getLength(); i++) ;
            Node nodo = (Node) listaCanciones.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) nodo;
                NodeList hijos = e.getChildNodes();
                for (int j = 0; j < hijos.getLength(); j = j+7) {
                    Node hijo = hijos.item(j);
                    if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                        int idconvertido = parseInt(hijo.getTextContent());
                        Element eHijo = (Element) hijo;
                        System.out.println( hijo.getNodeName() + " : "  + hijo.getTextContent());
                        if(idconvertido == idv ){
                            for(int a = j+1; a<j+2; a++ ){
                                Node artista = hijos.item(a);
                                Node genero = hijos.item(a+1);
                                Node album = hijos.item(a+2);
                                Node year = hijos.item(a+3);
                                Node nombre = hijos.item(a+4);
                                Node url = hijos.item(a+5);
                                String vartista = (artista.getTextContent());
                                String vgenero = (genero.getTextContent());
                                String valbum = (album.getTextContent());
                                int vyear = parseInt(year.getTextContent());
                                String vnombre = (nombre.getTextContent());
                                String vurl = (url.getTextContent());

                                cancion = new com.example.proyecto1luisygabriel.File(vartista,vgenero,valbum,vyear,vnombre,vurl);
                                System.out.println(url.getNodeName() + " : " + vurl);
                            }

                        }
                    }
                }
            }

        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void Listar (){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        try {
            Document documento = builder.parse(new File("musica.xml"));
            NodeList listaCanciones = documento.getElementsByTagName("cancion");


            int i;
            for (i = 0; 1 < listaCanciones.getLength(); i++) ;
            Node nodo = (Node) listaCanciones.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) nodo;
                NodeList hijos = e.getChildNodes();
                for (int j = 0; j < hijos.getLength(); j = j+7) {
                    Node hijo = hijos.item(j);
                    if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                        int idconvertido = parseInt(hijo.getTextContent());
                        prueba.agregarAlFinal(idconvertido);
                        System.out.println( hijo.getNodeName() + " : "  + hijo.getTextContent());

                    }
                }
            }

        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        songs = new ArrayList<>();
        prueba = new Listas();
        directory = new File("Music");
        files = directory.listFiles();
        Listar();


        if(files != null){ //sirve para anadir canciones a la playlist
            for (File file:files){
                songs.add(file);
                //prueba.agregarAlInicio();//hacer metodo de esta vara
                System.out.println(file);
            }
        }

        //prueba.getValor().getURL()
        media = new Media(songs.get(songNumber).toURI().toString()); //continuar desde aca
        System.out.println(songs);
        mediaPlayer = new MediaPlayer(media);

        songLabel.setText(songs.get(songNumber).getName());

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue()*0.01);
            }
        });
    }
    public void playMedia(){
        beginTimer();
        mediaPlayer.play();
        prueba.listar();
        XReader(5);
    }
    public void pauseMedia(){
        cancelTimer();
        mediaPlayer.pause();
        prueba.listar();
    }
    public void resetMedia(){
        songProgressBar.setProgress(0);
        mediaPlayer.seek(Duration.seconds(0));
    }
    public void previousMedia(){
        if(songNumber>0){
            songNumber--;
            mediaPlayer.stop();
            if(running){
                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());
        }
        else{
            songNumber=songs.size()-1;
            mediaPlayer.stop();
            if(running){
                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());

            playMedia();
        }
    }
    public void nextMedia(){
        if(songNumber<songs.size()-1){
            songNumber++;
            mediaPlayer.stop();
            if(running){
                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());
        }
        else{
            songNumber=0;
            mediaPlayer.stop();
            if(running){
                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());

            playMedia();
        }
    }
    ///public void changeSpeed(ActionEvent event){

    ///}
    public void beginTimer(){
        timer = new Timer();
        task = new TimerTask(){

            @Override
            public void run() {
                boolean runnig = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                System.out.println(current/end);
                songProgressBar.setProgress(current/end);
                if(current/end == 1){
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task,1000,1000);
    }
    public void cancelTimer(){
        running = false;
        timer.cancel();
    }
}