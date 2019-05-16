package org.jbox.textCutter.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jbox.configuration.Configuration;
import org.jbox.dao.Page;
import org.jbox.dao.Word;
/**
 * 
 * A filter is used to filter noise word.
 * 
 * <p>
 * 		It is used to filter noise word for {@link org.jbox.textCutter.CutterBox}. 
 * All noise words must be defined in a file in the directory "DICT/NOISE/". For 
 * example, word "fool" needed to be filtered, it should be added to a file in 
 * "DICT/NOISE/", or added to a new file such as "myNoise.txt" in "DICT/NOISE/". 
 * Then the word "fool" will be ignored when cutting text. 
 * {@link org.jbox.textCutter.CutterBox} will invoking this method when calling 
 * {@link org.jbox.textCutter.CutterBox#cutPage(Page)}.
 * @version 1.0
 * @see Dict
 * @see org.jbox.textCutter.CutterBox
 *
 */
public class NoiseFilter {
	protected Dict noise;
	/**
	 * Constructs a new NoiseFilter with default path "DICT/NOISE".
	 */
	public NoiseFilter(){
		this.noise = new Dict(Configuration.getWorkDir()+"/DICT/NOISE");
	}
	/**
	 * Constructs a new NoiseFilter with path.
	 * @param path path of noise dictionary file or directory. 
	 */
	public NoiseFilter(String path){
		this.noise = new Dict(path);
	}
	/**
	 * Filter noise words, remove words defined in noise dictionary 
	 * from the specified collection .
	 * 
	 * @param words Collection containing {@link Word Word} objects to be filtered.
	 */
	public void filterNoise(Collection<Word> words){
		Iterator<Word> wordsIterator = words.iterator();
		while(wordsIterator.hasNext()){
			String word = wordsIterator.next().getWordStr();
			if(noise.isExist(word)){
				wordsIterator.remove();
			}
		}
	}
	/**
	 * Filter redundant words.
	 * 
	 * If the specified collection contain two {@link Word Word} at the same 
	 * string, it will remove one word from the collection, and then 
	 * add the locations of removed word to the other word. For example, suppose 
	 * that there are two words at the same string "fun", and first word has 
	 * the locations {1,2}, second has {5}, after invoking this method, the second 
	 * word was removed, and locations of the first word is {1,2,5}.
	 * 
	 * @param unFilteredWords
	 */
	public void filterRedundancy(Collection<Word> unFilteredWords) {
		Map<String, Word> trimedWords = new LinkedHashMap<String, Word>();
		Iterator<Word> unTrimedWordsIt = unFilteredWords.iterator();
		while (unTrimedWordsIt.hasNext()) {
			Word unTrimedWord = unTrimedWordsIt.next();
			if (trimedWords.containsKey(unTrimedWord.getWordStr())) {
				Word trimedWord = trimedWords.get(unTrimedWord.getWordStr());
				trimedWord.getLocations().addAll(unTrimedWord.getLocations());
			} else {
				trimedWords.put(unTrimedWord.getWordStr(), unTrimedWord);
			}
		}
		unFilteredWords.clear();
		unFilteredWords.addAll(trimedWords.values());
	}
}
