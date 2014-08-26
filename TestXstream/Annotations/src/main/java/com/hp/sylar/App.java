package com.hp.sylar;

import com.thoughtworks.xstream.XStream;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	annotation_for_alias();
    }
 
    public static void annotation_for_alias()
    {
    	// 1.add Annotations for class RendezvousMessage
    	// 2.call xstream.processAnnotations() method to process Annotations in RendezvousMessage class
		XStream xstream = new XStream();
		xstream.processAnnotations(RendezvousMessage.class);
		RendezvousMessage msg = new RendezvousMessage(15);
		System.out.println(xstream.toXML(msg));
    }

}
