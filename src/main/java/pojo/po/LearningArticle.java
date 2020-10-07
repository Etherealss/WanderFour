package pojo.po;

import common.Partition;
import common.annontation.Db;
import common.annontation.DbTable;

/**
 * @author 寒洲
 * @description 学习天地的文章
 * @date 2020/10/7
 */
@Db(DbName = "wanderfour")
@DbTable(tableName = "article")
public class LearningArticle extends Article{
	public static final int PARTITION = Partition.LEARNING;
}
