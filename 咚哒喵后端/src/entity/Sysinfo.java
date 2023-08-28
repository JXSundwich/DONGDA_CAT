package entity;

import java.sql.Timestamp;

public class Sysinfo {
	
	private int creatorID;
	private String content;
	private String createTime;
	private int sysinfoID;
	private String creatorAvator;

	public Sysinfo() {
		// TODO 自动生成的构造函数存根
	}

	public int getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String string) {
		this.createTime = string;
	}

	public int getSysinfoID() {
		return sysinfoID;
	}

	public void setSysinfoID(int sysinfoID) {
		this.sysinfoID = sysinfoID;
	}

	public String getCreatorAvator() {
		return creatorAvator;
	}

	public void setCreatorAvator(String creatorAvator) {
		this.creatorAvator = creatorAvator;
	}

}
