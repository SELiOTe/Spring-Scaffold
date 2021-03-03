-- -------------------------------------------------------------------------
-- 数据库创建脚本
-- 版本：MariaDB 10.5.8
-- 用法 mariadb -uroot -p < create.sql
-- -------------------------------------------------------------------------

-- 创建数据库
CREATE DATABASE fitting_room CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';

-- 创建数据库用户
CREATE USER 'x7pGz6D'@'%' IDENTIFIED BY 'sU3%^vr7UJtd3KyYm';

-- 配置数据库用户权限
GRANT INSERT, UPDATE, SELECT ON fitting_room.* TO 'x7pGz6D'@'%';
FLUSH PRIVILEGES;

-- 切换数据库
USE fitting_room;

-- 创建用户表
CREATE TABLE user
(
    id           VARCHAR(36) NOT NULL COMMENT 'row id(UUID)',
    country_code VARCHAR(4)  NOT NULL COMMENT 'telephone number country calling code',
    tel_no       VARCHAR(15) NOT NULL COMMENT 'telephone number',
    nickname     VARCHAR(16) NOT NULL COMMENT 'user nickname',
    gender       TINYINT     NOT NULL DEFAULT 0 COMMENT 'user gender, 0 unknown, 1 male, 2 female',
    deleted      BOOLEAN     NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key'
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'user info';

-- 创建短信发送记录表
CREATE TABLE sms
(
    id           BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'row id',
    country_code VARCHAR(4)  NOT NULL COMMENT 'telephone number country calling code',
    tel_no       VARCHAR(15) NOT NULL COMMENT 'telephone number',
    type         TINYINT     NOT NULL COMMENT 'sms type, 1 login',
    result       TINYINT     NOT NULL COMMENT 'send result, 0 success, 1 failed send by api',
    deleted      BOOLEAN     NOT NULL DEFAULT FALSE COMMENT 'row logic deleted',
    created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row create time',
    modified_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row modify time',
    PRIMARY KEY pk_id (id) COMMENT 'row primary key'
) ENGINE = InnoDB
  CHARACTER SET 'utf8mb4'
  COLLATE 'utf8mb4_unicode_ci'
    COMMENT 'sms send log';
