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
	
	@Override
	public int hashCode()
	{
		String id = groupId+"."+artifactId;
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object o){ 
		if(!(o instanceof Dependency)) 
			return false; 
		Dependency d = (Dependency)o;
		boolean sameAticraft = this.groupId.equals(d.groupId)&&this.artifactId.equals(d.artifactId);
		//&&this.version.equals(d.version)
		
		if(sameAticraft)
		{
			String version1 = this.version==null?"":this.version;
			String version2 = d.version==null?"":d.version;
			boolean isVersionEq = version1.equals(version2);
			String scope1 = this.scope==null?"":this.scope;
			String scope2 = d.scope==null?"":d.scope;
			boolean isScopeEq = scope1.equals(scope2);
			if(!isVersionEq||!isScopeEq)
			{
				DependencyManagement.notValidList.add(this);
//				System.out.println("log: "+this.groupId+"."+this.artifactId+"."+this.version+"."+this.scope);
//				System.out.println("log: "+d.groupId+"."+d.artifactId+"."+d.version+"."+d.scope);
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
