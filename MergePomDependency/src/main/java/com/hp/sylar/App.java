package com.hp.sylar;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

public class App 
{
    public static void main( String[] args )
    {
    	String[] urls = {
    			"./resource/pom1.xml",
    			"./resource/pom2.xml",
    			"./resource/pom3.xml",
    			"./resource/pom4.xml"
    	};
    	String mergedUrl = "./result/merged.xml";
    	String notMergedUrl = "./result/mergeLeft.xml";
    	
    	PomManager.start(urls, mergedUrl, notMergedUrl);
    }
    
    
   
}
