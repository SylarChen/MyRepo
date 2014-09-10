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

public class PomManager {
	
	public static void start(String[] urls,String mergedUrl,String leftUrl)
	{
		System.out.println("Read poms....");
    	List<DependencyManagement> DM_List = getDependencyManagement(urls);
    	
    	System.out.println("Start to merge pom....");
    	DependencyManagement merged_DM = new DependencyManagement();
    	DependencyManagement left_DM = new DependencyManagement();
    	mergeDependencies(DM_List, merged_DM, left_DM);
    
    	System.out.println("Start to write result....");
    	writeBack(merged_DM,mergedUrl);
    	writeBack(left_DM,leftUrl);
	}
	
	private static List<DependencyManagement> getDependencyManagement(String[] urls)
    {
		List<DependencyManagement> list = new ArrayList<DependencyManagement>();
    	XStream xstream = new XStream();
    	xstream.processAnnotations(DependencyManagement.class);
    	xstream.processAnnotations(Dependency.class);
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
            list.add((DependencyManagement) xstream.fromXML("<dependencyManagement>"+directChildElement.asXML()+"</dependencyManagement>"));
    	}
    	return list;
    }
	    
	private static void mergeDependencies(List<DependencyManagement> DM_List, DependencyManagement merged_DM, DependencyManagement left_DM)
    {	
    	//do not merge dependency with classifier,type or exclusions
    	//do not merge dependency with no version
    	for(DependencyManagement dm : DM_List)
    	{
    		for(Dependency dependency : dm.dependencies)
    		{
    			if(dependency.version!=null && dependency.classifier==null && dependency.type==null && dependency.exclusions==null)
    			{
    				merged_DM.dependencies.add(dependency);
    			}
    			else
    			{
    				DependencyManagement.notValidList.add(dependency);
    				//System.out.println("log: "+dependency.groupId+"."+dependency.artifactId+"."+dependency.version+"."+dependency.scope);
    			}
    		}
    	}
    	//first merge  , left some not valid dependency in merged_DM
    	Set<Dependency> d_set = new HashSet<Dependency>(merged_DM.dependencies);
    	merged_DM.dependencies = new ArrayList<Dependency>(d_set);
    	//remove those not valid
    	for(Dependency d : DependencyManagement.notValidList)
    	{
    		left_DM.dependencies.add(d);
    	}
    	for(Dependency d : left_DM.dependencies)
    	{
    		merged_DM.dependencies.remove(d);
    		//System.out.println("log: "+d.groupId+"."+d.artifactId+"."+d.version+"."+d.scope);
    	}
    }

	private static void writeBack(DependencyManagement dependencyManagement,String url)
    {	
    	XStream xstream = new XStream();
    	xstream.processAnnotations(DependencyManagement.class);
    	xstream.processAnnotations(Dependency.class);
    		
    	String result = xstream.toXML(dependencyManagement);
    	File file = new File(url);
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
