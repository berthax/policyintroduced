package com.troila.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


//@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="policy_external")
public class PolicyExternal {
	@JsonIgnore
	@Id
	private String id;
	
	private String oid;
	
	private Integer parentid;
	
	private Integer metadataid;
	
	private Integer channelid;
	
	private Integer siteid;
	
	private String chnlname;
	
	private String chnldesc;
	
	private String filename;
	
	private String fileno;
	
	private Integer docid;
	
	private String docpuburl;
	
	private String polpacclass;
	
	private String cname;
	
	private String depname;
	
	private String thecontent;
	
	private String dmpuburl;
	
	private String filenamedesc;
	
	private String themeclass;
	
	private Date titledate;
	
	private Integer reldoc;
	
	private String readFlag;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Integer getMetadataid() {
		return metadataid;
	}

	public void setMetadataid(Integer metadataid) {
		this.metadataid = metadataid;
	}

	public Integer getChannelid() {
		return channelid;
	}

	public void setChannelid(Integer channelid) {
		this.channelid = channelid;
	}

	public Integer getSiteid() {
		return siteid;
	}

	public void setSiteid(Integer siteid) {
		this.siteid = siteid;
	}

	public String getChnlname() {
		return chnlname;
	}

	public void setChnlname(String chnlname) {
		this.chnlname = chnlname;
	}

	public String getChnldesc() {
		return chnldesc;
	}

	public void setChnldesc(String chnldesc) {
		this.chnldesc = chnldesc;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileno() {
		return fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno;
	}

	public Integer getDocid() {
		return docid;
	}

	public void setDocid(Integer docid) {
		this.docid = docid;
	}

	public String getDocpuburl() {
		return docpuburl;
	}

	public void setDocpuburl(String docpuburl) {
		this.docpuburl = docpuburl;
	}

	public String getPolpacclass() {
		return polpacclass;
	}

	public void setPolpacclass(String polpacclass) {
		this.polpacclass = polpacclass;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getDepname() {
		return depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	public String getThecontent() {
		return thecontent;
	}

	public void setThecontent(String thecontent) {
		this.thecontent = thecontent;
	}

	public String getDmpuburl() {
		return dmpuburl;
	}

	public void setDmpuburl(String dmpuburl) {
		this.dmpuburl = dmpuburl;
	}

	public String getFilenamedesc() {
		return filenamedesc;
	}

	public void setFilenamedesc(String filenamedesc) {
		this.filenamedesc = filenamedesc;
	}

	public String getThemeclass() {
		return themeclass;
	}

	public void setThemeclass(String themeclass) {
		this.themeclass = themeclass;
	}

	public Date getTitledate() {
		return titledate;
	}

	public void setTitledate(Date titledate) {
		this.titledate = titledate;
	}

	public Integer getReldoc() {
		return reldoc;
	}

	public void setReldoc(Integer reldoc) {
		this.reldoc = reldoc;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	@Override
	public String toString() {
		return "PolicyExternal [oid=" + oid + ", parentid=" + parentid + ", metadataid=" + metadataid + ", channelid="
				+ channelid + ", siteid=" + siteid + ", chnlname=" + chnlname + ", chnldesc=" + chnldesc + ", filename="
				+ filename + ", fileno=" + fileno + ", docid=" + docid + ", docpuburl=" + docpuburl + ", polpacclass="
				+ polpacclass + ", cname=" + cname + ", depname=" + depname + ", thecontent=" + thecontent
				+ ", dmpuburl=" + dmpuburl + ", filenamedesc=" + filenamedesc + ", themeclass=" + themeclass
				+ ", titledate=" + titledate + ", reldoc=" + reldoc + ", readFlag=" + readFlag + "]";
	}
	
}
