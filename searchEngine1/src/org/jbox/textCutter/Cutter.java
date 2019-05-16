package org.jbox.textCutter;

import java.awt.Color;
import java.lang.Character.UnicodeBlock;
import java.util.Collection;

import org.jbox.textCutter.util.LanguageFilter;

/**
 * The root interface of text cutter.
 * 
 * <p>
 * Cutter is put in a {@link CutterBox} to work.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see CutterBox
 * @see LanguageFilter
 */
public interface Cutter {
	/**
	 * Cut text in a StringBuffer into words.
	 * 
	 * @param unCheckedString
	 *            text with different UNICODEs.
	 * @return collection containing words of the sub text which belong to
	 *         unicode scope of the Cutter.
	 */
	Collection<String> cutSentenceToWord(StringBuffer unCheckedString);

	/**
	 * Set the unicode scope of Cutter.
	 * 
	 * @param unicodeBlocks
	 * @param unicodeScopes
	 */
	void setUnicode(UnicodeBlock[] unicodeBlocks, int[][] unicodeScopes);
	/**
	 * Highlight keywords in the text with specified color. The text maybe contain
	 * several different UNICODE.
	 * 
	 * @param text
	 * @param keywords
	 * @param c
	 * @return text with highlighted keywords.
	 */
	public String highlight(String text, Collection<String> keywords, Color c);
}
