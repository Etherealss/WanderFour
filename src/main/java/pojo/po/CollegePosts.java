package pojo.po;

import common.annontation.Db;
import common.annontation.DbTable;
import common.enums.Partition;
import pojo.CollegeWriting;
import pojo.Posts;

/**
 * @author 寒洲
 * @description 大学风采帖子
 * @date 2020/10/7
 */
@Db(DbName = "wanderfour")
@DbTable(tableName = "posts")
public class CollegePosts extends Posts  implements CollegeWriting {

}
