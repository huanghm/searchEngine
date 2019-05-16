package org.jbox.webSpider;

import org.jbox.dao.Page;
/**
 * The root interface of WebSpider.
 * 
 * It is used to crawl the Internet and fetch pages.
 * @author YiBin.H
 * @version 1.0
 *
 */
public interface WebSpider {
	/**
	 * Set start URLs of WebSpider.
	 * 
	 * @param startUrls String array containing start URLs of WebSpider.
	 */
	 void setStartUrls(String[] startUrls);
	 /**
	  * Set crawl rules of WebSpider.
	  * 
	  * A rule is written in REGEXP(regular expression), for example, rule:
	  * <br>{"http://.*(\.html)$"} 
	  * limits the spider just crawls URLs end with ".html".
	  * rules:
	  * <br>{"http://.*(\.html)$","http://localhost/.*"}
	  * limits the spider crawls URLS end with ".html" and start with 
	  * "http://localhost/".
	  *  
	  * @param rules String array containing rules written in REGEXP.
	  */
	 void setRules(String[] rules);
	 /**
	  * Set max number of pages that the spider will crawl.
	  * 
	  * @param maxPageNum max number of pages the WebSpider will crawl.
	  */
	 void setMaxPageNum(int maxPageNum);
	/**
	 * Check if there is a next page to visit.
	 * @return true if has next, or false otherwise.
	 */
	boolean hashNext();
	/**
	 * Visit and return the next @{link Page Page} Object.
	 * @return @{link Page Page} object.
	 */
	Page next();
	/**
	 * Return max page number defined in configuration file.
	 * @return max page number.
	 */
	int getMaxPageNum();
}
