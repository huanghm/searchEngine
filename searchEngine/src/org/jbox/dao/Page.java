package org.jbox.dao;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A class represents a web page, but without any html tag.
 * 
 * The data mapping details refer to "Page.hbm.xml".
 * 
 * @author YiBin.H
 * @version 1.0
 * @see PageHome
 * @see Word
 */
public class Page implements java.io.Serializable {
	private static final long serialVersionUID = -5991311367219458193L;

	private long urlId;
	private String url;
	private String title;
	private String text;
	private int wordNum;
	private Collection<Word> words = new LinkedList<Word>();

	/**
	 * Return page title.
	 * 
	 * @return String.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of page.
	 * 
	 * @param title
	 *            String.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Return the URL of page.
	 * 
	 * @return String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the URL of page.
	 * 
	 * @param url
	 *            String
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Return the text(without html tag) of page.
	 * 
	 * @return String
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the text(without html tag) of page.
	 * 
	 * @param text
	 *            String
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Return the words number of page.
	 * 
	 * @return Integer
	 */
	public int getWordNum() {
		return wordNum;
	}

	/**
	 * Set the words number of page.
	 * 
	 * @param wordNum
	 *            Integer
	 */
	public void setWordNum(int wordNum) {
		this.wordNum = wordNum;
	}

	/**
	 * Return the id of Page in data base.
	 * 
	 * @return long.
	 */
	public long getUrlId() {
		return urlId;
	}

	/**
	 * Set id of page.
	 * 
	 * @param urlId
	 *            id of page.
	 */
	public void setUrlId(long urlId) {
		this.urlId = urlId;
	}

	/**
	 * Return a collection containing {@link Word} objects of page.
	 * 
	 * @return Collection containing {@link Word} objects.
	 */
	public Collection<Word> getWords() {
		return words;
	}

	/**
	 * Set a collection containing {@link Word} object.
	 * 
	 * It should be noticed that the collection doesn't be updated to database
	 * when invoking {@link PageHome#savePage(Page)}.
	 * 
	 * @param words
	 *            Collection containing {@link Word} objects.
	 */
	public void setWords(Collection<Word> words) {
		this.words = words;
	}

	/**
	 * Return string of URL of Page.
	 * 
	 * @return String.
	 */
	public String toString() {
		return url;
	}

}
