package org.jbox.webSpider.simpleSpider;
/**
 * Thrown when encoding of a page couldn't be resolved. 
 * @author YiBin.H
 * @version 1.0
 *
 */
public class UnknownEncodingException extends Exception{

	private static final long serialVersionUID = -5894386395579921133L;
	
	public UnknownEncodingException (){
		super("unknow encoding.");
	}
	public UnknownEncodingException (String msg){
		super(msg);
	}
	public UnknownEncodingException (String msg,Throwable cause){
		super(msg,cause);
	}
	public UnknownEncodingException (Throwable cause){
		super(cause);
	}
}
