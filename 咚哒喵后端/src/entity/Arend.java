package entity;

import java.sql.Timestamp;

public class Arend {
	
	public Arend() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public int getArendID() {
		return arendID;
	}
	public void setArendID(int arendID) {
		this.arendID = arendID;
	}
	public int getCreatorID() {
		return creatorID;
	}
	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
	}
	public String[] getArendTag() {
		return arendTag;
	}
	public void setArendTag(String[] arendTag2) {
		this.arendTag = arendTag2;
	}
	public String getArendContent() {
		return arendContent;
	}
	public void setArendContent(String arendContent) {
		this.arendContent = arendContent;
	}
	public boolean getIfBoutique() {
		return ifBoutique;
	}
	public void setIfBoutique(boolean b) {
		this.ifBoutique = b;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp string) {
		this.createTime = string;
	}
	public String[] getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String[] imageurl2) {
		this.imageUrl = imageurl2;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Boolean getIfLike() {
		return ifLike;
	}

	public void setIfLike(Boolean ifLike) {
		this.ifLike = ifLike;
	}
	public Boolean getIfCollect() {
		return ifCollect;
	}

	public void setIfCollect(Boolean ifCollect) {
		this.ifCollect = ifCollect;
	}
	private int arendID;
	private int creatorID;
	private String[] arendTag;
	private String arendContent;
	private boolean ifBoutique;
	private Timestamp createTime;
	private String[] imageUrl;
	private int like;
	private int commentNum;
	private String type;
	private String creatorAvatar;
	private String creatorName;
	private Boolean ifLike;
	private Boolean ifCollect;
}
