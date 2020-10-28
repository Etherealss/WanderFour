/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.20 : Database - wanderfour
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wanderfour` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `wanderfour`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '文章Id',
  `partition` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '文章所属的社区分区',
  `category` tinyint unsigned NOT NULL COMMENT '分区',
  `author_id` int unsigned NOT NULL COMMENT '作者Id',
  `title` varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `label1` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标签1',
  `label2` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标签2',
  `label3` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标签3',
  `label4` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标签4',
  `label5` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标签5',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `liked` mediumint NOT NULL DEFAULT '0' COMMENT '点赞数',
  `collected` mediumint NOT NULL DEFAULT '0' COMMENT '收藏数',
  PRIMARY KEY (`id`),
  KEY `FK_Article_Partition_On_Id` (`partition`),
  KEY `FK_Article_Category_On_Category` (`category`),
  KEY `FK_Article_User_On_Author_id` (`author_id`),
  CONSTRAINT `FK_Article_Category_On_Category` FOREIGN KEY (`category`) REFERENCES `category` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Article_Partition_On_Id` FOREIGN KEY (`partition`) REFERENCES `partition` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Article_User_On_Author_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `article` */

insert  into `article`(`id`,`partition`,`category`,`author_id`,`title`,`label1`,`label2`,`label3`,`label4`,`label5`,`create_time`,`update_time`,`liked`,`collected`) values (1,1,1,2,'这是标题鸭写时话作半资养','label1','label2','label3','label4','label5','2020-10-19 21:43:50','2020-10-25 17:57:47',4,0),(2,1,1,1,'我是标题','标签1','标签2',NULL,NULL,NULL,'2020-10-19 21:51:04','2020-10-23 20:44:29',20,0),(3,1,1,1,'我是标题','标签1','标签2',NULL,NULL,NULL,'2020-10-19 21:57:00','2020-10-23 20:44:29',8,0),(9,1,2,1,'文章标题','第1个标签','第2个标签','第3个标签','第4个标签','第5个标签','2020-10-22 20:08:30','2020-10-22 20:08:30',0,0),(11,1,2,1,'文章标题','第1个标签','第2个标签','第3个标签','第4个标签','第5个标签','2020-10-23 20:42:38','2020-10-23 20:42:38',0,0),(12,1,1,1,'TestUtil测试','标签1','标签2',NULL,NULL,NULL,'2020-10-26 20:07:52','2020-10-26 20:07:52',0,0),(14,1,1,2,'发文章','标签',NULL,NULL,NULL,NULL,'2020-10-28 12:21:21','2020-10-28 12:21:21',0,0),(16,1,4,2,'发表文章','标签',NULL,NULL,NULL,NULL,'2020-10-28 12:49:56','2020-10-28 12:49:56',0,0),(17,1,1,2,'ja',NULL,NULL,NULL,NULL,NULL,'2020-10-28 21:22:14','2020-10-28 21:22:14',0,0);

/*Table structure for table `article_comment` */

DROP TABLE IF EXISTS `article_comment`;

CREATE TABLE `article_comment` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned NOT NULL COMMENT '用户id',
  `parent_id` int unsigned NOT NULL COMMENT '表示该记录在哪个文章（帖子）或评论之下，可用于表示评论和回复',
  `target_id` int unsigned DEFAULT NULL COMMENT '该记录指向的目标Id，可以指向评论或回复对象',
  `content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论/回复内容',
  `liked` mediumint unsigned NOT NULL DEFAULT '0' COMMENT '点赞数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `state` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '正常为1，已删除为0',
  PRIMARY KEY (`id`),
  KEY `FK_Article_comment_User_On_User_id` (`user_id`),
  KEY `FK_Article_comment_Article_comment_On_Target_id` (`target_id`),
  CONSTRAINT `FK_Article_comment_Article_comment_On_Target_id` FOREIGN KEY (`target_id`) REFERENCES `article_comment` (`id`),
  CONSTRAINT `FK_Article_comment_User_On_User_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `article_comment` */

insert  into `article_comment`(`id`,`user_id`,`parent_id`,`target_id`,`content`,`liked`,`create_time`,`state`) values (1,1,1,NULL,'我评论了id为1的文章！',5,'2020-10-22 19:56:21',1),(2,1,1,NULL,'我也评论了1L文章',6,'2020-10-22 19:59:17',1),(3,1,1,NULL,'我评论了id为1的文章！',4,'2020-10-22 19:56:21',1),(4,1,1,2,'我回复了id为1的评论',1,'2020-10-22 20:47:54',1),(5,1,1,2,'我又回复了id为1的评论',7,'2020-10-14 20:47:54',1),(6,1,3,NULL,'我评论了id为3的文章！',48,'2020-08-05 19:56:21',1),(7,1,3,NULL,'我评论了id为3的文章！',12,'2020-10-22 19:56:21',1),(8,1,3,NULL,'我评论了id为3的文章！',11,'2020-10-02 19:56:21',1),(10,3,1,NULL,'评论内容',0,'2020-10-23 09:32:59',1),(11,3,1,1,'回复内容',0,'2020-10-23 09:33:14',1),(13,1,2,NULL,'这是一条评论',0,'2020-10-27 17:23:48',1),(15,1,3,4,'哇偶',0,'2020-10-27 17:43:49',1);

/*Table structure for table `article_content` */

DROP TABLE IF EXISTS `article_content`;

CREATE TABLE `article_content` (
  `article_id` int unsigned NOT NULL COMMENT '文章Id',
  `content` text NOT NULL COMMENT '文章内容',
  PRIMARY KEY (`article_id`),
  CONSTRAINT `FK_Article_content_Article_On_Article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `article_content` */

insert  into `article_content`(`article_id`,`content`) values (1,'这是内容'),(2,'啊啊啊'),(3,'主要内容aaaaaaaaaaaaa'),(9,'<button type=\"button\" id=\"ajaxBtn\" onclick=\"changeLocation()\">登录</button>\r\n    <script src=\"./JS/jQuery.js\"></script>\r\n    <script>\r\n        function changeLocation() {\r\n            window.location.href = \"www.baidu.com\";\r\n        }\r\n    </script>'),(11,'文章正文'),(12,'主要内容'),(14,'发文啊章发文章<img id=\"articleUpImage0\" src=\"data:image/jpeg;base64,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\" style=\"width: 400px;\"><br><br><br>'),(16,'发表文章<img id=\"articleUpImage0\" src=\"data:image/jpeg;base64,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\" style=\"width: 400px;\">插入图片<br><br>'),(17,'<span style=\"font-size: medium;\">&lt;button type=\"button\" id=\"ajaxBtn\" onclick=\"changeLocation()\"&gt;登录&lt;/button&gt; &lt;script src=\"./JS/jQuery.js\"&gt;&lt;/script&gt; &lt;script&gt; function changeLocation() { window.location.href = \"www.baidu.com\"; } &lt;/script&gt;</span>');

/*Table structure for table `article_like_record` */

DROP TABLE IF EXISTS `article_like_record`;

CREATE TABLE `article_like_record` (
  `user_id` int unsigned NOT NULL COMMENT '用户id',
  `target_id` int unsigned NOT NULL COMMENT '点赞的文章id',
  PRIMARY KEY (`user_id`,`target_id`),
  KEY `FK_Article_like_record_Article_On_Target_id` (`target_id`),
  CONSTRAINT `FK_Article_like_record_Article_On_Target_id` FOREIGN KEY (`target_id`) REFERENCES `article` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Article_like_record_User_On_User_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `article_like_record` */

insert  into `article_like_record`(`user_id`,`target_id`) values (1,2),(2,2),(3,3);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` tinyint(3) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'id',
  `partition` tinyint unsigned NOT NULL COMMENT '分区',
  `name` varchar(10) NOT NULL COMMENT '分类名',
  PRIMARY KEY (`id`),
  KEY `FK_Category_Partition_On_Partition` (`partition`),
  CONSTRAINT `FK_Category_Partition_On_Partition` FOREIGN KEY (`partition`) REFERENCES `partition` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `category` */

insert  into `category`(`id`,`partition`,`name`) values (001,1,'语文'),(002,1,'数学'),(003,1,'英语'),(004,1,'物理'),(005,1,'化学'),(006,1,'生物'),(007,1,'历史'),(008,1,'地理'),(009,1,'政治'),(010,1,'专业'),(011,1,'大学'),(253,1,'其他'),(254,2,'其他'),(255,3,'其他');

/*Table structure for table `comment_like_record` */

DROP TABLE IF EXISTS `comment_like_record`;

CREATE TABLE `comment_like_record` (
  `user_id` int unsigned NOT NULL COMMENT '用户id',
  `target_id` int unsigned NOT NULL COMMENT '评论id',
  PRIMARY KEY (`user_id`,`target_id`),
  CONSTRAINT `FK_Comment_like_record_User_On_User_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `comment_like_record` */

/*Table structure for table `partition` */

DROP TABLE IF EXISTS `partition`;

CREATE TABLE `partition` (
  `id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '社区分区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `partition` */

insert  into `partition`(`id`,`name`) values (1,'learning'),(2,'major'),(3,'college');

/*Table structure for table `posts` */

DROP TABLE IF EXISTS `posts`;

CREATE TABLE `posts` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '提问帖子id',
  `partition` tinyint unsigned NOT NULL COMMENT '三个分区',
  `category` tinyint unsigned NOT NULL COMMENT '分类',
  `author_id` int unsigned NOT NULL COMMENT '提问者id',
  `title` varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` varchar(200) DEFAULT NULL COMMENT '问题描述',
  `label1` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `label2` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `label3` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `label4` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `label5` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `liked` mediumint NOT NULL DEFAULT '0' COMMENT '点赞数',
  `follow` mediumint NOT NULL DEFAULT '0' COMMENT '关注数',
  PRIMARY KEY (`id`),
  KEY `FK_Posts_Category_On_Name` (`category`),
  KEY `FK_Posts_User_On_Author_id` (`author_id`),
  KEY `FK_Posts_Partition_On_Partition` (`partition`),
  CONSTRAINT `FK_Posts_Category_On_Name` FOREIGN KEY (`category`) REFERENCES `category` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Posts_Partition_On_Partition` FOREIGN KEY (`partition`) REFERENCES `partition` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Posts_User_On_Author_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `posts` */

insert  into `posts`(`id`,`partition`,`category`,`author_id`,`title`,`content`,`label1`,`label2`,`label3`,`label4`,`label5`,`create_time`,`update_time`,`liked`,`follow`) values (2,1,2,1,'这是标题鸭','这是内容','label1','label2','label3','label4','label5','2020-10-20 15:11:20','2020-10-20 18:02:06',0,0),(4,1,1,1,'TestUtil测试','主要内容','标签1','标签2',NULL,NULL,NULL,'2020-10-20 15:25:15','2020-10-20 15:25:15',0,0),(5,1,2,2,'问贴标题','问贴描述内容','第1个标签','第2个标签','第3个标签','第4个标签','第5个标签','2020-10-20 17:58:48','2020-10-20 17:58:48',0,0);

/*Table structure for table `posts_comment` */

DROP TABLE IF EXISTS `posts_comment`;

CREATE TABLE `posts_comment` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned NOT NULL,
  `parent_id` int unsigned NOT NULL COMMENT '表示该记录在哪个文章（帖子）或评论之下，可用于表示评论和回复',
  `target_id` int unsigned DEFAULT NULL COMMENT '该记录指向的目标Id，可以指向评论或回复对象',
  `content` varchar(300) NOT NULL COMMENT '评论/回复内容',
  `liked` mediumint NOT NULL DEFAULT '0' COMMENT '点赞数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `state` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '正常为1，已删除为0',
  PRIMARY KEY (`id`),
  KEY `FK_Posts_comment_Posts_comment_On_Target_id` (`target_id`),
  CONSTRAINT `FK_Posts_comment_Posts_comment_On_Target_id` FOREIGN KEY (`target_id`) REFERENCES `posts_comment` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `posts_comment` */

insert  into `posts_comment`(`id`,`user_id`,`parent_id`,`target_id`,`content`,`liked`,`create_time`,`state`) values (2,1,2,NULL,'这是一条评论/回复',0,'2020-10-27 17:26:23',1),(4,1,2,2,'这是一条评论/回复',0,'2020-10-27 17:26:50',1);

/*Table structure for table `posts_like_record` */

DROP TABLE IF EXISTS `posts_like_record`;

CREATE TABLE `posts_like_record` (
  `user_id` int unsigned NOT NULL COMMENT '用户id',
  `target_id` int unsigned NOT NULL COMMENT '点赞的帖子id',
  PRIMARY KEY (`user_id`,`target_id`),
  KEY `FK_Post_like_record_Posts_On_Target_id` (`target_id`),
  CONSTRAINT `FK_Post_like_record_Posts_On_Target_id` FOREIGN KEY (`target_id`) REFERENCES `posts` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Post_like_record_User_On_User_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `posts_like_record` */

/*Table structure for table `sensitive_word` */

DROP TABLE IF EXISTS `sensitive_word`;

CREATE TABLE `sensitive_word` (
  `id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint unsigned NOT NULL COMMENT '敏感词类型',
  `sentitive_word` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '敏感词',
  PRIMARY KEY (`id`),
  KEY `FK_Sensitive_word_Sensitive_word_type_On_type` (`type`),
  CONSTRAINT `FK_Sensitive_word_Sensitive_word_type_On_type` FOREIGN KEY (`type`) REFERENCES `sensitive_word_type` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1483 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sensitive_word` */

insert  into `sensitive_word`(`id`,`type`,`sentitive_word`) values (2,1,'爱女人'),(3,1,'爱液'),(4,1,'按摩棒'),(5,1,'拔出来'),(6,1,'爆草'),(7,1,'包二奶'),(8,1,'暴干'),(9,1,'暴奸'),(10,1,'暴乳'),(11,1,'爆乳'),(12,1,'暴淫'),(13,1,'屄'),(14,1,'被操'),(15,1,'被插'),(16,1,'被干'),(17,1,'逼奸'),(18,1,'仓井空'),(19,1,'插暴'),(20,1,'操逼'),(21,1,'操黑'),(22,1,'操烂'),(23,1,'肏你'),(24,1,'肏死'),(25,1,'操死'),(26,1,'操我'),(27,1,'厕奴'),(28,1,'插比'),(29,1,'插b'),(30,1,'插逼'),(31,1,'插进'),(32,1,'插你'),(33,1,'插我'),(34,1,'插阴'),(35,1,'潮吹'),(36,1,'潮喷'),(37,1,'成人dv'),(38,1,'成人电影'),(39,1,'成人论坛'),(40,1,'成人小说'),(41,1,'成人电'),(42,1,'成人电影'),(43,1,'成人卡通'),(44,1,'成人聊'),(45,1,'成人片'),(46,1,'成人视'),(47,1,'成人图'),(48,1,'成人文'),(49,1,'成人小'),(50,1,'成人电影'),(51,1,'成人论坛'),(52,1,'成人色情'),(53,1,'成人网站'),(54,1,'成人文学'),(55,1,'成人小说'),(56,1,'艳情小说'),(57,1,'成人游戏'),(58,1,'吃精'),(59,1,'赤裸'),(60,1,'抽插'),(61,1,'扌由插'),(62,1,'抽一插'),(63,1,'春药'),(64,1,'大波'),(65,1,'大力抽送'),(66,1,'大乳'),(67,1,'荡妇'),(68,1,'荡女'),(69,1,'盗撮'),(70,1,'多人轮'),(71,1,'发浪'),(72,1,'放尿'),(73,1,'肥逼'),(74,1,'粉穴'),(75,1,'封面女郎'),(76,1,'风月大陆'),(77,1,'干死你'),(78,1,'干穴'),(79,1,'肛交'),(80,1,'肛门'),(81,1,'龟头'),(82,1,'裹本'),(83,1,'国产av'),(84,1,'好嫩'),(85,1,'豪乳'),(86,1,'黑逼'),(87,1,'后庭'),(88,1,'后穴'),(89,1,'虎骑'),(90,1,'花花公子'),(91,1,'换妻俱乐部'),(92,1,'黄片'),(93,1,'几吧'),(94,1,'鸡吧'),(95,1,'鸡巴'),(96,1,'鸡奸'),(97,1,'寂寞男'),(98,1,'寂寞女'),(99,1,'妓女'),(100,1,'激情'),(101,1,'集体淫'),(102,1,'奸情'),(103,1,'叫床'),(104,1,'脚交'),(105,1,'金鳞岂是池中物'),(106,1,'金麟岂是池中物'),(107,1,'精液'),(108,1,'就去日'),(109,1,'巨屌'),(110,1,'菊花洞'),(111,1,'菊门'),(112,1,'巨奶'),(113,1,'巨乳'),(114,1,'菊穴'),(115,1,'开苞'),(116,1,'口爆'),(117,1,'口活'),(118,1,'口交'),(119,1,'口射'),(120,1,'口淫'),(121,1,'裤袜'),(122,1,'狂操'),(123,1,'狂插'),(124,1,'浪逼'),(125,1,'浪妇'),(126,1,'浪叫'),(127,1,'浪女'),(128,1,'狼友'),(129,1,'聊性'),(130,1,'流淫'),(131,1,'铃木麻'),(132,1,'凌辱'),(133,1,'漏乳'),(134,1,'露b'),(135,1,'乱交'),(136,1,'乱伦'),(137,1,'轮暴'),(138,1,'轮操'),(139,1,'轮奸'),(140,1,'裸陪'),(141,1,'买春'),(142,1,'美逼'),(143,1,'美少妇'),(144,1,'美乳'),(145,1,'美腿'),(146,1,'美穴'),(147,1,'美幼'),(148,1,'秘唇'),(149,1,'迷奸'),(150,1,'密穴'),(151,1,'蜜穴'),(152,1,'蜜液'),(153,1,'摸奶'),(154,1,'摸胸'),(155,1,'母奸'),(156,1,'奈美'),(157,1,'奶子'),(158,1,'男奴'),(159,1,'内射'),(160,1,'嫩逼'),(161,1,'嫩女'),(162,1,'嫩穴'),(163,1,'捏弄'),(164,1,'女优'),(165,1,'炮友'),(166,1,'砲友'),(167,1,'喷精'),(168,1,'屁眼'),(169,1,'品香堂'),(170,1,'前凸后翘'),(171,1,'强jian'),(172,1,'强暴'),(173,1,'强奸处女'),(174,1,'情趣用品'),(175,1,'情色'),(176,1,'拳交'),(177,1,'全裸'),(178,1,'群交'),(179,1,'惹火身材'),(180,1,'人妻'),(181,1,'人兽'),(182,1,'日逼'),(183,1,'日烂'),(184,1,'肉棒'),(185,1,'肉逼'),(186,1,'肉唇'),(187,1,'肉洞'),(188,1,'肉缝'),(189,1,'肉棍'),(190,1,'肉茎'),(191,1,'肉具'),(192,1,'揉乳'),(193,1,'肉穴'),(194,1,'肉欲'),(195,1,'乳爆'),(196,1,'乳房'),(197,1,'乳沟'),(198,1,'乳交'),(199,1,'乳头'),(200,1,'三级片'),(201,1,'骚逼'),(202,1,'骚比'),(203,1,'骚女'),(204,1,'骚水'),(205,1,'骚穴'),(206,1,'色逼'),(207,1,'色界'),(208,1,'色猫'),(209,1,'色盟'),(210,1,'色情网站'),(211,1,'色区'),(212,1,'色色'),(213,1,'色诱'),(214,1,'色欲'),(215,1,'色b'),(216,1,'少年阿宾'),(217,1,'少修正'),(218,1,'射爽'),(219,1,'射颜'),(220,1,'食精'),(221,1,'释欲'),(222,1,'兽奸'),(223,1,'兽交'),(224,1,'手淫'),(225,1,'兽欲'),(226,1,'熟妇'),(227,1,'熟母'),(228,1,'熟女'),(229,1,'爽片'),(230,1,'爽死我了'),(231,1,'双臀'),(232,1,'死逼'),(233,1,'丝袜'),(234,1,'丝诱'),(235,1,'松岛枫'),(236,1,'酥痒'),(237,1,'汤加丽'),(238,1,'套弄'),(239,1,'体奸'),(240,1,'体位'),(241,1,'舔脚'),(242,1,'舔阴'),(243,1,'调教'),(244,1,'偷欢'),(245,1,'偷拍'),(246,1,'推油'),(247,1,'脱内裤'),(248,1,'文做'),(249,1,'我就色'),(250,1,'无码'),(251,1,'舞女'),(252,1,'无修正'),(253,1,'吸精'),(254,1,'夏川纯'),(255,1,'相奸'),(256,1,'小逼'),(257,1,'校鸡'),(258,1,'小穴'),(259,1,'小xue'),(260,1,'写真'),(261,1,'性感妖娆'),(262,1,'性感诱惑'),(263,1,'性虎'),(264,1,'性饥渴'),(265,1,'性技巧'),(266,1,'性交'),(267,1,'性奴'),(268,1,'性虐'),(269,1,'性息'),(270,1,'性欲'),(271,1,'胸推'),(272,1,'穴口'),(273,1,'学生妹'),(274,1,'穴图'),(275,1,'亚情'),(276,1,'颜射'),(277,1,'阳具'),(278,1,'杨思敏'),(279,1,'要射了'),(280,1,'夜勤病栋'),(281,1,'一本道'),(282,1,'一夜欢'),(283,1,'一夜情'),(284,1,'一ye情'),(285,1,'阴部'),(286,1,'淫虫'),(287,1,'阴唇'),(288,1,'淫荡'),(289,1,'阴道'),(290,1,'淫电影'),(291,1,'阴阜'),(292,1,'淫妇'),(293,1,'淫河'),(294,1,'阴核'),(295,1,'阴户'),(296,1,'淫贱'),(297,1,'淫叫'),(298,1,'淫教师'),(299,1,'阴茎'),(300,1,'阴精'),(301,1,'淫浪'),(302,1,'淫媚'),(303,1,'淫糜'),(304,1,'淫魔'),(305,1,'淫母'),(306,1,'淫女'),(307,1,'淫虐'),(308,1,'淫妻'),(309,1,'淫情'),(310,1,'淫色'),(311,1,'淫声浪语'),(312,1,'淫兽学园'),(313,1,'淫书'),(314,1,'淫术炼金士'),(315,1,'淫水'),(316,1,'淫娃'),(317,1,'淫威'),(318,1,'淫亵'),(319,1,'淫样'),(320,1,'淫液'),(321,1,'淫照'),(322,1,'阴b'),(323,1,'应召'),(324,1,'幼交'),(325,1,'幼男'),(326,1,'幼女'),(327,1,'欲火'),(328,1,'欲女'),(329,1,'玉女心经'),(330,1,'玉蒲团'),(331,1,'玉乳'),(332,1,'欲仙欲死'),(333,1,'玉穴'),(334,1,'援交'),(335,1,'原味内衣'),(336,1,'援助交际'),(337,1,'张筱雨'),(338,1,'招鸡'),(339,1,'招妓'),(340,1,'中年美妇'),(341,1,'抓胸'),(342,1,'自拍'),(343,1,'自慰'),(344,1,'作爱'),(345,1,'18禁'),(346,1,'99bb'),(347,1,'a4u'),(348,1,'a4y'),(349,1,'adult'),(350,1,'amateur'),(351,1,'anal'),(352,1,'a片'),(353,1,'fuck'),(354,1,'gay片'),(355,1,'g点'),(356,1,'g片'),(357,1,'hardcore'),(358,1,'h动画'),(359,1,'h动漫'),(360,1,'incest'),(361,1,'porn'),(362,1,'secom'),(363,1,'sexinsex'),(364,1,'sm女王'),(365,1,'xiao77'),(366,1,'xing伴侣'),(367,1,'tokyohot'),(368,1,'yin荡'),(369,1,'贱人'),(370,1,'装b'),(371,1,'大sb'),(372,1,'傻逼'),(373,1,'傻b'),(374,1,'煞逼'),(375,1,'煞笔'),(376,1,'刹笔'),(377,1,'傻比'),(378,1,'沙比'),(379,1,'欠干'),(380,1,'婊子养的'),(381,1,'我日你'),(382,1,'我操'),(383,1,'我草'),(384,1,'卧艹'),(385,1,'卧槽'),(386,1,'爆你菊'),(387,1,'艹你'),(388,1,'cao你'),(389,1,'你他妈'),(390,1,'真他妈'),(391,1,'别他吗'),(392,1,'草你吗'),(393,1,'草你丫'),(394,1,'操你妈'),(395,1,'擦你妈'),(396,1,'操你娘'),(397,1,'操他妈'),(398,1,'日你妈'),(399,1,'干你妈'),(400,1,'干你娘'),(401,1,'娘西皮'),(402,1,'狗操'),(403,1,'狗草'),(404,1,'狗杂种'),(405,1,'狗日的'),(406,1,'操你祖宗'),(407,1,'操你全家'),(408,1,'操你大爷'),(409,1,'妈逼'),(410,1,'你麻痹'),(411,1,'麻痹的'),(412,1,'妈了个逼'),(413,1,'马勒'),(414,1,'狗娘养'),(415,1,'贱比'),(416,1,'贱b'),(417,1,'下贱'),(418,1,'死全家'),(419,1,'全家死光'),(420,1,'全家不得好死'),(421,1,'全家死绝'),(422,1,'白痴'),(423,1,'无耻'),(424,1,'sb'),(425,1,'杀b'),(426,1,'你吗b'),(427,1,'你妈的'),(428,1,'婊子'),(429,1,'贱货'),(430,1,'人渣'),(431,1,'混蛋'),(432,1,'媚外'),(433,1,'和弦'),(434,1,'兼职'),(435,1,'限量'),(436,1,'铃声'),(437,1,'性伴侣'),(438,1,'男公关'),(439,1,'火辣'),(440,1,'精子'),(441,1,'射精'),(442,1,'诱奸'),(443,1,'强奸'),(444,1,'做爱'),(445,1,'性爱'),(446,1,'发生关系'),(447,1,'按摩'),(448,1,'快感'),(449,1,'处男'),(450,1,'猛男'),(451,1,'少妇'),(452,1,'屌'),(453,1,'屁股'),(454,1,'下体'),(455,1,'a片'),(456,1,'内裤'),(457,1,'浑圆'),(458,1,'咪咪'),(459,1,'发情'),(460,1,'刺激'),(461,1,'白嫩'),(462,1,'粉嫩'),(463,1,'兽性'),(464,1,'风骚'),(465,1,'呻吟'),(466,1,'sm'),(467,1,'阉割'),(468,1,'高潮'),(469,1,'裸露'),(470,1,'不穿'),(471,1,'一丝不挂'),(472,1,'脱光'),(473,1,'干你'),(474,1,'干死'),(475,1,'我干'),(476,1,'裙中性运动'),(477,1,'乱奸'),(478,1,'乱伦'),(479,1,'乱伦类'),(480,1,'乱伦小'),(481,1,'伦理大'),(482,1,'伦理电影'),(483,1,'伦理毛'),(484,1,'伦理片'),(485,1,'裸聊'),(486,1,'裸聊网'),(487,1,'裸体写真'),(488,1,'裸舞视'),(489,1,'裸照'),(490,1,'美女裸体'),(491,1,'美女写真'),(492,1,'美女上门'),(493,1,'美艳少妇'),(494,1,'妹按摩'),(495,1,'妹上门'),(496,1,'迷幻药'),(497,1,'迷幻藥'),(498,1,'迷昏口'),(499,1,'迷昏药'),(500,1,'迷昏藥'),(501,1,'迷魂香'),(502,1,'迷魂药'),(503,1,'迷魂藥'),(504,1,'迷奸粉'),(505,1,'迷奸药'),(506,1,'迷情粉'),(507,1,'迷情水'),(508,1,'迷情药'),(509,1,'迷药'),(510,1,'迷藥'),(511,1,'谜奸药'),(512,1,'骚妇'),(513,1,'骚货'),(514,1,'骚浪'),(515,1,'骚女'),(516,1,'骚嘴'),(517,1,'色电影'),(518,1,'色妹妹'),(519,1,'色情表演'),(520,1,'色情电影'),(521,1,'色情服务'),(522,1,'色情图片'),(523,1,'色情小说'),(524,1,'色情影片'),(525,1,'色情表演'),(526,1,'色情电影'),(527,1,'色情服务'),(528,1,'色情片'),(529,1,'色视频'),(530,1,'色小说'),(531,1,'性伴侣'),(532,1,'性服务'),(533,1,'性福情'),(534,1,'性感少'),(535,1,'性伙伴'),(536,1,'性交'),(537,1,'性交视频'),(538,1,'性交图片'),(539,1,'性奴'),(540,1,'性奴集中营'),(541,1,'性虐'),(542,1,'阴唇'),(543,1,'阴道'),(544,1,'阴蒂'),(545,1,'阴户'),(546,1,'阴间来电'),(547,1,'阴茎'),(548,1,'阴茎增大'),(549,1,'阴茎助勃'),(550,1,'阴毛'),(551,1,'陰唇'),(552,1,'陰道'),(553,1,'陰戶'),(554,1,'淫荡'),(555,1,'淫荡美女'),(556,1,'淫荡视频'),(557,1,'淫荡照片'),(558,1,'淫乱'),(559,1,'淫靡'),(560,1,'淫魔'),(561,1,'淫魔舞'),(562,1,'淫女'),(563,1,'淫情女'),(564,1,'淫肉'),(565,1,'淫騷妹'),(566,1,'淫兽'),(567,1,'淫兽学'),(568,1,'淫水'),(569,1,'淫穴'),(570,1,'morphine'),(571,1,'摇头丸'),(572,1,'迷药'),(573,1,'乖乖粉'),(574,1,'narcotic'),(575,1,'麻醉药'),(576,1,'精神药品'),(577,1,'爱女人'),(578,1,'爱液'),(579,1,'按摩棒'),(580,1,'拔出来'),(581,1,'爆草'),(582,1,'包二奶'),(583,1,'暴干'),(584,1,'暴奸'),(585,1,'暴乳'),(586,1,'爆乳'),(587,1,'暴淫'),(588,1,'屄'),(589,1,'被操'),(590,1,'被插'),(591,1,'被干'),(592,1,'逼奸'),(593,1,'仓井空'),(594,1,'插暴'),(595,1,'操逼'),(596,1,'操黑'),(597,1,'操烂'),(598,1,'肏你'),(599,1,'肏死'),(600,1,'操死'),(601,1,'操我'),(602,1,'厕奴'),(603,1,'插比'),(604,1,'插b'),(605,1,'插逼'),(606,1,'插进'),(607,1,'插你'),(608,1,'插我'),(609,1,'插阴'),(610,1,'潮吹'),(611,1,'潮喷'),(612,1,'成人电影'),(613,1,'成人论坛'),(614,1,'成人色情'),(615,1,'成人网站'),(616,1,'成人文学'),(617,1,'成人小说'),(618,1,'艳情小说'),(619,1,'成人游戏'),(620,1,'吃精'),(621,1,'赤裸'),(622,1,'抽插'),(623,1,'扌由插'),(624,1,'抽一插'),(625,1,'春药'),(626,1,'大波'),(627,1,'大力抽送'),(628,1,'大乳'),(629,1,'荡妇'),(630,1,'荡女'),(631,1,'盗撮'),(632,1,'多人轮'),(633,1,'发浪'),(634,1,'放尿'),(635,1,'肥逼'),(636,1,'粉穴'),(637,1,'封面女郎'),(638,1,'风月大陆'),(639,1,'干死你'),(640,1,'干穴'),(641,1,'肛交'),(642,1,'肛门'),(643,1,'龟头'),(644,1,'裹本'),(645,1,'国产av'),(646,1,'好嫩'),(647,1,'豪乳'),(648,1,'黑逼'),(649,1,'后庭'),(650,1,'后穴'),(651,1,'虎骑'),(652,1,'花花公子'),(653,1,'换妻俱乐部'),(654,1,'黄片'),(655,1,'几吧'),(656,1,'鸡吧'),(657,1,'鸡巴'),(658,1,'鸡奸'),(659,1,'寂寞男'),(660,1,'寂寞女'),(661,1,'妓女'),(662,1,'激情'),(663,1,'集体淫'),(664,1,'奸情'),(665,1,'叫床'),(666,1,'脚交'),(667,1,'金鳞岂是池中物'),(668,1,'金麟岂是池中物'),(669,1,'精液'),(670,1,'就去日'),(671,1,'巨屌'),(672,1,'菊花洞'),(673,1,'菊门'),(674,1,'巨奶'),(675,1,'巨乳'),(676,1,'菊穴'),(677,1,'开苞'),(678,1,'口爆'),(679,1,'口活'),(680,1,'口交'),(681,1,'口射'),(682,1,'口淫'),(683,1,'裤袜'),(684,1,'狂操'),(685,1,'狂插'),(686,1,'浪逼'),(687,1,'浪妇'),(688,1,'浪叫'),(689,1,'浪女'),(690,1,'狼友'),(691,1,'聊性'),(692,1,'流淫'),(693,1,'铃木麻'),(694,1,'凌辱'),(695,1,'漏乳'),(696,1,'露b'),(697,1,'乱交'),(698,1,'乱伦'),(699,1,'轮暴'),(700,1,'轮操'),(701,1,'轮奸'),(702,1,'裸陪'),(703,1,'买春'),(704,1,'美逼'),(705,1,'美少妇'),(706,1,'美乳'),(707,1,'美腿'),(708,1,'美穴'),(709,1,'美幼'),(710,1,'秘唇'),(711,1,'迷奸'),(712,1,'密穴'),(713,1,'蜜穴'),(714,1,'蜜液'),(715,1,'摸奶'),(716,1,'摸胸'),(717,1,'母奸'),(718,1,'奈美'),(719,1,'奶子'),(720,1,'男奴'),(721,1,'内射'),(722,1,'嫩逼'),(723,1,'嫩女'),(724,1,'嫩穴'),(725,1,'捏弄'),(726,1,'女优'),(727,1,'炮友'),(728,1,'砲友'),(729,1,'喷精'),(730,1,'屁眼'),(731,1,'品香堂'),(732,1,'前凸后翘'),(733,1,'强jian'),(734,1,'强暴'),(735,1,'强奸处女'),(736,1,'情趣用品'),(737,1,'情色'),(738,1,'拳交'),(739,1,'全裸'),(740,1,'群交'),(741,1,'惹火身材'),(742,1,'人妻'),(743,1,'人兽'),(744,1,'日逼'),(745,1,'日烂'),(746,1,'肉棒'),(747,1,'肉逼'),(748,1,'肉唇'),(749,1,'肉洞'),(750,1,'肉缝'),(751,1,'肉棍'),(752,1,'肉茎'),(753,1,'肉具'),(754,1,'揉乳'),(755,1,'肉穴'),(756,1,'肉欲'),(757,1,'乳爆'),(758,1,'乳房'),(759,1,'乳沟'),(760,1,'乳交'),(761,1,'乳头'),(762,1,'三级片'),(763,1,'骚逼'),(764,1,'骚比'),(765,1,'骚女'),(766,1,'骚水'),(767,1,'骚穴'),(768,1,'色逼'),(769,1,'色界'),(770,1,'色猫'),(771,1,'色盟'),(772,1,'色情网站'),(773,1,'色区'),(774,1,'色色'),(775,1,'色诱'),(776,1,'色欲'),(777,1,'色b'),(778,1,'少年阿宾'),(779,1,'少修正'),(780,1,'射爽'),(781,1,'射颜'),(782,1,'食精'),(783,1,'释欲'),(784,1,'兽奸'),(785,1,'兽交'),(786,1,'手淫'),(787,1,'兽欲'),(788,1,'熟妇'),(789,1,'熟母'),(790,1,'熟女'),(791,1,'爽片'),(792,1,'爽死我了'),(793,1,'双臀'),(794,1,'死逼'),(795,1,'丝袜'),(796,1,'丝诱'),(797,1,'松岛枫'),(798,1,'酥痒'),(799,1,'汤加丽'),(800,1,'套弄'),(801,1,'体奸'),(802,1,'体位'),(803,1,'舔脚'),(804,1,'舔阴'),(805,1,'调教'),(806,1,'偷欢'),(807,1,'偷拍'),(808,1,'推油'),(809,1,'脱内裤'),(810,1,'文做'),(811,1,'我就色'),(812,1,'无码'),(813,1,'舞女'),(814,1,'无修正'),(815,1,'吸精'),(816,1,'夏川纯'),(817,1,'相奸'),(818,1,'小逼'),(819,1,'校鸡'),(820,1,'小穴'),(821,1,'小xue'),(822,1,'写真'),(823,1,'性感妖娆'),(824,1,'性感诱惑'),(825,1,'性虎'),(826,1,'性饥渴'),(827,1,'性技巧'),(828,1,'性交'),(829,1,'性奴'),(830,1,'性虐'),(831,1,'性息'),(832,1,'性欲'),(833,1,'胸推'),(834,1,'穴口'),(835,1,'学生妹'),(836,1,'穴图'),(837,1,'亚情'),(838,1,'颜射'),(839,1,'阳具'),(840,1,'杨思敏'),(841,1,'要射了'),(842,1,'夜勤病栋'),(843,1,'一本道'),(844,1,'一夜欢'),(845,1,'一夜情'),(846,1,'一ye情'),(847,1,'阴部'),(848,1,'淫虫'),(849,1,'阴唇'),(850,1,'淫荡'),(851,1,'阴道'),(852,1,'淫电影'),(853,1,'阴阜'),(854,1,'淫妇'),(855,1,'淫河'),(856,1,'阴核'),(857,1,'阴户'),(858,1,'淫贱'),(859,1,'淫叫'),(860,1,'淫教师'),(861,1,'阴茎'),(862,1,'阴精'),(863,1,'淫浪'),(864,1,'淫媚'),(865,1,'淫糜'),(866,1,'淫魔'),(867,1,'淫母'),(868,1,'淫女'),(869,1,'淫虐'),(870,1,'淫妻'),(871,1,'淫情'),(872,1,'淫色'),(873,1,'淫声浪语'),(874,1,'淫兽学园'),(875,1,'淫书'),(876,1,'淫术炼金士'),(877,1,'淫水'),(878,1,'淫娃'),(879,1,'淫威'),(880,1,'淫亵'),(881,1,'淫样'),(882,1,'淫液'),(883,1,'淫照'),(884,1,'阴b'),(885,1,'应召'),(886,1,'幼交'),(887,1,'幼男'),(888,1,'幼女'),(889,1,'欲火'),(890,1,'欲女'),(891,1,'玉女心经'),(892,1,'玉蒲团'),(893,1,'玉乳'),(894,1,'欲仙欲死'),(895,1,'玉穴'),(896,1,'援交'),(897,1,'原味内衣'),(898,1,'援助交际'),(899,1,'张筱雨'),(900,1,'招鸡'),(901,1,'招妓'),(902,1,'中年美妇'),(903,1,'抓胸'),(904,1,'自拍'),(905,1,'自慰'),(906,1,'作爱'),(907,1,'18禁'),(908,1,'99bb'),(909,1,'a4u'),(910,1,'a4y'),(911,1,'adult'),(912,1,'amateur'),(913,1,'anal'),(914,1,'a片'),(915,1,'fuck'),(916,1,'gay片'),(917,1,'g点'),(918,1,'g片'),(919,1,'hardcore'),(920,1,'h动画'),(921,1,'h动漫'),(922,1,'incest'),(923,1,'porn'),(924,1,'secom'),(925,1,'sexinsex'),(926,1,'sm女王'),(927,1,'xiao77'),(928,1,'xing伴侣'),(929,1,'tokyohot'),(930,1,'yin荡'),(1021,2,'出售雷管'),(1022,2,'炸药'),(1023,2,'炸药'),(1024,2,'出售炸药'),(1025,2,'出售炸药 电话'),(1026,2,'制作火药配方'),(1027,2,'炸药出售 '),(1028,2,'出售雷管炸药'),(1029,2,'火药配方'),(1030,2,'在家里做原子弹'),(1031,2,'硝酸甘油炸弹制作'),(1032,2,'TNT 炸弹的制作'),(1033,2,'硝铵炸药配方'),(1034,2,'硝酸甘油制作'),(1035,2,'炸弹配方'),(1036,2,'简易炸药'),(1037,2,'火药制作简易炸弹'),(1038,2,'炸弹制作'),(1039,2,'硝酸甘油'),(1040,2,'硝酸甘油炸弹制作方法'),(1041,2,'手把手教你做炸弹'),(1042,2,'恐怖分子傻瓜手册'),(1043,2,'氢弹手工制作方法'),(1044,2,'起爆器'),(1045,2,'自制燃烧弹'),(1046,2,'燃烧弹'),(1047,2,'弹药制造方法'),(1048,2,'液体炸药配方'),(1049,2,'炸药配方与制作'),(1050,2,'自制炸药配方'),(1051,2,'tnt炸药成分'),(1052,2,'炸药的制作'),(1053,2,'tnt炸药配方'),(1054,2,'各种炸药配方大全'),(1055,2,'炸药的成分'),(1056,2,'燃烧弹的制作方 '),(1057,2,'黑火药的配方'),(1058,2,'黑火药的制作'),(1059,2,'黑火药比例'),(1060,2,'燃烧弹 制作'),(1061,2,'黑火药配方比例'),(1062,2,'黑火药的成分'),(1063,2,'原子弹配方'),(1064,2,'原子弹DIY教程'),(1065,2,'C4炸药'),(1066,2,'雷管出售'),(1067,2,'制作硝化甘油'),(1068,2,'DIY核弹'),(1069,2,'核弹制作方法'),(1070,2,'原子弹制作手册'),(1071,2,'简易原子弹教程'),(1072,2,'黑火药配方'),(1073,2,'自制炸药方法'),(1074,2,'教你怎么用土办法做武器'),(1075,2,'莫洛托夫鸡尾酒的配方'),(1076,2,'zz炸弹的制作方法'),(1077,2,'C4炸药的成分'),(1078,2,'炸药制造生产工艺'),(1079,2,'原子弹制造完全手册'),(1080,2,'「红烧兔子」大餐'),(1081,2,'汽车炸弹制作'),(1082,2,'人体炸弹制作流程'),(1083,2,'炸弹制作从入门'),(1084,2,'鸡尾酒炸弹制作'),(1085,2,'集束炸弹制作'),(1086,2,'硝酸铵的简便制取方法'),(1087,2,'莫洛托夫燃烧弹'),(1088,2,'塑胶炸弹'),(1089,2,'用火药混合塑料'),(1090,2,'高性能爆炸物质混合'),(1091,2,'原子弹 制作方法'),(1092,2,'手教你制作原子弹'),(1093,2,'轻型巡航导弹的制作'),(1094,2,'黑索金的制造过程'),(1095,2,'Zha药制造进程'),(1096,2,'购买自爆材料'),(1097,2,'可塑炸弹制作方法'),(1098,2,'C4塑胶炸药'),(1099,2,'水浴法制TNT'),(1100,2,'简易核弹头的制造'),(1101,2,'高爆炸药的基本配方'),(1102,2,'如何制硝化甘油'),(1103,2,'燃烧剂的简易制备'),(1104,2,'简易大威力炸弹'),(1105,2,'炸弹制作入门'),(1106,2,'硝酸甘油炸药制作'),(1107,2,'基本配方是硝酸甘'),(1108,2,'燃烧剂的简易制作'),(1109,2,'制作性能优良的燃烧弹'),(1110,2,'酸甘油炸药'),(1111,2,'简易导弹制作'),(1112,2,'核弹头的制造'),(1113,2,'可塑炸弹制作'),(1114,2,'炸药硝酸甘油制法'),(1115,2,'原子弹制作方法'),(1116,2,'火药的配方'),(1117,2,'手教你做原子弹'),(1118,2,'怎么用土办法做武器'),(1119,2,'手把手教你制作原子弹'),(1120,2,'燃烧弹的制作方法'),(1121,2,'怎么自制燃烧弹'),(1122,2,'TNT炸弹制作'),(1123,2,'高爆炸药基本配方'),(1124,2,'爆破和杀伤燃烧弹'),(1125,2,'炸弹制作从入门到精'),(1126,2,'写的炸弹制作教程'),(1127,2,'原子弹制作 方法'),(1128,2,'教你制作原子弹'),(1129,2,'下硝化甘油的制作方法'),(1130,2,'DIY原子弹'),(1131,2,'原子弹制作'),(1132,2,'炸药及其制造方法'),(1133,2,'ATOM弹制造'),(1134,2,'三分钟教你原子弹'),(1135,2,'火药原理'),(1136,2,'简易炸弹'),(1137,2,'简易燃烧弹'),(1138,2,'土炸药成份'),(1139,2,'你怎么用土办法做武器'),(1140,2,'燃烧弹制作'),(1141,2,'燃烧瓶的制作方法'),(1142,2,'炸弹制作手册 硫酸'),(1143,2,'原子弹制作方法DIY'),(1144,2,'汽油三分之一白糖混合'),(1145,2,'炸药的制作配方'),(1146,2,'燃烧弹的制作方'),(1147,2,'炸药的配方'),(1148,2,'如何自制燃烧弹'),(1149,2,'标准炸弹教程'),(1150,2,'黑火药和硝酸钾农药混合'),(1151,2,'恐怖分子自己制造炸弹'),(1152,2,'手柄制作炸弹引爆器'),(1153,2,'制作炸弹首先要'),(1154,2,'燃烧瓶制作'),(1155,2,'出售炸药QQ'),(1156,2,'出售气枪'),(1157,2,'出售气枪 QQ'),(1158,2,'出售气枪 电话'),(1159,2,'出售手枪'),(1160,2,'出售手枪 QQ'),(1161,2,'出售手枪 电话'),(1162,2,'出售猎枪'),(1163,2,'出售猎枪 QQ'),(1164,2,'出售猎枪 电话'),(1165,2,'出售汽枪'),(1166,2,'出售步枪'),(1167,2,'出售狙击枪'),(1168,2,'出售手狗'),(1169,2,'出售气狗'),(1170,2,'出售猎狗'),(1171,2,'【手枪出售】联系电话'),(1172,2,'64式手枪出售'),(1173,2,'92式手枪出售'),(1174,2,'出售:高仿真枪'),(1175,2,'出售54式手枪'),(1176,2,'出售M1911'),(1177,2,'出售各类猎狗'),(1178,2,'出售国产军用54手枪'),(1179,2,'出售火枪 –游戏'),(1180,2,'出售进口金属枪模'),(1181,2,'出售仿真枪'),(1182,2,'出售麻醉枪免收任何定金'),(1183,2,'出售手枪=货到付款'),(1184,2,'出售外国枪模'),(1185,2,'出售袖珍手枪'),(1186,2,'娥眉气枪出售'),(1187,2,'防身手枪QQ'),(1188,2,'防身武器手枪'),(1189,2,'仿真枪QQ'),(1190,2,'钢珠枪小口径步枪'),(1191,2,'高压狗QQ'),(1192,2,'高压气枪,气枪子弹'),(1193,2,'高压气枪|电狗专卖'),(1194,2,'高压气枪|气枪货到付款'),(1195,2,'高压气枪专卖店'),(1196,2,'各类军用枪'),(1197,2,'各类军用枪支'),(1198,2,'各式气枪出售'),(1199,2,'工字牌气枪出售专卖'),(1200,2,'气枪'),(1201,2,'工字汽狗麻醉枪'),(1202,2,'供应军用手枪'),(1203,2,'供应汽枪'),(1204,2,'购买枪支QQ'),(1205,2,'国产汽狗专卖|气枪货'),(1206,2,'集团出售手枪'),(1207,2,'集团出售手枪气枪'),(1208,2,'进口气枪,气枪子弹'),(1209,2,'军用54出售'),(1210,2,'军用77出售'),(1211,2,'军用手枪钢珠枪'),(1212,2,'里有汽枪出售'),(1213,2,'猎枪出售/枪'),(1214,2,'猎枪仿真枪QQ'),(1215,2,'猎枪麻醉枪钢珠枪'),(1216,2,'猎枪哪里有卖'),(1217,2,'猎枪手枪买卖'),(1218,2,'猎枪销售手枪销售气枪'),(1219,2,'猎枪专卖店'),(1220,2,'猎枪子弹底火专卖'),(1221,2,'麻醉枪出售可验货'),(1222,2,'麻醉枪猎枪军用'),(1223,2,'买卖54狗'),(1224,2,'买卖64狗'),(1225,2,'买卖警察枪支'),(1226,2,'买卖军狗'),(1227,2,'买卖军用枪支'),(1228,2,'卖64手枪'),(1229,2,'卖92手枪'),(1230,2,'卖钢珠枪QQ'),(1231,2,'哪里能买到自制手枪  '),(1232,2,'哪里有钢珠枪'),(1233,2,'哪里有连发猎枪买卖'),(1234,2,'哪里有卖双管猎'),(1235,2,'哪里有气枪卖'),(1236,2,'哪里有枪出售'),(1237,2,'哪里有真枪出售'),(1238,2,'哪里有真枪卖买'),(1239,2,'哪能买到真枪'),(1240,2,'期出售手枪气枪猎'),(1241,2,'气狗 高压气枪'),(1242,2,'气狗出售 进口气狗'),(1243,2,'气枪专卖网'),(1244,2,'气枪/出售/专卖网'),(1245,2,'气枪/猎枪/钢珠枪'),(1246,2,'气枪仿真枪出售'),(1247,2,'气枪购买|气枪组装'),(1248,2,'气枪货到付款,汽狗出售'),(1249,2,'气枪价格 仿真气枪'),(1250,2,'气枪价格，气枪专卖店'),(1251,2,'气枪看货加QQ'),(1252,2,'气枪联系QQ'),(1253,2,'气枪猎枪麻醉枪'),(1254,2,'气枪迷魂药'),(1255,2,'汽狗高压汽枪'),(1256,2,'铅弹 上海工字气枪'),(1257,2,'全金属仿真枪专卖'),(1258,2,'热武器QQ'),(1259,2,'三箭气枪出售'),(1260,2,'手枪气* 枪猎'),(1261,2,'手槍买卖自制手枪'),(1262,2,'售枪购买手枪'),(1263,2,'售手枪QQ'),(1264,2,'司马仿真枪模型QQ'),(1265,2,'淘宝气枪货到付款'),(1266,2,'提供气狗专卖店'),(1267,2,'提供专业及时的气狗'),(1268,2,'秃鹰气枪货到付款'),(1269,2,'锡峰气枪出售'),(1270,2,'仙鹤气枪出售'),(1271,2,'销售/专卖/买卖77式手枪'),(1272,2,'销售电手狗'),(1273,2,'销售气手狗'),(1274,2,'销售小口径步枪'),(1275,2,'销售运动步枪'),(1276,2,'小口径秃鹰沙漠'),(1277,2,'有军用枪支出售'),(1278,2,'长期出 售手枪'),(1279,2,'折叠狗QQ'),(1280,2,'转让 猎枪'),(1281,2,'子弹货到付款'),(1282,2,'自制手枪哪里买  '),(1283,2,'自制手枪哪里有卖'),(1284,2,'阻击枪/汽枪/高压气枪'),(1285,2,'组装手枪  '),(1286,2,'最新销售汽狗'),(1287,2,'出售警用手铐|仿真枪'),(1288,2,'猎枪|真枪'),(1289,2,'手枪|阻击枪'),(1290,2,'弓弩专营'),(1291,2,'弓弩专卖网货到付款'),(1292,2,'弓弩专卖网'),(1293,2,'弓弩专卖货到付款'),(1294,2,'弓弩专卖店网'),(1295,2,'弓弩专卖店'),(1296,2,'弓弩专卖'),(1297,2,'弓弩制作'),(1298,2,'弓弩直销'),(1299,2,'弓弩有限公司'),(1300,2,'弓弩营销中心'),(1301,2,'弓弩一百专卖店'),(1302,2,'弓弩销售'),(1303,2,'弓弩网 '),(1304,2,'弓弩网'),(1305,2,'弓弩图纸'),(1306,2,'弓弩特许经销'),(1307,2,'弓弩狩猎网'),(1308,2,'自制手弩'),(1309,2,'追风弓弩麻醉箭专卖'),(1310,2,'专业弓弩网'),(1311,2,'中国战神军用弓弩'),(1312,2,'中国弩弓专卖'),(1313,2,'中国弓弩专卖网'),(1314,2,'中国弓弩直销'),(1315,2,'中国弓弩网'),(1316,2,'中国弓弩狩猎网'),(1317,2,'中国弓驽网'),(1318,2,'制作简易弓弩 '),(1319,2,'郑州弓弩专卖'),(1320,2,'赵氏弓弩专卖网'),(1321,2,'赵氏弓弩专卖店'),(1322,2,'赵氏弓弩专卖'),(1323,2,'赵氏弓弩销售'),(1324,2,'小型弓弩专卖店'),(1325,2,'小猎人弓弩网'),(1326,2,'狩猎器材弓弩专卖'),(1327,2,'狩猎器材弓弩'),(1328,2,'狩猎弓弩专卖网'),(1329,2,'狩猎弓弩专卖'),(1330,2,'狩猎弓弩麻醉箭'),(1331,2,'手枪式折叠三用弩'),(1332,2,'三利达弓弩专卖网'),(1333,2,'三利达弓弩直营'),(1334,2,'三利达弓弩配件'),(1335,2,'三步倒药箭批发'),(1336,2,'三步倒弩箭专卖'),(1337,2,'三步倒麻醉弩箭销售'),(1338,2,'三步倒麻醉箭专卖'),(1339,2,'三步倒麻醉箭'),(1340,2,'三步倒捕狗药'),(1341,2,'军用弓弩专卖网'),(1342,2,'军用弓弩专卖店'),(1343,2,'军用弓弩批发'),(1344,2,'军用弓弩公司'),(1345,2,'供应三利达弓弩麻醉箭'),(1346,2,'供应三步倒麻醉箭'),(1347,2,'供应秦氏弓弩'),(1348,2,'供应弩用麻醉箭'),(1349,2,'供应弩捕狗箭'),(1350,2,'供应麻醉箭三步倒'),(1351,2,'供应麻醉箭批发'),(1352,2,'供应麻醉箭'),(1353,2,'供应军用弩折叠弩'),(1354,2,'供应军用弓弩专卖'),(1355,2,'供应精品弓弩'),(1356,2,'供应弓弩麻醉箭'),(1357,2,'供应弓弩'),(1358,2,'供应钢珠弓弩'),(1359,2,'弓弩商城专卖'),(1360,2,'弓弩商城'),(1361,2,'弓弩亲兄弟货到付款'),(1362,2,'弓弩批发'),(1363,2,'弓弩免定金货到付款'),(1364,2,'弓弩麻醉箭'),(1365,2,'弓弩麻醉镖'),(1366,2,'弓弩论坛 '),(1367,2,'钢珠弓弩专卖网'),(1368,2,'钢珠弓弩专卖店'),(1369,2,'打狗弓弩三步倒'),(1370,2,'麻醉弓弩专卖店'),(1371,2,'出售军刀'),(1372,2,'出售军刺'),(1373,2,'出售弹簧刀'),(1374,2,'出售三棱刀'),(1375,2,'出售跳刀'),(1376,2,'军刀网'),(1377,2,'南方军刀网'),(1378,2,'户外军刀网'),(1379,2,'三棱军刺专卖'),(1380,2,'出售开山刀军刺'),(1381,2,'西点军刀网'),(1382,2,'军刀专 卖'),(1383,2,'戈博军刀'),(1384,2,'阿兰德龙户外'),(1385,2,'出售军品军刀'),(1386,2,'勃朗宁军刀'),(1387,2,'军刀军品网'),(1388,2,'阿兰得龙野营刀具网'),(1389,2,'出售军刺军刀'),(1390,2,'警用刀具出售'),(1391,2,'折刀专卖网'),(1392,2,'阳江军品军刀网'),(1393,2,'野营刀专卖'),(1394,2,'砍刀精品折刀专卖'),(1395,2,'匕首蝴蝶甩刀专卖'),(1396,2,'军刀专卖军刺'),(1397,2,'军刀专卖刀具批发'),(1398,2,'军刀图片砍刀'),(1399,2,'军刀网军刀专卖'),(1400,2,'军刀价格军用刀具'),(1401,2,'军品军刺网'),(1402,2,'军刀军刺甩棍'),(1403,2,'阳江刀具批发网'),(1404,2,'北方先锋军刀'),(1405,2,'正品军刺出售'),(1406,2,'野营军刀出售'),(1407,2,'开山刀砍刀出售'),(1408,2,'仿品军刺出售'),(1409,2,'军刀直刀专卖'),(1410,2,'手工猎刀专卖'),(1411,2,'自动跳刀专卖'),(1412,2,'军刀电棍销售'),(1413,2,'军刀甩棍销售'),(1414,2,'美国军刀出售'),(1415,2,'极端武力折刀'),(1416,2,'防卫棍刀户外刀具'),(1417,2,'阿兰德龙野营刀'),(1418,2,'仿品军刺网'),(1419,2,'野营砍刀户外军刀'),(1420,2,'手工猎刀户外刀具'),(1421,2,'中国户外刀具网'),(1422,2,'西点军品军刀网'),(1423,2,'野营开山刀军刺'),(1424,2,'三利达弓弩军刀'),(1425,2,'尼泊尔军刀出售'),(1426,2,'防卫野营砍刀出售'),(1427,2,'防卫著名军刀出售'),(1428,2,'防卫棍刀出售'),(1429,2,'防卫甩棍出售'),(1430,2,'防卫电棍出售'),(1431,2,'军刺野营砍刀出售'),(1432,2,'著名精品折刀出售'),(1433,2,'战术军刀出售'),(1434,2,'刺刀专卖网'),(1435,2,'户外军刀出售'),(1436,2,'阳江刀具直销网'),(1437,2,'冷钢刀具直销网'),(1438,2,'防卫刀具直销网'),(1439,2,'极端武力直销网'),(1440,2,'刀具直销网'),(1441,2,'军刀直销网'),(1442,2,'直刀匕首直销网'),(1443,2,'军刀匕首直销网'),(1444,2,'折刀砍刀军品网'),(1445,2,'野营刀具军品网'),(1446,2,'阳江刀具军品网'),(1447,2,'冷钢刀具军品网'),(1448,2,'防卫刀具军品网'),(1449,2,'极端武力军品网'),(1450,2,'军用刀具军品网'),(1451,2,'军刀直刀军品网'),(1452,2,'折刀砍刀专卖'),(1453,2,'野营刀具专卖'),(1454,2,'阳江刀具专卖'),(1455,2,'冷钢刀具专卖'),(1456,2,'防卫刀具专卖'),(1457,2,'出售美军现役军刀'),(1458,2,'福音会'),(1459,2,'中国教徒'),(1460,2,'统一教'),(1461,2,'观音法门'),(1462,2,'清海无上师'),(1463,2,'轮功'),(1464,2,'法轮'),(1465,2,'轮法功'),(1466,2,'三去车仑'),(1467,2,'氵去车仑'),(1468,2,'发论工'),(1469,2,'法x功'),(1470,2,'法o功'),(1471,2,'法0功'),(1472,2,'法一轮一功'),(1473,2,'轮子功'),(1474,2,'车仑工力'),(1475,2,'法lun'),(1476,2,'fa轮'),(1477,2,'法lg'),(1478,2,'flg'),(1479,2,'fl功'),(1480,2,'falungong'),(1481,2,'大法弟子'),(1482,2,'法.轮.功');

/*Table structure for table `sensitive_word_type` */

DROP TABLE IF EXISTS `sensitive_word_type`;

CREATE TABLE `sensitive_word_type` (
  `id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(10) NOT NULL COMMENT '敏感词类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sensitive_word_type` */

insert  into `sensitive_word_type`(`id`,`type`) values (1,'色情'),(2,'违法'),(3,'广告'),(4,'政治');

/*Table structure for table `target_type` */

DROP TABLE IF EXISTS `target_type`;

CREATE TABLE `target_type` (
  `id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `value` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `target_type` */

insert  into `target_type`(`id`,`value`) values (1,'article'),(2,'posts'),(3,'comment');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(20) NOT NULL COMMENT '邮箱注册',
  `user_password` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(20) NOT NULL COMMENT '昵称',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `sex` tinyint(1) NOT NULL DEFAULT '1' COMMENT '性别',
  `avatar` varchar(100) NOT NULL COMMENT '用户头像路径',
  `user_type` tinyint unsigned NOT NULL COMMENT '高中/大学/教师/其他',
  `liked_count` int unsigned NOT NULL DEFAULT '0' COMMENT '获赞数',
  `collected_count` int unsigned NOT NULL DEFAULT '0' COMMENT '收藏数',
  `register_date` date NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`),
  KEY `FK_User_UserType_On_Type` (`user_type`),
  CONSTRAINT `FK_User_UserType_On_Type` FOREIGN KEY (`user_type`) REFERENCES `user_type` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`email`,`user_password`,`nickname`,`birthday`,`sex`,`avatar`,`user_type`,`liked_count`,`collected_count`,`register_date`) values (1,'111111@qq.com','2cb88t13iveaapvr602veqgt55','李四','2020-10-06',0,'D:\\WanderFourAvatar\\default\\boy.png',2,0,0,'2020-10-06'),(2,'123123@qq.com','-olpgo6uqju3lhb7id1sopfa1q','123123',NULL,0,'D:\\WanderFourAvatar\\default\\boy.png',1,0,0,'2020-10-10'),(3,'123333@qq.com','iinvklsel76k55lu64afpffvp','李四',NULL,1,'D:\\WanderFourAvatar\\default\\boy.png',3,0,0,'2020-10-17'),(4,'123456@qq.com','2i8jdhgfnouflho1iqsl47tc4l','张三','2020-10-06',1,'D:\\WanderFourAvatar\\default\\boy.png',1,0,0,'2020-10-06'),(5,'123456789@qq.com','2gufa44hp2m2ml7is93kbuet87','张三',NULL,1,'D:\\WanderFourAvatar\\default\\boy.png',3,0,0,'2020-10-15'),(6,'222222@qq.com','-3pkqe4q9rlcvnt9me7pj4l8td7','王五','2020-10-06',1,'D:\\WanderFourAvatar\\default\\boy.png',3,0,0,'2020-10-06'),(7,'333333@qq.com','lhu6gt4jibqrt6b0bekvk2mnf','李四',NULL,1,'D:\\WanderFourAvatar\\default\\boy.png',3,0,0,'2020-10-17'),(8,'121189@qq.com','dl9c2bnukgit4tko9f59p4v62','张三',NULL,1,'D:\\WanderFourAvatar\\default\\boy.png',3,0,0,'2020-10-18');

/*Table structure for table `user_type` */

DROP TABLE IF EXISTS `user_type`;

CREATE TABLE `user_type` (
  `id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_type` */

insert  into `user_type`(`id`,`type`) values (1,'高中生'),(2,'大学生'),(3,'教师'),(4,'其他');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
