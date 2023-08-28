package entity;

import java.sql.Timestamp;

public class Replymessage {

	public int getReplyMessageID() {
		return replyMessageID;
	}

	public void setReplyMessageID(int replyMessageID) {
		this.replyMessageID = replyMessageID;
	}

	public int getSenderID() {
		return senderID;
	}

	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}

	public int getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(int receiverID) {
		this.receiverID = receiverID;
	}

	public int getArendID() {
		return arendID;
	}

	public void setArendID(int arendID) {
		this.arendID = arendID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Timestamp getReplytime() {
		return replytime;
	}

	public void setReplytime(Timestamp replytime) {
		this.replytime = replytime;
	}

	private int replyMessageID;
	private int senderID;
	private int receiverID;
	private int arendID;
	private String type;
	private String content;
	private int state;
	private Timestamp replytime;
	
	public Replymessage() {
		// TODO 自动生成的构造函数存根
	}

}
