package weibo.design;

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

import weibo4j.Weibo;
import weibo4j.WeiboException;

public class Issue implements ActionListener{
	
	private WeiboFrame wf;
	private JTextArea jta;
	private JDialog jd;
	private JButton jb;
	public Issue(WeiboFrame wf) {
		this.wf = wf;
		init();
	}
	public void init() {
		 jd = new JDialog();
		 jd.setTitle("新浪-发微薄");
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
		Weibo weibo = wf.getWeibo();
		try {
			weibo.updateStatus(jta.getText());
			JOptionPane.showMessageDialog(null, "发布成功！");
			wf.getFriendsInfo();
			
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
