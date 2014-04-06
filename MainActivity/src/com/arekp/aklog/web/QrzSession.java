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

}
