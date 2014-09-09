package com.hp.sylar;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("dependency")
public class Dependency {	
	public String groupId;
	public String artifactId;
	public String version;
	public String scope;
	
	public String type;
	public String classifier;
	public String exclusions;
}
