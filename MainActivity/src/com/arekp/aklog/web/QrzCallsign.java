package com.arekp.aklog.web;

public class QrzCallsign {
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
	public QrzCallsign(String call, String dxcc, String fname, String name,
			String addr1, String addr2, String state, String zip,
			String country, String ccode, String grid, String county,
			String land, String qslmgr, String email, String u_views,
			String cqzone, String ituzone, String lotw, String iota) {
		super();
		this.call = call;
		this.dxcc = dxcc;
		this.fname = fname;
		this.name = name;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.state = state;
		this.zip = zip;
		this.country = country;
		this.ccode = ccode;
		this.grid = grid;
		this.county = county;
		this.land = land;
		this.qslmgr = qslmgr;
		this.email = email;
		this.u_views = u_views;
		this.cqzone = cqzone;
		this.ituzone = ituzone;
		this.lotw = lotw;
		this.iota = iota;
	}

	public QrzCallsign(){}
	
}
