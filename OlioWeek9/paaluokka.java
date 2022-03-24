package com.example.viikko9;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class paaluokka{

    private static paaluokka pl = null;

    private paaluokka() {

    }

    public static paaluokka getInstance() {
        if (pl == null) {
            pl = new paaluokka();
        }
        return pl;
    }

    public void readXML (Spinner spinner, CharSequence[] fillist) {
        Teatterit teatterit = Teatterit.getInstance();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                System.out.println("Element is this: " + node.getNodeName());

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.print("ID: ");
                    System.out.println(element.getElementsByTagName("ID").item(0).getTextContent());
                    System.out.print("Name: ");
                    System.out.println(element.getElementsByTagName("Name").item(0).getTextContent());

                    teatterit.lisaaTeatteri(element.getElementsByTagName("Name").item(0).getTextContent(),element.getElementsByTagName("ID").item(0).getTextContent());
                    fillist[i] = element.getElementsByTagName("Name").item(0).getTextContent();
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            System.out.println("####DONE####");
        }

    }

    public void readXMLteatteri (String name, TextView textViewElokuvat, String start, String end, String date) {
        Teatterit teatterit = Teatterit.getInstance();
        String id = teatterit.getID(name);
        //String pvm = "23.03.2022";
        textViewElokuvat.setText("");
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/Schedule/?area=" + id + "&dt=" + date;
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getDocumentElement().getElementsByTagName("Show");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                System.out.println("Element is this: " + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.print("Title: ");
                    System.out.println(element.getElementsByTagName("Title").item(0).getTextContent());
                    String title = element.getElementsByTagName("Title").item(0).getTextContent();
                    String showStart = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                    showStart = showStart.split("T")[1];
                    String startSplit = showStart.split(":")[0];
                    String startSplit1 = start.split(":")[0];
                    String endSplit = end.split(":")[0];
                    if (start.equals("") && end.equals("")) {
                        textViewElokuvat.setText(textViewElokuvat.getText().toString()+ showStart + " " + title + "\n");
                    } else if ((Integer.parseInt(startSplit1) <= Integer.parseInt(startSplit)) && (Integer.parseInt(endSplit) >= Integer.parseInt(startSplit))){
                        textViewElokuvat.setText(textViewElokuvat.getText().toString()+ showStart + " " + title + "\n");
                    }
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            System.out.println("####DONE####");
        }
    }
}
