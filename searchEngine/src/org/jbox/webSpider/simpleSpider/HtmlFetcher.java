package org.jbox.webSpider.simpleSpider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
/**
 * A HTML fetcher.
 * 
 * @author YiBin.H
 * @version 1.0
 *
 */

public class HtmlFetcher {
	protected URLConnection urlConn;
	/**
	 * Connect the specified URL.
	 * 
	 * @param urlStr the URL to Connect.
	 * @throws IOException thrown if fail to connect the URL.  
	 */
	public void connect(String urlStr) throws IOException {
		URL url = null;
		url = new URL(urlStr);
		urlConn = url.openConnection();
		urlConn.setRequestProperty("accept", "text/*");
	}
	/**
	 * Fetch encoding of a page.
	 * 
	 * If "charset=" exists in content type of response header, invoking this
	 * method will return the value of it, or else spider try to down load
	 * content of page until meeting string "charset=". If "charset=" exists in
	 * the content, the method will then return the value of "charset=", or
	 * else, throws an UnknownEncodingException.
	 * 
	 * @return encoding of a page.
	 * @throws IOException
	 *             thrown if fail to down load HTML of a page.
	 * @throws UnknownEncodingException
	 *             thrown if fail to fetch encoding of a page.
	 */
	protected String fetchEncoding() throws IOException,
			UnknownEncodingException {
		String encoding = null;
		String content = urlConn.getContentType();
		if (content != null) {
			String[] contentType = content.split(";");
			if (contentType.length >= 2) {
				String[] charset = contentType[1].split("=");
				if (charset.length >= 2) {
					encoding = charset[1];
				}
			}
		}
		if (encoding == null || encoding.length() == 0) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream(), "ISO-8859-1"));
			String s = null;
			int i = -1;
			while ((s = br.readLine()) != null) {
				if ((i = s.indexOf("charset")) != -1) {
					int j;
					for (j = i + "charset=".length(); j < s.length(); j++) {
						char c = s.charAt(j);
						if (c == '"' || c == '>')
							break;
					}
					encoding = s.substring(i + "charset=".length(), j).trim();
					break;
				}
			}
			br.close();
		}
		if (encoding != null) {
			this.connect(urlConn.getURL().toString());
		} else {
			throw new UnknownEncodingException("no charset or encoding:"
					+ urlConn.getURL().toString());
		}
		return encoding;
	}
	/**
	 * Fetch text of a page.
	 * 
	 * @return text of a page.
	 * @throws IOException thrown if fail to fetch the HTML of a page.
	 * @throws UnknownEncodingException thrown if fail to resolve encoding of 
	 * a page. 
	 */
	public String fectch() throws IOException, UnknownEncodingException {
		String encoding = this.fetchEncoding();
		String html = null;
		StringBuffer sb = new StringBuffer(10000);
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConn
				.getInputStream(),encoding));
		String s = null;
		while ((s = br.readLine()) != null) {
			sb.append("\n" + s);
		}
		br.close();
		html = sb.toString();
		return html;
	}

}
