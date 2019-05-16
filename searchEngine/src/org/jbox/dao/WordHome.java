package org.jbox.dao;

/**
 * The root interface of DAO for {@link Word},defines methods for visiting
 * database.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see Word
 */
public interface WordHome {
	/**
	 * Save a {@link Word} object to data base.
	 * 
	 * @param w <
	 *            {@link Word} object to be saved.
	 */
	void saveWord(Word w);

	/**
	 * Save {@link Word} objects in an array to data base.
	 * 
	 * @param words
	 *            array containing {@link Word} objects to be saved.
	 */
	void saveWords(Word[] words);

	/**
	 * Delete a {@link Word} object from data base.
	 * 
	 * @param w
	 *            {@link Word} object to be deleted.
	 */
	void deleteWord(Word w);

	/**
	 * Find a {@link Word} object from data base by the specified string.
	 * 
	 * @param wordStr
	 *            String.
	 * @return {@link Word} object with the specified string.
	 */
	Word findByWordStr(String wordStr);
}
