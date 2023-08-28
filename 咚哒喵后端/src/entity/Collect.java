package entity;

import java.sql.Timestamp;

public class Collect {
	

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getArendID() {
		return arendID;
	}

	public void setArendID(int arendID) {
		this.arendID = arendID;
	}


	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getCollectID() {
		return collectID;
	}

	public void setCollectID(int collectID) {
		this.collectID = collectID;
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

	public void setArendTag(String[] arendTag) {
		this.arendTag = arendTag;
	}

	public String getArendContent() {
		return arendContent;
	}

	public void setArendContent(String arendContent) {
		this.arendContent = arendContent;
	}

	public int getIfBoutique() {
		return ifBoutique;
	}

	public void setIfBoutique(int ifBoutique) {
		this.ifBoutique = ifBoutique;
	}

	public Timestamp getArendcreateTime() {
		return ArendcreateTime;
	}

	public void setArendcreateTime(Timestamp arendcreateTime) {
		ArendcreateTime = arendcreateTime;
	}

	public String[] getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String[] imageUrl) {
		this.imageUrl = imageUrl;
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

	private int collectID;
	private int userID;
	private Timestamp createTime;
	private int arendID;
	private int creatorID;
	private String[] arendTag;
	private String arendContent;
	private int ifBoutique;
	private Timestamp ArendcreateTime;
	private String[] imageUrl;
	private int like;
	private int commentNum;
	private String type;
	private String creatorAvatar;
	private String creatorName;
	private Boolean ifLike;
	private Boolean ifCollect;
	
	public Collect() {
		// TODO 自动生成的构造函数存根
	}

}
