package com.tencent.weibo.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.weibo.api.Friends_API;
import com.tencent.weibo.api.Statuses_API;
import com.tencent.weibo.api.T_API;
import com.tencent.weibo.api.User_API;
import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.WeiBoConst;

public class QWeiboFrame extends JPanel implements ActionListener {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField textField;
	private JTextField textField_1;
	private JLabel label_3, label_4, label_5, IconLabel;
	private JPanel panel, funclist, panel_list;
	private JTextPane panel_info;
	private JScrollPane scrollPane = null;
	private Document doc = null; // 插入文字样式
	private String access_token;

	private JButton fresh, home, friends, add;
	private List<JTextPane> textpanes = new ArrayList<JTextPane>();
	private List<JTextPane> textpanes1 = new ArrayList<JTextPane>();
	private OAuth oauth;
	//private Container gpane;

	private int ylen;
	
	
	public OAuth getOauth() {
		return oauth;
	}

	public void setOauth(OAuth oauth) {
		this.oauth = oauth;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new QWeiboFrame();
		// new Thread(new WeiboFrame()).start();
	}

	public QWeiboFrame(OAuth oauth) {
		this.oauth = oauth;
	//	initialize();
	}

	/**
	 * Create the application.
	 */
	public QWeiboFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {

		//this.setResizable(false);
	//	this.setBounds(100, 100,  250, 580);
		this.setLayout(new BorderLayout());
		this.setSize(250, 480);
		this.setVisible(true);
	//	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	this.setTitle("QWeiboE-Social");
	//	this.setIconImage(Toolkit.getDefaultToolkit().getImage(
	//			getClass().getResource("image/esocial.jpg")));
		panel = new JPanel();
	
	//	gpane = this.getContentPane();
		JLabel label_1 = new JLabel("姓名");

		textField = new JTextField();
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setColumns(10);
		textField.setEditable(false);

		JLabel label_2 = new JLabel("簽名：");

		textField_1 = new JTextField();
		textField_1.setText("");
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBackground(Color.LIGHT_GRAY);
		// textField_2.setBounds(this.getWidth()/3+40, 40, 60, 20);

		GroupLayout gl_panel = new GroupLayout(panel);
		label_3 = new JLabel("");

		label_4 = new JLabel("");

		label_5 = new JLabel("");

		IconLabel = new JLabel("");
		
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
																		IconLabel)
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
																								.addGap(64)
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
																		57,
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
																		IconLabel,
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
		
		this.add(panel, BorderLayout.NORTH);
	//	this.setLocationRelativeTo(null);
		panel.setVisible(true);
		label_3.setText("听众数:");
		label_4.setText("收听数:");
		label_5.setText("微博数:");
		if (oauth.getStatus() == 2) {
			System.out.println("Get Access Token failed!");
			return;
		} else {
			User_API tUserAPI = new User_API();
			try {
				String info = tUserAPI.info(oauth,
						WeiBoConst.ResultType.ResultType_Json);
				
				JSONObject user =new JSONObject(info);  
				user = user.getJSONObject("data");
				String nick = user.getString("nick");
				String introduction = user.getString("introduction");
				String head = user.getString("head");
				int fans = user.getInt("fansnum");
				int idolnum = user.getInt("idolnum");
				int tweetnum = user.getInt("tweetnum");
			
				textField.setText(nick);
				textField_1.setText(introduction);

				label_3.setText("听众数:"+fans);
				label_4.setText("收听数:"+idolnum);
				label_5.setText("微博数:"+tweetnum);
				
				head = URLDecoder.decode(head, "utf-8");
				URL url = new URL(head+"/50");
				IconLabel.setIcon(new ImageIcon(url));
				 FunctionList();
				getFriendsInfo();
				
		//		System.out.println(url.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// RenRenState();
	}
	
	public void publish(String content) {
		
		T_API t = new T_API();
		try {
			t.add(getOauth(),  WeiBoConst.ResultType.ResultType_Json, content, "10.99.163.149");
		//	qwf.getFriendsInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
}
	public void getFriendsInfo() {
		
		Statuses_API status = new Statuses_API();
		try {
			String info = status.home_timeline(oauth, WeiBoConst.ResultType.ResultType_Json, "0","0", "20");
			getDetail(info);
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
				
	}
	
	public void getMyInfo() {
		Statuses_API status = new Statuses_API();
		try {
			String info = status.broadcast_timeline(oauth, WeiBoConst.ResultType.ResultType_Json, "0", "0", "20", "0", "3", "1", "0");
	//		String infos = status.mentions_timeline(oauth,  WeiBoConst.ResultType.ResultType_Json, "0", "0", "20", "0");
		//	System.out.println(info);
		//	System.out.println(infos);
			getDetail(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getFriendsList() {
		
		panel_info = new JTextPane(); 
		panel_info.setBackground(Color.white);
		panel_info.setEditable(false);
		if(scrollPane != null) {
			this.remove(scrollPane);
		}
		scrollPane = new JScrollPane(panel_info);
		panel_info.setLocation(3, 3);
		this.add(scrollPane, BorderLayout.CENTER);
		Friends_API friends = new Friends_API();
		int len=0;
		try {
			String list = friends.idollist(oauth, WeiBoConst.ResultType.ResultType_Json, "30", "0");
			JSONObject jlist = new JSONObject(list);
			jlist = jlist.getJSONObject("data");
			JSONArray alist = jlist.getJSONArray("info");
			int size = alist.length();
			for(int i=0; i<size; i++) {
				JSONObject finfo = (JSONObject) alist.get(i);
				String fenge = "\n-----------------------------------------------------\n";
				SimpleAttributeSet attrset = new SimpleAttributeSet();
				StyleConstants.setForeground(attrset, Color.blue);
	
				JTextPane jtp = new JTextPane();
				jtp.setVisible(true);
				jtp.setEditable(false);

				Document tmpd = jtp.getDocument();
				String surl = null;
				surl = finfo.getString("head");
				URL url;
				try {
					if(!surl.isEmpty()) {
						surl = URLDecoder.decode(surl, "utf-8");
						url = new URL(surl+"/50");
						jtp.insertIcon(new ImageIcon(url));
					}
					tmpd.insertString(tmpd.getLength(), finfo.getString("nick"), attrset);
					tmpd.insertString(tmpd.getLength(), "\n 听众数("+finfo.getInt("fansnum")+") 偶像数("+finfo.getInt("idolnum")+")"+fenge,null);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				jtp.setBounds(5, len, 230, tmpd.getLength());
				len += tmpd.getLength() + 5;
				panel_info.setPreferredSize(new Dimension(250, len));
				textpanes1.add(jtp);
				panel_info.add(jtp);		
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		panel_info.setVisible(true);
		this.validate();
	}
	
	public void getDetail(String info) throws JSONException {
		
		panel_list = new JPanel();
		panel_list.setLayout(null);
		panel_list.setBackground(Color.white);
		panel_list.setLocation(3,3);
		panel_list.setVisible(true);
		ylen = 0;
		if(scrollPane != null) {
			this.remove(scrollPane);
		}
		scrollPane = new JScrollPane(panel_list);
		this.add(scrollPane, BorderLayout.CENTER);
		
		new Thread(new InfoThread(info,this)).start();
		
		this.validate();
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
	 * 点击事件处理
	 *  
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
			getFriendsList();
		}
	}


	private class InfoThread implements Runnable {
		private String info;
		private QWeiboFrame qwf;
		public InfoThread(String info,QWeiboFrame qwf) {
			this.info = info;
			this.qwf = qwf;
		}
		@Override
		public void run() {
			JSONObject friends;
			try {
				friends = new JSONObject(info);
				String o =friends.getString("data");
				if( !o.equals("null")) {
					friends = friends.getJSONObject("data");
					JSONArray friendlist = friends.getJSONArray("info");
					int num=friendlist.length();
					for (int i=0;i<num;i++) {
						JSONObject friend = (JSONObject) friendlist.get(i);
						JPDesign jpd = new JPDesign(qwf);
						jpd.setTexts(friend);
						jpd.setLocation(5, ylen);
						ylen += jpd.getHeight();
						textpanes.add(jpd);
						panel_list.add(jpd);
						panel_list.setPreferredSize(new Dimension(250, ylen));
					}
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		
		}
		
		
	}
}
