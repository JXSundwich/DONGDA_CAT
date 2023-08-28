package entity;
import java.sql.Timestamp;

public class Applymessage {

		public Applymessage() {
		super();
		// TODO 自动生成的构造函数存根
	}
		public int getApplyMessageID() {
			return applyMessageID;
		}
		public void setApplyMessageID(int applyMessageID) {
			this.applyMessageID = applyMessageID;
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
		public boolean getState() {
			return state;
		}
		public void setState(boolean state) {
			this.state = state;
		}
		public Timestamp getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Timestamp createTime) {
			this.createTime = createTime;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}

		
		private int applyMessageID;
		private int creatorID;
		private int arendID;
		private String type;
		private String content;
		private boolean state;
		private Timestamp createTime;
		private String result;
		
		
}
