package com.hp.sylar;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * Hello world!
 *
 */
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
    	List<String> DM_List = getDependencyManagement(urls);
    	List<DependencyManagement> DM_Obj_List = getDependencyManagementObject(DM_List);
    	for(DependencyManagement a : DM_Obj_List)
    	{
    		System.out.println(a);
    	}
    	
//    	File file = new File("./resource/dependencies.xml");
//    	char[] input = new char[(int) file.length()];
//    	try {
//			FileReader fr = new FileReader(file);
//			fr.read(input);		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	
//    	 XStream xstream = new XStream();  
//
//    	 xstream.processAnnotations(DependencyManagement.class);
//    	 xstream.processAnnotations(Dependency.class);
//    	 
//    	 DependencyManagement xmlObject = (DependencyManagement) xstream.fromXML(new String(input));
//    	 
//    	 for(Dependency d : xmlObject.dependencies)
//    	 {
//    		 System.out.println(d.groupId + d.artifactId + d.version + "--------" + d.scope);
//    	 }
//    	 
//    	 System.out.println( "=============================================" );
    }
    
    
    private static List<String> getDependencyManagement(String[] urls)
    {
    	List<String> list = new ArrayList<String>();
    	for(String url : urls)
    	{
        	SAXReader reader = new SAXReader();
        	Document document = null;
            try {
    			document = reader.read(url);
    		} catch (DocumentException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            Element root = document.getRootElement();
            Element directChildElement = root.element("dependencies");          
            list.add("<dependencyManagement>"+directChildElement.asXML()+"</dependencyManagement>");
    	}  	
    	return list;
    }

    private static List<DependencyManagement> getDependencyManagementObject(List<String> DM_List)
    {
    	List<DependencyManagement> list = new ArrayList<DependencyManagement>();
    	XStream xstream = new XStream();
    	xstream.processAnnotations(DependencyManagement.class);
    	xstream.processAnnotations(Dependency.class);
    	for(String dm : DM_List)
    	{
    		DependencyManagement dm_Object = (DependencyManagement) xstream.fromXML(dm);       
            list.add(dm_Object);
    	}
    	return list;
    }
}
