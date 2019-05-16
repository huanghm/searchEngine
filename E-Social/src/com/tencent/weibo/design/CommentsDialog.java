package com.tencent.weibo.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.weibo.api.T_API;
import com.tencent.weibo.utils.WeiBoConst;


public class CommentsDialog implements ActionListener{
	private long id;
	private QWeiboFrame qwf;
	private String relist;
	private JTextPane panel_info;
	private JScrollPane scrollPane;
	private JDialog jd;
	private JTextField jtf;
	private JButton jb,jc;
	
	public CommentsDialog(long id,String relist,QWeiboFrame qwf) {
		this.id = id;
		this.qwf = qwf;
		this.relist = relist;
		init();
	}
	public void init() {
	//	 jd = new JDialog(qwf,"评论",true);
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
		
		jc = new JButton("转发");
		jp.add(jc);
		jc.addActionListener(this);
		jd.add(jp,BorderLayout.SOUTH);
		jd.setBounds(200, 150, 450, 250);
		
		ComLists();
		jd.setVisible(true);
	}
	
	public void ComLists()  {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd号 HH:mm");
		Document doc = panel_info.getDocument();
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		try {
			JSONObject ret = new JSONObject(relist);
			String o =ret.getString("data");
			if( !o.equals("null")) {
			//	System.out.println(o );
				ret = ret.getJSONObject("data");
				JSONArray ret_lists = ret.getJSONArray("info");
				int num =ret_lists.length();
				for(int i=0;i<num;i++) {
					JSONObject ret1 = (JSONObject) ret_lists.get(i);
					String fenge = "\n----------------------------------------------------------\n";
					SimpleAttributeSet attrset = new SimpleAttributeSet();
					StyleConstants.setForeground(attrset, Color.blue);
					if(ret1.getInt("type") == 7) {
						doc.insertString(doc.getLength(), ret1.getString("nick"), attrset);
						doc.insertString(doc.getLength(),": "+ret1.getString("text")+"\n"+sdf.format(ret1.getLong("timestamp"))+fenge,null);
					}
				}
			}
			
		}  catch (JSONException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
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
		else if(e.getSource() == jc) {
			if(str.isEmpty())
				jtf.setText(" ");
			repost();
		}
	}
	
	public void publish() {
			T_API t = new T_API();
			try {
				t.comment(qwf.getOauth(),WeiBoConst.ResultType.ResultType_Json, jtf.getText(), "10.99.163.149", Long.toString(id));
				JOptionPane.showMessageDialog(null, "发布成功！");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void repost() {
		
			T_API t = new T_API();
			try {
				t.re_add(qwf.getOauth(), WeiBoConst.ResultType.ResultType_Json, jtf.getText(), "10.99.163.149", Long.toString(id));
				JOptionPane.showMessageDialog(null, "转发成功！");
			} catch (Exception e) {
				e.printStackTrace();
			}	
	}
}