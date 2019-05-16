package org.jbox.searcher;

import java.awt.Color;

import org.jbox.dao.Page;
import org.jbox.dao.PageHome;
import org.jbox.dao.WordHome;

/**
 * The root interface for searching index.
 * 
 * @author YiBin.H
 * @version 1.0
 * 
 */
public interface Searcher {
	/**
	 * Set a {@link CutterBox} object for Searcher to analyze the query string.
	 * 
	 * @param cutterBox
	 *            {@link CutterBox} object.
	 */

	/**
	 * Search index with the query string.
	 * 
	 * @param query
	 *            a query string.
	 * @return {@link Page} array related to the query string.
	 */
	public Page[] search(String query);

	/**
	 * Set PageHome for visiting Page table in data base.
	 * 
	 * @param pageHome
	 *            for visiting Page table in data base.
	 */
	void setPageHome(PageHome pageHome);

	/**
	 * Set WordHome for visiting Word table in data base.
	 * 
	 * @param wordHome
	 *            a WordHome for visiting Word table in data base.
	 */
	void setWordHome(WordHome wordHome);
	/**
	 * Highlight keywords in the text with specified color. The text maybe contain
	 * several different UNICODE.
	 * 
	 * @param text 
	 * @param query
	 * @param c
	 * @return text with highlighted keywords.
	 */
	String highlight(String text, StringBuffer query, Color c);
}
