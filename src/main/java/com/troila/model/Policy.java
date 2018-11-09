package com.troila.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="policy_inner")
public class Policy {
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章内容
	 */
	private String content;
	
	/**
	 * 发文单位
	 */
	private String publishUnit;
	
	/**
	 * 发文字号
	 */
	private String publishNo;
	
	/**
	 * 发文日期
	 */
	private Date publishDate;
	
	/**
	 * 文章链接
	 */
	private String publishUrl;
		
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 所属分类
	 */
	private String category;
	/**
	 * 是否已读
	 */
	private Integer readFlag;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublishUnit() {
		return publishUnit;
	}
	public void setPublishUnit(String publishUnit) {
		this.publishUnit = publishUnit;
	}	
	public Date getPublishDate() {
		return publishDate;
	}
	public String getPublishNo() {
		return publishNo;
	}
	public void setPublishNo(String publishNo) {
		this.publishNo = publishNo;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getPublishUrl() {
		return publishUrl;
	}
	public void setPublishUrl(String publishUrl) {
		this.publishUrl = publishUrl;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(Integer readFlag) {
		this.readFlag = readFlag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
