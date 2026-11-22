-- ============================================================
-- 项目功能扩充 · 销售地点信息地图展示
-- 为 sale 表增加经纬度与详细地址字段，并补齐现有 18 条药店的位置数据
-- 执行方式：mysql -uroot -p --default-character-set=utf8mb4 bin_text < 本文件
-- ============================================================

-- 1. 增加字段（已在库中则跳过这两条 ALTER）
ALTER TABLE `sale`
    ADD COLUMN `lng` double DEFAULT NULL COMMENT '经度' AFTER `updatetime`,
    ADD COLUMN `lat` double DEFAULT NULL COMMENT '纬度' AFTER `lng`,
    ADD COLUMN `address` varchar(255) DEFAULT '' COMMENT '详细地址' AFTER `lat`;

-- 2. 补齐现有销售地点的经纬度与地址（按 sale_id 精确更新）
UPDATE `sale` SET `lng` = 104.06607,  `lat` = 30.660842, `address` = '四川省成都市锦江区春熙路街道春熙路' WHERE `sale_id` = 12635265;
UPDATE `sale` SET `lng` = 104.099246, `lat` = 30.655776, `address` = '四川省成都市成华区猛追湾街道建设路' WHERE `sale_id` = 12635266;
UPDATE `sale` SET `lng` = 103.92934,  `lat` = 30.5575,   `address` = '四川省成都市双流区东升街道大成天府景秀' WHERE `sale_id` = 12635267;
UPDATE `sale` SET `lng` = 103.79934,  `lat` = 30.58146,  `address` = '四川省成都市崇州市三江街道羊江路' WHERE `sale_id` = 12635268;
UPDATE `sale` SET `lng` = 104.064155, `lat` = 30.662808, `address` = '四川省成都市青羊区西御河街道西华门街' WHERE `sale_id` = 12635269;
UPDATE `sale` SET `lng` = 103.59934,  `lat` = 30.60746,  `address` = '四川省成都市崇州市白头镇天竺路' WHERE `sale_id` = 12635270;
UPDATE `sale` SET `lng` = 103.90934,  `lat` = 30.62746,  `address` = '四川省成都市双流区九江街道元瑞家具城' WHERE `sale_id` = 12635271;
UPDATE `sale` SET `lng` = 104.052984, `lat` = 30.652199, `address` = '四川省成都市青羊区少城街道绮霞园' WHERE `sale_id` = 12635272;
UPDATE `sale` SET `lng` = 103.99442,  `lat` = 30.55747,  `address` = '四川省成都市双流区西航港街道四川大学江安校区' WHERE `sale_id` = 12635280;
UPDATE `sale` SET `lng` = 104.063352, `lat` = 30.637329, `address` = '四川省成都市武侯区玉林街道中学路' WHERE `sale_id` = 12635282;
UPDATE `sale` SET `lng` = 104.102294, `lat` = 30.675003, `address` = '四川省成都市成华区猛追湾街道电子科技大学沙河校区' WHERE `sale_id` = 12635285;
UPDATE `sale` SET `lng` = 104.08895,  `lat` = 30.675467, `address` = '四川省成都市金牛区驷马桥街道马鞍北路社区' WHERE `sale_id` = 12635286;
UPDATE `sale` SET `lng` = 104.337608, `lat` = 30.541705, `address` = '四川省成都市龙泉驿区山泉镇桃花故里景区' WHERE `sale_id` = 12635287;
UPDATE `sale` SET `lng` = 104.139854, `lat` = 30.634259, `address` = '四川省成都市成华区保和街道梦魔方广场' WHERE `sale_id` = 12635288;
UPDATE `sale` SET `lng` = 104.065697, `lat` = 30.611214, `address` = '四川省成都市武侯区火车南站街道沁园茶楼' WHERE `sale_id` = 12635289;
UPDATE `sale` SET `lng` = 103.981239, `lat` = 30.687418, `address` = '四川省成都市青羊区苏坡街道金辉路' WHERE `sale_id` = 12635290;
UPDATE `sale` SET `lng` = 104.003212, `lat` = 30.705721, `address` = '四川省成都市金牛区金泉街道风林假日' WHERE `sale_id` = 12635291;
UPDATE `sale` SET `lng` = 104.090798, `lat` = 30.688991, `address` = '四川省成都市双流区九江街道阿垦农场' WHERE `sale_id` = 12635292;

-- 3. 兜底：仍然没有坐标的记录给一个成都市中心坐标，避免地图上缺失
UPDATE `sale` SET `lng` = 104.06707, `lat` = 30.660842, `address` = '四川省成都市' WHERE `lng` IS NULL OR `lat` IS NULL;
