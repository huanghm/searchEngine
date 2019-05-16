package org.jbox.indexer;

import org.jbox.dao.Page;
import org.jbox.dao.PageHome;
import org.jbox.dao.WordHome;

/**
 * The root interface for creating and writing index.
 * 
 * The performance of writing index based on the concrete DAO implementation
 * which is specified in configuration file.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see Page
 * @see PageHome
 * @see WordHome
 */
public interface IndexWriter {
	/**
	 * Write index of words of a {@link Page} object to database.
	 * 
	 * @param page
	 *            {@link Page} object whose words needed to be saved.
	 */
	public void saveIndex(Page page);

	/**
	 * Set a PageHome for visiting Page table in data base.
	 * 
	 * @param pageHome
	 *            DAO of {@link Page}.
	 */
	void setPageHome(PageHome pageHome);

	/**
	 * Set a WordHome for visiting Word table in data base.
	 * 
	 * @param wordHome
	 *            DAO of {@link org.jbox.dao.Word}.
	 */
	void setWordHome(WordHome wordHome);
}
