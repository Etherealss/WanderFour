package service;

import common.enums.ResultType;
import pojo.po.LikeRecord;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/14
 */
public interface LikeService {

	ResultType LikeOrUnlike(LikeRecord likeRecord);
}
