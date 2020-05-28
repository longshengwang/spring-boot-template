--初始表
CREATE TABLE USERS(
USERNAME VARCHAR2(255) NOT NULL PRIMARY KEY,
PASSWORD VARCHAR2(255) NOT NULL,
PHONE VARCHAR2(255)
);

--MySQL----------------------------------------------------------------------------

--注意: 加binary使大小写敏感

-- CREATE DATABASE IF NOT EXISTS test DEFAULT CHARACTER SET = utf8mb4;
-- Use test;

-- CREATE TABLE `USERS` (
-- `USERNAME` varchar(255) binary PRIMARY KEY COMMENT '用户名',
-- `PASSWORD` varchar(255) binary NOT NULL COMMENT '密码',
-- `PHONE` varchar(255) binary COMMENT '手机'
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
