package com.renren.design;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;







public class RenrenConnect {
	private RenRenFrame renren;
	public RenRenFrame connect() {
		String str="https://graph.renren.com/oauth/authorize?"+
                "client_id=59f7cc21f0424c89ba5b9d6dbfdc93f1&response_type=token"+
                "&display=page&redirect_uri=http://graph.renren.com/oauth/login_success.html";
//		try {
//			readContentFromPost();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
		
		SWTBrowserTest swtbt = new SWTBrowserTest();
		String url = swtbt.init();
		String access_token;
		if(url != null) {
			 if(url.contains("access_token=")) { // 从URL中解析得到 access_token  
                 access_token = url.substring(url.indexOf("access_token=") + 13,url.indexOf("&expires"));  
                 try {  
                     access_token = URLDecoder.decode(access_token, "utf-8"); // 制定为utf-8编码  
                 } catch (UnsupportedEncodingException e) {  
                     e.printStackTrace();  
                 }  
              //   System.out.println("access_token = " + access_token);  
                 renren = new RenRenFrame(access_token);
                 return renren;
			 }
		}
		return null;
	
	}
	

	
	
}
