package com.arekp.aklog;

public class RaportBean {
	private String frequency;
    private String mode;
    private String data;
    private String callsign;
    private String rs;
    private String rt;
    private String note;
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getCallsign() {
		return callsign;
	}
	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}
	public String getRs() {
		return rs;
	}
	public void setRs(String rs) {
		this.rs = rs;
	}
	public String getRt() {
		return rt;
	}
	public void setRt(String rt) {
		this.rt = rt;
	}
	public RaportBean(String frequency, String mode, String data,
			String callsign, String rs, String rt, String note) {
		//super();
		this.frequency = frequency;
		this.mode = mode;
		this.data = data;
		this.callsign = callsign;
		this.rs = rs;
		this.rt = rt;
		this.note = note;
	}
	public String ExportSplit(){
		String linia="";
		linia=getFrequency()+";"+getMode()+";"+getData()+";"+getCallsign()+";"+getRs()+";"+getRt()+";"+getNote()+";";
		return linia;
	}
    

}
