import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import weibo.design.WeiboFrame;

import com.renren.design.RenRenFrame;
import com.renren.design.RenrenConnect;
import com.tencent.weibo.design.QWeiboConnect;
import com.tencent.weibo.design.QWeiboFrame;


public class MainFrame extends JFrame implements ActionListener {

//	private JFrame frame;
//	private JScrollPane scrollPane1,scrollPane2,scrollPane3;
	private JPanel panel,renren,qweibo;
	private RenrenConnect rcon;
	private WeiboFrame weiboframe;
	private RenRenFrame renframe;
	private QWeiboConnect qcon;
	private QWeiboFrame qframe;
	private Container gpane;
	private JButton b1,b2,b3,b4;
	private JTextArea content;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}
	
//	 public void paint(Graphics g)
//	  {
//		 final ImageIcon myimage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("image/1.jpg")));
//		 final Image image = myimage.getImage();
//		 g.drawImage(image, 0, 0,this.getWidth(),this.getHeight(),null);
//		 super.paintComponents(g);
//	  }
	
	/**
	 *初始化
	 */
	private void initialize() {
		
	//	frame = new JFrame();
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		this.setBounds(50, 50, 800, 620);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	frame.getContentPane().setLayout(new GridLayout(1, 3, 1, 1));
		this.setResizable(false);
		this.setTitle("E社区");
		
		
		//建立背景图
	//	frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/image/esocial.jpg")));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("image/esocial.jpg")));
		this.setLayout(null);
		final ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("image/1.jpg")));
		 JPanel panel1 = new JPanel(){
			  public void paintComponent(Graphics g){
				  g.drawImage(image.getImage(), 0, 0, null);
				  super.paintComponent(g);
			  }
		 };
	panel1.setLayout(null);
	 panel1.setOpaque(false);
	setContentPane( panel1 );
	
	gpane = this.getContentPane();
	
		//建立三个客户端面板
		getSina();
		getRenren();
		getQQ();
		
		panel = new JPanel();
		//panel.setLayout(new BorderLayout());
		
		 JLabel jl = new JLabel("内容:");	 
		 JPanel jp1 = new JPanel();
		
		 jp1.add(jl);
			
		content = new JTextArea(5,20);
		content.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(content);
		jp1.add(jsp);
	
		panel.add(jp1);
		
		b4 = new JButton("发布");
		b4.addActionListener(this);
		
		JPanel jp2 = new JPanel();
		jp2.add(b4);
	   panel.add(jp2);
	   panel.setOpaque(false);
		panel.setBounds(100, 500, 500, 100);
		gpane.add(panel);

	}
	
	public void getSina() {
		weiboframe = new WeiboFrame();
		new Thread(new Runnable(){
			@Override
			public void run() {
				
				weiboframe.setBounds(10, 10, 250, 480);
				weiboframe.initialize();
				
			}}).start();
		b1 = new JButton("x");
		gpane.add(b1);
		b1.setBounds(240, 10,20, 20);
		b1.addActionListener(this);
		gpane.add(weiboframe);
	}

	public void getRenren() {
		new Thread(new Runnable(){
			@Override
			public void run() {
	
				renren = new JPanel();		
				renren.setLayout(null);
				JButton button = new JButton("登录");
				renren.add(button);
				button.setBounds(69, 235, 69, 28);
				renren.setBounds(270,10,250,480);
				
				JLabel label = new JLabel("");
				renren.add(label);	
				label.setBounds(58, 68, 130, 50);
				label.setIcon(new ImageIcon(getClass().getResource("image/renren.jpg")));
				
				gpane.add(renren);
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						rcon = new RenrenConnect();
						 renframe = rcon.connect();
						if(renframe == null)
							JOptionPane.showMessageDialog(null, "登录出错!", "错误",JOptionPane.ERROR_MESSAGE);
						else {
							
							gpane.remove(renren);
							renframe.setBounds(270,10,250,480);
							gpane.add(renframe);						
							renframe.initialize();
						}
					}
				});

			}}).start();
		b2 = new JButton("x");
		gpane.add(b2);
		b2.setBounds(500, 10, 20, 20);
		b2.addActionListener(this);
	}
	
	public void getQQ() {
		new Thread(new Runnable(){
			@Override
			public void run() {
			
				qweibo = new JPanel();		
				qweibo.setLayout(null);
				JButton button1 = new JButton("登录");
				qweibo.add(button1);
				button1.setBounds(69, 235, 69, 28);
				qweibo.setBounds(530,10,250,480);
				
				JLabel label1 = new JLabel("");
				qweibo.add(label1);	
				label1.setBounds(48, 68, 150, 50);
				label1.setIcon(new ImageIcon(getClass().getResource("image/qweibo.jpg")));
				
				gpane.add(qweibo);
				button1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						qcon = new QWeiboConnect();
						qframe = qcon.connect();
						if(qframe == null)
							JOptionPane.showMessageDialog(null, "登录出错!", "错误",JOptionPane.ERROR_MESSAGE);
						else {
							gpane.remove(qweibo);
							qframe.setBounds(530,10,250,480);
							gpane.add(qframe);			
							qframe.initialize();
						}
					}
				});

			}}).start();
		b3 = new JButton("x");
		gpane.add(b3);
		b3.setBounds(760, 10, 20, 20);
		b3.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String str = "在";
		if(e.getSource() == b1 && weiboframe != null) {
		//	System.out.println("b1");
			weiboframe.removeAll();
			gpane.remove(weiboframe);
			weiboframe = null;
			gpane.remove(b1);
			getSina();
		}
		if(e.getSource() == b2 && renframe != null) {
		//	System.out.println("b2");
			gpane.remove(renframe);
			renframe.removeAll();
			renframe = null;
			gpane.remove(b2);
			getRenren();
		}
		if(e.getSource() == b3 && qframe != null) {
		//	System.out.println("b3");
			gpane.remove(qframe);
			qframe.removeAll();
			qframe = null;
			gpane.remove(b3);
			getQQ();
		}
		
		if(e.getSource() == b4) {
			String str1=content.getText() ;	
			if(str1.isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入文字！");
				content.requestFocus();
			}
			else {
				if(weiboframe != null) {
					weiboframe.publish(content.getText());
					str += "新浪微博";
				}
				if(renframe != null) {
					renframe.publish(content.getText());
					str += ",人人";
				}
				if(qframe != null) {
					qframe.publish(content.getText());
					str += ",腾讯微博";
				}
				if(!str.equals("在"))
					JOptionPane.showMessageDialog(null, str+"发布成功！");
				else
					JOptionPane.showMessageDialog(null, "发布失败！");
			}
		}
		
	}

}
