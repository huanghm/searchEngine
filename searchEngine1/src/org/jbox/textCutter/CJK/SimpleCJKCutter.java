package org.jbox.textCutter.CJK;

import java.util.ArrayList;
import java.util.Collection;

import org.jbox.configuration.Configuration;
import org.jbox.textCutter.AbstractCutter;

import org.jbox.textCutter.util.Dict;

/**
 * A concrete class of Cutter for CJK.
 * 
 * <P>
 * SimpleCJKCutter use dictionary files in directory "DICT/WORD/" to cut text.
 * If there is not file in the directory, the text will be cut by char. Noted
 * that if this class is used, the directory "DICT/WORD/" is needed, even if
 * there is not dictionary file exist,or else it will throw a
 * {@link org.jbox.textCutter.util.DictInitException}. 
 * <p>
 * Note: Just Chinese Dictionary is offered in jbox at current version.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see org.jbox.textCutter.CutterBox
 * @see org.jbox.textCutter.util.Dict
 * @see org.jbox.textCutter.util.LanguageFilter
 */
public class SimpleCJKCutter extends AbstractCutter {

	protected Dict dict;

	public SimpleCJKCutter() {
		this.dict = new Dict(Configuration.getWorkDir()+"/DICT/WORD/CJK/");
	}

	@Override
	public Collection<String> cutSentenceToWord(String s) {
		ArrayList<String> words = new ArrayList<String>();
		while (s != null && s.length() > 0) {
			boolean isChanged = false;
			for (int j = s.length(); j > 0; j--) {
				String subS = s.substring(0, j);
				if (dict.isExist(subS)) {
					words.add(subS);
					s = s.substring(j, s.length());
					isChanged = true;
					break;
				}
			}
			if (s.length() < 1) {
				s = null;
			} else {
				if (!isChanged)
					words.add(s.substring(0, 1));
				s = s.substring(1, s.length());
			}
		}
		return words;
	}

	protected String highlight(String sentence,Collection<String> keywords,String color){
		for(String word:keywords){
			sentence = sentence.replace(word, "<font color=" + color + ">" + word + "</font>");
		}
		return sentence;
	}
}
