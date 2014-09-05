package com.hp.sylar;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String url = "./resource/jar.xml";
    	Document document = getDocument(url);
    	//printDocument(document);
    	printDirectElement(document,"dependencyManagement");
    }
    
    //****************read xml file from url******************
    //SAXReader reader = new SAXReader();
    //Document document = reader.read(url);
    //the document represent the whole xml document, use asXML() to get the String
    public static Document getDocument(String url)
    {
    	SAXReader reader = new SAXReader();
    	Document document = null;
        try {
			document = reader.read(url);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return document;
    }
    
    //*********************print XML***********************
    //use document.asXML() to get xml String 
    public static String printDocument(Document document)
    {
    	System.out.println(document.asXML());
    	return document.asXML();
    }
    
    //***************print element under root*****************
    //root.element("dependencyManagement")
    //use this to get direct child element
    public static String printDirectElement(Document document, String childName)
    {
    	Element root = document.getRootElement();
    	Element directChildElement = root.element(childName); 	
    	System.out.println(directChildElement.asXML());
    	return directChildElement.asXML();
    }
}
