package entity;

import java.sql.Timestamp;

public class Collectset {
	
	
	public int getCollectSetID() {
		return collectSetID;
	}

	public void setCollectSetID(int collectSetID) {
		this.collectSetID = collectSetID;
	}

	public int getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
	}


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

	private int creatorID;
	private int collectSetID;
	private Timestamp createTime;
	private int arendID;

	public Collectset() {
		// TODO 自动生成的构造函数存根
	}

}
