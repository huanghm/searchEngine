package org.jbox.example;

import java.awt.Color;

import org.jbox.configuration.Configuration;
import org.jbox.dao.Page;
import org.jbox.searcher.Searcher;

public class MyJbox {
	private static Configuration cfg;
	private Searcher searcher;
	public MyJbox(){
		cfg = Configuration.config("jbox.cfg.xml");
		searcher = cfg.buildSearcher();
	}
	public Page[] search(String query){
		Page[] ps = searcher.search(query);
		return ps;
	}
	public String highlight(String text, String query, Color c){
		return searcher.highlight(text, new StringBuffer(query), c);
	}
}
