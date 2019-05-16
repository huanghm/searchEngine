package com.tencent.weibo.design;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.weibo.api.T_API;
import com.tencent.weibo.utils.WeiBoConst;


public class JPDesign extends JTextPane implements MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	QWeiboFrame qwf;
	/**
	 * Create the panel.
	 */
	public JPDesign(QWeiboFrame qwf) {
		this.qwf = qwf;
	}

	public void setTexts(JSONObject friend)
			throws BadLocationException, JSONException, MalformedURLException {
		this.id = friend.getLong("id");
		this.setVisible(true);
		this.setSize(230, 230);
		this.setEditable(false);
		this.setBackground(Color.white);
		String head = friend.getString("head");
		URL url = new URL(head+"/50");
		insertIcon(new ImageIcon(url));
		Document doc = getDocument();

		SimpleAttributeSet attrset = new SimpleAttributeSet();
		StyleConstants.setForeground(attrset, Color.blue);
		StyleConstants.setFontSize(attrset, 15);
		SimpleAttributeSet attrset1 = new SimpleAttributeSet();
		StyleConstants.setBackground(attrset1, Color.gray);

		SimpleAttributeSet attrset2 = new SimpleAttributeSet();
		StyleConstants.setForeground(attrset2, Color.gray);
		StyleConstants.setAlignment(attrset2, StyleConstants.ALIGN_RIGHT);
		
		doc.insertString(doc.getLength(),friend.getString("nick")+":", attrset);
		JSONObject source=null;
		if(friend.getInt("type") == 2) {
			doc.insertString(doc.getLength()," 转播 ", attrset2);
			source = friend.getJSONObject("source");
		}
		doc.insertString(doc.getLength(), friend.getString("text"), null);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		
		if(source != null) {
			long time= source.getLong("timestamp");
			Timestamp tstamp = new Timestamp(time*1000);
			doc.insertString(doc.getLength(),"\n"+source.getString("nick")+":"+source.getString("text")+"\n\n"
					+sdf.format(tstamp)+" 来自"+source.getString("from"), attrset1);
		}
		String fenge="\n----------------------------------------------------\n";
		long time= friend.getLong("timestamp");
		Timestamp tstamp = new Timestamp(time*1000);
		doc.insertString(doc.getLength(), "\n"+sdf.format(tstamp)+" 来自"+friend.getString("from")+
				   "\n评论("+friend.getInt("mcount")+") 转发("+friend.getInt("count")+")"+fenge,null);
	
		int height = doc.getLength()+5;
	//	System.out.println(height);
		this.setSize(230, height);
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int num = e.getClickCount();
		if(num == 2) {
			
			new Thread(new Runnable(){
				@Override
				public void run() {
					T_API t = new T_API();
					try {
					String relist = t.re_list(qwf.getOauth(), WeiBoConst.ResultType.ResultType_Json,"1", Long.toString(id), "0","0", "20","0");
					new CommentsDialog(id,relist,qwf);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}	
			}).start();	
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
