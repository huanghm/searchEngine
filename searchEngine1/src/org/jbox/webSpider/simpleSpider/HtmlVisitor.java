package org.jbox.webSpider.simpleSpider;

import java.util.LinkedList;

import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.beans.StringBean;
import org.htmlparser.tags.FrameTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.ParserException;
/**
 * A HTML text visitor.
 * 
 * @author YiBin.H
 * @version 1.0
 *
 */
public class HtmlVisitor extends StringBean{
	private static final long serialVersionUID = 3328690980669465798L;
	private Parser p;
	private String[] rules;
	private LinkedList<String> links = new LinkedList<String>();
	private String title;
	private String text;
	/**
	 * Constructs a new HTMLVisitor object with an String array of rules.
	 * @param rules String array containing rules written in REGEXP.
	 *
	 */
	public HtmlVisitor(String[] rules) {
		super();
		this.rules = rules;
		this.setCollapse(true);
		this.setReplaceNonBreakingSpaces(true);
	}
	/**
	 * Visit HTML Tag.
	 */
	public void visitTag(Tag tag) {
		super.visitTag(tag);
		if (tag instanceof FrameTag) {
			String link = ((FrameTag) tag).getFrameLocation();
			if(isMeetRules(link)){
				links.add(link);
			}
		}
		if (tag instanceof LinkTag) {
			String link = ((LinkTag) tag).getLink();
			if(isMeetRules(link)){
				links.add(link);
			}
		}
		if(tag instanceof TitleTag){
			title = ((TitleTag)tag).getTitle();
		}
	}

	private boolean isMeetRules(String link){
		for(String rule:this.rules){
			if(!link.matches(rule)){
				return false;
			}
		}
		return true;
	}
	/**
	 * Return text without HTML tag.
	 * 
	 * @return String text without HTML tag.
	 */
	public String getText() {
		this.text = super.getStrings();
		return this.text;
	}
	/**
	 * Return links in a HTML page which meet the rules.
	 * @return LinkedList object containing links in a HTML page 
	 * which meet the rules.
	 */
	public LinkedList<String> getLinksUnderRules() {
		return links;
	}
	/**
	 * Return title of a page.
	 * @return the title of a page.
	 */
	public String getTitle() {
		if(this.title==null){
			if(this.text!=null&&this.text.length()!=0){
				this.title = this.text.trim().split("\n")[0].trim();
			}
		}
		return title;
	}
	/**
	 * Parse HTML text with specified encoding. 
	 * @param html String of HTML text to parse.
	 * @param encoding String representing encoding for parsing.
	 * @throws ParserException thrown if fail to parse the html. 
	 */
	public void parse(String html,String encoding) throws ParserException {
		p =  Parser.createParser(html, encoding);
		p.visitAllNodesWith(this);
	}
}
