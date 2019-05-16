package weibo.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import weibo4j.Count;
import weibo4j.Status;
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;

public class WeiboFrame extends JPanel implements ActionListener{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField user;           
	private JPasswordField pass;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel label_3, label_4, label_5, lblNewLabel;
	private JPanel panel, panel_1, funclist, panel_list;
	private JTextPane panel_info;
	private JScrollPane scrollPane = null;
	private Document doc = null; // 插入文字样式
	
	private Weibo weibo;
	private JButton fresh, home, friends, add;
	private List<JTextPane> textpanes= new ArrayList<JTextPane>();

	//private Container gpane;
	 private Properties props;
	 private Writer write;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new WeiboFrame();
		//new Thread(new WeiboFrame()).start();
	}

	public Weibo getWeibo() {
		return weibo;
	}

	public void setWeibo(Weibo weibo) {
		this.weibo = weibo;
	}

	/**
	 * Create the application.
	 */
	public WeiboFrame() {
		//initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		
//		this.setResizable(false);
//		this.setTitle("E社区");
//		
//		this.setIconImage(Toolkit.getDefaultToolkit().getImage(WeiboFrame.class.getResource("/weibo/design/image/esocial.jpg")));
//		
		this.setLayout(new BorderLayout());
	//	setBounds(100, 100, 250, 580);
		this.setSize(250, 480);
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		gpane = this.getContentPane();
		panel = new JPanel();
		this.add(panel, BorderLayout.NORTH);

		this.Configuration();
	//	System.out.println(props.getProperty("username"));
		JLabel label_1 = new JLabel("昵称");

		JLabel label_2 = new JLabel("个性签名");

		textField = new JTextField();
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setColumns(10);
		textField.setEditable(false);

		textField_1 = new JTextField();
		textField_1.setBackground(Color.LIGHT_GRAY);
		textField_1.setColumns(10);
		textField_1.setEditable(false);

		label_3 = new JLabel("");

		label_4 = new JLabel("");

		label_5 = new JLabel("");

		lblNewLabel = new JLabel("");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														Alignment.TRAILING,
														gl_panel.createSequentialGroup()
																.addContainerGap()
																.addComponent(
																		lblNewLabel)
																.addPreferredGap(
																		ComponentPlacement.UNRELATED)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addComponent(
																						label_1)
																				.addComponent(
																						label_2))
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addGap(54)
																								.addComponent(
																										label_5))
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addGap(3)
																								.addComponent(
																										textField,
																										GroupLayout.PREFERRED_SIZE,
																										74,
																										GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										textField_1,
																										GroupLayout.PREFERRED_SIZE,
																										100,
																										GroupLayout.PREFERRED_SIZE)))
																.addGap(40))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addGap(22)
																.addComponent(
																		label_3,
																		GroupLayout.PREFERRED_SIZE,
																		47,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		ComponentPlacement.UNRELATED)
																.addComponent(
																		label_4)))
								.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addContainerGap(
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.BASELINE)
																				.addComponent(
																						label_1)
																				.addComponent(
																						textField,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.BASELINE)
																				.addComponent(
																						label_2)
																				.addComponent(
																						textField_1,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		ComponentPlacement.RELATED))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addGap(22)
																.addComponent(
																		lblNewLabel,
																		GroupLayout.PREFERRED_SIZE,
																		45,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		ComponentPlacement.RELATED)))
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(label_3)
												.addComponent(label_4)
												.addComponent(
														label_5,
														GroupLayout.PREFERRED_SIZE,
														40,
														GroupLayout.PREFERRED_SIZE))));
		panel.setLayout(gl_panel);
		panel.setVisible(false);

		panel_1 = new JPanel();
		this.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JLabel labelu = new JLabel("用户名：");
		labelu.setBounds(27, 143, 54, 15);
		panel_1.add(labelu);

		JLabel labelm = new JLabel("密码：");
		labelm.setBounds(27, 181, 54, 15);
		panel_1.add(labelm);
		
		user = new JTextField();//"ming311@yahoo.cn");
		String username = props.getProperty("username");
		if(username != null)
			user.setText(username);
		panel_1.add(user);
		user.setBounds(91, 140, 97, 28);
		user.setFocusable(true);

		user.setColumns(10);

		pass = new JPasswordField();//"ming2961");
		panel_1.add(pass);
		pass.setBounds(91, 181, 97, 28);
		String password = props.getProperty("password");
		if(password != null)
			user.setText(username);
		pass.setText(password);
		JButton button = new JButton("登录");
		panel_1.add(button);
		button.setBounds(69, 235, 69, 28);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "确定授权登录？",
						"请选择：", JOptionPane.OK_CANCEL_OPTION);
				if (response == 0) {
					WeiboLogin();
					
				}
			}
		});

		JLabel label = new JLabel("");
		panel_1.add(label);
		
		label.setBounds(78, 68, 60, 43);
		label.setIcon(new ImageIcon(getClass().getResource("image/sina.jpg")));
		//this.setLocationRelativeTo(null);

	}
	
	public void publish(String content) {
	
		try {
			weibo.updateStatus(content);
			getFriendsInfo();
			
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 验证用户名或密码有无错误，若正确显示主界面
	 */
	public void WeiboLogin() {
		OAuthUpdate oa = new OAuthUpdate();
		String username = user.getText();
		String password = pass.getText();
		// System.out.println(username+":"+password);
		
		if (false == oa.connect(username, password)) {
			JOptionPane.showMessageDialog(this, "用户名或密码错误!", "错误",
					JOptionPane.ERROR_MESSAGE);
			user.setText("");
			pass.setText("");
			user.setFocusable(true);
		} else {
		
			String path = this.getClass().getClassLoader().getResource("config.properties").getPath();
			//System.out.println(path);
			try {
				write=new FileWriter(path);
				 props.setProperty("username", user.getText());
				 props.setProperty("password", pass.getText());
				 props.store(write, "username");
				 props.store(write, "password");
			} catch (IOException e1) {
				e1.printStackTrace();
			}finally {
				try {
					write.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			weibo = oa.getWeibo();
			try {
				 
				panel_1.setVisible(false);
		
				weibo4j.User u = weibo.verifyCredentials();

				String name = u.getScreenName();
				String descri = u.getDescription();
				URL imaUrl = u.getProfileImageURL();
				
				lblNewLabel.setIcon(new ImageIcon(imaUrl));
				int friends = u.getFriendsCount();
				int followers = u.getFollowersCount();
				int states = u.getStatusesCount();
			
				label_3.setText("粉丝" + Integer.toString(followers));
				label_4.setText("关注" + Integer.toString(friends));
				label_5.setText("微博" + Integer.toString(states));
				// System.out.println(""+Integer.toString(friends)+"+"+Integer.toString(followers)+"="+Integer.toString(states));
				textField.setText(name);
				textField_1.setText(descri);
				panel.setVisible(true);

				getFriendsInfo();
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			FunctionList();
		}
	}
	/*
	 * 获取好友的微博
	 */
	public void getFriendsInfo() {
		List<Status> statuses;
		try {
			statuses = weibo.getFriendsTimeline();
			showInfoNew(statuses);
		//	System.out.println(statuses);
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}
	/*
	 * 获取个人的微博
	 */
	public void getMyInfo() {
		List<Status> statuses;
		try {
			statuses = weibo.getUserTimeline();	
			showInfoNew(statuses);
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}
	/*
	 * 显示好友列表
	 */
	public void getFriendsDetail() {
		
		 panel_info = new JTextPane();
		 
		panel_info.setBackground(Color.white);
		panel_info.setEditable(false);
		doc = panel_info.getDocument();
	//	int size=0;
		if(doc.getLength() == 0) {
			try {
				long[] ids = weibo.getFriendsIDSByUserId(textField.getText(), 1).getIDs();
			//	size = ids.length;
				for (int i = 0; i < ids.length; i++) {
					long id = ids[i];
					// System.out.println(id);
					User user = weibo.showUser(Long.toString(id));
					String fenge = "\n----------------------------------------------------------\n";
					SimpleAttributeSet attrset = new SimpleAttributeSet();
					StyleConstants.setForeground(attrset, Color.blue);
	
					doc.insertString(doc.getLength(), user.getScreenName()
							+ "   描述：" + user.getDescription(), attrset);
					doc.insertString(
							doc.getLength(),
							"\n关注:" + user.getFriendsCount() + "  粉丝："
									+ user.getFollowersCount() + "  微薄："
									+ user.getStatusesCount() + fenge, null);
				}
			} catch (WeiboException e1) {
	
				e1.printStackTrace();
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		//System.out.println("id=" +size);
		if(scrollPane != null) {
			this.remove(scrollPane);
		}
		scrollPane = new JScrollPane(panel_info);
		panel_info.setLocation(3, 3);
		 this.add(scrollPane, BorderLayout.CENTER);
		panel_info.setVisible(true);
		this.validate();
	}
	/*
	 * 排版微博信息
	 */
	public void showInfoNew(List<Status> statuses) {
			panel_list = new JPanel();
			panel_list.setLayout(null);
	
			panel_list.setBackground(Color.white);
			panel_list.setLocation(3,3);
			List<Count> t = getCount(statuses);
			
			int ylen = 0;
			for (Status status : statuses) {
	
				JPDesign jpd = new JPDesign(this);
				String rcontent = null;
				if (status.getRetweeted_status() != null)
					rcontent = status.getRetweeted_status().getText();
				SimpleDateFormat sdf = new SimpleDateFormat("MM月dd号 HH:mm");
				try {
		//			System.out.println(t.toString());
					long tmpid = status.getId();
					long tmpcom=0,tmprt=0;
					for(int j=0;j<t.size();j++){
						if(tmpid == t.get(j).getId()) {
							tmpcom = t.get(j).getComments();
							tmprt = t.get(j).getRt();
							t.remove(j);
							break;
						}
					}
					jpd.setTexts(status.getId(),status.getUser().getProfileImageURL(), "评论("
							+ tmpcom + ")", "转发("
							+ tmprt + ")", status.getUser().getName(),
							status.getText(), rcontent,
							sdf.format(status.getCreatedAt()));
					
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
	
				jpd.setLocation(5, ylen);
				ylen += jpd.getHeight();
				textpanes.add(jpd);
				panel_list.add(jpd);
			}
			if(scrollPane != null) {
				this.remove(scrollPane);
			}
			scrollPane = new JScrollPane(panel_list);
			
			this.add(scrollPane, BorderLayout.CENTER);
			panel_list.setPreferredSize(new Dimension(250, ylen));
			panel_list.setVisible(true);
			this.validate();
	}

	/*
	 * 获取微博的评论和转发情况
	 */
	public List<Count> getCount(List<Status> statuses) {

		try {

			StringBuilder ids = new StringBuilder();
			for (Status status : statuses) {
				ids.append(status.getId()).append(',');
			}
			ids.deleteCharAt(ids.length() - 1);
			List<Count> counts = weibo.getCounts(ids.toString());
			
			return counts;
		} catch (WeiboException e) {	
			e.printStackTrace();
		}

		return null;

	}
	/*
	 * 显示工具栏
	 */
	public void FunctionList() {
		funclist = new JPanel();

		fresh = new JButton("");
		funclist.add(fresh);
		fresh.setToolTipText("刷新");
		fresh.setIcon(new ImageIcon(getClass().getResource("image/Refresh.png")));
		fresh.addActionListener(this);
		fresh.setSize(10, 10);

		home = new JButton("");
		funclist.add(home);
		home.setToolTipText("主页");
		home.setIcon(new ImageIcon(getClass().getResource("image/Home.png")));
		home.addActionListener(this);
		home.setSize(10, 10);

		friends = new JButton("");
		funclist.add(friends);
		friends.setToolTipText("好友");
		friends.setIcon(new ImageIcon(getClass().getResource(
				"image/friends.png")));
		friends.addActionListener(this);
		friends.setSize(10, 10);
	
		add = new JButton("");
		funclist.add(add);
		add.setToolTipText("发微薄");
		add.setIcon(new ImageIcon(getClass().getResource("image/add.png")));
		add.addActionListener(this);
		add.setSize(10, 10);
		this.add(funclist, BorderLayout.SOUTH);
		

	}
	/*
	 *点击事件处理
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add) {
			 new Issue(this);
		} else if (e.getSource() == fresh) {
			getFriendsInfo();
		} else if (e.getSource() == home) {
			getMyInfo();	
		} else if (e.getSource() == friends) {
			new Thread(new InfoThread()).start();
		}
	}
	/*
	 * 载入配置文件
	 */
	 public void Configuration()
	    {
	        props = new Properties();
	       
	        	InputStream is = this.getClass().getClassLoader().getResourceAsStream("config.properties");  
	            try {
					props.load(is);
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					 try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
	           
	      
	    }

	private class InfoThread implements Runnable {

		@Override
		public void run() {
			getFriendsDetail();

		}
	}

	
}
