package common.bean;

import java.util.HashMap;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/4
 */
public class SensitiveNode {

	/** 单词是否结束 */
	boolean isEnd = false;
	/** 下一个字 */
	HashMap<Character, SensitiveNode> next;

	public SensitiveNode() {
		next = new HashMap<>(5);
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean end) {
		isEnd = end;
	}

	public HashMap<Character, SensitiveNode> getMap() {
		return next;
	}

	public void setNext(HashMap<Character, SensitiveNode> next) {
		this.next = next;
	}

	/**
	 * @param isEnd
	 * @param next
	 */
	public SensitiveNode(boolean isEnd, HashMap<Character, SensitiveNode> next) {
		this.isEnd = isEnd;
		this.next = next;
	}

	/**
	 * @param ch
	 * @param sensitiveNode
	 */
	public void add(Character ch, SensitiveNode sensitiveNode){
		next.put(ch, sensitiveNode);
	}
}
