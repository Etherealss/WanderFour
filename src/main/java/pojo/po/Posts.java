package pojo.po;

import common.annontation.Db;
import common.annontation.DbTable;
import common.annontation.DbTableFK;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/9
 */
@Db(DbName = "wanderfour")
@DbTable(tableName = "posts")
@DbTableFK(foreignKey = {"user", "partition"})
public class Posts extends Writing{
}
