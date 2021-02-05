package com.example.mymusic.search.identity;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;



/**
 * Http Client 工具类
 */
public class HttpUtil {
	
	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param header
	 * @param bytearray
	 * @return
	 */
	public static String doPost(String url, Map<String, String> header, byte[] bytearray) {
		String result = "";
		BufferedReader in = null;
		//PrintWriter out = null;
		try {
			// 设置 url
			URL realUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
			// 设置 header
			for (String key : header.keySet()) {
				connection.setRequestProperty(key, header.get(key));
			}
			connection.setRequestProperty("Connection", "Keep-Alive");
			// 设置请求 body
			connection.setDoOutput(true);
			connection.setDoInput(true);
			//设置连接超时和读取超时时间
			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);

			BufferedOutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
			
			outputStream.write(bytearray);
			// 发送body
			outputStream.flush();
			outputStream.close();
			int responseCode = connection.getResponseCode();
			if(HttpURLConnection.HTTP_OK == responseCode) {
				// 获取响应body
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}
				
			
		} catch (Exception e) {
			e.printStackTrace();
//			return null;
		}
		return result;
	}
}
