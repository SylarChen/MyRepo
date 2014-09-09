package com.hp.sylar;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("dependencyManagement")
public class DependencyManagement {
	public List<Dependency> dependencies;
}
