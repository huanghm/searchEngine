package org.jbox.searcher;

import java.awt.Color;

import org.jbox.configuration.Configuration;
import org.jbox.dao.PageHome;
import org.jbox.dao.WordHome;
import org.jbox.searcher.simpleSearcher.PageProxy;
import org.jbox.textCutter.CutterBox;
/**
 * An abstract class define default behavior of Searcher.
 * 
 * @author YiBin.H
 * @version 1.0
 * 
 */
public abstract class AbstractSearcher implements Searcher{
	protected CutterBox cutterBox = Configuration.config().buildCutterBox();
	protected PageHome pageHome;
	protected WordHome wordHome;
	/**
	 * number of pages in table "Page" in data base.
	 */
	protected static long ALLPAGENUM= -1;
	/**
	 * Set PageHome for visiting Page table in data base.
	 * 
	 * @param pageHome for visiting Page table in data base.
	 */
	public void setPageHome(PageHome pageHome) {
		this.pageHome = pageHome;
	}
	/**
	 * Set WordHome for visiting Word table in data base.
	 * 
	 * @param wordHome a WordHome for visiting Word table in data base.
	 */ 
	public void setWordHome(WordHome wordHome) {
		this.wordHome = wordHome;
	}
	/**
	 * Return number of pages in data base.
	 * @return number of pages in data base.
	 */
	protected long getAllPageNum(){
		if(ALLPAGENUM == -1){
			ALLPAGENUM = this.pageHome.findPageNum();
		}
		return ALLPAGENUM;
	}
	/**
	 * Calculator TFIDF. 
	 * @param tf TF.
	 * @param pageNum number of pages.
	 * @return TFIDF.
	 */
	protected double calculateTFIDF(double tf,double pageNum){
		double idf = Math.log(this.getAllPageNum()/pageNum);
		double tfidf = tf*idf;
		return tfidf;
	}
	/**
	 * Rank pages.
	 * 
	 * This method using "Quick sort Algorithm" to rank pages between 
	 * start position and end position by TFIDF.
	 * @param pageProxys {@link PageProxy} array.
	 * @param start start position of the array.
	 * @param end end position of the array.
	 */
	protected void pageRank(PageProxy[] pageProxys,int start,int end){
		if(pageProxys.length<2)return;
		if(start>=end)return;
		int i ,j;
		PageProxy temp;
LOOPJ:		for(i=start,j=end;i<j;j--){
			if(pageProxys[j].getTFIDF()>pageProxys[i].getTFIDF()){
				temp = pageProxys[i];
				pageProxys[i] = pageProxys[j];
				pageProxys[j] = temp;
				for(i++;i<j;i++){
					if(pageProxys[i].getTFIDF()<pageProxys[j].getTFIDF()){
						temp = pageProxys[i];
						pageProxys[i] = pageProxys[j];
						pageProxys[j] = temp;
						continue LOOPJ;
					}
				}
			}
		}
		pageRank(pageProxys,start,i);
		pageRank(pageProxys,i+1,end);
	}
	public String highlight(String text, StringBuffer query, Color c){
		return this.cutterBox.highlight(text, query, c);
	}
}
