package org.jbox.textCutter.EN;

import java.util.ArrayList;
import java.util.Collection;

import org.jbox.textCutter.AbstractCutter;
/**
 * A concrete class of Cutter for English.
 * 
 * <p>
 * SimpleENCutter is used to cut English text into words. It use a arithmetic similar 
 * with Apache open source project "Lucene" to change a English word to 
 * it's prototype.
 * @author YiBin.H
 * @version 1.0
 * @see org.jbox.textCutter.CutterBox
 * @see org.jbox.textCutter.util.LanguageFilter
 *
 */
public class SimpleENCutter extends AbstractCutter {
	protected String highlight(String sentence,Collection<String> keywords,String color){
		String[] words = sentence.trim().split("\\s+");
		StringBuffer tempSentence = new StringBuffer(sentence.length()+sentence.length());
		for(String word:words){
			String prototype = this.filterWord(word);
			if(keywords.contains(prototype)){
				tempSentence.append("<font color=" + color + ">" + word + "</font>");
			}else{
				tempSentence.append(word);
			}
		}
		return tempSentence.toString();
	}
	@Override
	public Collection<String> cutSentenceToWord(String sentence) {
		ArrayList<String> wordList = new ArrayList<String>();
		String[] words = sentence.trim().split("\\s+");
		for(String word:words){
			word = this.filterWord(word);
			wordList.add(word);
		}
		return wordList;
	}
	private String filterWord(String word){
		word = word.toLowerCase().trim();
		word = this.step0(word);
		word = this.step1(word);
		word = this.step2(word);
		word = this.step3(word);
		word = this.step4(word);
		word = this.step5(word);
		word = this.step6(word);
		//	word = this.step7(word);
		return word;
	}
	private boolean isConsonant(String s,int i) {
		switch (s.charAt(i)) {
		case 'a':
		case 'e':
		case 'i':
		case 'o':
		case 'u':
			return false;
		case 'y':
			return (i == 0) ? true : !isConsonant(s,i - 1);
		default:
			return true;
		}
	}

	private int countConsonant(String s) {
		int count = 0;
		for(int i = 0;i<s.length();i++){
			if(this.isConsonant(s, i)){
				count++;
			}
		}
		return count;
	}
	
	private boolean hasVowel(String s){
		for(int i = 0;i<s.length();i++){
			if(!this.isConsonant(s,i)){
				return true;
			}
		}
		return false;
	}
	/*
	private boolean isCVC(String s,int i){
		if(i<3||!this.isConsonant(s, i)||this.isConsonant(s, i-1)||!this.isConsonant(s, i-2)){
			return false;
		}
		else{
		      int ch = s.charAt(i);
		      if (ch == 'w' || ch == 'x' || ch == 'y') return false;
		}
		return true;
	}
	*/
	private boolean isDoubleConsonant(String s,int i){
		if(i<0||s.substring(0,i).length()<2)return false;
		if(s.charAt(i)!=s.charAt(i-1))return false;
		return this.isConsonant(s, i);
	}
	private String step0(String s){
		if(s.matches("\\w*'s")){
			s = s.substring(0,s.length()-2);
			return s;
		}
		if(s.matches("\\w*s'")){
			s = s.substring(0,s.length() - 1);
			return s;
		}
		if(s.matches("(is|are|am)")){
			s = "be";
			return s;
		}
		if(s.matches("(his|him|himself)")){
			s = "he";
			return s;
		}
		if(s.matches("(my|me|myself)")){
			s = "he";
			return s;
		}
		if(s.matches("(your|yourself|yourselves|yours)")){
			s = "you";
			return s;
		}
		if(s.matches("(their|them|theirs|themselves)")){
			s = "they";
			return s;
		}
		return s;
	}
	private String step1(String s) {
		if(s.length()==1)return s;
		if (s.matches("\\w*s")) {
			if (s.matches("\\w*(sses|ies)")) {
				s = s.substring(0, s.length() - 2);
			} else if (!s.matches("\\w*ss")) {
				s = s.substring(0, s.length() - 1);
			}
		}
		return s;
	}

	private String step2(String s) {
		if(s.length()==1)return s;
		int consonantCount = this.countConsonant(s);
		if (s.matches("\\w*eed")) {
			if(consonantCount>0){
				s = s.substring(0,s.length()-1);
			}
		}else if(s.matches("\\w*ed")){
			if(s.matches("\\w*(ated|bled|ized)")){
				s = s.substring(0,s.length()-1);
			}else if(this.isDoubleConsonant(s, s.length()-3)&&s.matches("\\w*[^lsz]ed")){
				s = s.substring(0,s.length()-3);
			}else if(this.countConsonant(s)>1){
				s = s.substring(0,s.length()-2);
			}
		}else if(s.matches("\\w*ing")){
			if(s.matches("\\w*(ating|bling|izing)")){
				s = s.replace("ing", "e");
			}else if(this.isDoubleConsonant(s, s.length()-4)&&s.matches("\\w*[^lsz]ing")){
				s = s.substring(0,s.length()-4);
			}else if(this.countConsonant(s)>1){
				s = s.substring(0,s.length()-3);
			}
		}
		return s;
	}
	private String step3(String s){
		if(s.length()==1)return s;
		if(s.matches("\\w*y")&&this.hasVowel(s)){
			s = s.substring(0,s.length()-1)+"i";
		}
		return s;
	}
	private String step4(String s){
		if(s.length()==1)return s;
	    switch (s.charAt(s.length()-2)) {
	    case 'a':
	      if (s.matches("\\w*ational")) { s = s.replace("ational", "ate"); break; }
	      if (s.matches("\\w*tional")) { s = s.replace("tional","tion"); break; }
	      break;
	    case 'c':
	      if (s.matches("\\w*(enci|anci)")) { s = s.replace("nci","nce"); break; }
	      break;
	    case 'e':
	      if (s.matches("\\w*izer")) { s = s.replace("izer","ize"); break; }
	      break;
	    case 'l':
	      if (s.matches("\\w*bli")) { s = s.replace("bli","ble"); break; }
	      if (s.matches("\\w*alli")) { s = s.replace("alli","al"); break; }
	      if (s.matches("\\w*entli")) { s = s.replace("entli","ent"); break; }
	      if (s.matches("\\w*eli")) { s = s.replace("eli","e"); break; }
	      if (s.matches("\\w*ousli")) { s = s.replace("ousli","ous"); break; }
	      if (s.matches("\\w*fulli")) { s = s.replace("ousli","full"); break; }
	      break;
	    case 'o':
	      if (s.matches("\\w*ization")) { s = s.replace("ization","ize"); break; }
	      if (s.matches("\\w*ation")) { s = s.replace("ation","ate"); break; }
	      if (s.matches("\\w*ator")) {s = s.replace("ator","ate"); break; }
	      break;
	    case 's':
	      if (s.matches("\\w*alism")) {s = s.replace("alism","al"); break; }
	      if (s.matches("\\w*iveness")) { s = s.replace("iveness","ive"); break; }
	      if (s.matches("\\w*fulness")) {s = s.replace("fulness","ful"); break; }
	      if (s.matches("\\w*ousness")) { s = s.replace("ousness","ous"); break; }
	      break;
	    case 't':
	      if (s.matches("\\w*aliti")) { s = s.replace("alitil","al"); break; }
	      if (s.matches("\\w*iviti")) {s =  s.replace("iviti","ive"); break; }
	      if (s.matches("\\w*biliti")) { s = s.replace("biliti","ble"); break; }
	      break;
	    case 'g':
	      if (s.matches("\\w*logi")) { s = s.replace("logi","log"); break; }
	    }
	    return s;
	}
	private String step5(String s){
		if(s.length()==1)return s;
	    switch (s.charAt(s.length()-1)) {
	    case 'e':
	      if (s.matches("\\w*icate")) { s = s.replace("icate","c"); break; }
	      if (s.matches("\\w*ative")) {s = s.replace("ative",""); break; }
	      if (s.matches("\\w*alize")) {s = s.replace("alize","al"); break; }
	      break;
	    case 'i':
	      if (s.matches("\\w*iciti")) { s = s.replace("iciti","ic"); break; }
	      break;
	    case 'l':
	      if (s.matches("\\w*ical")) { s = s.replace("ical","ic"); break; }
	      if (s.matches("\\w*ful")) {s = s.replace("ful",""); break; }
	      break;
	    case 's':
	      if (s.matches("ness$")) { s = s.replace("ness",""); break; }
	      break;
	    }
	    return s;
	}
	private String step6(String s){
		if(s.length()==1)return s;
	    switch (s.charAt(s.length()-2)) {
	    case 'a':
	      if (s.matches("\\w*al"))
	    	  s.replaceAll("al","");
	      break;
	    case 'c':
	      if (s.matches("\\w*(ance|ence)"))
	    	  s.replaceAll("(ance|ence)","");
	      break;
	    case 'e':
	      if (s.matches("\\w*er")){
	    	  s = s.replaceAll("er","");
	    	  if(this.isDoubleConsonant(s, s.length()-1)){
	    		  s = s.substring(0,s.length()-1);
	    	  }
	    	  break;
	      }
	    case 'i':
	      if (s.matches("\\w*ic"))  s = s.replaceAll("ic","");break;
	    case 'l':
	      if (s.matches("\\w*(able|ible)"))
	    	  s.replaceAll("(able|ible)","");
	      break; 
	    case 'n':
	      if (s.matches("\\w*(ant|ement|ment|ent)"))
	      	s.replaceAll("(ant|ement|ment|ent)","");
	      break;
	    case 'o':
	      if (s.matches("\\w*(ion|ou)"))
	    	  s.replaceAll("(ion|ou)","");
	      break;
	    case 's':
	      if (s.matches("\\w*ism"))
	      	s.replaceAll("ism","");
	      break;
	    case 't':
	      if (s.matches("\\w*(ate|iti)"))
	    	  s.replaceAll("(ate|iti)","");
	      break;
	    case 'u':
	      if (s.matches("\\w*ous"))
	    	  s.replaceAll("ous","");
	      break;
	    case 'v':
	      if (s.matches("\\w*ive"))
	    	  s.replaceAll("ive","");
	      break;
	    case 'z':
	      if (s.matches("\\w*ize"))
	    	  s.replaceAll("ize","");
	      break;
	    default:
	      break;
	    }
	    return s;
	}
/*
	private String step7(String s){
		if(s.charAt(s.length()-1)=='e'){
			int a = this.countConsonant(s);
			if(a>1||a==1&&this.isCVC(s, s.length()-2)){
				s = s.substring(0,s.length()-1);
			}
		}
		if(s.matches("ll$")){
			s = s.substring(0,s.length()-1);
		}
		return s;
	}
*/

}
