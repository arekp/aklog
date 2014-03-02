package com.arekp.aklog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.database.Cursor;
import android.widget.EditText;

public class RaportBean {
    public RaportBean(long id, Float frequency, String mode, Date data,
			String callsign, Integer rs, Integer rt, String note) {
		super();
		this.id = id;
		this.frequency = frequency;
		this.mode = mode;
		this.data = data;
		this.callsign = callsign;
		this.rs = rs;
		this.rt = rt;
		this.note = note;
	}
	private long id;
	private Float frequency;
    private String mode;
    private Date data;
    private String callsign;
    private Integer rs;
    private Integer rt;
    private String note;
    
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Float getFrequency() {
		return frequency;
	}
	public void setFrequency(Float frequency) {
		this.frequency = frequency;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public Date getData() {
		return data;
	}
	public String getTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(" HH:mm:ss");
		return dateFormat.format(this.data);
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getCallsign() {
		return callsign;
	}
	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}
	public Integer getRs() {
		return rs;
	}
	public void setRs(Integer rs) {
		this.rs = rs;
	}
	public Integer getRt() {
		return rt;
	}
	public void setRt(Integer rt) {
		this.rt = rt;
	}

	public RaportBean(String frequency, String mode, Date data,
			String callsign, String rs, String rt, String note2) {
		//super();
		this.id = 0;
		this.frequency = Float.valueOf(frequency);
		this.mode = mode;
		this.data = data;
		this.callsign = callsign;
		this.rs = Integer.parseInt(rs);
		this.rt = Integer.parseInt(rt);
		this.note = note2;
	}
	public RaportBean(String frequency, String mode, String data,
			String callsign, String rs, String rt, String note2) {
		//super();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		this.id = 0;
		this.frequency = Float.valueOf(frequency);
		this.mode = mode;
		try {
			this.data = dateFormat.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.callsign = callsign;
		this.rs = Integer.parseInt(rs);
		this.rt = Integer.parseInt(rt);
		this.note = note2;
	}
	public RaportBean(Cursor mCursor) {
		//super();
		//(mCursor.getString(1), mCursor.getString(2), mCursor.getString(3) , mCursor.getString(4), mCursor.getString(5), mCursor.getString(6), mCursor.getString(7)
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		this.id = mCursor.getLong(0);
		this.frequency = mCursor.getFloat(1);
		this.mode = mCursor.getString(2);
		try {
			this.data = dateFormat.parse(mCursor.getString(3));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.callsign = mCursor.getString(4);
		this.rs = mCursor.getInt(5);
		this.rt = mCursor.getInt(6);
		this.note = mCursor.getString(7);
	}
	// Do poprawy 
	public String ExportSplit(){
		String linia="";
		linia=getFrequency()+";"+getMode()+";"+getData()+";"+getCallsign()+";"+getRs()+";"+getRt()+";"+getNote();
		return linia;
	}
    

}
