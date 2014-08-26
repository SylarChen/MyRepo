package com.hp.sylar;

import java.io.File;
import java.io.FileReader;

import com.thoughtworks.xstream.XStream;
import com.hp.sylar.Person;
import com.hp.sylar.PhoneNumber;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	obj_to_str();
    	str_to_obj();
    }
    
    public static String obj_to_str()
    {
        System.out.println( "\n=========== Object To Xml String ============" );
        
        XStream xstream = new XStream();     
     	//xml preset "person" instead of "com.hp.sylar.Person",  Field can alias too when use xstream.aliasField
        xstream.alias("person", Person.class);          
        //age element use default 0
        //cellphone use null (not display) , and if change type of age from int to Integer,it won't display;
        //if don't initialize reference type all give them null , they won't display
        Person joe = new Person("Joe", "Walnes");
        joe.setPhone(new PhoneNumber(123, "1234-456"));
        joe.setFax(new PhoneNumber(123, "9999-999"));     
        String xml = xstream.toXML(joe);
        System.out.println( xml );
        
        System.out.println( "=============================================" );
        return xml;
    }
    
    public static void str_to_obj()
    {
    	System.out.println( "\n=========== Xml String To Object ============" );
    	
    	File file = new File("./resource/dependencies.xml");
    	char[] input = new char[(int) file.length()];
    	try {
			FileReader fr = new FileReader(file);
			fr.read(input);		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	 XStream xstream = new XStream();  
    	 // 1.  every element of dependency must be declared in Dependency.class
    	 // 2.  must set alias name, otherwise com.hp.sylar.dependencyManagement won't match xml element
    	 // 3.  notcie dependencies in DependencyManagement.class, it's an element name
    	 xstream.alias("dependencyManagement", DependencyManagement.class);   
    	 xstream.alias("dependency", Dependency.class);      	 
    	 DependencyManagement xmlObject = (DependencyManagement) xstream.fromXML(new String(input));
    	 
    	 for(Dependency d : xmlObject.dependencies)
    	 {
    		 System.out.println(d.groupId + d.artifactId + d.version + "--------" + d.scope);
    	 }
    	 
    	 System.out.println( "=============================================" );
    }
}
