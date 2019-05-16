package weibo.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

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

import weibo4j.Comment;
import weibo4j.Weibo;
import weibo4j.WeiboException;

public class CommentsDialog implements ActionListener{
	private Long id;
	private WeiboFrame wf;
	private JTextPane panel_info;
	private JScrollPane scrollPane;
	private JDialog jd;
	private JTextField jtf;
	private JButton jb,jc;
	
	public CommentsDialog(Long id,WeiboFrame wf) {
		this.id = id;
		this.wf = wf;
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
		
		jc = new JButton("转发");
		jp.add(jc);
		jc.addActionListener(this);
		jd.add(jp,BorderLayout.SOUTH);
		jd.setBounds(200, 150, 450, 250);
		
		ComLists();
		jd.setVisible(true);
	}
	
	public void ComLists() {
		Weibo weibo = wf.getWeibo();
		List<Comment> comments;
		try {
			comments = weibo.getComments(Long.toString(id));
			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd号 HH:mm");
			
			Document doc = panel_info.getDocument();
			
			doc.remove(0, doc.getLength());
			
			String fenge = "\n----------------------------------------------------------\n";
			SimpleAttributeSet attrset = new SimpleAttributeSet();
			StyleConstants.setForeground(attrset, Color.blue);
			
			comments = weibo.getComments(Long.toString(id));
			for(Comment comment : comments) {
				doc.insertString(doc.getLength(), comment.getUser().getName(), attrset);
				doc.insertString(doc.getLength(),": "+comment.getText()+"\n"+sdf.format(comment.getCreatedAt())+fenge,null);
				//System.out.println(comment.getUser().getName()+comment.getCreatedAt()+"++"+comment.getText());
			}
		} catch (WeiboException e3) {
			e3.printStackTrace();
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
		else if(e.getSource() == jc)
			repost();
	}
	
	public void publish() {
		Weibo weibo = wf.getWeibo();
		try {
			weibo.updateComment(jtf.getText(), Long.toString(id), null);
			JOptionPane.showMessageDialog(null, "发布成功！");
			
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
	
	public void repost() {
		Weibo weibo = wf.getWeibo();
		try {
			weibo.repost(Long.toString(id),jtf.getText());
			JOptionPane.showMessageDialog(null, "转发成功！");
			
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}