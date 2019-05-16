package com.tencent.weibo.design;

import com.tencent.weibo.utils.OAuthClient;


public class QWeiboConnect {
	private QWeiboFrame qweibo;
	public QWeiboFrame connect() {
		String str="https://open.t.qq.com/cgi-bin/oauth2/authorize?client_id=" +
				"801143463&response_type=token&redirect_uri=http://t.qq.com/yinhen3";

		com.tencent.weibo.beans.OAuth oauth=new com.tencent.weibo.beans.OAuth();
		OAuthClient auth=new OAuthClient();
 
		// 获取request token
		try {
			oauth = auth.requestToken(oauth);
			String oauth_token = oauth.getOauth_token();
			
			String url = "http://open.t.qq.com/cgi-bin/authorize?oauth_token="+ oauth_token;
			 SWTBrowserTest swtbt = new SWTBrowserTest(url);
			 String urls = swtbt.init();
			 if(urls != null) {
				 if(urls.contains("oauth_verifier=")) { // 从URL中解析得到 access_token  
					 String  verifier = urls.substring(urls.indexOf("oauth_verifier=") + 15,urls.indexOf("&openid"));  
	                
	              //   System.out.println("access_token = " + verifier);  

	 		        //获取access token
	 		      //  System.out.println("GetAccessToken......");
	 		       oauth.setOauth_verifier(verifier);
	 		        oauth = auth.accessToken(oauth);
	 		    //    System.out.println("Response from server：");
	 		      qweibo =  new QWeiboFrame(oauth);
	 		      return qweibo;
	                 
				 }
				 else
					 System.out.println("decode url fail");
			}
			 else
				 System.out.println("can not get the url");
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
