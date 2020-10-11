package pojo.po;

import common.annontation.Db;
import common.annontation.DbTable;
import common.annontation.DbTableFK;
import common.enums.Partition;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/9
 */
@Db(DbName = "wanderfour")
@DbTable(tableName = "article")
@DbTableFK(foreignKey = {"user", "partition"})
public class Article extends Writing {
}
