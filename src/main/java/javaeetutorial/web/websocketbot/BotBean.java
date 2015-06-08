/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.web.websocketbot;

import java.io.Closeable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javaeetutorial.web.websocketbot.decoders.WeatherDecoder;
import javaeetutorial.web.websocketbot.messages.Weather;

import javax.inject.Named;
import javax.websocket.DecodeException;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Named
public class BotBean {

	/* Respond to a message from the chat */
	public String respond(String msg) {
		String response = "";
		String weatherresult = null;
		List<Weather> list = null;
		boolean flag=false;
		WeatherDecoder wd;
                int  i=0;
		/* Remove question marks */
		msg = msg.toLowerCase().replaceAll("\\?", "");
		if (msg.contains("how are you")) {
			response = "I'm doing great, thank you!";
		} else if (msg.contains("how old are you")) {
			Calendar dukesBirthday = new GregorianCalendar(1995, Calendar.MAY,
					23);
			Calendar now = GregorianCalendar.getInstance();
			int dukesAge = now.get(Calendar.YEAR)
					- dukesBirthday.get(Calendar.YEAR);
			response = String.format("I'm %d years old.", dukesAge);
		} else if (msg.contains("when is your birthday")) {
			response = "My birthday is on May 23rd. Thanks for asking!";
		} else if (msg.contains("your favorite color")) {
			response = "My favorite color is blue. What's yours?";
		} else if (msg.contains("天")||msg.contains("气")||msg.contains("不")||msg.contains("说")||msg.contains("告诉")) {
			String weatherUri="http://v.juhe.cn/weather/forecast3h?key=ff97eea617c7583d2359b680cbb2e7b3&dtype=json&cityname=";
			String fenciUri="http://ltpapi.voicecloud.cn/analysis/?api_key=M3Z823P6sCnz1nbhMLpgpslE3N4MfPJmbbDdKDRn&pattern=ws&format=plain&text=";
			String fenciResult=get(msg, fenciUri);//分词之后的结果
			System.out.println("#######"+fenciResult);
			String[] phrase=fenciResult.split("\\s");//将分词后的结果拆分成词语
			for (int j=0;j<phrase.length;j++) {  //分别对每个词语进行天气的查询
				System.out.println("%"+phrase[j]+"\n");
				weatherresult = get(phrase[j],weatherUri);
                        if(msg.contains("不")||msg.contains("说")||msg.contains("告诉")||msg.contains("你")){
                            response ="你不说让我怎么告诉你天气呀！";
                            flag=true;
                              break;
                         }
			 // if((msg.contains("今天")||msg.contains("明天"))&&flag==false){
                         //   response ="请告诉我你想知道哪个城市的天气呀？";
                         //   flag=true;
                         //     break;
                        // }  
			
			wd = new WeatherDecoder();

			list = wd.decode(weatherresult);
                      
			if (list != null) {
				response += "城市:"+phrase[j];
				for (Weather weather : list) {
                                      if(msg.contains("明天")){
                                            continue;
                                        }
                     System.out.println("@@@@@"+weather.toString());                 
					response += "\n日期:" + weather.getDate() + ",时间段:"+weather.getSh()+"~"+weather.getEh()+"时，天气为:"
							+ weather.getWeather() + ",气温为"
							+ weather.getTemp1() + "~" + weather.getTemp2()
							+ "度!";
					response += "\n";
                                        i++;
                                        if(msg.contains("今天")&&i==7){
                                            break;
                                        }
                                        if(msg.contains("明天")){
                                            break;
                                        }
                                       
				}
				flag=true;//表明list数组不全为空，证明有天气被查询到
			}
				
			}
			if(!flag)
				response="你查询不到该城市的信息!";

		} else {
			response = "Sorry, I did not understand what you said. ";
			response += "You can ask me how I'm doing today; how old I am; or ";
			response += "what my favorite color is.";
		}
		try {
			Thread.sleep(1200);
		} catch (InterruptedException ex) {
		}
		return response;
	}

	public String get(String msg,String uri) {
		msg = msg.toLowerCase().replaceAll("\\?", "");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(uri+ msg);
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				System.out.println(response.getStatusLine());
				if (entity != null) {
					String jsonresult = EntityUtils.toString(entity);
					System.out.println("Response content:" + jsonresult);
					return jsonresult;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				httpclient.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return null;
	}
}
