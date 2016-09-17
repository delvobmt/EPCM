DROP DATABASE IF EXISTS `epcmdb`;
CREATE DATABASE `epcmdb`;
USE `epcmdb`;

DROP TABLE IF EXISTS `Account`;
CREATE TABLE IF NOT EXISTS `Account`(
	`account_id` INT AUTO_INCREMENT,
    `username` VARCHAR(50) UNIQUE NOT NULL,
    `password` VARCHAR(64) NOT NULL,
    `email` VARCHAR(50) UNIQUE NOT NULL,
    `name` VARCHAR(50),
	`status` VARCHAR(10) DEFAULT 'inactive',
    `createAt` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `lastActiveAt` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`account_id`)
);

DROP TABLE IF EXISTS `Account_Role`;
CREATE TABLE IF NOT EXISTS `Account_Role`(
	`role` VARCHAR(10) DEFAULT 'user', 
	`account_id` INT REFERENCES `Account`(`account_id`),
    `expireAt` DATETIME,
    PRIMARY KEY(`role`)
);

DROP TABLE IF EXISTS `Device`;
CREATE TABLE IF NOT EXISTS `Device`(
	`device_id` INT AUTO_INCREMENT,
    `model` VARCHAR(20) DEFAULT 'unknown',
    `version` VARCHAR(10) DEFAULT '0.0',
    `macAddress` VARCHAR(18) DEFAULT '00:00:00:00:00:00',
    `ipAddress` VARCHAR(16) DEFAULT '0.0.0.0',
    `consumeNumber` int DEFAULT 0,
    `oldNumber` int DEFAULT 0,
    `status`  BIT DEFAULT 0,
	`location` VARCHAR(250),
    `lastUpdate` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`device_id`)
);

DROP TABLE IF EXISTS `Customer`;
CREATE TABLE IF NOT EXISTS `Customer`(
	`customer_id` INT AUTO_INCREMENT,
	`firstName` VARCHAR(50),
	`lastName` VARCHAR(100),
	`address` VARCHAR(250),
	`contact` VARCHAR(250),
	`registerDate` DATETIME DEFAULT CURRENT_TIMESTAMP,
	`note` VARCHAR(250),
	PRIMARY KEY(`customer_id`)
);

DROP TABLE IF EXISTS `Customer_Group`;
CREATE TABLE IF NOT EXISTS `Customer_Group`(
	`customerGroup_id` INT AUTO_INCREMENT,
	`customer_id` INT REFERENCES `Customer`(`customer_id`),
	`group` VARCHAR(10),
	PRIMARY KEY(`customerGroup_id`),
	UNIQUE(`customer_id`, `group`)
);

DROP TABLE IF EXISTS `Customer_Device`;
CREATE TABLE IF NOT EXISTS `Customer_Device`(
	`customerDevice_id` INT AUTO_INCREMENT,
	`customer_id` INT REFERENCES `Customer`(`customer_id`),
	`device_id` INT REFERENCES `Device`(`device_id`),
	PRIMARY KEY(`customerDevice_id`),
	UNIQUE(`customer_id`, `device_id`)
);

DROP TABLE IF EXISTS `DeviceNotification`;
CREATE TABLE IF NOT EXISTS `DeviceNotification`(
	`deviceNotification_id` INT AUTO_INCREMENT,
	`device_id` INT REFERENCES `Device`(`device_id`),
	`severity` VARCHAR(10) DEFAULT 'info',
	`description` VARCHAR(250),
	PRIMARY KEY(`deviceNotification_id`)
); 

DROP TABLE IF EXISTS `DeviceConfig`;
CREATE TABLE IF NOT EXISTS `DeviceConfig`(
	`deviceConfig_id` INT AUTO_INCREMENT,
	`limitedConsume` INT,
	`alarmConsume` INT,
	PRIMARY KEY(`deviceConfig_id`)
);

DROP TABLE IF EXISTS `ConsumePolicy`;
CREATE TABLE IF NOT EXISTS `ConsumePolicy`(
	`consumePolicy_id` INT AUTO_INCREMENT,
	`min` INT,
	`max` INT,
	`percent` INT,
	PRIMARY KEY(`consumePolicy_id`)
);