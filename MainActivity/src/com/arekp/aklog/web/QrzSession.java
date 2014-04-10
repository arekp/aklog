package com.arekp.aklog.web;

import android.util.Log;

public class QrzSession {
	//Session
	public String Key;
	public String Count;
	public String SubExp;
	public String GMTime;
	public String Message;
	public String Error="";
	
	//Callsign
	
	private String call;
	private String dxcc;
	private String fname;
	private String name;
	private String addr1;
	private String addr2;
	private String state;
	private String zip;
	private String country;
	private String ccode;
	private String grid;
	private String county;
	private String land;
	private String qslmgr;
	private String email;
	private String u_views;
	private String cqzone;
	private String ituzone;
	private String lotw;
	private String iota;
	
	public QrzSession(String key, String count, String subExp, String gMTime,
			String message, String error) {
		super();
		Key = key;
		Count = count;
		SubExp = subExp;
		GMTime = gMTime;
		Message = message;
		Error = error;
	}
	public QrzSession(){
		
	}
	
	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public String getSubExp() {
		return SubExp;
	}
	public void setSubExp(String subExp) {
		SubExp = subExp;
	}
	public String getGMTime() {
		return GMTime;
	}
	public void setGMTime(String gMTime) {
		GMTime = gMTime;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getError() {
		return Error;
	}
	public void setError(String error) {
		Error = error;
	}

	   public String getHTMLDialog(){
		   String html="Brak danyh";
		   Log.d("qrzSession", "1 " + getError());
		   
		   if(getError().equals(""))
		   {
			   
			   Log.d("qrzSession", "3 else " + html);
		   html="<b> Key </b> &nbsp;&nbsp;"+getKey()+" <br>";
		   html+="<b> Count </b> &nbsp;&nbsp;"+getCount()+" <br>";
		   html+="<b> SubExp </b> &nbsp;&nbsp;"+getSubExp()+" <br>";
		   html+="<b> GMTime </b> &nbsp;&nbsp;"+getGMTime()+" <br>";
		   html+="<b> Message </b> &nbsp;&nbsp;"+getMessage()+" <br>";
		   html+="<b> Error </b> &nbsp;&nbsp;"+getError()+" <br>";
		  // return html;
		   }else {
			   Log.d("qrzSession", "2 error " + html);
			   html="<h1> ERROR: "+this.Error+"</h1>";

	 }
			 Log.d("qrzSession", "4 end --" + getError() +"--");
			 
		   		   		   return html;
	   
	}
	public String getCall() {
		return call;
	}
	public void setCall(String call) {
		this.call = call;
	}
	public String getDxcc() {
		return dxcc;
	}
	public void setDxcc(String dxcc) {
		this.dxcc = dxcc;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCcode() {
		return ccode;
	}
	public void setCcode(String ccode) {
		this.ccode = ccode;
	}
	public String getGrid() {
		return grid;
	}
	public void setGrid(String grid) {
		this.grid = grid;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getLand() {
		return land;
	}
	public void setLand(String land) {
		this.land = land;
	}
	public String getQslmgr() {
		return qslmgr;
	}
	public void setQslmgr(String qslmgr) {
		this.qslmgr = qslmgr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getU_views() {
		return u_views;
	}
	public void setU_views(String u_views) {
		this.u_views = u_views;
	}
	public String getCqzone() {
		return cqzone;
	}
	public void setCqzone(String cqzone) {
		this.cqzone = cqzone;
	}
	public String getItuzone() {
		return ituzone;
	}
	public void setItuzone(String ituzone) {
		this.ituzone = ituzone;
	}
	public String getLotw() {
		return lotw;
	}
	public void setLotw(String lotw) {
		this.lotw = lotw;
	}
	public String getIota() {
		return iota;
	}
	public void setIota(String iota) {
		this.iota = iota;
	}
	   
	 public String getHTMLCallsign(){
		   String html="Brak danyh";
		   Log.d("qrzSession", "1  " + getError());
		   
		   if(getError().equals(""))
		   {
			   /* 	private String call;
	private String dxcc;
	private String fname;
	private String name;
	private String addr1;
	private String addr2;
	private String state;
	private String zip;
	private String country;
	private String ccode;
	private String grid;
	private String county;
	private String land;
	private String qslmgr;
	private String email;
	private String u_views;
	private String cqzone;
	private String ituzone;
	private String lotw;
	private String iota; */
			   
			   Log.d("qrzSession getHTMLCallsign", "3 else " + html);
/*		   html="<b> call </b> &nbsp;&nbsp;"+getCall()+" <br>";
		//   html+="<b> dxcc </b> &nbsp;&nbsp;"+getDxcc()+" <br>";
		   html+="<b> fname </b> &nbsp;&nbsp;"+getFname()+" <br>";
		   html+="<b> name </b> &nbsp;&nbsp;"+getName()+" <br>";
		   html+="<b> addr1 </b> &nbsp;&nbsp;"+getAddr2()+" <br>";
		   html+="<b> country </b> &nbsp;&nbsp;"+getCountry()+" <br>";*/
		   html="<b><font color=#41C200> CALLSIGN </font></b> &nbsp;&nbsp;"+getCall()+" <br>";
		   html+="<b><font color=#41C200> Fname </font></b> &nbsp;&nbsp;"+getFname()+" <br>";
		   html+="<b><font color=#41C200> Lname </font></b> &nbsp;&nbsp;"+getName()+" <br>";
		   html+="<b><font color=#41C200> Addr </font></b> &nbsp;&nbsp;"+getAddr2()+" <br>";
		   html+="<b><font color=#41C200> country </font></b> &nbsp;&nbsp;"+getCountry()+" <br>";
		   html+="<b> Key </b> &nbsp;&nbsp;"+getKey()+" <br>";
		   html+="<b> Count </b> &nbsp;&nbsp;"+getCount()+" <br>";
		   html+="<b> SubExp </b> &nbsp;&nbsp;"+getSubExp()+" <br>";
		   html+="<b> GMTime </b> &nbsp;&nbsp;"+getGMTime()+" <br>";
		   html+="<b> Message </b> &nbsp;&nbsp;"+getMessage()+" <br>";
		   html+="<b> Error </b> &nbsp;&nbsp;"+getError()+" <br>";

		  // return html;
		   }else {
			   Log.d("qrzSession", "2 error " + html);
			   html="<h1> ERROR: "+this.Error+"</h1>";

	 }
			 Log.d("qrzSession", "4 end --" + getError() +"--");
			 
		   		   		   return html;
	   
	}
	   

}
