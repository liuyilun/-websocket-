/*package javaeetutorial.web.websocketbot.decoders;

import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javaeetutorial.web.websocketbot.messages.Weather;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class WeatherDecoder implements Decoder.Text<Weather> {
	private Map<String, String> weatherMap;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Weather decode(String string) throws DecodeException {
		// TODO Auto-generated method stub
		Weather weather = null;
		if (willDecode(string)) {
			weather = new Weather(weatherMap.get("weather"), weatherMap.get("temp1"),
					weatherMap.get("temp2"), weatherMap.get("date"));
		}else {
            throw new DecodeException(string, "[Weather] Can't decode.");
        }
		return weather;
	}

	@Override
	public boolean willDecode(String string) {
		boolean decodes = false;
		// TODO Auto-generated method stub
		weatherMap = new HashMap<>();
		JsonParser parser = Json.createParser(new StringReader(string));
		
		while (parser.hasNext()) {
			if (parser.next() == JsonParser.Event.KEY_NAME) {
				String key = parser.getString();
				parser.next();
				String value = parser.getString();
				weatherMap.put(key, value);
			}
		}
		 Check the kind of message and if all fields are included 
		Set keys = weatherMap.keySet();
		String[] weatherkeys = { "weather", "temp1", "temp2", "date" };
		if (keys.containsAll(Arrays.asList(weatherkeys))) {
			return decodes;
		}
		return false;
	}

}
 */
package javaeetutorial.web.websocketbot.decoders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javaeetutorial.web.websocketbot.messages.Weather;

public class WeatherDecoder {
	public List<Weather> decode(String string){
		List<Weather> list=new ArrayList<Weather>();
		Gson gson=new Gson();
		JsonParser parser=new JsonParser();
		JsonElement jsonEl=parser.parse(string);
		JsonObject jsonObject=null;
		jsonObject=jsonEl.getAsJsonObject();//转换成json对象
		String reason=jsonObject.get("reason").getAsString();
		System.out.println("++++"+reason);
		if (reason.equals("successed!")) {
			JsonElement resultElement=jsonObject.get("result");
                        System.out.println("@@@"+resultElement);
			if (resultElement.isJsonArray()) {
				JsonArray jsonArray=resultElement.getAsJsonArray();//转为数组
				Weather weather;
				for (Iterator iter=jsonArray.iterator();iter.hasNext();) {
					JsonObject obj =(JsonObject) iter.next();
					weather=new Weather();
					weather=gson.fromJson(obj, Weather.class);
					System.out.println("&&&"+weather.toString());
					list.add(weather);
				}
			}
			return list;
		}else {
			return null;
		}
		
	}
}
