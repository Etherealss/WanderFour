package pojo;

import com.alibaba.fastjson.annotation.JSONField;
import common.enums.websocket.InfoType;

/**
 * @author 寒洲
 * @description WebSocket最终信息包
 * @date 2020/11/18
 */
public class Information {
	@JSONField(ordinal = 0)
	private InfoType infoType;

	@JSONField(ordinal = 1)
	private boolean isSystemInfo;

	@JSONField(ordinal = 3)
	private Object data;

	@Override
	public String toString() {
		return "Infomation{" +
				"infoType=" + infoType +
				", isSystem=" + isSystemInfo +
				", data=" + data +
				'}';
	}

	public InfoType getInfoType() {
		return infoType;
	}

	public void setInfoType(InfoType infoType) {
		this.infoType = infoType;
	}

	public boolean getIsSystemInfo() {
		return isSystemInfo;
	}

	public void setIsSystemInfo(boolean system) {
		isSystemInfo = system;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Information() {
	}

	/**
	 * @param infoType
	 * @param isSystemInfo
	 * @param data
	 */
	public Information(InfoType infoType, boolean isSystemInfo, Object data) {
		this.infoType = infoType;
		this.isSystemInfo = isSystemInfo;
		this.data = data;
	}

	/**
	 * @param infoType
	 * @param isSystemInfo
	 */
	public Information(InfoType infoType, boolean isSystemInfo) {
		this.infoType = infoType;
		this.isSystemInfo = isSystemInfo;
	}
}

