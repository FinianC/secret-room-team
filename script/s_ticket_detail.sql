/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : secret_room

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 19/03/2023 17:58:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_ticket_detail
-- ----------------------------
DROP TABLE IF EXISTS `s_ticket_detail`;
CREATE TABLE `s_ticket_detail`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ticket_id` int NULL DEFAULT NULL COMMENT '密室id',
  `maximum_number` int NULL DEFAULT NULL COMMENT '最大拼团人数',
  `clustering_number` int NULL DEFAULT NULL COMMENT '成团数',
  `time` int NULL DEFAULT NULL COMMENT '时间 单位 分钟',
  `purchase_instructions` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT '购买须知',
  `introduce` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT '商品介绍',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
