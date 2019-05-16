package org.jbox.textCutter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jbox.dao.Page;
import org.jbox.dao.Word;
import org.jbox.textCutter.util.NoiseFilter;

/**
 * Container of Cutter.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see Cutter
 * @see NoiseFilter
 * @see org.jbox.textCutter.util.LanguageFilter
 * 
 */
public class CutterBox {
	private Collection<Cutter> cutterBox;

	private NoiseFilter noiseFilter;

	/**
	 * Constructs a new CutterBox.
	 */
	public CutterBox() {
		cutterBox = new ArrayList<Cutter>();
		noiseFilter = new NoiseFilter();

	}

	/**
	 * Add a {@link Cutter} into CutterBox.
	 * 
	 * @param c
	 *            a {@link Cutter} object.
	 */
	public void addCutter(Cutter c) {
		cutterBox.add(c);
	}

	/**
	 * Cut text of {@link Page} object into words, calculate the TF of
	 * {@link Word}, and stored the words in the {@link Page} object. All words
	 * in the text defined in noise file will not be stored.
	 * 
	 * @param p
	 *            {@link Page} object contain the text to be cut.
	 */
	public void cutPage(Page p) {
		String text = p.getText();
		String[] sentences = cutArticleToSentence(text);
		for (int i = 0; i < sentences.length; i++) {
			StringBuffer sentence = new StringBuffer(sentences[i]);
			for (Cutter cutter : cutterBox) {
				Collection<String> words = cutter.cutSentenceToWord(sentence);
				for (String wordStr : words) {
					Word w = new Word();
					w.setWordStr(wordStr);
					w.addLocation(i);
					p.getWords().add(w);
				}
			}
		}
		p.setWordNum(p.getWords().size());
		this.noiseFilter.filterNoise(p.getWords());
		this.noiseFilter.filterRedundancy(p.getWords());
		this.calWordsTF(p.getWords(), p.getWordNum());
	}

	/**
	 * Cut text into words.
	 * 
	 * @param text
	 *            text to be cut.
	 * @return string collection containing words.
	 */
	public Collection<String> cutText(String text) {
		Collection<String> words = new ArrayList<String>();
		String[] sentences = cutArticleToSentence(text);
		for (int i = 0; i < sentences.length; i++) {
			StringBuffer sentence = new StringBuffer(sentences[i]);
			for (Cutter cutter : cutterBox) {
				words.addAll(cutter.cutSentenceToWord(sentence));
			}
		}
		return words;
	}

	/**
	 * Calculate the TF of {@link Word}.
	 * 
	 * @param words
	 *            collection containing {@link Word} objects.
	 * @param wordNum
	 *            words Number of the cut text.
	 */
	private void calWordsTF(Collection<Word> words, int wordNum) {
		Iterator<Word> wordsIt = words.iterator();
		while (wordsIt.hasNext()) {
			Word word = wordsIt.next();
			double wordCount = word.getLocations().size();
			word.setTf(wordCount / wordNum);
		}
	}

	/**
	 * Static method for splitting text into Sentences by "." or "!".
	 * 
	 * @param article
	 *            text to be cut.
	 * @return string array containing sentences.
	 */
	public static String[] cutArticleToSentence(String article) {
		String[] sentences = article.replaceAll("(\\n|\\r|\\t|\\s)+", " ")
				.split("(\\.|¡£|!|£¡)+");
		return sentences;
	}
	/**
	 * Highlight keywords in the text with specified color. The text maybe contain
	 * several different UNICODE.
	 * 
	 * @param text 
	 * @param query
	 * @param c
	 * @return text with highlighted keywords.
	 */
	public String highlight(String text, StringBuffer query, Color c) {
		for (Cutter cutter : cutterBox) {
			String[] sentences = cutArticleToSentence(text);
			Collection<String> keywords = cutter.cutSentenceToWord(query);
			StringBuffer tempText = new StringBuffer(text.length()
					+ text.length());
			for (String sentence : sentences) {
				tempText.append(cutter.highlight(sentence, keywords, c));
			}
			text = tempText.toString();
		}
		return text;
	}
}
