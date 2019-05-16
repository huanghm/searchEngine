package org.jbox.webSpider.simpleSpider;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;
import org.jbox.dao.Page;
import org.jbox.webSpider.simpleSpider.HtmlFetcher;
import org.jbox.webSpider.simpleSpider.HtmlVisitor;
import org.jbox.webSpider.simpleSpider.SimpleSpider;
import org.jbox.webSpider.simpleSpider.UnknownEncodingException;
import org.jbox.webSpider.WebSpider;
/**
 * An implementation of {@link WebSpider WebSpider}.
 * 
 * It should be noticed that the SimpleSpider doesn't take care 
 * of "rebot.txt".
 * @author YiBin.H
 * @version 1.0
 *
 */
public class SimpleSpider implements WebSpider{
	private static Logger spiderLogger = Logger.getLogger(SimpleSpider.class);
	private static int pageCounter = 0;
	private LinkedList<String> links = new LinkedList<String>();
	private HashSet<String> visitedLinks = new HashSet<String>(50000);
	private HtmlFetcher urlFecther = new HtmlFetcher(); 
	private String[] rules;
	private int maxPageNum;
	/**
	 * Constructs a new SimpleSpider.
	 */
	public SimpleSpider(){
		System.setProperty ("sun.net.client.defaultReadTimeout", "7000");
		System.setProperty ("sun.net.client.defaultConnectTimeout", "7000");
	}
	/**
	 * Set start URLs of WebSpider.
	 * 
	 * @param startUrls String array containing start URLs of WebSpider.
	 */
	public void setStartUrls(String[] startUrls) {
		for(String url:startUrls){
			links.add(url);
		}
	}
	 /**
	  * Set crawl rules of WebSpider.
	  * 
	  * A rule is written in REGEXP(regular expression). For example, rule:
	  * <br>{"http://.*(\.html)$"} 
	  * limits the spider to crawls URLs end with ".html".
	  * rules:
	  * <br>{"http://.*(\.html)$","http://localhost/.*"}
	  * limits the spider to crawls URLS end with ".html" and start with 
	  * "http://localhost/".
	  *  
	  * @param rules String array containing rules written in REGEXP.
	  */
	public void setRules(String[] rules) {
		this.rules = rules;
	}
	 /**
	  * Set the max page number that the spider will crawl.
	  * 
	  * @param maxPageNum max page number.
	  */
	public void setMaxPageNum(int maxPageNum) {
		this.maxPageNum = maxPageNum;
	}
	/**
	 * Return the max page number that the spider will crawl.
	 * @return max page number.
	 */
	public int getMaxPageNum() {
		return this.maxPageNum;
	}
	/**
	 * Check if there is a next page to visit or if has reached the max page 
	 * number.
	 * @return true if has next and still not reach the max page number, 
	 * or false otherwise.
	 */
	public boolean hashNext(){
		return !links.isEmpty()&&SimpleSpider.pageCounter<this.maxPageNum;
	}
	/**
	 * Visit and return the next @{link Page Page} object.
	 * 
	 * Return Page with URL,title,and text.
	 * It should be noticed that all the page fetched by SimpleSpider will be
	 * encode to "UTF-8", but not the value of  "charset=" in 
	 * content type in response header.
	 * If it waits too long to fetch content from a URL, the URL will be 
	 * skipped.
	 * @return a Page.
	 * @throws UnknownEncodingException thrown if encoding of a page couldn't 
	 * not be resolve. 
	 */
	public Page next(){
		if(!this.hashNext()){
			return null;
		}
		String url = null;
		HtmlVisitor visitor = null;
		String html = null;
		do{
			url = links.poll();
			if(url!=null){
				visitedLinks.add(url);
				try {
					urlFecther.connect(url);
				} catch (IOException e) {
					if(spiderLogger.isEnabledFor(Level.WARN))
						spiderLogger.warn("fail to connect:"+url, e);
					continue;
				} 
				try {
					html = urlFecther.fectch();
				} catch (IOException e) {
					if(spiderLogger.isEnabledFor(Level.WARN))
						spiderLogger.warn("fail to fecth:"+url, e);
					continue;
				} catch(UnknownEncodingException e){
					if(spiderLogger.isEnabledFor(Level.WARN))
						spiderLogger.warn("fail to fecth:"+url, e);
					continue;
				}catch(RuntimeException e){
					if(spiderLogger.isEnabledFor(Level.WARN))
						spiderLogger.warn("unExpected exception when fetching:"+url, e);
					visitor = null;
					continue;
				}
				visitor = new HtmlVisitor(rules);
				try{
					visitor.parse(html, "UTF-8");
				} catch (ParserException e) {
					if(spiderLogger.isEnabledFor(Level.WARN))
						spiderLogger.warn("fail to parse:"+url, e);
					visitor = null;
					continue;
				}catch(RuntimeException e){
					if(spiderLogger.isEnabledFor(Level.WARN))
						spiderLogger.warn("unExpected exception when parsing:"+url, e);
					visitor = null;
					continue;
				}
			}
		}while(visitor == null&&!links.isEmpty());
		if(visitor ==null||visitor.getText()==null){
			return null;
		}else{
			if(spiderLogger.isInfoEnabled())
				spiderLogger.info("fecth:"+url);
		}
		LinkedList<String> pageLinks =visitor.getLinksUnderRules();
		while(pageLinks!=null&&!pageLinks.isEmpty()){
			String pageLink = pageLinks.poll();
			if(visitedLinks.contains(pageLink)||links.contains(pageLink)){
				continue;
			}else{
				links.add(pageLink);
			}
		}
		Page page = new Page();
		page.setUrl(url);
		page.setTitle(visitor.getTitle());
		page.setText(visitor.getText());
		pageCounter++;
		return page;
	}

}
