package weibo.design;
import java.io.IOException;

import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.RequestToken;

public class OAuthUpdate
{
	private Weibo weibo;
	public Weibo getWeibo() {
		return weibo;
	}
	public boolean connect(String user,String pass) {
		
		 try {
	        	System.setProperty("weibo4j.oauth.consumerKey", "2430084755");
	        	System.setProperty("weibo4j.oauth.consumerSecret", "9013097caec25bf09afcce02f656d0bf");
	        	
	        	//System.out.println("weibo setp1");
	             weibo = new Weibo();
	            weibo.setUsername(user);
	    		weibo.setPassword(pass);
	            // set callback url, desktop app please set to null
	            // http://callback_url?oauth_token=xxx&oauth_verifier=xxx
	            RequestToken requestToken = weibo.getOAuthRequestToken();
	            
	            System.out.println("Got request token.");
	            System.out.println("Request token: "+ requestToken.getToken());
	            System.out.println("Request token secret: "+ requestToken.getTokenSecret());
	            AccessToken accessToken = null;
	            
	            //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	            while (null == accessToken) {
	                System.out.println("Open the following URL and grant access to your account:");
	                System.out.println(requestToken.getAuthorizationURL());
	                //BareBonesBrowserLaunch.openURL(requestToken.getAuthorizationURL());
	                System.out.print("Hit enter when it's done.[Enter]:");
	                
	                String pin = weibo.getPin(requestToken.getAuthorizationURL(), requestToken
	                    .getToken());
	                try{
	               // 	System.out.println("pin="+pin);
	                	if(pin==null)
	                		return false;
	                	
	                    accessToken = requestToken.getAccessToken(pin);
	                } catch (WeiboException te) {
	                    if(401 == te.getStatusCode()){
	                        System.out.println("Unable to get the access token.");
	                    }else{
	                        te.printStackTrace();
	                    }
	                }
	            }
	            System.out.println("Got access token.");
	            System.out.println("Access token: "+ accessToken.getToken());
	            System.out.println("Access token secret: "+ accessToken.getTokenSecret());
	            return true;
	           /* weibo.setToken(accessToken.getToken(), accessToken.getTokenSecret());

	            Status status = weibo.updateStatus("test message6 ");
	            System.out.println("Successfully updated the status to ["
	            		+ status.getText() + "].");
	             
	             try {
	            	Thread.sleep(3000);
	            } catch (InterruptedException e) {
	            	// TODO Auto-generated catch block
	            	e.printStackTrace();
	            }*/
	         //   System.exit(0);
	        } catch (WeiboException te) {
	            System.out.println("Failed to get timeline: " + te.getMessage());
	            System.exit( -1);
	        } catch (IOException e) {
	            System.out.println(e.toString());
	            System.exit(-1);
	        }
		 	return true;
	}
}