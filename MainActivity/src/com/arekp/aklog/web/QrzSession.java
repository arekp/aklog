package com.arekp.aklog.web;

public class QrzSession {
	public String Key;
	public String Count;
	public String SubExp;
	public String GMTime;
	public String Message;
	public String Error;
	
	
	
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
		   String html="";
		   if(!this.Error.equals(""))
		   {
		   html="<h1> ERROR: "+this.Error+"</h1>";
		   return html;
		   }else{
		   html+="<b> Key </b> &nbsp;&nbsp;"+this.Key+" <br>";
		   html+="<b> Count </b> &nbsp;&nbsp;"+this.Count+" <br>";
		   html+="<b> SubExp </b> &nbsp;&nbsp;"+this.SubExp+" <br>";
		   html+="<b> GMTime </b> &nbsp;&nbsp;"+this.GMTime+" <br>";
		   html+="<b> Message </b> &nbsp;&nbsp;"+this.Message+" <br>";
		   html+="<b> Error </b> &nbsp;&nbsp;"+this.Error+" <br>";
		 }
		   		   		   return html;
	   
	}

}
