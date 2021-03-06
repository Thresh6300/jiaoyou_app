/*
 Navicat Premium Data Transfer

 Source Server         : 47.108.134.128_jiaoyou
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : 47.108.134.128:3306
 Source Schema         : jiaoyou

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 11/01/2021 17:27:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for PDMAN_DB_VERSION
-- ----------------------------
DROP TABLE IF EXISTS `PDMAN_DB_VERSION`;
CREATE TABLE `PDMAN_DB_VERSION`  (
  `DB_VERSION` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `VERSION_DESC` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CREATED_TIME` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for accept_offline_activities
-- ----------------------------
DROP TABLE IF EXISTS `accept_offline_activities`;
CREATE TABLE `accept_offline_activities`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sendOfflineActivities` int(11) NULL DEFAULT NULL COMMENT '聚会ID',
  `acceptMemberId` int(11) NULL DEFAULT NULL COMMENT '接收人id',
  `acceptMemberHead` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收人用户头像',
  `acceptMemberNickName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收人昵称',
  `acceptMemberSex` int(11) NULL DEFAULT NULL COMMENT '接收人性别',
  `acceptMemberAge` int(11) NULL DEFAULT NULL COMMENT '接收人年龄',
  `keepAnAppointmentTime` datetime NULL DEFAULT NULL COMMENT '赴约时间',
  `keepAnAppointmentState` int(11) NULL DEFAULT NULL COMMENT '状态  \r\n1已申请赴约 待发起方审核\r\n2发起方通过 赴约申请\r\n3已到达赴约地点,\r\n4待审核,\r\n5已取消,\r\n6平台已拒绝\r\n7平台审核通过\r\n8聚会已结束\r\n9发起者未通过你的申请 已拒绝',
  `oldState` int(255) NULL DEFAULT NULL COMMENT '历史状态',
  `closeType` int(11) NULL DEFAULT NULL COMMENT '1:申请取消  2：强制取消',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '线下活动列表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `roleId` int(11) NULL DEFAULT NULL COMMENT '角色',
  `addTime` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `editTime` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `token` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'token',
  `enableState` int(11) NULL DEFAULT NULL COMMENT '启用状态 1:启用2:禁用',
  `deleteState` int(11) NULL DEFAULT NULL COMMENT '删除状态 1:已删除2:未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台管理员表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `consignee_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `consignee_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人地址',
  `consignee_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人联系方式',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '收货地址状态0默认;1不默认',
  `address_user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市',
  `district` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区',
  PRIMARY KEY (`address_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '地址管理表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for advance_order
-- ----------------------------
DROP TABLE IF EXISTS `advance_order`;
CREATE TABLE `advance_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `to_shop_time` datetime NULL DEFAULT NULL COMMENT '到店时间',
  `shop_id` int(11) NULL DEFAULT NULL COMMENT '商品id',
  `shop_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `shop_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `call` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '称呼',
  `check_in` int(10) NULL DEFAULT NULL COMMENT '入住天数(入住人数)',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime NULL DEFAULT NULL,
  `business_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺名称',
  `business_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺log',
  `shop_introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品简介',
  `width` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介图片的宽高比（宽/高）默认两位小数',
  `isDealWith` int(2) NULL DEFAULT 1 COMMENT '客服是否已处理（0：已处理，1：未处理）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '预定服务表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for agreement
-- ----------------------------
DROP TABLE IF EXISTS `agreement`;
CREATE TABLE `agreement`  (
  `untitled` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `aboutUS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '关于我们',
  `registrationAgreement` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '注册协议',
  `memberDescription` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '会员说明',
  `sharingRules` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分享规则',
  `greenCard` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '绿卡介绍',
  `privacy` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '隐私政策',
  `editTime` datetime NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`untitled`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '协议表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for bank_Manage
-- ----------------------------
DROP TABLE IF EXISTS `bank_Manage`;
CREATE TABLE `bank_Manage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bankName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡开户行名字（如：中国银行）',
  `bankIcon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡图标',
  `bankSort` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡排序',
  `bankColor1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颜色值1',
  `bankColor2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颜色值2',
  `bankStatus` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态 0：正常 1：删除',
  `createTime` datetime NULL DEFAULT NULL,
  `UpdateTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '银行卡管理' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for bankcard
-- ----------------------------
DROP TABLE IF EXISTS `bankcard`;
CREATE TABLE `bankcard`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '会员id',
  `openingBank` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开户行',
  `bankMemberName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `bankNumber` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `weight` int(11) NULL DEFAULT NULL COMMENT '权重',
  `addTime` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `editTime` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '银行卡表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '关联会员',
  `driving_license_photo` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行驶证照片',
  `images` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车辆照片',
  `vehicleType` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车型',
  `model` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `addTime` datetime NULL DEFAULT NULL COMMENT '绑定时间',
  `weight` int(11) NULL DEFAULT NULL COMMENT '权重',
  `deledeState` int(11) NULL DEFAULT NULL COMMENT '删除状态 1:已删除2:未删除',
  `auditState` int(11) NULL DEFAULT NULL COMMENT '1:待審核  2：審核通過 3：審核未通過',
  `reason` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '审核原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '车辆表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for car_copy
-- ----------------------------
DROP TABLE IF EXISTS `car_copy`;
CREATE TABLE `car_copy`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '关联会员',
  `driving_license_photo` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行驶证照片',
  `images` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车辆照片',
  `vehicleType` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车型',
  `model` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `addTime` datetime NULL DEFAULT NULL COMMENT '绑定时间',
  `weight` int(11) NULL DEFAULT NULL COMMENT '权重',
  `deledeState` int(11) NULL DEFAULT NULL COMMENT '删除状态 1:已删除2:未删除',
  `auditState` int(11) NULL DEFAULT NULL COMMENT '1:待審核  2：審核通過 3：審核未通過',
  `reason` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '审核原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '车辆表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for circle_of_friends
-- ----------------------------
DROP TABLE IF EXISTS `circle_of_friends`;
CREATE TABLE `circle_of_friends`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memerId` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `memberHead` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `memberNickName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `memberSex` int(11) NULL DEFAULT NULL COMMENT '性别 1:男2:女',
  `memberAge` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `lable` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车标',
  `city` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在市',
  `addTime` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '动态内容',
  `images` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片数组',
  `video` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '视频',
  `likeSize` int(11) NULL DEFAULT NULL COMMENT '点赞量',
  `commentSize` int(11) NULL DEFAULT NULL COMMENT '评论量',
  `state` int(11) NULL DEFAULT NULL COMMENT '是否隐藏地址  1：是 2：否',
  `strangersInTheSameCity` int(1) NULL DEFAULT NULL COMMENT '是否允许同城陌生人查看  1：允许  2：不允许',
  `strangersOutsideTheCity` int(1) NULL DEFAULT NULL COMMENT '是否允许同城以外陌生人 1：允许  2：不允许',
  `citySynchronization` int(1) NULL DEFAULT NULL COMMENT '是否同步到世界圈  1：同步  2：不同步',
  `defaultType` int(1) NULL DEFAULT NULL COMMENT '1;默认动态  2：不是默认动态(新人报道)',
  `readMembers` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '已读会员数组',
  `newComerReport` int(1) NULL DEFAULT 0 COMMENT '是否是新人报道（0：不是，1：是）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 333 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '朋友圈动态' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for club
-- ----------------------------
DROP TABLE IF EXISTS `club`;
CREATE TABLE `club`  (
  `club_id` int(11) NOT NULL AUTO_INCREMENT,
  `club_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `club_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `club_grade` int(11) NULL DEFAULT 0 COMMENT '等级',
  `club_introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `wage` int(11) NULL DEFAULT 0 COMMENT '每日工资（银钻对的这个是银钻）',
  `wage_time` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '每日发工资的时间',
  `club_notice` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公告',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0潜力俱乐部;1阔绰俱乐部',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `total_wages` decimal(11, 2) NULL DEFAULT 0.00 COMMENT '累计工资',
  `privacy_mode` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '隐私模式 0开;1关',
  `secretary_number` int(11) NULL DEFAULT 1 COMMENT '可以设置几个俱乐部秘书',
  `club_sort` int(11) NULL DEFAULT 10 COMMENT '排序',
  `today_wage` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '今日是否发了工资 0没有发;1发了',
  PRIMARY KEY (`club_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '俱乐部' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for club_buddy
-- ----------------------------
DROP TABLE IF EXISTS `club_buddy`;
CREATE TABLE `club_buddy`  (
  `buddy_id` int(11) NOT NULL AUTO_INCREMENT,
  `oneself_id` int(11) NULL DEFAULT NULL COMMENT '自身id',
  `member_id` int(11) NULL DEFAULT NULL COMMENT '会员id',
  `secretary_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0不是秘书1是秘书',
  `club_id` int(11) NULL DEFAULT NULL COMMENT '俱乐部id',
  `diamondNumber` int(11) NULL DEFAULT 0 COMMENT '获取的金钻数量',
  `diamondDayNumber` int(11) NULL DEFAULT 0 COMMENT '每日获取的金钻数量',
  `diamondWeekNumber` int(11) NULL DEFAULT 0 COMMENT '每周获取的金钻数量',
  `diamondMothNumber` int(11) NULL DEFAULT 0 COMMENT '每月获取的金钻数量',
  `diamond_time` datetime NULL DEFAULT NULL COMMENT '金钻得时间',
  `no_disturbing` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '消息免打扰 0,开-打扰;1关-不打扰',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`buddy_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 203 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '俱乐部好友表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for club_notice
-- ----------------------------
DROP TABLE IF EXISTS `club_notice`;
CREATE TABLE `club_notice`  (
  `buddy_notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `buddy_notice_user_id` int(11) NULL DEFAULT NULL COMMENT '申请人id',
  `buddy_notice_club_id` int(11) NULL DEFAULT NULL COMMENT '俱乐部id',
  `buddy_notice_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人名称',
  `hend` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `buddy_notice_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请审核 0审核中;1通过2不通过',
  `buddy_notice_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请标题',
  `buddy_notice_club_user_id` int(11) NULL DEFAULT NULL COMMENT '俱乐部创建人id',
  `buddy_notice_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '理由',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `is_reads` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '已读id',
  PRIMARY KEY (`buddy_notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for club_task
-- ----------------------------
DROP TABLE IF EXISTS `club_task`;
CREATE TABLE `club_task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务内容',
  `task_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `task_number` int(11) NULL DEFAULT 0 COMMENT '参与人数',
  `task_not_number` int(11) NULL DEFAULT 0 COMMENT '未完成人数',
  `task_has_number` int(11) NULL DEFAULT 0 COMMENT '已完成人数',
  `member_id` int(11) NULL DEFAULT NULL COMMENT '会员id/发布人id',
  `club_id` int(11) NULL DEFAULT NULL COMMENT '俱乐部id',
  `gameLabelId` int(11) NULL DEFAULT NULL COMMENT '任务标签id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `past_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0未过期;1过期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '俱乐部任务表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `collect_id` int(20) NOT NULL AUTO_INCREMENT,
  `collect_user_id` int(20) NULL DEFAULT NULL COMMENT '用户id',
  `collect_shop_id` int(20) NULL DEFAULT NULL COMMENT '商品的id',
  `collect_server_id` int(10) NULL DEFAULT NULL COMMENT '服务的id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`collect_id`) USING BTREE,
  INDEX `collect_user_id`(`collect_user_id`) USING BTREE,
  INDEX `collect_vitae_id`(`collect_shop_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 180 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '喜欢' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for deliver_goods_logs
-- ----------------------------
DROP TABLE IF EXISTS `deliver_goods_logs`;
CREATE TABLE `deliver_goods_logs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `send_user_id` int(11) NULL DEFAULT NULL COMMENT '发货人id值',
  `order_id` int(11) NULL DEFAULT NULL COMMENT '订单id',
  `receive_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人姓名',
  `receive_user_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人手机号',
  `receive_user_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人地址',
  `send_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '寄件人姓名',
  `send_user_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '寄件人手机号',
  `send_user_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '寄件人地址',
  `express_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '快递类型(顺丰)',
  `express_weight` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '快递重量',
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物品名称',
  `pay_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '支付类型',
  `remark` varchar(5096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '发货记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for diamondconsumptionrecord
-- ----------------------------
DROP TABLE IF EXISTS `diamondconsumptionrecord`;
CREATE TABLE `diamondconsumptionrecord`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '接收方会员ID',
  `sendMemberId` int(11) NULL DEFAULT NULL COMMENT '发送方会员ID',
  `type` int(11) NULL DEFAULT 1 COMMENT '类型 1：红包 2： 购买礼物',
  `consumptionTime` datetime NULL DEFAULT NULL COMMENT '消费时间',
  `consumptionSize` bigint(20) NULL DEFAULT NULL COMMENT '消费数量',
  `surplusSzie` bigint(20) NULL DEFAULT NULL COMMENT '剩余数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '钻石消费记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for diamonds
-- ----------------------------
DROP TABLE IF EXISTS `diamonds`;
CREATE TABLE `diamonds`  (
  `diamonds_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `diamonds_product_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品ID',
  `diamonds_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `diamonds_price` decimal(11, 2) NULL DEFAULT NULL COMMENT '金额',
  `diamonds_size` int(11) NULL DEFAULT NULL COMMENT '钻石数量',
  `diamonds_type` int(11) NULL DEFAULT NULL COMMENT '1:黑卡 2：会员 3：钻石',
  `diamonds_terminal` int(11) NULL DEFAULT NULL COMMENT '1:苹果 2;安卓',
  `start_color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开始颜色',
  `end_color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结束颜色',
  `month` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月份描述',
  `type` int(11) NULL DEFAULT NULL COMMENT '1:黑卡  2：会员 3：钻石',
  PRIMARY KEY (`diamonds_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '产品信息表（购卡或者购买钻石，VIP开通使用的表 ）' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dynamic_comments_of_car_users
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_comments_of_car_users`;
CREATE TABLE `dynamic_comments_of_car_users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dynamicIdOrCommentId` int(11) NULL DEFAULT NULL COMMENT '动态id/评论id 主要看级别',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '评论人',
  `memberHead` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论人头像',
  `memberNickName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论人昵称',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '评论内容',
  `addTime` datetime NULL DEFAULT NULL COMMENT '评论时间',
  `level` int(11) NULL DEFAULT NULL COMMENT '评论级别 1：直接评论动态2：回复评论',
  `circle_of_friends_id` int(11) NULL DEFAULT NULL COMMENT '朋友圈id',
  `oneself_id` int(11) NULL DEFAULT NULL COMMENT '自身的id',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0评论1点赞',
  `context_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0内容1图片2视频',
  `lable` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '车友动态评论表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dynamic_review_of_friends_circle
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_review_of_friends_circle`;
CREATE TABLE `dynamic_review_of_friends_circle`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dynamicIdOrCommentId` int(11) NULL DEFAULT NULL COMMENT '动态id/评论id 主要看级别',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '评论人',
  `memberHead` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论人头像',
  `memberNickName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论人昵称',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '评论内容',
  `addTime` datetime NULL DEFAULT NULL COMMENT '评论时间',
  `level` int(11) NULL DEFAULT NULL COMMENT '评论级别 1：直接评论动态2：回复评论',
  `lable` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车标',
  `oneself_id` int(11) NULL DEFAULT NULL COMMENT '自身的id',
  `context_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0内容;1图片;2视频',
  `delFlag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '0未删除1已删除',
  `readMembers` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '已读会员数组',
  `dynamicId` int(11) NULL DEFAULT NULL COMMENT '动态id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 235 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '朋友圈动态评论表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for evaluate
-- ----------------------------
DROP TABLE IF EXISTS `evaluate`;
CREATE TABLE `evaluate`  (
  `evaluate_id` int(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL COMMENT '评价时间',
  `evaluate_content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评价内容',
  `evaluate_user_id` int(20) NULL DEFAULT NULL COMMENT '用户id(买家id值)',
  `evaluate_order_id` int(20) NULL DEFAULT NULL COMMENT '订单id',
  `evaluate_shop_id` int(11) NULL DEFAULT NULL COMMENT '商品的id',
  `evaluate_server_id` int(11) NULL DEFAULT NULL COMMENT '服务的id',
  `describe_conform` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述相符',
  `quality_satisfaction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '质量满意',
  `service_attitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务态度',
  `logistics_service` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流服务',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`evaluate_id`) USING BTREE,
  INDEX `evaluate_identity`(`evaluate_order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评价' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for express_code
-- ----------------------------
DROP TABLE IF EXISTS `express_code`;
CREATE TABLE `express_code`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '快递公司编码' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for fabulous
-- ----------------------------
DROP TABLE IF EXISTS `fabulous`;
CREATE TABLE `fabulous`  (
  `fabulousId` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '会员ID',
  `dynamicId` int(11) NULL DEFAULT NULL COMMENT '动态id',
  `type` int(11) NULL DEFAULT NULL COMMENT '动态类型 1：朋友圈动态 2：车友圈动态',
  `addTime` datetime NULL DEFAULT NULL COMMENT '点赞时间',
  `oneself_id` int(11) NULL DEFAULT NULL COMMENT '自身的id',
  `context_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0内容;1图片;2视频',
  `memberHead` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `memberNickName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `delFlag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '0未删除1已删除',
  PRIMARY KEY (`fabulousId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 211 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '朋友圈动态点赞信息记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for game_bank
-- ----------------------------
DROP TABLE IF EXISTS `game_bank`;
CREATE TABLE `game_bank`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `game_stem` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '题干（内容）',
  `game_select_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选项游戏选项标签表的id（答题游戏视频或者其他）',
  `true_answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '正确答案',
  `game_sort` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '排序值',
  `game_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '答题游戏标题',
  `game_title_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题图标',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '答题游戏题库' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for game_select_label
-- ----------------------------
DROP TABLE IF EXISTS `game_select_label`;
CREATE TABLE `game_select_label`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `game_select_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选项标题',
  `game_select_sort` int(11) NULL DEFAULT NULL COMMENT '排序值',
  `game_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '1答题任务  2视频任务 3图片任务 ',
  `game_select_title_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '选择标题的图标',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '答题游戏库选项标签' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gift
-- ----------------------------
DROP TABLE IF EXISTS `gift`;
CREATE TABLE `gift`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `images` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `weight` int(11) NULL DEFAULT NULL COMMENT '权重',
  `price` int(11) NULL DEFAULT NULL COMMENT '单价',
  `addTime` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `editTime` datetime NULL DEFAULT NULL COMMENT '编辑时间',
  `enableState` int(11) NULL DEFAULT NULL COMMENT '启用状态 1:启用2:禁用',
  `deleteState` int(11) NULL DEFAULT NULL COMMENT '删除状态 1:已删除2:未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '礼物表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for giftconsumption
-- ----------------------------
DROP TABLE IF EXISTS `giftconsumption`;
CREATE TABLE `giftconsumption`  (
  `REVISION` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '接收方ID',
  `sendMemberId` int(11) NULL DEFAULT NULL COMMENT '发送方id',
  `sendMemberHead` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送方头像',
  `sendMemberNickName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送方昵称',
  `sendLable` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车标',
  `giftId` int(11) NULL DEFAULT NULL COMMENT '礼物ID',
  `giftImages` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '礼物图片',
  `giftName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '礼物名称',
  `giftSize` int(11) NULL DEFAULT NULL COMMENT '礼物数量',
  `addTime` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `drawInProportion` int(32) NULL DEFAULT NULL COMMENT '该礼物是否被接收方接收 1： 是 2 ：否',
  `withdrawTime` datetime NULL DEFAULT NULL COMMENT '申请提现时间',
  `end_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`REVISION`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 411 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '礼物记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for good_friend
-- ----------------------------
DROP TABLE IF EXISTS `good_friend`;
CREATE TABLE `good_friend`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `oneselfId` int(11) NULL DEFAULT NULL COMMENT '会员id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '好友id',
  `memberHead` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友头像',
  `memberNickName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友昵称',
  `remarks` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友备注',
  `addTime` datetime NULL DEFAULT NULL COMMENT '成为好友时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '好友列表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lable
-- ----------------------------
DROP TABLE IF EXISTS `lable`;
CREATE TABLE `lable`  (
  `label_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NULL DEFAULT NULL COMMENT '会员ID',
  `label_charm_default` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '魅力默认',
  `label_charm_select` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '魅力选中',
  `label_city_default` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '城市默认',
  `label_city_select` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市选中',
  `label_local_default` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '地方默认',
  `label_local_select` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地方选中',
  `label_hobby_default` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '爱好默认',
  `label_hobby_select` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '爱好选中',
  `label_education_default` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '学历默认',
  `label_education_select` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学历选中',
  `lable_state_default` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '状态默认',
  `lable_state_select` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态选中',
  `lable_annual_income_default` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '年收入默认',
  `lable_annual_income_select` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年收入选中',
  `lable_car_size_default` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '车辆默认',
  `lable_car_size_select` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车辆选中',
  `lable_image_default` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '形象默认',
  `lable_image_select` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '形象选中',
  `lable_character_default` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '性格默认',
  `lable_character_select` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性格选中',
  `lable_industry_default` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '行业默认',
  `lable_industry_select` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行业选中',
  `lable_height` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身高',
  `lable_weight` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体重',
  `lable_birthday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生日',
  `lable_drinking_power` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '喝酒实力',
  `lable_pets` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '我的宠物',
  `lable_introduce` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '自我介绍',
  `lable_smoking_status` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '抽烟习惯',
  `lable_occupation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业',
  PRIMARY KEY (`label_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 262 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '个人标签信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for level
-- ----------------------------
DROP TABLE IF EXISTS `level`;
CREATE TABLE `level`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `levelName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '等级名称',
  `empiricalValue` bigint(20) NULL DEFAULT NULL COMMENT '经验值',
  `addTime` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `editTime` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '等级表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for love_heart_donation
-- ----------------------------
DROP TABLE IF EXISTS `love_heart_donation`;
CREATE TABLE `love_heart_donation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `love_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `love_explain` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '说明',
  `love_need_money` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '需要金额',
  `love_now_money` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '已捐赠金额',
  `love_cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面',
  `love_state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0:正常，1：删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '爱心捐赠' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for love_heart_donation_logs
-- ----------------------------
DROP TABLE IF EXISTS `love_heart_donation_logs`;
CREATE TABLE `love_heart_donation_logs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '捐赠用户id',
  `donation_project_id` int(11) NULL DEFAULT NULL COMMENT '捐赠项目id',
  `donation_time` datetime NULL DEFAULT NULL COMMENT '捐赠时间',
  `donation_money` decimal(12, 2) NULL DEFAULT NULL COMMENT '捐赠金额',
  `donation_remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '爱心捐赠记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `nickName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `head` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号(124后缀是已注销用户的手机号)',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` int(11) NULL DEFAULT NULL COMMENT '性别 1：男2：女',
  `age` int(11) NULL DEFAULT 0 COMMENT '年龄',
  `IDNumber` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '身份证号',
  `province` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '所在省',
  `city` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '所在市',
  `area` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '所在区',
  `addTime` datetime NULL DEFAULT NULL COMMENT '注册时间',
  `isvip` int(11) NULL DEFAULT NULL COMMENT '1:是vip  2：不是vip',
  `memberExpirationDate` datetime NULL DEFAULT NULL COMMENT '会员到期时间',
  `enableSate` int(11) NULL DEFAULT NULL COMMENT '启用状态 1：启用2：禁用',
  `pid` int(11) NOT NULL DEFAULT 0 COMMENT '父级id',
  `recommended` int(11) NOT NULL DEFAULT 0 COMMENT '直推人数',
  `facePhoto` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人脸拍照照片',
  `positivePhotoOfIDCard` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证正面照片',
  `level` int(11) NULL DEFAULT 1 COMMENT '用户等级',
  `invitationCode` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邀请码',
  `examineState` int(11) NULL DEFAULT 3 COMMENT '1:审核通过 2：审核未过 3：审核中',
  `registrationChannel` int(11) NULL DEFAULT NULL COMMENT '1:颜值注册  2：车友注册3：黑卡注册',
  `reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '原因',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'token',
  `loginSize` int(11) NULL DEFAULT NULL COMMENT '登录次数',
  `backgroundImages` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '背景图',
  `pushId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推送ID',
  `carLable` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车标图片',
  `isCustomer` int(11) NULL DEFAULT 2 COMMENT '是否是客服  1是 2不是',
  `longitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经度',
  `latitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `loginTimeofOne` datetime NULL DEFAULT NULL COMMENT '第一次登陆时间',
  `todayLoginTime` datetime NULL DEFAULT NULL COMMENT '最近一次的登录时间',
  `numberOfRemainingFriendsToAdd` int(11) NULL DEFAULT 50,
  `video` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频',
  `activitySize` bigint(11) NULL DEFAULT NULL,
  `delUserStatus` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除状态 0:未删除，1：删除',
  `drivingLicensePhoto` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行驶证照片',
  `registrationModel` int(11) NULL DEFAULT NULL COMMENT '注册机型  1：苹果 2：安卓',
  `carModel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车辆型号',
  `onLine` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0:不在线1：在线',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1259 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'app会员表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_assets
-- ----------------------------
DROP TABLE IF EXISTS `member_assets`;
CREATE TABLE `member_assets`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '会员id',
  `memberDiamondsizeOfBlack` bigint(20) NULL DEFAULT 0 COMMENT '剩余黑钻石数',
  `memberhornSize` bigint(20) NULL DEFAULT 0 COMMENT '会员喇叭数',
  `meberExperienceSize` bigint(20) NULL DEFAULT 0 COMMENT '会员经验值',
  `memberLabel` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员标签',
  `ciphertext` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `memberDiamondsizeOfGold` bigint(20) NULL DEFAULT 0 COMMENT '剩余金钻数',
  `memberDiamondsizeOfSilver` bigint(20) NULL DEFAULT 0 COMMENT '剩余银钻数',
  `oldMemberDiamondsizeOfBlack` bigint(20) NULL DEFAULT 0 COMMENT '已提现黑钻数量',
  `oldMemberDiamondsizeOfSilver` bigint(20) NULL DEFAULT 0 COMMENT '累计银钻数/魅力',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 258 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员资产表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_assets_copy
-- ----------------------------
DROP TABLE IF EXISTS `member_assets_copy`;
CREATE TABLE `member_assets_copy`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '会员id',
  `memberDiamondsizeOfBlack` bigint(20) NULL DEFAULT NULL COMMENT '剩余黑钻石数',
  `memberhornSize` bigint(20) NULL DEFAULT NULL COMMENT '会员喇叭数',
  `meberExperienceSize` bigint(20) NULL DEFAULT NULL COMMENT '会员经验值',
  `memberLabel` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员标签',
  `ciphertext` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `memberDiamondsizeOfGold` bigint(20) NULL DEFAULT NULL COMMENT '剩余金钻数',
  `memberDiamondsizeOfSilver` bigint(20) NULL DEFAULT NULL COMMENT '剩余银钻数',
  `oldMemberDiamondsizeOfBlack` bigint(20) NULL DEFAULT NULL COMMENT '已提现黑钻数量',
  `oldMemberDiamondsizeOfSilver` bigint(20) NULL DEFAULT NULL COMMENT '累计银钻数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1549545 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员资产表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_head_lable
-- ----------------------------
DROP TABLE IF EXISTS `member_head_lable`;
CREATE TABLE `member_head_lable`  (
  `member_head_lable_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_head_lable_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `member_head_lable_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `member_head_lable_enable_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '1：启用 2：禁用',
  `member_head_lable_del_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '1:已删除 2：未删除',
  `member_head_lable_add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `member_head_lable_edit_time` datetime NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`member_head_lable_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for member_matching_log
-- ----------------------------
DROP TABLE IF EXISTS `member_matching_log`;
CREATE TABLE `member_matching_log`  (
  `member_matching_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_matching_log_member_id` int(11) NULL DEFAULT NULL,
  `member_matching_log_member_ids_soul` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `member_matching_log_member_ids_car` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`member_matching_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 361 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for membership_settings
-- ----------------------------
DROP TABLE IF EXISTS `membership_settings`;
CREATE TABLE `membership_settings`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '会员id',
  `sameCityWithinState` int(11) NULL DEFAULT NULL COMMENT '同城内可查看 1：是2：否',
  `sameCityExternalState` int(11) NULL DEFAULT NULL COMMENT '同城外可查看 1：是2：否',
  `confidentialityOfInformationState` int(11) NULL DEFAULT NULL COMMENT '信息保密开关 1：是2：否',
  `friendMessageState` int(11) NULL DEFAULT NULL COMMENT '好友消息提醒开关 1：是2：否',
  `noticeState` int(11) NULL DEFAULT NULL COMMENT '系统消息/公告提醒开关 1：是2：否',
  `dynamicResponseState` int(11) NULL DEFAULT NULL COMMENT '我的动态回复提醒开关 1：是2：否',
  `worldInformation` int(11) NULL DEFAULT NULL COMMENT '世界之窗开关 1：是2：否',
  `editState` datetime NULL DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 259 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员设置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `f_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `f_clasid` int(11) NULL DEFAULT NULL COMMENT '分类 1:一级,2:二级,3:三级',
  `f_tid` int(11) NULL DEFAULT NULL COMMENT '二级id',
  `f_oid` int(11) NULL DEFAULT NULL COMMENT '一级id',
  `f_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `f_weight` int(11) NULL DEFAULT NULL COMMENT '权重',
  `f_address` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限地址',
  PRIMARY KEY (`f_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 159 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for new_friend
-- ----------------------------
DROP TABLE IF EXISTS `new_friend`;
CREATE TABLE `new_friend`  (
  `new_friend_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `new_friend_oneself_id` int(11) NULL DEFAULT NULL COMMENT '自身ID',
  `new_friend_other_party_id` int(11) NULL DEFAULT NULL COMMENT '对方ID',
  `new_friend_other_party_head` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对方头像',
  `new_friend_other_party_auto_logos` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对方头像车标',
  `new_friend_other_party_nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对方昵称',
  `new_friend_other_party_level` int(255) NULL DEFAULT NULL COMMENT '对方等级',
  `new_friend_other_party_sex` int(11) NULL DEFAULT NULL COMMENT '对方性别 1:男 2：女',
  `new_friend_other_party_age` int(11) NULL DEFAULT NULL COMMENT '对方年龄',
  `new_friend_other_party_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对方城市',
  `new_friend_other_party_add_time` datetime NULL DEFAULT NULL COMMENT '打招呼时间',
  `new_friend_other_party_end_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `new_friend_other_party_gift_id` int(11) NULL DEFAULT NULL COMMENT '礼物ID',
  `new_friend_other_party_gift_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '礼物图片',
  `new_friend_other_party_gift_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '礼物名称',
  `new_friend_other_party_gift_size` int(11) NULL DEFAULT NULL COMMENT '礼物数量',
  `remarks` int(11) NULL DEFAULT NULL COMMENT '打招呼送礼物的ID',
  `runSize` int(11) NULL DEFAULT NULL COMMENT '发送金钻数',
  `surplus` int(11) NULL DEFAULT NULL COMMENT '会员剩余金钻数',
  `is_retreat` int(1) NULL DEFAULT 2 COMMENT '该礼物是否被接收方接收 1： 是 2 ：否',
  PRIMARY KEY (`new_friend_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 333 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '打招呼记录表（加好友）' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` int(11) NULL DEFAULT NULL COMMENT '报名人员id',
  `head` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `nickName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '昵称',
  `sex` int(11) NULL DEFAULT NULL COMMENT '性别 1 :男  2：女',
  `shenqing_time` datetime NULL DEFAULT NULL COMMENT '申请时间',
  `daochang_time` datetime NULL DEFAULT NULL COMMENT '到场时间',
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '聚会主题',
  `per_size` int(11) NULL DEFAULT NULL COMMENT '限制人数',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `of_member` int(11) NULL DEFAULT NULL COMMENT '发起聚会人员ID',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品内容',
  `type` int(11) NULL DEFAULT NULL COMMENT '1俱乐部;2聚会;3会员专享，4：商品新增提示',
  `add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `shenqingId` int(11) NULL DEFAULT NULL COMMENT '申请的ID',
  `readMembers` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '已读会员数组',
  `member_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送的会员数组',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1392796 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公告表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for opinion
-- ----------------------------
DROP TABLE IF EXISTS `opinion`;
CREATE TABLE `opinion`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '会员id',
  `memberName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员昵称',
  `memberHead` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员头像',
  `memberPhone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员手机号',
  `context` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '反馈内容',
  `images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '反馈图片',
  `addTime` datetime NULL DEFAULT NULL COMMENT '反馈时间',
  `state` int(11) NULL DEFAULT NULL COMMENT '处理状态 1:已处理2:未处理',
  `reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '处理意见',
  `handleTime` datetime NULL DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '意见表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_manage
-- ----------------------------
DROP TABLE IF EXISTS `order_manage`;
CREATE TABLE `order_manage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id（购买者）',
  `order_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号码',
  `order_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态（1：待支付，2：待收货，3：已完成，4：已取消,5：已发货，6：未发货）',
  `order_shop_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `order_shop_id` int(11) NULL DEFAULT NULL COMMENT '商品id',
  `order_price` decimal(12, 2) NULL DEFAULT NULL COMMENT '订单价格（单价）',
  `order_shop_specs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品规格',
  `order_address_id` int(11) NULL DEFAULT NULL COMMENT '地址表id',
  `order_real_price` decimal(12, 2) NULL DEFAULT NULL COMMENT '实付金额',
  `order_shop_number` int(12) NULL DEFAULT NULL COMMENT '订单商品数量',
  `recever_people_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人（名字）',
  `recever_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人手机号',
  `recever_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人地址',
  `order_del_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '删除状态（0：删除，1：正常）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '下单时间（创建时间）',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务类型 0：商品 1 服务',
  `pay_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '付款方式0微信;1支付宝',
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递100返回的订单id，用来后期接口回调的参数匹配',
  `pay_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付状态0待支付;1支付成功;2支付失败',
  `express_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递单号',
  `express_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递公司编码',
  `express_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递公司名称',
  `express_from` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出发地城市，省-市-区',
  `express_to` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的地城市，省-市-区',
  `buyer_message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '买家留言',
  `business_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺名称',
  `business_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺log',
  `shop_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `shop_introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品简介',
  `width` double NULL DEFAULT NULL COMMENT '简介图片的宽高比（宽/高）默认两位小数',
  `is_evaluate` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否评价1是;0不是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 199 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for platform_parameter_setting
-- ----------------------------
DROP TABLE IF EXISTS `platform_parameter_setting`;
CREATE TABLE `platform_parameter_setting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `launchPage` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'app端启用页图片',
  `loginImage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'login背景图',
  `drawInProportion` decimal(32, 2) NULL DEFAULT NULL COMMENT '用户收到的礼物平台抽成比例',
  `telephone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客服电话',
  `proportionOfReturnedServants` decimal(32, 2) NULL DEFAULT NULL COMMENT '设置一级分销返佣比例',
  `proportionOfReturnedServantsTwo` decimal(32, 2) NULL DEFAULT NULL COMMENT '二级分销返佣比例',
  `selfWithdrawal` decimal(32, 2) NULL DEFAULT NULL COMMENT '设置通过他人分享注册的用户，自身提现比例提高XX%',
  `giftExperienceRatio` decimal(32, 2) NULL DEFAULT NULL COMMENT '发送礼物获得对应钻石价值比例的经验值数量',
  `proportionOfExperience` decimal(32, 2) NULL DEFAULT NULL COMMENT '发起赴约获得对应钻石价值比例的经验值数量',
  `unitPriceOfLoudspeaker` int(32) NULL DEFAULT NULL COMMENT '设置小喇叭单价',
  `exchangeRateOfGoldDiamond` decimal(32, 2) NULL DEFAULT NULL COMMENT '设置银钻兑换金钻手续费比例',
  `exchangeRateForBlackDiamonds` decimal(32, 2) NULL DEFAULT NULL COMMENT '设置银钻兑换黑钻手续费比例',
  `proportionOfExchangeBalance` decimal(32, 2) NULL DEFAULT NULL COMMENT '设置黑钻兑换余额比例',
  `sixGoldSize` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '设置六个可兑换金钻的选项',
  `sixBlackSize` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '设置六个可兑换黑钻的数量',
  `goldDiamondsPerYuan` int(11) NULL DEFAULT NULL COMMENT '设置充值时每元等于多少金钻',
  `version` decimal(15, 2) NULL DEFAULT NULL COMMENT '版本号',
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '新版本描述',
  `applePackageDownloadUrl` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'ios包下载地址',
  `androidPackageDownloadUrl` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'android',
  `faceSimilarity` int(3) NULL DEFAULT NULL COMMENT '人脸相似度',
  `memberUsageTime` int(11) NULL DEFAULT NULL COMMENT '会员使用时间（小时）',
  `appleVersion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '255',
  `worldSpeakGlod` int(12) NULL DEFAULT NULL COMMENT '世界之窗发言需要金钻设置',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '平台参数设置' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题名称',
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '答案',
  `addTime` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `editTime` datetime NULL DEFAULT NULL COMMENT '编辑时间',
  `enableState` int(11) NULL DEFAULT NULL COMMENT '启用状态 1:启用2:禁用',
  `deleteState` int(11) NULL DEFAULT NULL COMMENT '删除状态 1:已删除2:未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '常见问题表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for proportionofwithdrawal
-- ----------------------------
DROP TABLE IF EXISTS `proportionofwithdrawal`;
CREATE TABLE `proportionofwithdrawal`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `numberOfParticipants` int(11) NULL DEFAULT NULL COMMENT '分享人数',
  `giftCost` int(11) NULL DEFAULT 0 COMMENT '礼物价值',
  `proportion` decimal(32, 2) NULL DEFAULT NULL COMMENT '对应比例',
  `addTime` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `editTime` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '提现比例表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `recharge_record`;
CREATE TABLE `recharge_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '会员ID',
  `currency` int(11) NULL DEFAULT NULL COMMENT '1：会员 2：黑卡 3：金钻  4：银钻 5：黑钻6:小喇叭，',
  `mode` int(32) NULL DEFAULT NULL COMMENT '1：微信 2：支付宝 3：苹果 4：系统',
  `type` int(11) NULL DEFAULT NULL COMMENT '1：增加 2：消耗',
  `runSize` bigint(11) NULL DEFAULT NULL COMMENT '产生数量',
  `surplus` bigint(11) NULL DEFAULT NULL COMMENT '剩余数量',
  `addTime` datetime NULL DEFAULT NULL COMMENT '充值时间',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用来放系统增加的时候的注释信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66148 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '充值记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for red_log
-- ----------------------------
DROP TABLE IF EXISTS `red_log`;
CREATE TABLE `red_log`  (
  `red_log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '红包ID',
  `red_log_member_id` int(11) NULL DEFAULT NULL COMMENT '发送人ID',
  `red_log_member_nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送人昵称',
  `red_log_member_head` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送人头像',
  `red_log_gold_size` int(11) NULL DEFAULT NULL COMMENT '钻石数量',
  `red_log_red_size` int(11) NULL DEFAULT NULL COMMENT '红包个数',
  `red_log_number_receipts` int(11) NULL DEFAULT NULL COMMENT '领取个数',
  `red_log_number_remaining` int(11) NULL DEFAULT NULL COMMENT '剩余个数',
  `red_log_send_time` datetime NULL DEFAULT NULL,
  `red_log_end_time` datetime NULL DEFAULT NULL,
  `red_log_sex` int(255) NULL DEFAULT NULL COMMENT '1:只限男 2：只限女 2：不限',
  `red_log_remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `type` int(11) NULL DEFAULT NULL COMMENT '1:一对一  2：一对多',
  `state` int(11) NULL DEFAULT NULL COMMENT '1:已过期  2:未过期',
  `surplus` int(11) NULL DEFAULT NULL COMMENT '余额',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`red_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '红包发送记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for red_receive_log
-- ----------------------------
DROP TABLE IF EXISTS `red_receive_log`;
CREATE TABLE `red_receive_log`  (
  `red_receive_log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '领取记录id',
  `red_receive_log_member_id` int(11) NULL DEFAULT NULL COMMENT '领取人id',
  `red_receive_log_member_nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '领取人昵称',
  `red_receive_log_member_head` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '领取人头像',
  `red_receive_log_red_id` int(11) NULL DEFAULT NULL COMMENT '红包ID',
  `red_receive_log_gold_size` int(11) NULL DEFAULT NULL COMMENT '领取钻石数',
  `red_receive_log_time` datetime NULL DEFAULT NULL COMMENT '领取时间',
  `red_receive_log_is_luck` int(11) NULL DEFAULT NULL COMMENT '是否是手气最佳  1:是  2：不是',
  PRIMARY KEY (`red_receive_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '红包领取记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `report_id` int(11) NOT NULL AUTO_INCREMENT,
  `report_informant_id` int(11) NULL DEFAULT NULL COMMENT '举报人ID',
  `report_informant_head` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '举报人头像',
  `report_informant_nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '举报人昵称',
  `report_informant_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '举报人电话',
  `report_bei_informant_id` int(11) NULL DEFAULT NULL COMMENT '被举报人ID',
  `report_bei_informant_head` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '被举报人头像',
  `report_bei_informant_nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '被举报人昵称',
  `report_bei_informant_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '被举报人电话',
  `report_reason` int(11) NULL DEFAULT NULL COMMENT '举报原因',
  `report_context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '举报内容',
  `report_add_time` datetime NULL DEFAULT NULL COMMENT '举报时间',
  `report_Examine_state` int(11) NULL DEFAULT NULL COMMENT '审核状态 1：待审核  2：审核通过 3：审核未通过',
  `report_Examone_reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '审核原因',
  PRIMARY KEY (`report_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '举报记录信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for report_type
-- ----------------------------
DROP TABLE IF EXISTS `report_type`;
CREATE TABLE `report_type`  (
  `report_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `report_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `report_type_create_time` datetime NULL DEFAULT NULL,
  `report_type_edit_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`report_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '举报分类' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for rider_dynamics
-- ----------------------------
DROP TABLE IF EXISTS `rider_dynamics`;
CREATE TABLE `rider_dynamics`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memerId` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `memberHead` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `memberNickName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `memberSex` int(11) NULL DEFAULT NULL COMMENT '性别 1:男2:女',
  `memberAge` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `lable` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在市',
  `addTime` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '动态内容',
  `images` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片数组',
  `video` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '视频',
  `likeSize` int(11) NULL DEFAULT NULL COMMENT '点赞量',
  `commentSize` int(11) NULL DEFAULT NULL COMMENT '评论量',
  `state` int(11) NULL DEFAULT NULL COMMENT '是否隐藏地址',
  `strangersInTheSameCity` int(11) NULL DEFAULT NULL COMMENT '是否允许同城陌生人查看  1：允许  2：不允许',
  `strangersOutsideTheCity` int(11) NULL DEFAULT NULL COMMENT '是否允许同城以外陌生人 1：允许  2：不允许',
  `citySynchronization` int(11) NOT NULL COMMENT '是否同步到HC大咖秀  1：同步  2：不同步',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '车友圈动态' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `menus` varchar(3072) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单',
  `addTime` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `editTime` datetime NULL DEFAULT NULL COMMENT '编辑时间',
  `enableState` int(11) NULL DEFAULT NULL COMMENT '启用状态 1:启用2:禁用',
  `deleteState` int(11) NULL DEFAULT NULL COMMENT '删除状态 1:已删除2:未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for send_offline_activities
-- ----------------------------
DROP TABLE IF EXISTS `send_offline_activities`;
CREATE TABLE `send_offline_activities`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sendMemberId` int(11) NULL DEFAULT NULL COMMENT '发起人id',
  `sendMemberHead` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起人用户头像',
  `sendMemberNickName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `backgroundImages` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '背景图',
  `sendMemberSex` int(11) NULL DEFAULT NULL COMMENT '发起人性别(1男，2女，牛笔，这个还是我在前端看到才加上的，真是厉害死了，注释都没有,要是错了就再来修改一下pa)',
  `sendMemberAge` int(11) NULL DEFAULT NULL COMMENT '发起人年龄',
  `activityTheme` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动主题',
  `address` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地点',
  `averageDiamondsSize` int(11) NULL DEFAULT NULL COMMENT '平均钻石数量',
  `perSize` int(11) NULL DEFAULT NULL COMMENT '活动人数',
  `addTime` datetime NULL DEFAULT NULL COMMENT '发起时间',
  `startTime` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态 1  待报名 2 待对方确认3待自己确认4审核中5审核通过6审核未通过7已取消',
  `reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '原因',
  `lable` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `longitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经度',
  `latitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `readMembers` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '已读会员数组',
  `is_overdueout` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0没有过期;1有过期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '线下活动列表（发起聚会）' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for server_info_manager
-- ----------------------------
DROP TABLE IF EXISTS `server_info_manager`;
CREATE TABLE `server_info_manager`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_type_id` int(11) NULL DEFAULT NULL COMMENT '服务类型id',
  `server_cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务信息封面图',
  `server_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务标题',
  `server_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `server_introduce` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图文简介',
  `server_is_top` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否置顶 0：未置顶 1：置顶',
  `server_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态 0：删除 1：正常',
  `place` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货地',
  `payment_number` int(11) NULL DEFAULT 0 COMMENT '付款人数',
  `business_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺名称',
  `business_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺LOGO',
  `shop_specs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '0商品;1服务',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市字段市',
  `width` double NULL DEFAULT NULL COMMENT '简介图片的宽高比（宽/高）默认两位小数',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '服务管理信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for server_info_type
-- ----------------------------
DROP TABLE IF EXISTS `server_info_type`;
CREATE TABLE `server_info_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `server_sort` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排序',
  `server_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态 0：删除，1：正常',
  `server_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '服务类型 0：商品 1 服务',
  `server_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标信息',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '服务信息管理类型表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for shop_manage
-- ----------------------------
DROP TABLE IF EXISTS `shop_manage`;
CREATE TABLE `shop_manage`  (
  `id` int(13) NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `shop_price` decimal(12, 2) NULL DEFAULT NULL COMMENT '商品单价',
  `shop_specs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品规格',
  `shop_introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品简介（这就***是商品的简介图片）',
  `shop_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片(这就是***商品的封面图)',
  `shop_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '商品状态(0：删除，1：正常)',
  `shop_is_top` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否置顶（0：未置顶，1：置顶）',
  `business_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺名称',
  `business_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺LOGO',
  `place` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货地',
  `payment_number` int(11) NULL DEFAULT 0 COMMENT '付款人数',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0商品;1服务',
  `create_time` datetime NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市(地址）',
  `width` double NULL DEFAULT NULL COMMENT '简介图片的宽高比（宽/高）默认两位小数',
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品管理' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for speakerconsumptionrecord
-- ----------------------------
DROP TABLE IF EXISTS `speakerconsumptionrecord`;
CREATE TABLE `speakerconsumptionrecord`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '会员ID',
  `consumptionTime` datetime NULL DEFAULT NULL COMMENT '消费时间',
  `consumptionSize` bigint(20) NULL DEFAULT NULL COMMENT '消费数量',
  `surplusSize` bigint(20) NULL DEFAULT NULL COMMENT '剩余数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 254 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '喇叭消费记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for system_message
-- ----------------------------
DROP TABLE IF EXISTS `system_message`;
CREATE TABLE `system_message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `addTime` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `editTime` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `enableState` int(11) NULL DEFAULT NULL COMMENT '启用状态 1:启用2:禁用',
  `deleteState` int(11) NULL DEFAULT NULL COMMENT '删除状态 1:已删除2:未删除',
  `readMembers` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '已读会员数组',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统消息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for task_answer_questions
-- ----------------------------
DROP TABLE IF EXISTS `task_answer_questions`;
CREATE TABLE `task_answer_questions`  (
  `questions_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务答案id',
  `member_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `task_id` int(11) NULL DEFAULT NULL COMMENT '任务id',
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务答案',
  `task_rating` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0需修改;1通过;2完美',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `video_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频地址',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `diamondDayNumber` int(11) NULL DEFAULT 0 COMMENT '每日获取的金钻数量',
  `wage_time` datetime NULL DEFAULT NULL COMMENT '每日获取的金钻时间',
  `surplus_szie` int(255) NULL DEFAULT NULL COMMENT '余额',
  `is_read` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0没有;1有',
  PRIMARY KEY (`questions_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wages_logs
-- ----------------------------
DROP TABLE IF EXISTS `wages_logs`;
CREATE TABLE `wages_logs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员id值',
  `taskId` int(11) NULL DEFAULT NULL,
  `monery` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '钱数',
  `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `surplus_szie` int(255) NULL DEFAULT NULL COMMENT '余额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工资记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for withdraw
-- ----------------------------
DROP TABLE IF EXISTS `withdraw`;
CREATE TABLE `withdraw`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `memberId` int(11) NULL DEFAULT NULL COMMENT '会员ID',
  `memberNickName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `memberHead` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `memberPhone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `price` decimal(32, 2) NULL DEFAULT NULL COMMENT '提现金额',
  `money` decimal(32, 2) NULL DEFAULT NULL COMMENT '实际到账金额',
  `bankName` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开户行',
  `bankMemberName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `bankNumber` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卡号',
  `applyTime` datetime NULL DEFAULT NULL COMMENT '申请时间',
  `reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '原因',
  `examineTime` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `examineState` int(11) NULL DEFAULT NULL COMMENT '审核状态 1:通过2:不通过3:待审核',
  `balance` decimal(32, 2) NULL DEFAULT NULL COMMENT '余额',
  `oldOrNew` int(11) NULL DEFAULT NULL COMMENT '1：提现记录  2：失败后返现记录',
  `isExamine` int(11) NULL DEFAULT NULL COMMENT '1:审核过  2：待审核',
  `proportion` decimal(32, 2) NULL DEFAULT NULL COMMENT '提现比例',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '提现申请表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for withdrawal_logs
-- ----------------------------
DROP TABLE IF EXISTS `withdrawal_logs`;
CREATE TABLE `withdrawal_logs`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `memberId` int(11) NULL DEFAULT NULL COMMENT '会员id值',
  `gold` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '金钻数',
  `sliver` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银钻数',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1:金钻兑换银钻 2：银钻兑换金钻',
  `createTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '金钻银钻兑换记录表' ROW_FORMAT = Compact;

-- ----------------------------
-- Function structure for getChildrenOrg
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildrenOrg`;
delimiter ;;
CREATE FUNCTION `getChildrenOrg`(orgid INT)
 RETURNS varchar(4000) CHARSET utf8mb4
BEGIN
DECLARE oTemp VARCHAR(4000);
DECLARE two VARCHAR(4000);

SET oTemp = '';#集合
SET two = ''; #二级数组

SELECT GROUP_CONCAT(id) INTO two FROM dynamic_review_of_friends_circle WHERE dynamicIdOrCommentId = orgid AND `level` = 1;

WHILE two IS NOT NULL
DO
SET oTemp = CONCAT(oTemp,',',two);
SELECT GROUP_CONCAT(id) INTO two FROM dynamic_review_of_friends_circle WHERE FIND_IN_SET(dynamicIdOrCommentId,two) AND `level` = 2;
END WHILE;
RETURN oTemp;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for getChildrenOrgOfCar
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildrenOrgOfCar`;
delimiter ;;
CREATE FUNCTION `getChildrenOrgOfCar`(orgid INT)
 RETURNS varchar(4000) CHARSET utf8mb4
BEGIN
DECLARE oTemp VARCHAR(4000);
DECLARE two VARCHAR(4000);

SET oTemp = '';#集合
SET two = ''; #二级数组

SELECT GROUP_CONCAT(id) INTO two FROM dynamic_comments_of_car_users WHERE dynamicIdOrCommentId = orgid AND `level` = 1;

WHILE two IS NOT NULL
DO
SET oTemp = CONCAT(oTemp,',',two);
SELECT GROUP_CONCAT(id) INTO two FROM dynamic_comments_of_car_users WHERE FIND_IN_SET(dynamicIdOrCommentId,two) AND `level` = 2;
END WHILE;
RETURN oTemp;
END
;;
delimiter ;

-- ----------------------------
-- Event structure for activitySize
-- ----------------------------
DROP EVENT IF EXISTS `activitySize`;
delimiter ;;
CREATE EVENT `activitySize`
ON SCHEDULE
EVERY '1' WEEK STARTS '2020-09-14 00:00:00'
ON COMPLETION PRESERVE
DO BEGIN
#每周一凌晨清空前一周的点击活跃度
UPDATE member SET activitySize = 0;
END
;;
delimiter ;

-- ----------------------------
-- Event structure for clear_meath_log
-- ----------------------------
DROP EVENT IF EXISTS `clear_meath_log`;
delimiter ;;
CREATE EVENT `clear_meath_log`
ON SCHEDULE
EVERY '1' DAY STARTS '2020-07-22 00:00:00'
ON COMPLETION PRESERVE
DO BEGIN
#每日凌晨清空前一次的匹配
UPDATE member_matching_log
SET member_matching_log_member_ids_soul = '',member_matching_log_member_ids_car='';
END
;;
delimiter ;

-- ----------------------------
-- Event structure for member
-- ----------------------------
DROP EVENT IF EXISTS `member`;
delimiter ;;
CREATE EVENT `member`
ON SCHEDULE
EVERY '10' SECOND STARTS '2020-06-18 11:02:08'
DO BEGIN
#修改会员状态
UPDATE member
	SET isvip = 2
	WHERE isvip = 1 AND memberExpirationDate < NOW();

#聚会取消后  修改已报名的人的状态
UPDATE accept_offline_activities a ,(

SELECT a.* FROM accept_offline_activities a ,(

SELECT * FROM send_offline_activities WHERE state = 7

)b WHERE a.sendOfflineActivities = b.id

)b SET a.keepAnAppointmentState = 5,a.closeType = 1 WHERE a.id = b.id;

#聚会审核通过后 修改已报名人的状态

#修改所有通过的参与数据状态为7
UPDATE accept_offline_activities a ,(

SELECT a.* FROM accept_offline_activities a ,(

SELECT * FROM send_offline_activities WHERE state = 5

)b WHERE a.sendOfflineActivities = b.id

)b SET a.keepAnAppointmentState = 7 WHERE a.id = b.id AND a.keepAnAppointmentState = 3;


#修改所有未通过的参与数据状态为8
UPDATE accept_offline_activities a ,(

SELECT a.* FROM accept_offline_activities a ,(

SELECT * FROM send_offline_activities WHERE state = 5

)b WHERE a.sendOfflineActivities = b.id

)b SET a.keepAnAppointmentState = 8 WHERE a.id = b.id AND a.keepAnAppointmentState != 7;





#30分钟时检测有没有人报名参加这个聚会  如果没有 取消掉
UPDATE send_offline_activities a,(

SELECT b.* FROM (

SELECT group_concat(a.id) as id FROM (

SELECT id,timestampdiff(SECOND,NOW(),startTime) as time FROM send_offline_activities WHERE state != 7

)a WHERE a.time > 0 AND a.time <= 1800

)a,(

SELECT a.id FROM send_offline_activities a ,(

SELECT IFNULL(group_concat(a.sendOfflineActivities),0) as bid FROM accept_offline_activities a ,(

SELECT group_concat(a.id) as id FROM (

SELECT id,timestampdiff(SECOND,addTime,NOW()) as time FROM send_offline_activities WHERE state != 7

)a WHERE a.time > 0 AND a.time <= 1800

)b WHERE FIND_IN_SET(a.sendOfflineActivities,b.id)

)b WHERE NOT FIND_IN_SET(a.id,b.bid)

)b WHERE FIND_IN_SET(b.id,a.id)

) b SET a.state = 7 WHERE a.id=b.id;


#24小时后检测有没有人报名参加这个聚会  如果没有 取消掉
UPDATE send_offline_activities a,(

SELECT b.* FROM (

SELECT group_concat(a.id) as id FROM (

SELECT id,timestampdiff(HOUR,addTime,NOW()) as time FROM send_offline_activities WHERE state != 7

)a WHERE a.time >= 24

)a,(

SELECT * FROM send_offline_activities a ,(

SELECT IFNULL(group_concat(a.sendOfflineActivities),0) as bid FROM accept_offline_activities a ,(

SELECT group_concat(a.id) as id FROM (

SELECT id,timestampdiff(HOUR,addTime,NOW()) as time FROM send_offline_activities WHERE state != 7

)a WHERE a.time >= 24

)b WHERE FIND_IN_SET(a.sendOfflineActivities,b.id)

)b WHERE NOT FIND_IN_SET(a.id,b.bid)

)b WHERE FIND_IN_SET(b.id,a.id)

) b SET a.state = 7 WHERE a.id=b.id;

#修改第一次登陆的时间
UPDATE member SET loginTimeofOne = NOW() WHERE loginSize = 1;


#红包剩余回执
UPDATE member_assets a ,(
SELECT b.red_log_id,b.red_log_member_id,b.red_log_gold_size,SUM(a.red_receive_log_gold_size) as yi FROM red_receive_log a ,(
select red_log_member_id,red_log_id,red_log_gold_size FROM red_log WHERE red_log_end_time < NOW() AND red_log_number_remaining > 0  AND ISNULL(state)
) b WHERE a.red_receive_log_red_id = b.red_log_id GROUP BY a.red_receive_log_red_id
) b SET a.memberDiamondsizeOfGold = (a.memberDiamondsizeOfGold + (b.red_log_gold_size)) WHERE a.memberId = b.red_log_member_id;

#红包剩余个数修改
UPDATE red_log a,(
SELECT b.red_log_id,b.red_log_member_id,b.red_log_gold_size,SUM(a.red_receive_log_gold_size) as yi,b.state FROM red_receive_log a ,(
select red_log_member_id,red_log_id,red_log_gold_size,state FROM red_log WHERE red_log_end_time < NOW() AND red_log_number_remaining > 0 AND ISNULL(state)
) b WHERE a.red_receive_log_red_id = b.red_log_id GROUP BY a.red_receive_log_red_id
) b SET a.state = 1 WHERE a.red_log_id = b.red_log_id;
END
;;
delimiter ;

-- ----------------------------
-- Event structure for vip
-- ----------------------------
DROP EVENT IF EXISTS `vip`;
delimiter ;;
CREATE EVENT `vip`
ON SCHEDULE
EVERY '2' SECOND STARTS '2020-06-18 11:02:08'
DO BEGIN
#为经验满1000的人加上一级 并将可加好友数变成巅峰值
UPDATE member a,(

SELECT a.id FROM member a ,(

SELECT memberId FROM member_assets WHERE meberExperienceSize >= 1000

) b WHERE a.id = b.memberId

) b SET a.`level` = (a.`level` + 1),a.numberOfRemainingFriendsToAdd = ((a.`level` * 10) + 50) WHERE a.id = b.id;
#经验减1000
UPDATE member_assets a,(

SELECT a.id FROM member a ,(

SELECT memberId FROM member_assets WHERE meberExperienceSize >= 1000

) b WHERE a.id = b.memberId

) b SET a.meberExperienceSize = a.meberExperienceSize-1000 WHERE a.memberId = b.id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table accept_offline_activities
-- ----------------------------
DROP TRIGGER IF EXISTS `updateCanJia`;
delimiter ;;
CREATE TRIGGER `updateCanJia` AFTER UPDATE ON `accept_offline_activities` FOR EACH ROW BEGIN
        #当修改状态时
        #2：发起方通过 赴约申请
        IF ( new.keepAnAppointmentState =2) and ( new.keepAnAppointmentState != old.keepAnAppointmentState)  THEN	
	          #修改发起方状态 为待对方确认
	          UPDATE send_offline_activities a SET state = 2 WHERE a.id = new.sendOfflineActivities;
        END IF;
        #3：已到达赴约地点
        IF ( new.keepAnAppointmentState = 3)  and ( new.keepAnAppointmentState != old.keepAnAppointmentState)   THEN	
	     #修改发起方状态 为待自己确认
	     UPDATE send_offline_activities a SET state = 3 WHERE a.id = new.sendOfflineActivities;
        END IF;
        #4：强制取消时(报名通过需要赔偿)
IF ( new.keepAnAppointmentState = 5) and (new.closeType = 2) and (old.keepAnAppointmentState = 2)  and ( new.keepAnAppointmentState != old.keepAnAppointmentState) THEN
#------------------------------------------------------------------------------------------------------------------------------------------------------
#发起方钻石增多
#1.发起方钻石足够 直接增加报名方赔偿的钻石
UPDATE member_assets a,(
	SELECT a.memberDiamondsizeOfSilver, b.* FROM member_assets a,(
			SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize,timestampdiff(HOUR, NOW(), b.startTime) AS time FROM accept_offline_activities a,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.id = new.id
		) b WHERE a.memberId = b.acceptMemberId
) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.9)),a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.9)) WHERE a.memberId = b.sendMemberId AND b.memberDiamondsizeOfSilver >= floor(b.averageDiamondsSize * 0.9) AND b.time < 6;
#2.报名方钻石不足 但差值控制在30%
UPDATE member_assets a,(
	SELECT a.memberDiamondsizeOfSilver, b.* FROM member_assets a,(
			SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize,timestampdiff(HOUR, NOW(), b.startTime) AS time FROM accept_offline_activities a,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.id = new.id
		) b WHERE a.memberId = b.acceptMemberId
) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.9)),a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.9))  WHERE a.memberId = b.sendMemberId AND b.memberDiamondsizeOfSilver < floor(b.averageDiamondsSize * 0.9) AND floor(b.averageDiamondsSize * 0.9) - b.memberDiamondsizeOfSilver <= (floor(b.averageDiamondsSize * 0.9* 0.3)) AND b.time < 6;
#3.报名方钻石不足 差值大于30%
UPDATE member_assets a,(
	SELECT a.memberDiamondsizeOfSilver, b.* FROM member_assets a,(
			SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize,timestampdiff(HOUR, NOW(), b.startTime) AS time FROM accept_offline_activities a,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.id = new.id
		) b WHERE a.memberId = b.acceptMemberId
) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + b.memberDiamondsizeOfSilver + (floor(b.averageDiamondsSize * 0.9 * 0.3))),a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + b.memberDiamondsizeOfSilver + (floor(b.averageDiamondsSize * 0.9 * 0.3)))  WHERE a.memberId = b.sendMemberId AND b.memberDiamondsizeOfSilver < floor(b.averageDiamondsSize * 0.9) AND (floor(b.averageDiamondsSize * 0.9) - b.memberDiamondsizeOfSilver) > (floor(b.averageDiamondsSize * 0.9* 0.3)) AND b.time < 6;
#------------------------------------------------------------------------------------------------------------------------------------------------------
#1.发起方钻石足够 直接增加报名方赔偿的钻石
UPDATE member_assets a,(
	SELECT a.memberDiamondsizeOfSilver, b.* FROM member_assets a,(
			SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize,timestampdiff(HOUR, NOW(), b.startTime) AS time FROM accept_offline_activities a,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.id = new.id
		) b WHERE a.memberId = b.acceptMemberId
) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.7)),a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.7))  WHERE a.memberId = b.sendMemberId AND b.memberDiamondsizeOfSilver >= floor(b.averageDiamondsSize * 0.7) AND b.time < 12 AND b.time >= 6;
#2.报名方钻石不足 但差值控制在30%
UPDATE member_assets a,(
	SELECT a.memberDiamondsizeOfSilver, b.* FROM member_assets a,(
			SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize,timestampdiff(HOUR, NOW(), b.startTime) AS time FROM accept_offline_activities a,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.id = new.id
		) b WHERE a.memberId = b.acceptMemberId
) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.7)),a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.7))  WHERE a.memberId = b.sendMemberId AND b.memberDiamondsizeOfSilver < floor(b.averageDiamondsSize * 0.7) AND floor(b.averageDiamondsSize * 0.7) - b.memberDiamondsizeOfSilver <= (floor(b.averageDiamondsSize * 0.7 * 0.3)) AND b.time < 12 AND b.time >= 6;
#3.报名方钻石不足 差值大于30%
UPDATE member_assets a,(
	SELECT a.memberDiamondsizeOfSilver, b.* FROM member_assets a,(
			SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize,timestampdiff(HOUR, NOW(), b.startTime) AS time FROM accept_offline_activities a,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.id = new.id
		) b WHERE a.memberId = b.acceptMemberId
) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + b.memberDiamondsizeOfSilver + (floor(b.averageDiamondsSize * 0.7* 0.3))),a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + b.memberDiamondsizeOfSilver + (floor(b.averageDiamondsSize * 0.7 * 0.3)))  WHERE a.memberId = b.sendMemberId AND b.memberDiamondsizeOfSilver < floor(b.averageDiamondsSize * 0.7) AND (floor(b.averageDiamondsSize * 0.7) - b.memberDiamondsizeOfSilver) > (floor(b.averageDiamondsSize * 0.7 * 0.3)) AND b.time < 12 AND b.time >= 6;
#------------------------------------------------------------------------------------------------------------------------------------------------------
#1.发起方钻石足够 直接增加报名方赔偿的钻石
UPDATE member_assets a,(
	SELECT a.memberDiamondsizeOfSilver, b.* FROM member_assets a,(
			SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize,timestampdiff(HOUR, NOW(), b.startTime) AS time FROM accept_offline_activities a,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.id = new.id
		) b WHERE a.memberId = b.acceptMemberId
) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.5)),a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.5))  WHERE a.memberId = b.sendMemberId AND b.memberDiamondsizeOfSilver >= floor(b.averageDiamondsSize * 0.5) AND b.time < 24 AND b.time >= 12;
#2.报名方钻石不足 但差值控制在30%
UPDATE member_assets a,(
	SELECT a.memberDiamondsizeOfSilver, b.* FROM member_assets a,(
			SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize,timestampdiff(HOUR, NOW(), b.startTime) AS time FROM accept_offline_activities a,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.id = new.id
		) b WHERE a.memberId = b.acceptMemberId
) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.5)),a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.5))  WHERE a.memberId = b.sendMemberId AND b.memberDiamondsizeOfSilver < floor(b.averageDiamondsSize * 0.5) AND floor(b.averageDiamondsSize * 0.5) - b.memberDiamondsizeOfSilver <= (floor(b.averageDiamondsSize * 0.5  * 0.3)) AND b.time < 24 AND b.time >= 12;
#3.报名方钻石不足 差值大于30%
UPDATE member_assets a,(
	SELECT a.memberDiamondsizeOfSilver, b.* FROM member_assets a,(
			SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize,timestampdiff(HOUR, NOW(), b.startTime) AS time FROM accept_offline_activities a,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.id = new.id
		) b WHERE a.memberId = b.acceptMemberId
) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + b.memberDiamondsizeOfSilver + (floor(b.averageDiamondsSize * 0.5 * 0.3))),a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + b.memberDiamondsizeOfSilver + (floor(b.averageDiamondsSize * 0.5 * 0.3)))  WHERE a.memberId = b.sendMemberId AND b.memberDiamondsizeOfSilver < floor(b.averageDiamondsSize * 0.5) AND (floor(b.averageDiamondsSize * 0.5) - b.memberDiamondsizeOfSilver) > (floor(b.averageDiamondsSize * 0.5  * 0.3)) AND b.time < 24 AND b.time >= 12;
#------------------------------------------------------------------------------------------------------------------------------------------------------
#1.发起方钻石足够 直接增加报名方赔偿的钻石
UPDATE member_assets a,(
	SELECT a.memberDiamondsizeOfSilver, b.* FROM member_assets a,(
			SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize,timestampdiff(HOUR, NOW(), b.startTime) AS time FROM accept_offline_activities a,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.id = new.id
		) b WHERE a.memberId = b.acceptMemberId
) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.3)),a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.3)) WHERE a.memberId = b.sendMemberId AND b.memberDiamondsizeOfSilver >= floor(b.averageDiamondsSize * 0.3) AND b.time >= 24;
#2.报名方钻石不足 但差值控制在30%
UPDATE member_assets a,(
	SELECT a.memberDiamondsizeOfSilver, b.* FROM member_assets a,(
			SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize,timestampdiff(HOUR, NOW(), b.startTime) AS time FROM accept_offline_activities a,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.id = new.id
		) b WHERE a.memberId = b.acceptMemberId
) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.3)),a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.3)) WHERE a.memberId = b.sendMemberId AND b.memberDiamondsizeOfSilver < floor(b.averageDiamondsSize * 0.3) AND floor(b.averageDiamondsSize * 0.3) - b.memberDiamondsizeOfSilver <= (floor(b.averageDiamondsSize * 0.3  * 0.3)) AND b.time >= 24;
#3.报名方钻石不足 差值大于30%
UPDATE member_assets a,(
	SELECT a.memberDiamondsizeOfSilver, b.* FROM member_assets a,(
			SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize,timestampdiff(HOUR, NOW(), b.startTime) AS time FROM accept_offline_activities a,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.id = new.id
		) b WHERE a.memberId = b.acceptMemberId
) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + b.memberDiamondsizeOfSilver + (floor(b.averageDiamondsSize * 0.3* 0.3))),a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + b.memberDiamondsizeOfSilver + (floor(b.averageDiamondsSize * 0.3 * 0.3))) WHERE a.memberId = b.sendMemberId AND b.memberDiamondsizeOfSilver < floor(b.averageDiamondsSize * 0.3) AND (floor(b.averageDiamondsSize * 0.3) - b.memberDiamondsizeOfSilver) > (floor(b.averageDiamondsSize * 0.3  * 0.3)) AND b.time >= 24;

#报名方减少
#1.报名方银钻足够

UPDATE member_assets a ,(

SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize, timestampdiff(HOUR,NOW(),b.startTime) as time FROM accept_offline_activities a , send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a. id = new.id

)b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver - floor(b.averageDiamondsSize * 0.9)) WHERE a.memberId = b.acceptMemberId AND a.memberDiamondsizeOfSilver >= floor(b.averageDiamondsSize * 0.9) AND b.time < 6;
#2.报名方银钻不足

UPDATE member_assets a ,(

SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize, timestampdiff(HOUR,NOW(),b.startTime) as time FROM accept_offline_activities a , send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a. id = new.id

)b SET a.memberDiamondsizeOfSilver = 0 WHERE a.memberId = b.acceptMemberId AND a.memberDiamondsizeOfSilver < floor(b.averageDiamondsSize * 0.9) AND b.time < 6;


#1.报名方银钻足够

UPDATE member_assets a ,(

SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize, timestampdiff(HOUR,NOW(),b.startTime) as time FROM accept_offline_activities a , send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a. id = new.id

)b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver - floor(b.averageDiamondsSize * 0.7)) WHERE a.memberId = b.acceptMemberId AND a.memberDiamondsizeOfSilver >= floor(b.averageDiamondsSize * 0.7) AND b.time < 12 AND b.time >= 6;
#2.报名方银钻不足

UPDATE member_assets a ,(

SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize, timestampdiff(HOUR,NOW(),b.startTime) as time FROM accept_offline_activities a , send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a. id = new.id

)b SET a.memberDiamondsizeOfSilver = 0 WHERE a.memberId = b.acceptMemberId AND a.memberDiamondsizeOfSilver < floor(b.averageDiamondsSize * 0.7) AND b.time < 12 AND b.time >= 6;

#1.报名方银钻足够
UPDATE member_assets a ,(

SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize, timestampdiff(HOUR,NOW(),b.startTime) as time FROM accept_offline_activities a , send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a. id = new.id

)b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver - floor(b.averageDiamondsSize * 0.5)) WHERE a.memberId = b.acceptMemberId AND a.memberDiamondsizeOfSilver >= floor(b.averageDiamondsSize * 0.5) AND b.time < 24 AND b.time >= 12;

#2.报名方银钻不足
UPDATE member_assets a ,(

SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize, timestampdiff(HOUR,NOW(),b.startTime) as time FROM accept_offline_activities a , send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a. id = new.id

)b SET a.memberDiamondsizeOfSilver = 0 WHERE a.memberId = b.acceptMemberId AND a.memberDiamondsizeOfSilver < floor(b.averageDiamondsSize * 0.5) AND b.time < 24 AND b.time >= 12;


#1.报名方银钻足够
UPDATE member_assets a ,(

SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize, timestampdiff(HOUR,NOW(),b.startTime) as time FROM accept_offline_activities a , send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a. id = new.id

)b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver - floor(b.averageDiamondsSize * 0.3)) WHERE a.memberId = b.acceptMemberId AND a.memberDiamondsizeOfSilver >= floor(b.averageDiamondsSize * 0.3) AND b.time >= 24;

#2.报名方银钻不足
UPDATE member_assets a ,(

SELECT a.id,a.acceptMemberId,b.sendMemberId,b.averageDiamondsSize, timestampdiff(HOUR,NOW(),b.startTime) as time FROM accept_offline_activities a , send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a. id = new.id

)b SET a.memberDiamondsizeOfSilver = 0 WHERE a.memberId = b.acceptMemberId AND a.memberDiamondsizeOfSilver < floor(b.averageDiamondsSize * 0.3) AND b.time >= 24;

END IF;

END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table diamondconsumptionrecord
-- ----------------------------
DROP TRIGGER IF EXISTS `insterZuanShiJiLu`;
delimiter ;;
CREATE TRIGGER `insterZuanShiJiLu` BEFORE INSERT ON `diamondconsumptionrecord` FOR EACH ROW BEGIN
        #当发送礼物时
        IF ( new.type != 1 )  THEN	
	#发送方 减去金钻石
	UPDATE member_assets a SET memberDiamondsizeOfGold = memberDiamondsizeOfGold-new.consumptionSize WHERE a.memberId = new.sendMemberId;
        END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table giftconsumption
-- ----------------------------
DROP TRIGGER IF EXISTS `insterLiWuJiLu`;
delimiter ;;
CREATE TRIGGER `insterLiWuJiLu` AFTER INSERT ON `giftconsumption` FOR EACH ROW BEGIN
              #送礼物时
	#发送方 发送方减少金钻石
              UPDATE member_assets a ,(
              SELECT price*new.giftSize as zong FROM gift WHERE id = new.giftId
              )b
	SET a.memberDiamondsizeOfGold = a.memberDiamondsizeOfGold-b.zong WHERE a.memberId = new.sendMemberId;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table member
-- ----------------------------
DROP TRIGGER IF EXISTS `insterMember`;
delimiter ;;
CREATE TRIGGER `insterMember` AFTER INSERT ON `member` FOR EACH ROW BEGIN
IF ( new.registrationChannel = 1 ) and ( new.sex = 1 ) THEN
             INSERT INTO member_assets (
	memberId,
	memberDiamondsizeOfBlack,
	memberhornSize,
	meberExperienceSize,
	memberLabel,
	ciphertext,
	memberDiamondsizeOfGold,
	memberDiamondsizeOfSilver,
	oldMemberDiamondsizeOfBlack,
	oldMemberDiamondsizeOfSilver
              ) VALUES (
	new.id,
	0,
	0,
	0,
	"",
	"",
	0,
	0,
	0,
	0
                                                        );
END IF;
IF ( new.registrationChannel = 1 ) and ( new.sex = 2 ) THEN
             INSERT INTO member_assets (
	memberId,
	memberDiamondsizeOfBlack,
	memberhornSize,
	meberExperienceSize,
	memberLabel,
	ciphertext,
	memberDiamondsizeOfGold,
	memberDiamondsizeOfSilver,
	oldMemberDiamondsizeOfBlack,
	oldMemberDiamondsizeOfSilver
              ) VALUES (
	new.id,
	0,
	0,
	0,
	"",
	"",
	100,
	0,
	0,
	0
                                                        );
END IF;
IF ( new.registrationChannel = 2 ) THEN
             INSERT INTO member_assets (
	memberId,
	memberDiamondsizeOfBlack,
	memberhornSize,
	meberExperienceSize,
	memberLabel,
	ciphertext,
	memberDiamondsizeOfGold,
	memberDiamondsizeOfSilver,
	oldMemberDiamondsizeOfBlack,
	oldMemberDiamondsizeOfSilver
              ) VALUES (
	new.id,
	0,
	0,
	0,
	"",
	"",
	0,
	0,
	0,
	0
                                                        );
END IF;
IF ( new.registrationChannel = 3 ) THEN
INSERT INTO member_assets(
	memberId,
	memberDiamondsizeOfBlack,
	memberhornSize,
	meberExperienceSize,
	memberLabel,
	ciphertext,
	memberDiamondsizeOfGold,
	memberDiamondsizeOfSilver,
	oldMemberDiamondsizeOfBlack,
	oldMemberDiamondsizeOfSilver
              ) VALUES (
	new.id,
	0,
	0,
	(SELECT diamonds_price FROM diamonds a,member b WHERE a.type = 1 AND a.diamonds_terminal = b.registrationModel AND b.id = new.id),
	"",
	"",
	38888,
	0,
	0,
	0
);
END IF;
IF ( new.sex != 1 ) THEN
INSERT INTO `jiaoyou`.`lable` (
	`member_id`,
	`label_charm_default`,
	`label_charm_select`,
	`label_city_default`,
	`label_city_select`,
	`label_local_default`,
	`label_local_select`,
	`label_hobby_default`,
	`label_hobby_select`,
	`label_education_default`,
	`label_education_select`,
	`lable_state_default`,
	`lable_state_select`,
	`lable_annual_income_default`,
	`lable_annual_income_select`,
	`lable_car_size_default`,
	`lable_car_size_select`,
	`lable_image_default`,
	`lable_image_select`,
	`lable_character_default`,
	`lable_character_select`,
	`lable_industry_default`,
	`lable_industry_select`,
	`lable_height`,
	`lable_weight`,
	`lable_drinking_power`,
	`lable_pets`,
	`lable_introduce`,
	`lable_smoking_status`,
	`lable_occupation`
)
VALUES
	(
		new.id,
		'声音,笑容,脾气,性格,脸,眼睛,嘴,颈,手,腰,胸部,腿,臀',
		'',
		'北京,上海,广州,香港,厦门,长沙,成都,杭州,苏州,重庆,首尔,东京,纽约,华盛顿,伦敦,莫斯科,巴黎,新德里,曼谷,新加坡,迪拜,罗马,柏林,雅典,卢森堡,马德里',
		'',
		'中国,台湾,香港,成都,重庆,三亚,丽江,大理,西藏,张家界,洱海,鼓浪屿,日本,北海道,名古屋,东京,韩国,济州岛,巴厘岛,普吉岛,长滩岛,塞班岛,新加坡,泰国,马来西亚,菲律宾,印度尼西亚,印度,越南,尼泊尔,迪拜,土耳其,希腊,美国,加拿大,澳大利亚,英国,法国,意大利,西班牙,葡萄牙,荷兰,瑞士,瑞典,芬兰,巴西,新西兰,俄罗斯,德国,埃及',
		'',
		'旅游,锻炼,唱歌,乐器,美食,电影,追星,看书,追剧,睡觉,游戏,画画,短视频,喝酒',
		'',
		'不填,本科,硕士,博士',
		'',
		'单身,热恋,冷战,已婚,保密',
		'',
		'10万以下,10万-20万,20万-30万,30万-50万,50万-100万,100万-200万,200万-500万,500万以上',
		'',
		'无,1辆,2辆-5辆,5辆-10辆,10辆以上',
		'',
		'粉雕玉琢,出水芙蓉,婀娜多姿,秀外慧中,楚楚动人,天生丽质,闭月羞花,倾国倾城,珠圆玉润,明眸皓齿,千娇百媚,我见犹怜,丰绰性感',
		'',
		'开朗,腼腆,活泼,文静,天真,自信,乐观,内向,开放,冷酷,刚毅,傲娇,冰冷',
		'',
		'能源,餐饮,酒店,房地产,服务,服装,公益组织,广告业,健康,教育,培训,互联网,会计,美容,媒体,出版,旅游业,司法,律师,演艺,医疗,艺术,设计,银行,金融,音乐,舞蹈,机关,咨询',
		'',
		'',
		'',
		'',
		'',
		'',
		'',
		''
	);
END IF;
IF ( new.sex != 2) THEN
INSERT INTO `jiaoyou`.`lable` (
	`member_id`,
	`label_charm_default`,
	`label_charm_select`,
	`label_city_default`,
	`label_city_select`,
	`label_local_default`,
	`label_local_select`,
	`label_hobby_default`,
	`label_hobby_select`,
	`label_education_default`,
	`label_education_select`,
	`lable_state_default`,
	`lable_state_select`,
	`lable_annual_income_default`,
	`lable_annual_income_select`,
	`lable_car_size_default`,
	`lable_car_size_select`,
	`lable_image_default`,
	`lable_image_select`,
	`lable_character_default`,
	`lable_character_select`,
	`lable_industry_default`,
	`lable_industry_select`,
	`lable_height`,
	`lable_weight`,
	`lable_drinking_power`,
	`lable_pets`,
	`lable_introduce`,
	`lable_smoking_status`,
	`lable_occupation`
)
VALUES
	(
		new.id,
		'声音,笑容,脾气,性格,脸,眼睛,嘴,颈,手,腰,胸部,腿,臀',
		'',
		'北京,上海,广州,香港,厦门,长沙,成都,杭州,苏州,重庆,首尔,东京,纽约,华盛顿,伦敦,莫斯科,巴黎,新德里,曼谷,新加坡,迪拜,罗马,柏林,雅典,卢森堡,马德里',
		'',
		'中国,台湾,香港,成都,重庆,三亚,丽江,大理,西藏,张家界,洱海,鼓浪屿,日本,北海道,名古屋,东京,韩国,济州岛,巴厘岛,普吉岛,长滩岛,塞班岛,新加坡,泰国,马来西亚,菲律宾,印度尼西亚,印度,越南,尼泊尔,迪拜,土耳其,希腊,美国,加拿大,澳大利亚,英国,法国,意大利,西班牙,葡萄牙,荷兰,瑞士,瑞典,芬兰,巴西,新西兰,俄罗斯,德国,埃及',
		'',
		'旅游,锻炼,唱歌,乐器,美食,电影,追星,看书,追剧,睡觉,游戏,画画,短视频,喝酒',
		'',
		'不填,本科,硕士,博士',
		'',
		'单身,热恋,冷战,已婚,保密',
		'',
		'10万以下,10万-20万,20万-30万,30万-50万,50万-100万,100万-200万,200万-500万,500万以上',
		'',
		'无,1辆,2辆-5辆,5辆-10辆,10辆以上',
		'',
		'伟岸刚毅,狂野不拘,俊美绝伦,风度翩翩,正气凛然,顶天立地,桀骜不驯,才貌双全,眉清目秀,温文尔雅,风流倜傥,堂堂正正,冷酷无情',
		'',
		'开朗,腼腆,活泼,文静,天真,自信,乐观,内向,开放,冷酷,刚毅,傲娇,冰冷',
		'',
		'能源,餐饮,酒店,房地产,服务,服装,公益组织,广告业,健康,教育,培训,互联网,会计,美容,媒体,出版,旅游业,司法,律师,演艺,医疗,艺术,设计,银行,金融,音乐,舞蹈,机关,咨询',
		'',
		'',
		'',
		'',
		'',
		'',
		'',
		''
	);
END IF;
INSERT INTO membership_settings (
	memberId,
	sameCityWithinState,
	sameCityExternalState,
	confidentialityOfInformationState,
	friendMessageState,
	noticeState,
	dynamicResponseState,
	worldInformation
)
VALUES
	(new.id, 1, 1, 1, 1, 1, 1, 2);
INSERT INTO member_matching_log (
	member_matching_log_member_id,
	member_matching_log_member_ids_soul,
	member_matching_log_member_ids_car
)
VALUES
	(new.id, '', '');

IF ( new.registrationChannel = 3 ) THEN
        INSERT INTO circle_of_friends (
	memerId,
	memberHead,
	memberNickName,
	memberSex,
	memberAge,
	city,
	addTime,
	context,
	likeSize,
	commentSize,
	lable,
	strangersInTheSameCity,
	strangersOutsideTheCity,
	citySynchronization,
	defaultType
)
VALUES
	(
		new.id,
		new.head,
		new.nickName,
		new.sex,
		new.age,
		new.city,
		now(),
		'新人报道！',
		0,
		0,
		new.carLable,
		1,
		1,
		1,
		1

	);
END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table member
-- ----------------------------
DROP TRIGGER IF EXISTS `updateHead`;
delimiter ;;
CREATE TRIGGER `updateHead` AFTER UPDATE ON `member` FOR EACH ROW BEGIN

 IF ( old.head != new.head ) OR ( old.backgroundImages != new.backgroundImages ) OR ( old.sex != new.sex ) OR ( old.nickName != new.nickName ) OR ( old.age != new.age ) OR ( old.city != new.city ) OR ( old.carLable != new.carLable )  THEN
	
	UPDATE member a, dynamic_review_of_friends_circle b
	SET b.memberHead = a.head,b.memberNickName = a.nickName,b.lable = a.carLable
	WHERE b.memberId = a.id;

	UPDATE member a, dynamic_comments_of_car_users b
	SET b.memberHead = a.head,b.memberNickName = a.nickName,b.lable = a.carLable
	WHERE b.memberId = a.id;

	UPDATE member a, report b
	SET b.report_informant_head = a.head,b.report_informant_nick_name = a.nickName,b.report_informant_phone = a.phone
	WHERE b.report_informant_id = a.id;

	UPDATE member a, report b
	SET b.report_bei_informant_head = a.head,b.report_bei_informant_nick_name = a.nickName,b.report_bei_informant_phone = a.phone
	WHERE b.report_bei_informant_id = a.id;

	UPDATE member a, accept_offline_activities b
	SET b.acceptMemberHead = a.head,b.acceptMemberNickName = a.nickName,b.acceptMemberSex = a.sex,b.acceptMemberAge = a.age	
	WHERE b.acceptMemberId = a.id;

	UPDATE member a, send_offline_activities b
	SET b.sendMemberHead = a.head,b.sendMemberNickName = a.nickName,b.sendMemberSex = a.sex
	WHERE b.sendMemberId = a.id;

	UPDATE member a, withdraw b
	SET b.memberHead = a.head,b.memberNickName = a.nickName,b.memberPhone = a.phone
	WHERE b.memberId = a.id;

	UPDATE member a, rider_dynamics b
	SET b.memberHead = a.head,b.memberNickName = a.nickName,b.memberSex = a.sex,b.memberAge = a.age,b.lable = a.carLable
	WHERE b.memerId = a.id;

	UPDATE member a, circle_of_friends b
	SET b.memberHead = a.head,b.memberNickName = a.nickName,b.memberSex = a.sex,b.memberAge = a.age,b.lable = a.carLable
	WHERE b.memerId = a.id;

	UPDATE member a, new_friend b
	SET b.new_friend_other_party_head = a.head,b.new_friend_other_party_nick_name = a.nickName,b.new_friend_other_party_sex = a.sex,b.new_friend_other_party_age = a.age,b.new_friend_other_party_level = a.`level`,b.new_friend_other_party_city = a.city
	WHERE b.new_friend_oneself_id = a.id;
	
	UPDATE member a, club b
	SET b.club_icon = a.head
	WHERE b.memberId = a.id;
	
	UPDATE member a, task_answer_questions b
	SET b.nick_name = a.nickName
	WHERE b.member_id = a.id;
	
END IF;

IF ( old.examineState != 1 ) and ( old.examineState != 2 ) and ( old.examineState != new.examineState ) and ( old.registrationChannel != 2 ) and ( old.registrationChannel != 3 )  and ( new.examineState = 1 )THEN
        INSERT INTO circle_of_friends (
	memerId,
	memberHead,
	memberNickName,
	memberSex,
	memberAge,
	city,
	addTime,
	context,
	images,
	likeSize,
	commentSize,
	lable,
	strangersInTheSameCity,
	strangersOutsideTheCity,
	citySynchronization,
	defaultType
)
VALUES
	(
		new.id,
		new.head,
		new.nickName,
		new.sex,
		new.age,
		new.city,
		now(),
		'新人报道！',
		new.facePhoto,
		0,
		0,
		new.carLable,
		1,
		1,
		1,
		1

	);
END IF;

IF ( old.examineState != 1 ) and ( old.examineState != 2 ) and ( old.examineState != new.examineState ) and ( old.registrationChannel != 1 ) THEN
        INSERT INTO circle_of_friends (
	memerId,
	memberHead,
	memberNickName,
	memberSex,
	memberAge,
	city,
	addTime,
	context,
	likeSize,
	commentSize,
	lable,
	strangersInTheSameCity,
	strangersOutsideTheCity,
	citySynchronization,
	defaultType
)
VALUES
	(
		new.id,
		new.head,
		new.nickName,
		new.sex,
		new.age,
		new.city,
		now(),
		'新人报道！',
		0,
		0,
		new.carLable,
		1,
		1,
		1,
		1

	);
END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table member_assets
-- ----------------------------
DROP TRIGGER IF EXISTS `upload`;
delimiter ;;
CREATE TRIGGER `upload` BEFORE UPDATE ON `member_assets` FOR EACH ROW BEGIN
INSERT INTO member_assets_copy(
	memberId,
	memberDiamondsizeOfBlack,
	memberhornSize,
	meberExperienceSize,
	memberLabel,
	ciphertext,
	memberDiamondsizeOfGold,
	memberDiamondsizeOfSilver,
	oldMemberDiamondsizeOfBlack,
	oldMemberDiamondsizeOfSilver
              ) VALUES (
	new.memberId,
	new.memberDiamondsizeOfBlack,
	new.memberhornSize,
	new.meberExperienceSize,
	new.memberLabel,
	new.ciphertext,
	new.memberDiamondsizeOfGold,
	new.memberDiamondsizeOfSilver,
	new.oldMemberDiamondsizeOfBlack,
	new.oldMemberDiamondsizeOfSilver
);
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table new_friend
-- ----------------------------
DROP TRIGGER IF EXISTS `del`;
delimiter ;;
CREATE TRIGGER `del` AFTER DELETE ON `new_friend` FOR EACH ROW BEGIN
#修改礼物状态为已接收
UPDATE giftconsumption SET drawInProportion = 1 WHERE REVISION = old.remarks;

END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table recharge_record
-- ----------------------------
DROP TRIGGER IF EXISTS `insert`;
delimiter ;;
CREATE TRIGGER `insert` AFTER INSERT ON `recharge_record` FOR EACH ROW BEGIN

#author:nan
#time:20210106
#content:用户每充值一块钱，则增加一点经验值




END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table red_log
-- ----------------------------
DROP TRIGGER IF EXISTS `insterRed`;
delimiter ;;
CREATE TRIGGER `insterRed` AFTER INSERT ON `red_log` FOR EACH ROW BEGIN
    #减去发送方相应钻石
    UPDATE member_assets SET memberDiamondsizeOfGold = memberDiamondsizeOfGold-new.red_log_gold_size WHERE memberId=new.red_log_member_id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table red_receive_log
-- ----------------------------
DROP TRIGGER IF EXISTS `insterRedLog`;
delimiter ;;
CREATE TRIGGER `insterRedLog` AFTER INSERT ON `red_receive_log` FOR EACH ROW BEGIN
    #增加接收方相应钻石(银钻与魅力值)
    UPDATE member_assets SET memberDiamondsizeOfSilver = memberDiamondsizeOfSilver+new.red_receive_log_gold_size,oldmemberDiamondsizeOfSilver = oldmemberDiamondsizeOfSilver + new.red_receive_log_gold_size
		WHERE memberId=new.red_receive_log_member_id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table send_offline_activities
-- ----------------------------
DROP TRIGGER IF EXISTS `insterHuoDong`;
delimiter ;;
CREATE TRIGGER `insterHuoDong` AFTER INSERT ON `send_offline_activities` FOR EACH ROW BEGIN
        #发起活动时
	#发送方 发送方减少金钻石
              UPDATE member_assets a 
	SET a.memberDiamondsizeOfGold = a.memberDiamondsizeOfGold-new.perSize*new.averageDiamondsSize WHERE a.memberId = new.sendMemberId;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table send_offline_activities
-- ----------------------------
DROP TRIGGER IF EXISTS `updateState`;
delimiter ;;
CREATE TRIGGER `updateState` AFTER UPDATE ON `send_offline_activities` FOR EACH ROW BEGIN
#取消活动时
#发送方 发送方增加金钻石并赔偿银钻石
IF (new.state = 7) and (new.state != old.state) THEN


#先增加 发起方金钻

UPDATE member_assets a,(

SELECT a.*,b.perSize,b.averageDiamondsSize FROM member_assets  a, (

SELECT * FROM send_offline_activities WHERE id = new.id

) b WHERE a.memberId = b.sendMemberId

) b SET a.memberDiamondsizeOfGold = (a.memberDiamondsizeOfGold + (b.perSize*b.averageDiamondsSize)) WHERE a.memberId = b.memberId;

#增加 报名放赔偿的银钻

UPDATE member_assets a,(

SELECT a.acceptMemberId, timestampdiff(HOUR,NOW(),startTime) as time,b.averageDiamondsSize,b.sendMemberId FROM accept_offline_activities a ,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.sendOfflineActivities = new.id AND a.keepAnAppointmentState = 2

) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.9)) ,a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.9)) WHERE a.memberId = b.acceptMemberId AND b.time < 6;

UPDATE member_assets a,(

SELECT a.acceptMemberId, timestampdiff(HOUR,NOW(),startTime) as time,b.averageDiamondsSize,b.sendMemberId FROM accept_offline_activities a ,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.sendOfflineActivities = new.id AND a.keepAnAppointmentState = 2

) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.7)) ,a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.7))  WHERE a.memberId = b.acceptMemberId AND b.time < 12 AND b.time >= 6;

UPDATE member_assets a,(

SELECT a.acceptMemberId, timestampdiff(HOUR,NOW(),startTime) as time,b.averageDiamondsSize,b.sendMemberId FROM accept_offline_activities a ,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.sendOfflineActivities = new.id AND a.keepAnAppointmentState = 2

) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.5)) ,a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.5))  WHERE a.memberId = b.acceptMemberId AND b.time < 24 AND b.time >= 12;

UPDATE member_assets a,(

SELECT a.acceptMemberId, timestampdiff(HOUR,NOW(),startTime) as time,b.averageDiamondsSize,b.sendMemberId FROM accept_offline_activities a ,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.sendOfflineActivities = new.id AND a.keepAnAppointmentState = 2

) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.3)) ,a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + floor(b.averageDiamondsSize * 0.3))  WHERE a.memberId = b.acceptMemberId AND b.time >= 24;
#减少 发起方金钻

UPDATE member_assets a,(

SELECT a.acceptMemberId, timestampdiff(HOUR,NOW(),startTime) as time,b.averageDiamondsSize,b.sendMemberId FROM accept_offline_activities a ,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.sendOfflineActivities = new.id AND a.keepAnAppointmentState = 2

) b SET a.memberDiamondsizeOfGold = (a.memberDiamondsizeOfGold - floor(b.averageDiamondsSize * 0.9)) WHERE a.memberId = b.sendMemberId AND b.time < 6;

UPDATE member_assets a,(

SELECT a.acceptMemberId, timestampdiff(HOUR,NOW(),startTime) as time,b.averageDiamondsSize,b.sendMemberId FROM accept_offline_activities a ,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.sendOfflineActivities = new.id AND a.keepAnAppointmentState = 2

) b SET a.memberDiamondsizeOfGold = (a.memberDiamondsizeOfGold - floor(b.averageDiamondsSize * 0.7)) WHERE a.memberId = b.sendMemberId AND b.time < 12 AND b.time >= 6;

UPDATE member_assets a,(

SELECT a.acceptMemberId, timestampdiff(HOUR,NOW(),startTime) as time,b.averageDiamondsSize,b.sendMemberId FROM accept_offline_activities a ,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.sendOfflineActivities = new.id AND a.keepAnAppointmentState = 2

) b SET a.memberDiamondsizeOfGold = (a.memberDiamondsizeOfGold - floor(b.averageDiamondsSize * 0.5)) WHERE a.memberId = b.sendMemberId AND b.time < 24 AND b.time >= 12;

UPDATE member_assets a,(

SELECT a.acceptMemberId, timestampdiff(HOUR,NOW(),startTime) as time,b.averageDiamondsSize,b.sendMemberId FROM accept_offline_activities a ,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.sendOfflineActivities = new.id AND a.keepAnAppointmentState = 2

) b SET a.memberDiamondsizeOfGold = (a.memberDiamondsizeOfGold - floor(b.averageDiamondsSize * 0.3)) WHERE a.memberId = b.sendMemberId AND b.time >= 24;


#修改所有的参与数据状态为5
#UPDATE accept_offline_activities  SET keepAnAppointmentState = 5,closeType = 1 WHERE sendOfflineActivities = new.id;

END IF;
#审核通过时
IF (new.state = 5)  and (new.state != old.state) THEN

#先增加 发起方金钻

UPDATE member_assets a,(

SELECT a.*,b.perSize,b.averageDiamondsSize FROM member_assets  a, (

SELECT * FROM send_offline_activities WHERE id = new.id

) b WHERE a.memberId = b.sendMemberId

) b SET a.memberDiamondsizeOfGold = (a.memberDiamondsizeOfGold + (b.perSize*b.averageDiamondsSize)) WHERE a.memberId = b.memberId;

#增加 报名方奖励的银钻

UPDATE member_assets a,(

SELECT a.acceptMemberId, b.averageDiamondsSize,b.sendMemberId FROM accept_offline_activities a ,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.sendOfflineActivities = new.id AND a.keepAnAppointmentState = 3

) b SET a.memberDiamondsizeOfSilver = (a.memberDiamondsizeOfSilver + b.averageDiamondsSize), a.oldMemberDiamondsizeOfSilver = (a.oldMemberDiamondsizeOfSilver + b.averageDiamondsSize) WHERE a.memberId = b.acceptMemberId;

#减少 发起方的金钻

UPDATE member_assets a,(

SELECT a.acceptMemberId, b.averageDiamondsSize,b.sendMemberId FROM accept_offline_activities a ,send_offline_activities b WHERE a.sendOfflineActivities = b.id AND a.sendOfflineActivities = new.id AND a.keepAnAppointmentState = 3

) b SET a.memberDiamondsizeOfGold = (a.memberDiamondsizeOfGold - b.averageDiamondsSize) WHERE a.memberId = b.sendMemberId;

END IF;
#审核未通过
IF (new.state = 6)  and (new.state != old.state) THEN
#先增加 发起方金钻

UPDATE member_assets a,(

SELECT a.*,b.perSize,b.averageDiamondsSize FROM member_assets  a, (

SELECT * FROM send_offline_activities WHERE id = new.id

) b WHERE a.memberId = b.sendMemberId

) b SET a.memberDiamondsizeOfGold = (a.memberDiamondsizeOfGold + b.perSize*b.averageDiamondsSize) WHERE a.memberId = b.memberId;
#修改所有参与数据状态为8
UPDATE accept_offline_activities  SET keepAnAppointmentState = 8,closeType = 1 WHERE sendOfflineActivities = new.id;
END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table speakerconsumptionrecord
-- ----------------------------
DROP TRIGGER IF EXISTS `insterSpeakerLog`;
delimiter ;;
CREATE TRIGGER `insterSpeakerLog` AFTER INSERT ON `speakerconsumptionrecord` FOR EACH ROW BEGIN
        UPDATE member_assets SET memberhornSize = new.surplusSize WHERE memberId = new.memberId;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
