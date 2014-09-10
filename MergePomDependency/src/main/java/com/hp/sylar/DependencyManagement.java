package com.hp.sylar;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("dependencyManagement")
public class DependencyManagement {
	public List<Dependency> dependencies = new ArrayList<Dependency>();
	public static List<Dependency> notValidList = new ArrayList<Dependency>();
}
