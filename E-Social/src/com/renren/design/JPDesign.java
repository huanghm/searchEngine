package com.renren.design;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JPDesign extends JTextPane implements MouseListener {
	
	private Long aid,sid;
	private RenRenFrame renren;
	private JSONObject comments;
	/**
	 * Create the panel.
	 */
	public JPDesign(RenRenFrame renren) {
		this.renren = renren;
	}

	public void setTexts(Long aid,Long sid,String url, String name, String title, String description,
			String message, String date,JSONObject comments, JSONObject trace,JSONArray attachment) throws BadLocationException, UnsupportedEncodingException, MalformedURLException
			 {
		this.aid = aid;
		this.sid = sid;
		this.setVisible(true);
		this.setSize(230, 230);
		this.setEditable(false);
		this.setBackground(Color.white);
		URL url1;
		url1 = new URL(url);
		insertIcon(new ImageIcon(url1));
		
		Document doc = getDocument();

		SimpleAttributeSet attrset = new SimpleAttributeSet();
		StyleConstants.setForeground(attrset, Color.blue);
		StyleConstants.setFontSize(attrset, 13);
		SimpleAttributeSet attrset1 = new SimpleAttributeSet();
		StyleConstants.setBackground(attrset1, Color.gray);

		SimpleAttributeSet attrset2 = new SimpleAttributeSet();
		StyleConstants.setForeground(attrset2, Color.lightGray);
		
		
		
		doc.insertString(doc.getLength(), name+ ":",attrset);
		if(trace != null) {
			//String tracetext = (String)((JSONObject)trace.get(0)).get("text");
			String tracetext = (String)trace.get("text");
			if(tracetext != null) 
				doc.insertString(doc.getLength(), tracetext+"\n", null);
		}
		doc.insertString(doc.getLength(), title, null);
		if(description !=null)
			doc.insertString(doc.getLength(), "\n"+ description, attrset1);
		if (message != null)
			doc.insertString(doc.getLength(), "\n"+ message, attrset1);
		
		
		if(attachment != null && attachment.size()>0) {
			String media_type = (String)((JSONObject)attachment.get(0)).get("media_type");
			
			if( media_type != null){
				if(media_type.equals("status")) {
					String content = (String)((JSONObject)attachment.get(0)).get("content");
					doc.insertString(doc.getLength(), "\n类型:"+media_type+"    内容:"+content, attrset2);
				}
				else {
					String href = (String)((JSONObject)attachment.get(0)).get("href");
					if(href != null)
						href = URLDecoder.decode(href, "utf-8");
				
					doc.insertString(doc.getLength(), "\n类型:"+media_type +"    地址:"+href, attrset2);
				}
			}
		}
		
	//	int coms = (Integer)((JSONObject)comments.get(1)).get("count");
		this.comments = comments;
		Long coms = (Long)comments.get("count");
		doc.insertString(doc.getLength(),"\n评论("+coms+")         "+date , null);
	//	doc.insertString(doc.getLength(), "\n"+comments, attrset2);
		String fenge="\n----------------------------------------------------\n";
		doc.insertString(doc.getLength(), fenge, null);
		
		int height = doc.getLength()+5;
	//	System.out.println(height);
		this.setSize(230, height);
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int num = e.getClickCount();
		if(num == 2) {
		//	System.out.println("sid="+sid+"+aid="+aid);
			CommentsDialog cd = new CommentsDialog(aid,sid,renren,comments);
		//	cd.ComLists();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {	
	}

	@Override
	public void mousePressed(MouseEvent arg0) {	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
