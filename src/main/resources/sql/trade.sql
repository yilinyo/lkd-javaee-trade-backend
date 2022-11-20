/*
 Navicat Premium Data Transfer

 Source Server         : javaee
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Schema         : trade

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 20/11/2022 14:06:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `cid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `gid` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  `uid` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发表时间',
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `gid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品号',
  `goodsName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goodsPrice` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `uid` bigint(20) NULL DEFAULT NULL COMMENT '商品出售者id',
  `goodsNum` bigint(20) NULL DEFAULT NULL COMMENT '商品数量',
  `createTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `goodsImg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图像',
  `goodsInfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品详情',
  `isDeleted` tinyint(4) NULL DEFAULT 1 COMMENT '0 删除',
  PRIMARY KEY (`gid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `gid` bigint(20) NULL DEFAULT NULL,
  `sellerId` bigint(20) NULL DEFAULT NULL,
  `buyerId` bigint(20) NULL DEFAULT NULL,
  `nums` int(10) NULL DEFAULT NULL,
  `createTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isDeleted` tinyint(4) NULL DEFAULT 1 COMMENT '0删除',
  PRIMARY KEY (`oid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `passWord` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `userCredit` int(11) NULL DEFAULT 100 COMMENT '用户值',
  `userStatus` tinyint(4) UNSIGNED ZEROFILL NULL DEFAULT 0000 COMMENT '用户状态 0 正常 1 异常',
  `userRole` tinyint(4) UNSIGNED NULL DEFAULT 0 COMMENT '用户角色 0 正常 1 管理员',
  `createTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `isDeleted` tinyint(4) NULL DEFAULT 1 COMMENT '0 删除 ',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
