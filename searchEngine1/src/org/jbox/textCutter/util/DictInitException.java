package org.jbox.textCutter.util;

/**
 * Thrown when the Dict could be created. 
 *
 * @author YiBin.H
 * @version 1.0
 * @see Dict
 *
 */
public class DictInitException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public DictInitException(){
		super("error when loading Dict.");
	}
	public DictInitException(String msg){
		super(msg);
	}
	public DictInitException(String msg,Throwable cause){
		super(msg,cause);
	}
	public DictInitException(Throwable cause){
		super(cause);
	}
}
