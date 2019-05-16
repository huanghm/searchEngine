package org.jbox.textCutter;

import java.awt.Color;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.Collection;

import org.jbox.textCutter.util.LanguageFilter;

/**
 * A abstract class define default behavior of {@link Cutter}.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see CutterBox
 * @see LanguageFilter
 */
public abstract class AbstractCutter implements Cutter {
	protected LanguageFilter langFilter;

	/**
	 * Cut text into words.
	 * 
	 * @param checkedString
	 *            text contain chars belongs the unicode scope of the Cutter.
	 * @return words of text.
	 */
	abstract protected Collection<String> cutSentenceToWord(String checkedString);

	/**
	 * Be used to specify the unicode scope of Cutter for filtering text.
	 */
	public void setUnicode(UnicodeBlock[] unicodeBlocks, int[][] unicodeScopes) {
		this.langFilter = new LanguageFilter(unicodeBlocks, unicodeScopes);
	}
	
	public Collection<String> cutSentenceToWord(StringBuffer unCheckedString) {
		ArrayList<String> allWords = new ArrayList<String>();
		Collection<String> subList = langFilter.filter(unCheckedString);
		for(String s:subList){
			allWords.addAll(this.cutSentenceToWord(s));
		}
		return allWords;
	}
	/**
	 * Highlight keywords in the sentence with specified color.The sentence contains
	 * only one UNICODE.
	 * @param sentence
	 * @param keywords
	 * @param color
	 * @return sentence whose keywords is marked with specified color.
	 */
	abstract protected String highlight(String sentence,Collection<String> keywords,String color);
	
	public String highlight(String sentence, Collection<String> keywords, Color c) {
		String r = Integer.toHexString(c.getRed());
		if (r.length() < 2) {
			r = "0" + r;
		}
		String g = Integer.toHexString(c.getGreen());
		if (g.length() < 2) {
			g = "0" + g;
		}
		String b = Integer.toHexString(c.getBlue());
		if (b.length() < 2) {
			b = "0" + b;
		}
		String color = "#" + r + g + b;
		StringBuffer text = new StringBuffer(sentence);
		Collection<String> subList = langFilter.filter(text);
		for(String s:subList){
			String highlightString = this.highlight(s, keywords, color);
			sentence = sentence.replace(s, highlightString);
		}
		return sentence;
	}
}
