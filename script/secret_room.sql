/*
 Navicat MySQL Data Transfer

 Source Server         : 192.168.0.102
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : 192.168.0.102:3306
 Source Schema         : secret_room

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 29/01/2023 17:41:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_admin
-- ----------------------------
DROP TABLE IF EXISTS `s_admin`;
CREATE TABLE `s_admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '密码',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '后台管理账户' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for s_banner
-- ----------------------------
DROP TABLE IF EXISTS `s_banner`;
CREATE TABLE `s_banner`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `picture` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '图片',
  `status` int NULL DEFAULT 0 COMMENT '状态 1上架 0下架',
  `extra_links` int NULL DEFAULT 0 COMMENT '是否是外链接 1是 0不是',
  `value` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '值 外链地址 或者车队id',
  `sort` int NULL DEFAULT 0 COMMENT '排序 值越大优先级越高',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '描述',
  `delete_state` int NULL DEFAULT 0 COMMENT '删除状态 0未删除 1删除',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '小程序横幅' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for s_file
-- ----------------------------
DROP TABLE IF EXISTS `s_file`;
CREATE TABLE `s_file`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '名称',
  `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'url',
  `type` int NULL DEFAULT 1 COMMENT '类型 1图片',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for s_fleet_change_message
-- ----------------------------
DROP TABLE IF EXISTS `s_fleet_change_message`;
CREATE TABLE `s_fleet_change_message`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `motorcade_id` int NOT NULL COMMENT '车队id',
  `type` int NOT NULL COMMENT '类型 1 加入 2离开',
  `read` int NOT NULL DEFAULT 0 COMMENT '已读 1  未读0',
  `user_id` int NULL DEFAULT NULL COMMENT '车队队长id',
  `message` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '消息',
  `delete_state` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_user` int NOT NULL COMMENT '创建人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `update_user` int NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '消息通知表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for s_fleet_type
-- ----------------------------
DROP TABLE IF EXISTS `s_fleet_type`;
CREATE TABLE `s_fleet_type`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '类型名称',
  `delete_state` int NULL DEFAULT 0 COMMENT '删除状态 0未删除 1删除',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '车队类型' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for s_group_chat
-- ----------------------------
DROP TABLE IF EXISTS `s_group_chat`;
CREATE TABLE `s_group_chat`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '群名',
  `group_head_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '群头像',
  `motorcade_id` int NULL DEFAULT NULL COMMENT '车队id',
  `banned` int NULL DEFAULT 0 COMMENT '0未禁言 1禁言',
  `delete_state` int NULL DEFAULT 0 COMMENT '删除 1删除 ',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for s_group_chat_member
-- ----------------------------
DROP TABLE IF EXISTS `s_group_chat_member`;
CREATE TABLE `s_group_chat_member`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '成员id',
  `group_id` int NOT NULL COMMENT '群id',
  `is_group_leader` int NOT NULL DEFAULT 0 COMMENT '群主 1',
  `first_message_id` int NOT NULL COMMENT '第一次进入群聊消息',
  `last_message_id` int NOT NULL COMMENT '上一次收到消息的id',
  `delete_state` int NOT NULL DEFAULT 0 COMMENT '删除 1',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for s_group_msg_content
-- ----------------------------
DROP TABLE IF EXISTS `s_group_msg_content`;
CREATE TABLE `s_group_msg_content`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` int NOT NULL COMMENT '群组id',
  `user_id` int NOT NULL COMMENT '发送者id',
  `content` varchar(900) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人',
  `message_type_id` int NOT NULL DEFAULT 1 COMMENT '消息类型id',
  `delete_state` int NOT NULL DEFAULT 0 COMMENT '删除状态',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '群聊消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for s_joined_motorcade
-- ----------------------------
DROP TABLE IF EXISTS `s_joined_motorcade`;
CREATE TABLE `s_joined_motorcade`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `motorcade_id` int NULL DEFAULT NULL COMMENT '车队id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `delete_state` int NULL DEFAULT 0 COMMENT '删除状态 0未删除 1删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `update_user` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '已加入的车队' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for s_keyword_record
-- ----------------------------
DROP TABLE IF EXISTS `s_keyword_record`;
CREATE TABLE `s_keyword_record`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `keyword` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '关键字',
  `total` int NULL DEFAULT NULL COMMENT '搜索次数',
  `date` int NULL DEFAULT NULL COMMENT '日期 YYYYMMDD日期',
  `delete_state` int NULL DEFAULT NULL COMMENT '删除状态 0未删除 1删除',
  `create_time` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '关键字记录' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for s_message_type
-- ----------------------------
DROP TABLE IF EXISTS `s_message_type`;
CREATE TABLE `s_message_type`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '消息类型编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '消息类型名称',
  `delete_state` int NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '消息类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for s_motorcade
-- ----------------------------
DROP TABLE IF EXISTS `s_motorcade`;
CREATE TABLE `s_motorcade`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '标题',
  `group_head_img` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '群头像',
  `terror_level` int NULL DEFAULT NULL COMMENT '恐怖等级',
  `type_id` int NULL DEFAULT NULL COMMENT '车队类型 密室or剧本等等..',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `theme` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '主题',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '描述',
  `competition_date` datetime(0) NULL DEFAULT NULL COMMENT '拼场日期',
  `pictures` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '图片 json数组',
  `status` int NULL DEFAULT NULL COMMENT '车队状态 1组队中 2组队成功 3组队失败',
  `banned` int NULL DEFAULT 0 COMMENT '0未禁言 1禁言',
  `delete_state` int NULL DEFAULT 0 COMMENT '删除状态 0未删除 1 删除',
  `maximum_number` int NULL DEFAULT NULL COMMENT '最大拼团人数',
  `clustering_number` int NULL DEFAULT NULL COMMENT '成团数',
  `already_existing` int NULL DEFAULT 0 COMMENT '已有人数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` int NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '车队' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for s_subscribe
-- ----------------------------
DROP TABLE IF EXISTS `s_subscribe`;
CREATE TABLE `s_subscribe`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `motorcade_id` int NULL DEFAULT NULL COMMENT '车队id',
  `openid` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'opneid',
  `form_id` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'form_id',
  `delete_state` int NULL DEFAULT 0 COMMENT ' 是否删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '订阅消息' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for s_terror_level
-- ----------------------------
DROP TABLE IF EXISTS `s_terror_level`;
CREATE TABLE `s_terror_level`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '名称',
  `delete_state` int NULL DEFAULT 0 COMMENT '删除状态',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '恐怖等级' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `open_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'open_id',
  `union_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'union_id',
  `nickname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '昵称',
  `true_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '电话号码',
  `wechat_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '微信号',
  `header_img` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '头像',
  `role` int NULL DEFAULT NULL COMMENT '权限 1仅拼团 2发布拼团',
  `sex` int NULL DEFAULT NULL COMMENT '性别 0女 1男',
  `delete_state` int NULL DEFAULT 0 COMMENT '删除状态 0未删除 1删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` int NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '用户' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for s_wish_team
-- ----------------------------
DROP TABLE IF EXISTS `s_wish_team`;
CREATE TABLE `s_wish_team`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tile` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '愿望标题',
  `allow_push` int NULL DEFAULT 1 COMMENT '允许推送 1允许推送  0取消推送',
  `delete_state` int NULL DEFAULT 0 COMMENT '删除状态 0未删除 1删除',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '愿望车队' ROW_FORMAT = COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
