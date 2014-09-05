package com.hp.sylar;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
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
    	printDocument(document);
    	List list = document.selectNodes( "dependencyManagement" );
//    	for(Element e : list)
//    	{
//    		System.out.println(e.asXML());
//    	}
    	System.out.println("e.asXML()");
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
}
