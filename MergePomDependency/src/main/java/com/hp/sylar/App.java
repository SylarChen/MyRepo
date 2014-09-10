package com.hp.sylar;

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
