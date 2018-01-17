CREATE USER 'library-spring'@'localhost' IDENTIFIED BY 'library-spring';

GRANT ALL PRIVILEGES ON  *.* TO 'library-spring'@'localhost';

CREATE DATABASE  IF NOT EXISTS `library_db`;

USE `library_db`;


DROP TABLE IF EXISTS `user_detail`;

CREATE TABLE `user_detail` (
	`id` int(6) NOT NULL AUTO_INCREMENT,
    `first_name` varchar(60) DEFAULT NULL,
    `last_name` varchar(60) DEFAULT NULL,
    `email` varchar(60) NOT NULL,
    
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `authority`;

CREATE TABLE `role` (
	`id` int(6) NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL UNIQUE,
    
	PRIMARY KEY (`id`)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
	`id` int(6) NOT NULL AUTO_INCREMENT,
    `login` varchar(64) NOT NULL UNIQUE,
    `password` varchar(100) NOT NULL,
    `enable` boolean NOT NULL,
	`user_detail_id` int(6) NOT NULL,
    
    PRIMARY KEY (`id`),
    
    KEY `user_detail` (`user_detail_id`),
    CONSTRAINT `FK_USER_DETAIL` FOREIGN KEY (`user_detail_id`)
    REFERENCES `user_detail` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user_authority`;

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
    REFERENCES `user` (`login`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `role` (`name`) VALUES
	("USER"), ("ADMIN"), ("LIBRARIAN");
	

INSERT INTO `user_detail` (`first_name`, `last_name`, `email`) VALUES

	("Edard", "Stark", "edard.stark@winterfell.com"),
    ("Sam", "Tarly", "sam.tarly@night-watch.com"),
    ("Cersai", "Lanister", "cersai.lanister@kings-landing.com")
;

INSERT INTO `user` (`login`, `password`, `enable`, `user_detail_id`) VALUES

	("adusermin", "$2a$10$TDb0af7bDK9Gr4xAPoqZqunjFVLCM6togtL556J.M4BEJeUZd7psu", TRUE, 1),
    ("book", "$2a$10$ZsA1jIBWe/GcMWgKRSwIt.wcFKUH86O1t63JsJ/.JCKlT3iJK/n0a", TRUE, 2),
    ("test", "$2a$10$a9WYkCHn5goa5wEgplC87.4Dy.A23khRSAssrU5OFYKMGva7.c9Ba", TRUE, 3)
;

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES

	(1, 1),
    (1, 2),
    (2, 1),
    (2, 3),
    (3, 1)
;

