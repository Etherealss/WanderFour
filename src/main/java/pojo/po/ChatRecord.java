package pojo.po;

import java.util.Date;

/**
 * @author 寒洲
 * @description WebSocket 信息包
 * @date 2020/11/18
 */
public class ChatRecord {

	Long fromId;

	Long toId;

	String message;

	Date createTime;

	@Override
	public String toString() {
		return "ChatRecord{" +
				"fromId=" + fromId +
				", toId=" + toId +
				", message='" + message + '\'' +
				", createDate=" + createTime +
				'}';
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ChatRecord(Long fromId, Long toId, String message) {
		this.fromId = fromId;
		this.toId = toId;
		this.message = message;
	}

	public ChatRecord(Long fromId) {
		this.fromId = fromId;
	}

	public ChatRecord(Long toId, String message, Date createTime) {
		this.toId = toId;
		this.message = message;
		this.createTime = createTime;
	}

	public ChatRecord() {
	}
}
