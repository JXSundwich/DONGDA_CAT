package entity;

import java.sql.Timestamp;

public class Comment {
	
	public int getCommentID() {
		return commentID;
	}

	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}

	public int getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
	}

	public int getArendID() {
		return arendID;
	}

	public void setArendID(int arendID) {
		this.arendID = arendID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}

	public String getCreatorAvatar() {
		return creatorAvatar;
	}

	public void setCreatorAvatar(String creatorAvatar) {
		this.creatorAvatar = creatorAvatar;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	private int commentID;
	private int creatorID;
	private int arendID;
	private String content;
	private Timestamp creatTime;
	private String creatorAvatar;
	private String creatorName;

	public Comment() {
		// TODO 自动生成的构造函数存根
	}

}
