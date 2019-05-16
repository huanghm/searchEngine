package org.jbox.textCutter.util;
/**
 * Thrown when couldn't create LanguageFilter.
 * @author YiBin.H
 * @version 1.0
 *
 */
public class LangFilterInitException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public LangFilterInitException(){
	}
	public LangFilterInitException(String msg){
		super(msg);
	}
	public LangFilterInitException(String msg,Throwable cause){
		super(msg,cause);
	}
	public LangFilterInitException(Throwable cause){
		super(cause);
	}
}
