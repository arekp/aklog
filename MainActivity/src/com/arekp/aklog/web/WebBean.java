package com.arekp.aklog.web;

public class WebBean {

	public WebBean(String name, String freq, String uTC, String spotter,
			String spotterUrl, String comment) {
		super();
		Name = name;
		Freq = freq;
		UTC = uTC;
		Spotter = spotter;
		SpotterUrl = spotterUrl;
		Comment = comment;
	}
	
	@Override
	public String toString() {
		return "WebBean [Name=" + Name + ", Freq=" + Freq + ", UTC=" + UTC
				+ ", Spotter=" + Spotter + ", SpotterUrl=" + SpotterUrl
				+ ", Comment=" + Comment + "]";
	}

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getFreq() {
		return Freq;
	}
	public void setFreq(String freq) {
		Freq = freq;
	}
	public String getUTC() {
		return UTC;
	}
	public void setUTC(String uTC) {
		UTC = uTC;
	}
	public String getSpotter() {
		return Spotter;
	}
	public void setSpotter(String spotter) {
		Spotter = spotter;
	}
	public String getSpotterUrl() {
		return SpotterUrl;
	}
	public void setSpotterUrl(String spotterUrl) {
		SpotterUrl = spotterUrl;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public String Name ;
	public String Freq ;
	public String UTC;
	public String Spotter;
	public String SpotterUrl;
	public String Comment;

}
