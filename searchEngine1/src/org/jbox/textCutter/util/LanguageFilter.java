package org.jbox.textCutter.util;

import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * A filter is used to filter text with different language unicode.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see org.jbox.textCutter.CutterBox
 */
public class LanguageFilter {
	private static Logger logger = Logger.getLogger(LanguageFilter.class);
	private UnicodeBlock[] unicodeBlocks;
	private int[][] unicodeScopes;
	/**
	 * Constructs the LanguageFilter with UnicodeBlock array.
	 * @param unicodeBlocks
	 */
	public LanguageFilter(UnicodeBlock[] unicodeBlocks) {
		init(unicodeBlocks);
	}
	/**
	 * Constructs the LanguageFilter with int array of unicode scope.
	 * @param unicodeScopes
	 */
	public LanguageFilter(int[][] unicodeScopes){
		init(unicodeScopes);
	}
	/**
	 * Constructs the LanguageFilter with UnicodeBlock array and integer array 
	 * of unicode scope.
	 * @param unicodeBlocks
	 * @param unicodeScopes
	 */
	public LanguageFilter(UnicodeBlock[] unicodeBlocks,int[][] unicodeScopes){
		init(unicodeBlocks,unicodeScopes);
	}
	private void init(UnicodeBlock[] unicodeBlocks) {
		init(unicodeBlocks,null);
	}
	private void init(int[][] unicodeScopes){
		init(null,unicodeScopes);
	}
	private void init(UnicodeBlock[] unicodeBlocks,int[][] unicodeScopes){
		if((unicodeBlocks == null||unicodeBlocks.length==0)&&(unicodeScopes==null||unicodeScopes.length==0||unicodeScopes[0].length<2)){
			LangFilterInitException lfie = new LangFilterInitException("inputted parameter error:unicodeBlock and unicodeScopes are both null.");
			if(logger.isEnabledFor(Level.ERROR))
				logger.error(lfie.getMessage(),lfie);
			throw lfie;
		}
		this.unicodeBlocks = unicodeBlocks;
		this.unicodeScopes = unicodeScopes;
	}
	private boolean checkByUnicodeBlock(char c){
		if(this.unicodeBlocks==null||this.unicodeBlocks.length==0)return false;
		for(UnicodeBlock ub:this.unicodeBlocks){
			if(Character.UnicodeBlock.of(c)==ub)
				return true;
		}
		return false;
	}
	private boolean checkByUnicodeScope(char c) {
		if(this.unicodeScopes==null||this.unicodeScopes.length==0||this.unicodeScopes[0].length<2)return false;
		for (int[] scope : this.unicodeScopes) {
			int startUnicode = scope[0];
			int endUnicode = scope[1];
			if (c >= startUnicode && c <= endUnicode)
				return true;
		}
		return false;
	}
	private boolean check(char c){
		return checkByUnicodeBlock(c)||checkByUnicodeScope(c);
	}
	/**
	 * Filter text. 
	 * @param sb StringBuffer containing text with different unicode to 
	 * be filtered.
 	 * @return <code>Collection</code> containing strings with same unicode.
	 */
	public Collection<String> filter(StringBuffer sb){
		ArrayList<String> subList = new ArrayList<String>();
Loop1:		for(int i =0;i<sb.length();i++){
			if(this.check(sb.charAt(i))){
				for(int j = i+1;j<sb.length();j++){
					if(!check(sb.charAt(j))){
						subList.add(sb.substring(i,j));
						sb.replace(i, j, "");
						i--;
						continue Loop1;
					}
				}
				subList.add(sb.substring(i));
				sb.replace(i, sb.length(), "");
			}
		}
		return subList;
	}
}
