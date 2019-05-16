package com.renren.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
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
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.param.impl.AccessToken;

public class RenRenFrame extends JPanel implements ActionListener {

	private JTextField textField;
	private JTextField textField_1;
	private JLabel label_3, IconLabel;
	private JPanel panel, funclist, panel_list, panel_info;
	private JScrollPane scrollPane = null;
	private Document doc = null; // 插入文字样式

	private JButton fresh, home, find, friends, add;
	private List<JTextPane> textpanes = new ArrayList<JTextPane>();
	private List<JTextPane> textpanes1 = new ArrayList<JTextPane>();
	private static boolean tag_home = true;
	private RenrenApiClient apiClient;
	private String access_token;
	private long uid;
	private int ylen = 0;

	// public static void main(String[] args) {
	// new RenRenFrame().initialize();
	// }
	//
	public RenRenFrame() {

	}

	/**
	 * Create the application.
	 */
	public RenRenFrame(String access_token) {
		this.access_token = access_token;
	//	initialize();
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public RenrenApiClient getApiClient() {
		return apiClient;
	}

	public void setApiClient(RenrenApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		// this.setResizable(false);
	//	setBounds(100, 100, 250, 580);
		this.setLayout(new BorderLayout());
		this.setSize(250, 480);
		setVisible(true);
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		IconLabel = new JLabel("");

		JLabel label_1 = new JLabel("姓名");

		textField = new JTextField();
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setColumns(5);
		textField.setEditable(false);

		label_3 = new JLabel("大学");

		textField_1 = new JTextField();
		textField_1.setText("");
		textField_1.setEditable(false);
		textField_1.setColumns(5);
		textField_1.setBackground(Color.LIGHT_GRAY);
		// textField_2.setBounds(this.getWidth()/3+40, 40, 60, 20);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGap(21)
								.addComponent(IconLabel)
								.addGap(22)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		label_1)
																.addGap(12)
																.addComponent(
																		textField,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		label_3)
																.addGap(12)
																.addComponent(
																		textField_1,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(59, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addContainerGap()
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
																.addGap(18)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.BASELINE)
																				.addComponent(
																						label_3)
																				.addComponent(
																						textField_1,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addGap(12)
																.addComponent(
																		IconLabel)))
								.addContainerGap(35, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		add(panel, BorderLayout.NORTH);
	//	this.setLocationRelativeTo(null);
		panel.setVisible(true);
		RenRenState();

	}
	
public void publish(String content) {
		
		String url="http://www.renren.com/"+this.getUid();
        //String image="http://www.huohu123.com/static/nav/images/firefox-logo_v2.png";
        String action_link="http://wiki.dev.renren.com/wiki/SDK";
		this.getApiClient().getFeedService().publicFeed(content,content, url,"", "","",action_link, content,new AccessToken(this.getAccess_token()));
		this.getFriendsInfo();
	}

	public void RenRenState() {
		apiClient = RenrenApiClient.getInstance();
		uid = apiClient.getUserService().getLoggedInUser(
				new AccessToken(access_token));

		String fields = "name,email_hash, sex,star,birthday,tinyurl,headurl,mainurl,hometown_location,hs_history,university_history,work_history,contact_info";
		JSONArray userInfo = apiClient.getUserService().getInfo(
				String.valueOf(uid), fields, // "name,tinyurl,email,university",
				new AccessToken(access_token));
		// System.out.println(userInfo);
		if (userInfo != null && userInfo.size() > 0) {
			JSONObject currentUser = (JSONObject) userInfo.get(0);

			if (currentUser != null) {
				String name = (String) currentUser.get("name");
				// Long email = (Long) currentUser.get("email");
				String university = (String) ((JSONObject) ((JSONArray) currentUser
						.get("university_history")).get(0)).get("name");
				String headurl = (String) currentUser.get("tinyurl");
				try {
					headurl = URLDecoder.decode(headurl, "utf-8");
				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}
				URL url;
				try {
					url = new URL(headurl);
					IconLabel.setIcon(new ImageIcon(url));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block

				}
				// System.out.println("u="+currentUser.get("university")+";email="+
				// currentUser.get("email"));

				// System.out.println(currentUser);
				textField.setText(name);
				// textField_1.setText(Long.toString(email));
				textField_1.setText(university);

				getFriendsInfo();
				FunctionList();
			} else
				System.out.println("not find");
		}

	}

	public void getFriendsInfo() {

		JSONArray feeds = apiClient
				.getFeedService()
				.getFeed(
						"10,11,20,21,22,23,30,31,32,33,34,35,36,40,41,50,51,52,53,54,55",
						0, 1, 20, new AccessToken(access_token));
		// JSONArray statuses=apiClient.getStatusService().getStatuses(0, 1,
		// 1,new AccessToken(access_token));
		// System.out.println("statuses:\n"+statuses);
		// System.out.println();
		showInfoNew(feeds);

	}

	public void getFriendsDetail() {

		panel_info = new JPanel();
		panel_info.setLayout(null);
		if (scrollPane != null) {
			this.remove(scrollPane);
		}
		scrollPane = new JScrollPane(panel_info);
		this.add(scrollPane, BorderLayout.CENTER);
		panel_info.setLocation(3, 3);
		panel_info.setBackground(Color.white);
		int len = 0;
		JSONArray friends = apiClient.getFriendsService().getFriends(1, 10,
				new AccessToken(this.getAccess_token()));
		int size = friends.size();
	//	System.out.println("size="+size);
		for (int i = 0; i < size; i++) {
			JTextPane jtp = new JTextPane();
			jtp.setVisible(true);
			jtp.setEditable(false);

			Document tmpd = jtp.getDocument();
			JSONObject friend = (JSONObject) friends.get(i);
			// System.out.println(friend);
			String fenge = "\n-----------------------------------------------------\n";
			SimpleAttributeSet attrset = new SimpleAttributeSet();
			StyleConstants.setForeground(attrset, Color.blue);
			String surl = (String) friend.get("tinyurl");
			URL url;
			try {
				url = new URL(surl);
				jtp.insertIcon(new ImageIcon(url));
				tmpd.insertString(tmpd.getLength(), (String) friend.get("name")+fenge, attrset);
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			jtp.setBounds(5, len, 230, tmpd.getLength());
		//	System.out.println((String) friend.get("name")+len);
			len += tmpd.getLength()+5;
			panel_info.setPreferredSize(new Dimension(250, len));
			textpanes1.add(jtp);
			panel_info.add(jtp);
			
		}

		panel_info.setVisible(true);
		this.validate();
	}

	public void showInfoNew(JSONArray feeds) {

		panel_list = new JPanel();
		panel_list.setLayout(null);

		panel_list.setBackground(Color.white);
		panel_list.setLocation(3, 3);

		if (scrollPane != null) {
			this.remove(scrollPane);
		}
		scrollPane = new JScrollPane(panel_list);
		this.add(scrollPane, BorderLayout.CENTER);
		ylen=0;
		new Thread(new FeedThread(feeds, this)).start();

		panel_list.setVisible(true);
		this.validate();
	}

	
	public void FunctionList() {
		funclist = new JPanel();

		fresh = new JButton("");
		funclist.add(fresh);
		fresh.setToolTipText("刷新");
		fresh.setIcon(new ImageIcon(getClass().getResource("image/share.png")));
		fresh.addActionListener(this);
		fresh.setSize(10, 10);

		friends = new JButton("");
		funclist.add(friends);
		friends.setToolTipText("好友");
		friends.setIcon(new ImageIcon(getClass().getResource(
				"image/friends.png")));
		friends.addActionListener(this);
		friends.setSize(10, 10);
		// find = new JButton("");
		// funclist.add( find );
		// find.setToolTipText("查找");
		// find.setIcon(new
		// ImageIcon(getClass().getResource("image/find.png")));

		add = new JButton("");
		funclist.add(add);
		add.setToolTipText("新鲜事");
		add.setIcon(new ImageIcon(getClass().getResource("image/5.png")));
		add.addActionListener(this);
		add.setSize(10, 10);
		this.add(funclist, BorderLayout.SOUTH);

		// new Thread(new RemindThread()).start();

	}

	private class FeedThread implements Runnable {
		private JSONArray feeds;
		private RenRenFrame rrf;

		public FeedThread(JSONArray feeds, RenRenFrame rrf) {
			this.feeds = feeds;
			this.rrf = rrf;
		}

		@Override
		public void run() {
			int i = 0;
			int size = feeds.size();
			for (i = 0; i < size; i++) {
				JSONObject feed = (JSONObject) feeds.get(i);
				// System.out.println(feed.get("name")+"于"+feed.get("updateTime")+
				// "发表"+feed.get("href")+
				// feed.get("title")+"\n描述："+feed.get("description")+
				// "内容："+feed.get("message"));

				JPDesign jpd = new JPDesign(rrf);
				
				try {
					jpd.setTexts((Long) feed.get("actor_id"),
							(Long) feed.get("source_id"),
							(String) feed.get("headurl"),
							(String) feed.get("name"),
							(String) feed.get("title"),
							(String) feed.get("description"),
							(String) feed.get("message"),
							(String) feed.get("update_time"),
							(JSONObject) feed.get("comments"),
							(JSONObject) feed.get("trace"),
							(JSONArray) feed.get("attachment"));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (BadLocationException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

				jpd.setLocation(5, ylen);
				ylen += jpd.getHeight();
				textpanes.add(jpd);
				panel_list.add(jpd);
				panel_list.setPreferredSize(new Dimension(250, ylen));
				// try {
				// Thread.sleep(100);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add) {
			// System.out.println("add");
			Issue issue = new Issue(this);
		} else if (e.getSource() == fresh) {
			// System.out.println("fresh");
			getFriendsInfo();
		} else if (e.getSource() == friends) {
			// System.out.println("好友");
			getFriendsDetail();
		}
	}

}
