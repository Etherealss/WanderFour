package pojo.po;

import common.annontation.Db;
import common.annontation.DbTable;
import common.enums.Partition;
import pojo.LearningWriting;
import pojo.Posts;

/**
 * @author 寒洲
 * @description 学习天地的帖子
 * @date 2020/10/7
 */
@Db(DbName = "wanderfour")
@DbTable(tableName = "posts")
public class LearningPosts extends Posts implements LearningWriting {

}
