package com.tencent.weibo.design;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.tencent.weibo.api.T_API;
import com.tencent.weibo.utils.WeiBoConst;

public class Issue implements ActionListener{
	
	private QWeiboFrame qwf;
	private JTextArea jta;
	private JDialog jd;
	private JButton jb;
	public Issue(QWeiboFrame qwf) {
		this.qwf = qwf;
		init();
	}
	public void init() {
// jd = new JDialog(qwf,,true);
		 jd = new JDialog();
		 jd.setTitle("腾讯-发微薄");
		jd.setModal(true);
		 JLabel jl = new JLabel("内容:");
		 jd.add(jl,BorderLayout.NORTH);
		 jta = new JTextArea(5,20);
		 jta.setLineWrap(true);
		 
		 JScrollPane jsp = new JScrollPane(jta);
		jd.add(jsp,BorderLayout.CENTER);
		
		JPanel jp = new JPanel();
		jb = new JButton("发布");
		jp.add(jb);
		jd.add(jp,BorderLayout.SOUTH);
		jb.addActionListener(this);
		jd.setBounds(200, 150, 400, 200);
		jd.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String str=jta.getText() ;
		
		if(str.isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入文字！");
			jta.requestFocus();
		}
		else {
			publish();
			jd.dispose();
		}
	}
	
	public void publish() {
		
			T_API t = new T_API();
			try {
				t.add(qwf.getOauth(),  WeiBoConst.ResultType.ResultType_Json, jta.getText(), "10.99.163.149");
				JOptionPane.showMessageDialog(null, "发布成功！");
			//	qwf.getFriendsInfo();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		
	}
}
