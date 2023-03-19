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

 Date: 19/03/2023 17:57:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `s_sys_config`;
CREATE TABLE `s_sys_config`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `variable` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '变量 key',
  `value` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '值',
  `delete_state` int NULL DEFAULT 0 COMMENT '删除状态',
  `create_user` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_sys_config
-- ----------------------------
INSERT INTO `s_sys_config` VALUES (1, 'orderTimeout', '30', 0, NULL, NULL, NULL, NULL);
INSERT INTO `s_sys_config` VALUES (2, 'qrCodeExpire', '3', 0, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
