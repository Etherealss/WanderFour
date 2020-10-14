package service;

import common.enums.ResultState;
import pojo.po.LikeRecord;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/14
 */
public interface LikeService {

	ResultState LikeOrUnlike(LikeRecord likeRecord);
}
