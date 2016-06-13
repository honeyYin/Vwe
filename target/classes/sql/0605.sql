ALTER TABLE Paper 
add COLUMN type int(2),
add COLUMN url VARCHAR(1024) CHARACTER SET UTF8 DEFAULT NULL;

UPDATE Paper SET type = 0;

DROP TABLE IF EXISTS `CrawlerSite`;
CREATE TABLE `CrawlerSite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT NULL,
  `disabled` int(2) DEFAULT NULL,
  `title` varchar(255) CHARACTER SET UTF8 DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `url` varchar(1024) CHARACTER SET UTF8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crawlersite
-- ----------------------------
INSERT INTO `CrawlerSite`(createTime,updateTime , disabled , title , url ) VALUES (CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '0', '宝宝树', 'http://www.babytree.com/');
INSERT INTO `CrawlerSite`(createTime,updateTime , disabled , title , url ) VALUES (CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '0', '妈妈网', 'http://www.mama.cn/');
INSERT INTO `CrawlerSite`(createTime,updateTime , disabled , title , url ) VALUES (CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP() ,'0', '摇篮网', 'http://www.yaolan.com/');
