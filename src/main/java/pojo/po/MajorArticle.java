package pojo.po;

import common.enums.Partition;
import common.annontation.Db;
import common.annontation.DbTable;
import pojo.Article;
import pojo.MajorWriting;
import pojo.Writing;

/**
 * @author 寒洲
 * @description 专业介绍的文章
 * @date 2020/10/7
 */
@Db(DbName = "wanderfour")
@DbTable(tableName = "article")
public class MajorArticle extends Article implements MajorWriting {

}
