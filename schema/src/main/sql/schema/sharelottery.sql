use sharelottery;

/*
 * The decimal(8,4) is not big enough and causes data integrity issues
 */

CREATE TABLE `sharelottery`.`payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `amount` decimal(50,20) NOT NULL,
  `version` int(1) default 0,
  `success` int(1) default 0,
  `transaction_id` varchar(100) NOT NULL,
  `transaction_message` varchar(100),
  `invoice_number` varchar(100) NOT NULL,
  `created_on` datetime,
  `last_modified` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `sharelottery`.`transaction_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `lottery_ticket_id` bigint(20),
  `version` int(1) default 0,
  `current_balance` decimal(50,20) NOT NULL,
  `amount` decimal(50,20) NOT NULL,
  `transaction_count` int(200) default 0,
  `transaction_type` int(50),
  `created_on` TIMESTAMP default NOW(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `sharelottery`.`balance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `amount` decimal(50,20) NOT NULL,
  `earned` decimal(50,20) NOT NULL,
  `spent` decimal(50,20) NOT NULL,
  `last_cash_out_amount` decimal(50,20) NOT NULL,
  `billable` decimal(50,20) NOT NULL,
  `last_billed_date` datetime,
  `last_cash_out_date` datetime,
  `version` int(1) default 0,
  `transaction_count` int(200) default 0,
  `spent_count` int(200) default 0,
  `earned_count` int(200) default 0,
  `list_item_count` int(200) default 0,
  `last_modified` datetime,
  `created_on` TIMESTAMP default NOW(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `sharelottery`.`lottery_buyer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `buyer_user_id` bigint(20) NOT NULL,
  `lottery_ticket_id` bigint(20) NOT NULL,
  `cost` decimal(50,20) NOT NULL,
  `numbers` varchar(50),
  `version` int(1) default 0,
  `paid` TINYINT(1) default 0,
  `referer_code`	varchar(50),
  `ticket_date` datetime,
  `created_on` TIMESTAMP default NOW(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `sharelottery`.`lottery_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `state` varchar(50),
  `ticket_number` varchar(100),
  `lotto_game_id` bigint(20),
  `numbers` varchar(50),
  `lotto_game_name` varchar(200),
  `slot_size` int(10) default 0,
  `split_number` int(10) default 0,
  `buyer_count` int(10) default 0,
  `cost` decimal(8,2),
  `version` int(1) default 0,
  `flag` TINYINT(1) default 0,
  `deleted` TINYINT(1) default 0,
  `already_purchased` TINYINT(1) default 0,
  `image_bytes` LONGBLOB, 
  `icon_byte` LONGBLOB,  
  `referer_code`	varchar(50),
  `ticket_date` datetime,
  `last_modified` datetime,
  `created_on` TIMESTAMP default NOW(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `sharelottery`.`lotto_game` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` varchar(50),
  `game_name` varchar(200) NOT NULL,
  `lotto_game_engine_name` varchar(100),
  `winning_amount` varchar(50),
  `frequency` int(21),
  `link` varchar(50),
  `numbers` varchar(50),
  `logo` LONGBLOB,  
  `prev_drawing_date` varchar(30),
  `next_drawing_date` varchar(30),
  `version` int(1) default 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `sharelottery`.`lotto_game_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` varchar(50) NOT NULL,
  `class_name` varchar(100) NOT NULL,
  `version` int(1) default 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `address` varchar(150) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `postal_code` varchar(15) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `credentials_expired` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `account_enabled` bit(1) DEFAULT NULL,
  `tweeted` tinyint(1) DEFAULT 0,
  `fbliked` tinyint(1) DEFAULT 0,
  `fbfollow` tinyint(1) DEFAULT 0,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `password_hint` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `facebook_id` varchar(50) DEFAULT NULL,
  `access_token` varchar(100) DEFAULT NULL,
  `referer_code`	varchar(50),
  `refered_by` varchar(50),
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(64) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK143BF46A4FD90D75` (`role_id`),
  KEY `FK143BF46AF503D155` (`user_id`),
  CONSTRAINT `FK143BF46AF503D155` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK143BF46A4FD90D75` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE INDEX user_referral_code ON app_user.referral_code ( referral_code);

