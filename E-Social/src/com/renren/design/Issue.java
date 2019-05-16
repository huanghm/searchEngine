package com.renren.design;

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

import com.renren.api.client.param.impl.AccessToken;

public class Issue implements ActionListener{
	
	private RenRenFrame rrf;
	private JTextArea jta;
	private JDialog jd;
	private JButton jb;
	public Issue(RenRenFrame rrf) {
		this.rrf = rrf;
		init();
	}
	public void init() {
	
		 jd = new JDialog();
		 jd.setTitle("人人-新鲜事");
		jd.setModal(true);
		 JPanel jp = new JPanel();
		 JLabel jl = new JLabel("内容:");
		 
		 
		 JPanel jp1 = new JPanel();
		 jp1.add(jl);
		 jta = new JTextArea(5,20);
		 jta.setLineWrap(true);
		 //jp1.add(jta);
		 
		 JScrollPane jsp = new JScrollPane(jta);
		jp1.add(jsp);
		
		jd.add(jp1,BorderLayout.CENTER);
		
		JPanel jp2 = new JPanel();
		jb = new JButton("发布");
		jp2.add(jb);
		jd.add(jp2,BorderLayout.SOUTH);
		
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
		
		String url="http://www.renren.com/"+rrf.getUid();
        //String image="http://www.huohu123.com/static/nav/images/firefox-logo_v2.png";
        String action_link="http://wiki.dev.renren.com/wiki/SDK";
		rrf.getApiClient().getFeedService().publicFeed(jta.getText(), jta.getText(), url,"", "","",action_link, jta.getText(),new AccessToken(rrf.getAccess_token()));
		JOptionPane.showMessageDialog(rrf, "发布成功！");
		rrf.getFriendsInfo();
	}
}
