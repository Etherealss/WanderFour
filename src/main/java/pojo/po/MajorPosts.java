package pojo.po;

import common.annontation.Db;
import common.annontation.DbTable;
import common.enums.Partition;
import pojo.MajorWriting;
import pojo.Posts;

/**
 * @author 寒洲
 * @description 专业介绍分区的帖子
 * @date 2020/10/7
 */
@Db(DbName = "wanderfour")
@DbTable(tableName = "posts")
public class MajorPosts extends Posts implements MajorWriting {
}
