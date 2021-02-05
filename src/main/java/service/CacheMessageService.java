package service;

/**
 * @author 寒洲
 * @description 未发送的聊天信息缓存操作
 * @date 2020/11/19
 */
public interface CacheMessageService {

	/**
	 * 缓存未发送的聊天信息
	 * @throws Exception
	 */
	void cacheMessageToJedis() throws Exception;
}
