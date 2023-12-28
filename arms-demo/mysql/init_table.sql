CREATE DATABASE IF NOT EXISTS `arms_mock`;
USE `arms_mock`;

CREATE TABLE IF NOT EXISTS `dummy_record` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `content` varchar(200) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1642 DEFAULT CHARSET=utf8mb4