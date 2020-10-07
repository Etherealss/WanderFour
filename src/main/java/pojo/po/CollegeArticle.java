package pojo.po;

import common.Partition;
import common.annontation.Db;
import common.annontation.DbTable;

/**
 * @author 寒洲
 * @description 大学生活的文章
 * @date 2020/10/7
 */
@Db(DbName = "wanderfour")
@DbTable(tableName = "article")
public class CollegeArticle extends Article{
	public static final int PARTITION = Partition.COLLEGE;
}
