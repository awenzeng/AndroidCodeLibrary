package com.awen.codebase.widget;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class CityWeather {

	public static WeatherBean getWeather() {
				try {
					WeatherBean weather = new WeatherBean();
					URL url = new URL("http://m.weather.com.cn/data/101280601.html");
					InputStream is = url.openStream();
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					int len = -1;
					byte[] buffer = new byte[1024];
					while ((len = is.read(buffer)) != -1) {
						bos.write(buffer, 0, len);
					}
					String info = bos.toString("utf-8");
					JSONObject dataJson = new JSONObject(info);
					JSONObject json = dataJson.getJSONObject("weatherinfo");
					weather.setCity(json.getString("city"));
					weather.setTemp(json.getString("temp1"));
					weather.setWeather(json.getString("weather1"));
					weather.setImg(json.getString("img1"));
					bos.close();
					is.close();
					return weather;
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
	}

}
