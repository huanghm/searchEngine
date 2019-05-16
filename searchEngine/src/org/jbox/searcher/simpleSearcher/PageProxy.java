package org.jbox.searcher.simpleSearcher;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.jbox.dao.Page;
import org.jbox.dao.PageHome;
import org.jbox.dao.Word;
import org.jbox.textCutter.CutterBox;
/**
 * A proxy of {@link Page Page}.
 * 
 * This class is used to change the behavior of {@link Page#getText()}. 
 * It will return an introduction instead of returning 
 * the whole text of page.
 * object.
 *  
 * @author YiBin.H
 * @version 1.0
 * @see Page
 * @see SimpleSearcher
 *
 */

public class PageProxy extends Page{
	private static final long serialVersionUID = -5991311367219458193L;
	private double tfidf;
	private Page page;
	private Set<Integer>  location;
	private String title;
	private String shortText;
	/**
	 * Constructs a new <code>PageProxy</code> object.
	 */
	public PageProxy(){
		page = new Page();
	}
	/**
	 * Set location of words to relative <@link Page> object.
	 * @param location locations of words in relative<@link Page> object.
	 */
	public void setLocation(Set<Integer> location) {
		this.location = location;
	}
	/**
	 * Get locations of words in relative <@link Page> object.
	 * @return location locations of words in relative <@link Page Page> object.
	 */
	public Set<Integer> getLocation(){
		return this.location;
	}
	/**
	 * Add locations of words in relative <@link Page> object.
	 */
	public void addLocation(Set<Integer> location){
		this.location.addAll(location);
	}
	/**
	 * Get id of  the relative <@link Page Page> object. 
	 * @return id of  the relative <@link Page Page> object. 
	 */
	public long getPageId() {
		
		return this.page.getUrlId();
	}
	/**
	 * Set id of relative <@link Page> object. 
	 * @param pageId id of  relative <@link Page> object. 
	 */
	public void setPageId(long pageId) {
		this.page.setUrlId(pageId);
	}
	/**
	 * Get TFIDF of  relative <@link Page> object. 
	 * @return TFIDF of  relative <@link Page> object. 
	 */
	public double getTFIDF() {
		return tfidf;
	}
	/**
	 * Set TFIDF of  relative <@link Page> object. 
	 * @param tfidf TFIDF of relative <@link Page> object. 
	 */
	public void setTfidf(double tfidf) {
		this.tfidf = tfidf;
	}
	/**
	 * Return an introduction of page instead of returning a whole text of page.
	 * in {@link Page#getText()}.
	 * 
	 * For example, suppose that the text of relative <@link Page> object is:
	 * 
	 * "She has a dog. I have a dog too. John hasn't. He doesn't like dog.". 
	 * 
	 * Locations of word "dog" in the text is {0,1,3}, so the introduction is:
	 * 
	 *  "She has a dog...I have a dog too...He doesn't like dog...".
	 * 
	 * If the size of locations is bigger than 3, it will just return the first 
	 * three sentences in the text with locations.
	 * 
	 * @return introduction of relative page.
	 */
	public String getText() {
		if(this.shortText!=null){
			return this.shortText;
		}
		StringBuffer sb = new StringBuffer();
		String[] text = CutterBox.cutArticleToSentence(page.getText());
		if(this.location==null)
			return null;
		Iterator<Integer> it = this.location.iterator();
		int count = 0;
		while(it.hasNext()&&count++<3){
			sb.append(text[it.next()]+" ... ");
		}
		this.shortText = sb.toString();
		return shortText;
	}
	/**
	 * Return relative <@link Page> object.
	 * @return <@link Page> object.
	 */
	public Page getPage() {
		return page;
	}
	/**
	 * Set a <@link Page> object to this <code>PageProxy</code>.
	 * @param page <@link Page Page> object.
	 */
	public void setPage(Page page) {
		this.page = page;
	}
	/**
	 * Return title of relative <@link Page> object.
	 * 
	 * @return title of relative <@link Page> object.
	 */
	public String getTitle() {
		if(this.title==null){
			this.title = page.getTitle();
		}
		return this.title;
	}
	/**
	 * Set title.
	 * 
	 * @param title String
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Return the URL of relative <@link Page> object.
	 * 
	 * @return String representing a URL.
	 */
	public String getUrl() {
		return page.getUrl();
	}
	/**
	 * Set a URL to relative <@link Page> object.
	 * 
	 * @param url String representing a URL.
	 */
	public void setUrl(String url) {
		page.setUrl(url);
	}
	/**
	 * Set text to the relative <@link Page> object. 
	 * 
	 * @param text String.
	 */
	public void setText(String text) {
		this.shortText = text;
	}
	/**
	 * Return the words number of relative page.
	 * 
	 * @return words number of relative page.
	 */
	public int getWordNum() {
		return page.getWordNum();
	}
	/**
	 * Set words number to relative <@link Page> object.
	 * 
	 * @param wordNum Integer
	 */
	public void setWordNum(int wordNum) {
		page.setWordNum(wordNum);
	}
	/**
	 * Return id of relative <@link Page> object. 
	 * 
	 * @return id of relative <@link Page> object.
	 */
	public long getUrlId() {
		return page.getUrlId();
	}
	/**
	 * Set id to relative <@link Page> object. 
	 * 
	 * @param urlId long value.
	 */
	public void setUrlId(long urlId) {
		page.setUrlId(urlId);
	}
	/**
	 * Return a collection containing {@link Word} objects of Page.
	 * 
	 * @return Collection containing {@link Word} objects.
	 */
	public Collection<Word> getWords() {
		return page.getWords();
	}
	/**
	 * Set a collection containing {@link Word Word} object.
	 * 
	 * It should be noticed that the collection isn't updated to database 
	 * when invoking {@link PageHome#savePage(Page)}.
	 * @param words Collection containing {@link Word} objects.
	 */
	public void setWords(Collection<Word> words) {
		this.page.setWords(words);
	}

	/**
	 * Return URL of relative <@link Page> object. 
	 * 
	 * @return string representing URL of the relative <@link Page> object. 
	 */
	public String toString(){
		return page.getUrl();
	}
}
