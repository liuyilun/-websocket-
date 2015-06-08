package javaeetutorial.web.websocketbot.messages;

public class Weather {
	private String weather;
	private String temp1;
	private String temp2;
	private String date;
	private String sh;
	private String eh;
	public Weather(String weather, String temp1, String temp2, String date,String sh,String eh) {
		super();
		this.weather = weather;
		this.temp1 = temp1;
		this.temp2 = temp2;
		this.date = date;
		this.sh=sh;
		this.eh=eh;
	}
	public String getSh() {
		return sh;
	}
	public void setSh(String sh) {
		this.sh = sh;
	}
	public String getEh() {
		return eh;
	}
	public void setEh(String eh) {
		this.eh = eh;
	}
	public Weather() {
		// TODO Auto-generated constructor stub
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Weather [weather=" + weather + ", temp1=" + temp1 + ", temp2="
				+ temp2 + ", date=" + date + ", sh=" + sh + ", eh=" + eh + "]";
	}
	
	
}
