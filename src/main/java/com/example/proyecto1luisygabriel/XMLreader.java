package com.example.proyecto1luisygabriel;

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

import static java.lang.Integer.parseInt;

public class XMLreader {
    public static void main (String[] args) {
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
                        Element eHijo = (Element) hijo;
                        System.out.println( hijo.getNodeName() + " : "  + hijo.getTextContent());
                        if(idconvertido == 1 ){
                            for(int a = j+1; a<j+7; a++ ){
                                Node cancion = hijos.item(a);
                                System.out.println(cancion.getNodeName() + " : "  + cancion.getTextContent());
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
}
