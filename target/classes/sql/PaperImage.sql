

-- ----------------------------
-- Table structure for paperimage
-- ----------------------------
DROP TABLE IF EXISTS `Image`;
CREATE TABLE `Image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `imgUrl` varchar(255) CHARACTER SET UTF8 DEFAULT NULL ,
  `ratio` varchar(255) CHARACTER SET UTF8 DEFAULT NULL ,
  `size` bigint(20) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

