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
    	DependencyManagement dependencyManagement= mergeDependencies(DM_Obj_List);
    
    	writeResult(dependencyManagement,"./result/merged.xml","./result/mergeLeft.xml");
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
    
    private static DependencyManagement mergeDependencies(List<DependencyManagement> DM_Obj_List)
    {	
    	DependencyManagement merged_DM = new DependencyManagement();
    	//do not merge dependency with classifier,type or exclusions
    	//do not merge dependency with no version
    	for(DependencyManagement dm : DM_Obj_List)
    	{
    		for(Dependency dependency : dm.dependencies)
    		{
    			if(dependency.version!=null && dependency.classifier==null && dependency.type==null && dependency.exclusions==null)
    			{
    				merged_DM.dependencies.add(dependency);
    			}
    			//can write to log
    			else
    			{
    				DependencyManagement.notValidList.add(dependency);
    				//System.out.println("log: "+dependency.groupId+"."+dependency.artifactId+"."+dependency.version+"."+dependency.scope);
    			}
    		}
    	}
    	System.out.println("\n");
    	Set<Dependency> d_set = new HashSet<Dependency>(merged_DM.dependencies);
    	merged_DM.dependencies = new ArrayList<Dependency>(d_set);
    	return merged_DM;
    }
    
    private static void writeResult(DependencyManagement dependencyManagement,String urlMerged,String urlLeft)
    {	
    	XStream xstream = new XStream();
    	xstream.processAnnotations(DependencyManagement.class);
    	xstream.processAnnotations(Dependency.class);
    	
    	List<Dependency> needToDeleteList = new ArrayList<Dependency>();
    	for(Dependency d : DependencyManagement.notValidList)
    	{
    		needToDeleteList.add(d);
    	}
    	for(Dependency d : needToDeleteList)
    	{
    		dependencyManagement.dependencies.remove(d);
    		//System.out.println("log: "+d.groupId+"."+d.artifactId+"."+d.version+"."+d.scope);
    	}
    		
    	String result = xstream.toXML(dependencyManagement);
    	File file = new File(urlMerged);
    	try {
			FileWriter fw = new FileWriter(file);
			fw.write(result);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	DependencyManagement left_dependencyManagement = new DependencyManagement();
    	left_dependencyManagement.dependencies=needToDeleteList;
    	result = xstream.toXML(left_dependencyManagement);
    	file = new File(urlLeft);
    	try {
			FileWriter fw = new FileWriter(file);
			fw.write(result);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
