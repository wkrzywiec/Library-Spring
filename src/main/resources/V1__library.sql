CREATE USER 'library-spring'@'localhost' IDENTIFIED BY 'library-spring';

GRANT ALL PRIVILEGES ON  *.* TO 'library-spring'@'localhost';

DROP DATABASE `library_db`;

CREATE DATABASE  IF NOT EXISTS `library_db`;

USE `library_db`;

DROP TABLE IF EXISTS `user_detail`;

CREATE TABLE `user_detail` (
	`id` int(6) NOT NULL AUTO_INCREMENT,
    `first_name` varchar(60) DEFAULT NULL,
    `last_name` varchar(60) DEFAULT NULL,
    `phone` varchar(60) DEFAULT NULL,
    `birthday` date DEFAULT NULL,
    `address` varchar(120) DEFAULT NULL,
	`postal` varchar(60) DEFAULT NULL,
    `city` varchar(60) DEFAULT NULL,
    
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
	`id` int(6) NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL UNIQUE,
    
	PRIMARY KEY (`id`)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
	`id` int(6) NOT NULL AUTO_INCREMENT,
    `username` varchar(64) NOT NULL UNIQUE,
    `password` varchar(100) NOT NULL UNIQUE,
    `email` varchar(60) NOT NULL UNIQUE,
    `enable` boolean NOT NULL,
	`user_detail_id` int(6) NOT NULL,
    
    PRIMARY KEY (`id`),
    
    KEY `user_detail` (`user_detail_id`),
    CONSTRAINT `FK_USER_DETAIL` FOREIGN KEY (`user_detail_id`)
    REFERENCES `user_detail` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
	`user_id` int(6) NOT NULL,
    `role_id` int(6) NOT NULL,
    
    PRIMARY KEY (`user_id`, `role_id`),
    
    KEY `user` (`user_id`),
    CONSTRAINT `FK_USER_AUTH` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    KEY `role` (`role_id`),
    CONSTRAINT `FK_ROLE_USER` FOREIGN KEY (`role_id`)
	REFERENCES `role` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `persistent_logins`;

CREATE TABLE persistent_logins (
	`username` varchar(64) NOT NULL,
    `series` varchar(64) NOT NULL,
    `token` varchar(64) NOT NULL,
    `last_used` TIMESTAMP NOT NULL,
    
	PRIMARY KEY (`series`),
    CONSTRAINT `FK_USERNAME_LOGIN` FOREIGN KEY (`username`)
    REFERENCES `user` (`username`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user_logs`;

CREATE TABLE user_logs (
	`id` int(12) NOT NULL AUTO_INCREMENT,
    `level` varchar(10) NOT NULL,
    `dated` TIMESTAMP NOT  NULL DEFAULT CURRENT_TIMESTAMP,
    `username` varchar(64) NOT NULL,
    `field` varchar(60) NOT NULL,
    `from_value` varchar(1000) NOT NULL DEFAULT '',
    `to_value` varchar(1000) NOT NULL DEFAULT '',
    `message` varchar(500) NOT NULL,
    
    PRIMARY KEY (`id`),
    
    KEY `user` (`username`),
    CONSTRAINT `FK_USERNAME` FOREIGN KEY (`username`)
    REFERENCES `user` (`username`)
    ON DELETE NO ACTION ON UPDATE NO ACTION

)ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `role` (`name`) VALUES
	("USER"), ("ADMIN"), ("LIBRARIAN");
	

INSERT INTO `user_detail` (`first_name`, `last_name`) VALUES

	("Edard", "Stark"),
    ("Sam", "Tarly"),
    ("Cersai", "Lanister")
;

INSERT INTO `user` (`username`, `password`, `email`, `enable`, `user_detail_id`) VALUES

	("admin", "$2a$10$TDb0af7bDK9Gr4xAPoqZqunjFVLCM6togtL556J.M4BEJeUZd7psu", "edard.stark@winterfell.com", TRUE, 1),
    ("book", "$2a$10$ZsA1jIBWe/GcMWgKRSwIt.wcFKUH86O1t63JsJ/.JCKlT3iJK/n0a", "sam.tarly@night-watch.com", TRUE, 2),
    ("test", "$2a$10$a9WYkCHn5goa5wEgplC87.4Dy.A23khRSAssrU5OFYKMGva7.c9Ba", "cersai.lanister@kings-landing.com", TRUE, 3)
;

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES

	(1, 1),
    (1, 2),
    (2, 1),
    (2, 3),
    (3, 1)
;

insert into `user_logs` (`level`, `username`, `field`, `from`, `to`, `message`) VALUES ("debug", "tofik", "all", "", "all", "New User");
