package weibo.design;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;


public class JPDesign extends JTextPane implements MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	WeiboFrame wf;
	/**
	 * Create the panel.
	 */
	public JPDesign(WeiboFrame wf) {
		this.wf = wf;
	}

	public void setTexts(Long id,URL url, String comments, String rt, String user,
			String content, String rcontent, String date)
			throws BadLocationException {
		this.id = id;
		this.setVisible(true);
		this.setSize(230, 230);
		this.setEditable(false);
		this.setBackground(Color.white);
		insertIcon(new ImageIcon(url));
		Document doc = getDocument();

		SimpleAttributeSet attrset = new SimpleAttributeSet();
		StyleConstants.setForeground(attrset, Color.blue);
		StyleConstants.setFontSize(attrset, 15);
		SimpleAttributeSet attrset1 = new SimpleAttributeSet();
		StyleConstants.setBackground(attrset1, Color.gray);

		SimpleAttributeSet attrset2 = new SimpleAttributeSet();
		StyleConstants.setForeground(attrset2, Color.cyan);
		StyleConstants.setAlignment(attrset2, StyleConstants.ALIGN_RIGHT);
		
		doc.insertString(doc.getLength(), "          "+comments+"  "+rt+"\n", attrset2);
		doc.insertString(doc.getLength(), user + ":",attrset);
		doc.insertString(doc.getLength(), content, null);
		if (rcontent != null)
			doc.insertString(doc.getLength(), "\n"+ rcontent, attrset1);
		
		doc.insertString(doc.getLength(),"\n" + date, null);
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
			new CommentsDialog(id,wf);
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
