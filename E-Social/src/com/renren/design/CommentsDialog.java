package com.renren.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;

import com.renren.api.client.param.impl.AccessToken;
import com.renren.api.client.services.RenrenApiException;

public class CommentsDialog implements ActionListener{
	private Long aid,sid;
	private RenRenFrame renren;
	private JTextPane panel_info;
	private JScrollPane scrollPane;
	private JDialog jd;
	private JTextField jtf;
	private JButton jb,jc;
	private JSONObject comments;
	private boolean tag=true;
	
	public CommentsDialog(Long aid,Long sid,RenRenFrame renren,JSONObject comments) {
		this.aid = aid;
		this.sid = sid;
		this.comments = comments;
		this.renren= renren;
		init();
	}
	public void init() {
		 jd = new JDialog();
		 jd.setTitle("评论");
		jd.setModal(true);
		 panel_info = new JTextPane();
//		 panel_info.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()/3));
		 scrollPane = new JScrollPane(panel_info);
		 panel_info.setBackground(Color.white);
		 panel_info.setEditable(false);
		panel_info.setAlignmentX(20);
		panel_info.setAlignmentY(20);
		jd.add(scrollPane,BorderLayout.CENTER);
		
		 JPanel jp = new JPanel();
		jtf = new JTextField();
		jp.add(jtf);
		jtf.setColumns(20);
		jb = new JButton("评论");
		jp.add(jb);
		jb.addActionListener(this);
		
		jc = new JButton("分享");
		jp.add(jc);
		jc.addActionListener(this);
		jd.add(jp,BorderLayout.SOUTH);
		jd.setBounds(200, 150, 450, 250);
		
		ComLists();
//		if(tag)
			jd.setVisible(true);
	}
	
	public void ComLists() {
		try{
			JSONArray comlist = (JSONArray) renren.getApiClient().getStatusService().getComments(sid, aid, 1, 20, 0,new AccessToken(renren.getAccess_token()));
			int num = comlist.size();
			if(num > 0) {
				Document doc = panel_info.getDocument();
				try {
					doc.remove(0, doc.getLength());
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				String fenge = "\n----------------------------------------------------------\n";
				SimpleAttributeSet attrset = new SimpleAttributeSet();
				StyleConstants.setForeground(attrset, Color.blue);
				int i;
				//JSONObject com =(JSONObject)comlist.get(i)
				
				for(i=0; i< num; i++) {
					JSONObject comment = (JSONObject)comlist.get(i);
					try {
						doc.insertString(doc.getLength(), (String) comment.get("name"), attrset);
						doc.insertString(doc.getLength(),": "+comment.get("text")+"\n"+comment.get("time")+fenge,null);
					} catch (BadLocationException e) {	
						e.printStackTrace();
					}
					
				}
			}
		}catch(RenrenApiException e){
		//	JOptionPane.showMessageDialog(null, "获取数据失败！");
			tag=false;
		}
		
		if(!tag) {
			  long num=(Long)comments.get("count");
			  if(num>0) {
				  JSONArray coms = (JSONArray)comments.get("comment");
				  num = coms.size();
					Document doc = panel_info.getDocument();
					try {
						doc.remove(0, doc.getLength());
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					String fenge = "\n----------------------------------------------------------\n";
					SimpleAttributeSet attrset = new SimpleAttributeSet();
					StyleConstants.setForeground(attrset, Color.blue);
					int i;
					//JSONObject com =(JSONObject)comlist.get(i)
					
					for(i=0; i< num; i++) {
						JSONObject comment = (JSONObject)coms.get(i);
						try {
							doc.insertString(doc.getLength(), (String) comment.get("name"), attrset);
							doc.insertString(doc.getLength(),": "+comment.get("text")+"\n"+comment.get("time")+fenge,null);
						} catch (BadLocationException e) {	
							e.printStackTrace();
						}
						
					}
				}
			  jb.setEnabled(false);
			  jc.setEnabled(false);
		}
			
				//System.out.println(comment.getUser().getName()+comment.getCreatedAt()+"++"+comment.getText());
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String str=jtf.getText() ;
		if(e.getSource() ==  jb) {
			if(str.isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入文字！");
				jtf.requestFocus();
			}
			else {
				publish();
				ComLists();
			//	jd.dispose();
			}
		}
		else if(e.getSource() == jc)
			share();
	}
	
	public void publish() {
		//System.out.println("sid="+sid+"+aid="+aid);
		int res = renren.getApiClient().getStatusService().addComment(sid, aid, jtf.getText(), 0,new AccessToken(renren.getAccess_token()));
		//	int res=renren.getApiClient().getBlogService().addComment(id, renren.getUid(), jtf.getText(), 0,0, BlogService.BLOG_FOR_USER,new AccessToken(renren.getAccess_token()));
			JOptionPane.showMessageDialog(null, "发布成功！");
			jtf.setText("");
	}
	
	public void share() {
		String str=jtf.getText() ;
		if(str.isEmpty())
			str="";
		//	System.out.println("sid="+sid+"+aid="+aid);
			JSONObject json = renren.getApiClient().getStatusService().forwardStatus(sid, aid,str, new AccessToken(renren.getAccess_token()));
			Long id=(Long)json.get("id");
	        Assert.assertTrue(id>0);
			
			JOptionPane.showMessageDialog(null, "转发成功！");
			
	}
}