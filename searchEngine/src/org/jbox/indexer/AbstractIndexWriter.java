package org.jbox.indexer;

import org.jbox.dao.Page;
import org.jbox.dao.PageHome;
import org.jbox.dao.Word;
import org.jbox.dao.WordHome;

/**
 * An abstract class defines default behavior of IndexWriter.
 * 
 * @author YiBin.H
 * @version 1.0
 */
public abstract class AbstractIndexWriter implements IndexWriter {
	protected PageHome pageHome;
	protected WordHome wordHome;

	/**
	 * Create index of a {@link Word Word}.
	 * 
	 * @param w
	 *            {@link Word Word} object needed to create index.
	 * @param pageId
	 *            id of a page which contains the word.
	 */
	abstract protected void createIndex(Word w, long pageId);

	public void saveIndex(Page page) {
		long pageId = pageHome.savePage(page);
		if (pageId != -1) {
			Word[] words = new Word[page.getWords().size()];
			page.getWords().toArray(words);
			for (Word w : words) {
				this.createIndex(w, page.getUrlId());
			}
			wordHome.saveWords(words);
		}
	}

	public void setPageHome(PageHome pageHome) {
		this.pageHome = pageHome;
	}

	public void setWordHome(WordHome wordHome) {
		this.wordHome = wordHome;
	}
}
