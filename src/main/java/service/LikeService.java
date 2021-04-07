package service;

import common.enums.ResultType;
import pojo.po.LikeRecord;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/14
 */
public interface LikeService {

	/**
	 * 点赞
	 * @param likeRecord
	 * @return
	 * @throws Exception
	 */
	ResultType likeOrUnlike(LikeRecord likeRecord);

	/**
	 * 点赞关系记录持久化到数据库点赞表中
	 * @throws Exception
	 */
	void persistLikeRecord();

	/**
	 * 点赞数量统计持久化到数据库作品表中
	 * @throws Exception
	 */
	void persistLikeCount();
}
