package org.jbox.example;

import org.jbox.configuration.Configuration;
import org.jbox.dao.Page;
import org.jbox.textCutter.CutterBox;
import org.jbox.webSpider.WebSpider;


public class SimpleSample {
	public static void main(String[] args){
		Configuration cfg = Configuration.config();
		WebSpider s = cfg.buildWebSpider();
		CutterBox cb = cfg.buildCutterBox();
		org.jbox.indexer.IndexWriter iw = cfg.buildIndexWriter();
		long start = System.currentTimeMillis();
		while(s.hashNext()){
			Page p = s.next();
			if(p==null||p.getText()==null)continue;
			cb.cutPage(p);
			iw.saveIndex(p);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
