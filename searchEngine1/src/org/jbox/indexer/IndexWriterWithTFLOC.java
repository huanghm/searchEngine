package org.jbox.indexer;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jbox.dao.Word;

/**
 * A implementation of IndexWriter, creating word index with TF and location.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see Word
 * 
 */
public class IndexWriterWithTFLOC extends AbstractIndexWriter {
	/**
	 * Create index of a Word with TF and location.
	 * 
	 * <br>
	 * The "index" created might be like below:
	 * 
	 * "22-0.166667-0,1"
	 * 
	 * The first field "22" means the word did appear in Page with id 22, the
	 * Second field "0.166667" means the TF of the word in a page, and "0,1"
	 * represents the locations of the word. For example:
	 * 
	 * "I have a cat. You have a dog. He is so funny."
	 * 
	 * The word "have" appear in first sentence and second, so locations of the
	 * word in the text is "0,1". The text have 12 words, so the TF is 2/12 =
	 * 0.166667. Suppose the id of this text is "22", and then index of this
	 * word in the text is "22-0.166667-0,1".
	 * 
	 * @param w
	 *            which need to create index.
	 * @param urlId
	 *            the id of page which contains the word.
	 */
	public void createIndex(Word w, long urlId) {
		StringBuffer index = new StringBuffer();
		index.append(urlId);
		index.append("-");
		NumberFormat nf = DecimalFormat.getInstance();
		nf.setMaximumFractionDigits(5);
		index.append(nf.format(w.getTf()));
		index.append("-");
		String location = w.getLocations().toString();
		index.append(w.getLocations().toString().substring(1,
				location.length() - 1));
		w.setIndex(index.toString());
	}
}
