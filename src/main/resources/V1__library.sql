CREATE USER 'library-spring'@'localhost' IDENTIFIED BY 'library-spring';

GRANT ALL PRIVILEGES ON  *.* TO 'library-spring'@'localhost';

DROP DATABASE `library_db`;

CREATE DATABASE  IF NOT EXISTS `library_db`;

USE `library_db`;

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
    `password` varchar(100) NOT NULL,
    `email` varchar(60) NOT NULL UNIQUE,
    `enable` boolean NOT NULL,
	`first_name` varchar(60) DEFAULT NULL,
    `last_name` varchar(60) DEFAULT NULL,
    `phone` varchar(60) DEFAULT NULL,
    `birthday` date DEFAULT NULL,
    `address` varchar(120) DEFAULT NULL,
	`postal` varchar(60) DEFAULT NULL,
    `city` varchar(60) DEFAULT NULL,
    `record_created` timestamp DEFAULT CURRENT_TIMESTAMP,
    
    PRIMARY KEY (`id`)
    
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

CREATE TABLE `user_logs` (
	`id` int(12) NOT NULL AUTO_INCREMENT,
    `level` varchar(10) NOT NULL,
    `dated` TIMESTAMP NOT  NULL DEFAULT CURRENT_TIMESTAMP,
    `user_id` int(6) NOT NULL,
    `field` varchar(60) NOT NULL,
    `from_value` varchar(1000) NOT NULL DEFAULT '',
    `to_value` varchar(1000) NOT NULL DEFAULT '',
    `message` varchar(500) NOT NULL,
    `changed_by_username` varchar(64) NOT NULL,
    
    PRIMARY KEY (`id`)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `isbn` (
	`id` int(12) NOT NULL AUTO_INCREMENT,
    `isbn_10` varchar(20) NOT NULL UNIQUE,
    `isbn_13` varchar(20) NOT NULL UNIQUE,
   
    PRIMARY KEY (`id`)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `book` (
	`id` int(12) NOT NULL AUTO_INCREMENT,
    `google_id` varchar(100) NOT NULL UNIQUE,
    `title` varchar(200) NOT NULL,
    `publisher` varchar(200) DEFAULT NULL,
    `published_date` varchar(100) DEFAULT NULL,
    `isbn_id` int(12) DEFAULT NULL,
    `page_count` int(6) DEFAULT NULL,
    `rating` real(4,1) DEFAULT NULL,
    `image_link` varchar(1000) DEFAULT NULL,
    `description` text DEFAULT NULL,
   
    PRIMARY KEY (`id`),
    
    CONSTRAINT `FK_ISBN` FOREIGN KEY (`isbn_id`)
    REFERENCES `isbn` (`id`)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `author` (
	`id` int(12) NOT NULL AUTO_INCREMENT,
    `name` varchar(200) NOT NULL UNIQUE,
   
    PRIMARY KEY (`id`)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `bookcategory` (
	`id` int(12) NOT NULL AUTO_INCREMENT,
    `name` varchar(200) NOT NULL UNIQUE,
   
    PRIMARY KEY (`id`)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `book_author` (
	`book_id` int(12) NOT NULL,
    `author_id` int(12) NOT NULL,
    
    PRIMARY KEY (`book_id`, `author_id`),
    
    KEY `book` (`book_id`),
    CONSTRAINT `FK_BOOK_AUTHOR` FOREIGN KEY (`book_id`)
    REFERENCES `book` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    KEY `author` (`author_id`),
    CONSTRAINT `FK_AUTHOR_BOOK` FOREIGN KEY (`author_id`)
	REFERENCES `author` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `book_bookcategory` (
	`book_id` int(12) NOT NULL,
    `bookcategory_id` int(12) NOT NULL,
    
    PRIMARY KEY (`book_id`, `bookcategory_id`),
    
    KEY `book` (`book_id`),
    CONSTRAINT `FK_BOOK_CATEGORY` FOREIGN KEY (`book_id`)
    REFERENCES `book` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    KEY `bookcategory` (`bookcategory_id`),
    CONSTRAINT `FK_CATEGORY_BOOK` FOREIGN KEY (`bookcategory_id`)
	REFERENCES `bookcategory` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_book` (
	`id` int(12) NOT NULL AUTO_INCREMENT,
	`user_id` int(6) NOT NULL,
    `book_id` int(12) NOT NULL,
    `dated` date NOT NULL,
    `approval_date` date DEFAULT NULL,
    `due_date` date DEFAULT NULL,
    
    PRIMARY KEY (`id`),
    
    KEY `user` (`user_id`),
    CONSTRAINT `FK_USER_BOOK` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    KEY `book` (`book_id`),
    CONSTRAINT `FK_BOOK_USER` FOREIGN KEY (`book_id`)
	REFERENCES `book` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `book_logs` (
	`id` int(12) NOT NULL AUTO_INCREMENT,
    `level` varchar(10) NOT NULL,
    `message` varchar(500) NOT NULL,
    `book_id` int(12) NOT NULL,
    `user_id` int(6) NOT NULL,
    `dated` TIMESTAMP NOT  NULL DEFAULT CURRENT_TIMESTAMP,
   
    PRIMARY KEY (`id`)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `role` (`name`) VALUES
	("USER"), ("ADMIN"), ("LIBRARIAN");
	
INSERT INTO `user` (`username`, `password`, `email`, `enable`, `first_name`, `last_name`) VALUES

	("admin", "$2a$10$TDb0af7bDK9Gr4xAPoqZqunjFVLCM6togtL556J.M4BEJeUZd7psu", "edard.stark@winterfell.com", TRUE, "Edard", "Stark"),
    ("book", "$2a$10$ZsA1jIBWe/GcMWgKRSwIt.wcFKUH86O1t63JsJ/.JCKlT3iJK/n0a", "sam.tarly@night-watch.com", TRUE, "Sam", "Tarly"),
    ("test", "$2a$10$a9WYkCHn5goa5wEgplC87.4Dy.A23khRSAssrU5OFYKMGva7.c9Ba", "cersai.lanister@kings-landing.com", TRUE, "Cersai", "Lanister"),
    ("rob", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","rob.stark@winterfell.com",TRUE, "Rob", "Stark"),
    ("lyanna", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","lyanna.stark@winterfell.com",TRUE, "Lyanna", "Stark"),
    ("ramsay", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","ramsay.boltonk@winterfell.com",TRUE, "Ramsay", "Bolton"),
    ("roose", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","roose.bolton@winterfell.com",TRUE, "Roose", "Bolton"),
    ("walder", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","walder.frey@crossing.com",TRUE, "Walder", "Frey"),
    ("theon", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","theon.greyjoy@pyke.com",TRUE, "Theon", "Greyjoy"),
    ("balon", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","balon.greyjoy@pyke.com",FALSE, "Balon", "Greyjoy"),
    ("yara", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","yara.greyjoy@pyke.com",TRUE, "Yara", "Greyjoy"),
    ("euron", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","euron.greyjoy@pyke.com",FALSE, "Euron", "Greyjoy"),
    ("jon", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","jon.aryyn@vale.com",TRUE, "Jon", "Arryn"),
    ("lysa", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","lysa.aryyn@vale.com",TRUE, "Lysa", "Arryn"),
    ("robin", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","robin.aryyn@vale.com",TRUE, "Robin", "Arryn"),
    ("petyr", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","petyr.bealish@vale.com",TRUE, "Petyr", "Baelish"),
    ("daenerys", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","denerys.targaryen@kings-landing.com",TRUE, "Daenerys", "Targaryen"),
    ("varys", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","varys@westeros.com",TRUE, "Varys", "Spider"),
    ("oberyn", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","oberyn.martell@dorne.com",TRUE, "Oberyn", "Martell"),
    ("doran", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","doran.martell@dorne.com",TRUE, "Doran", "Martell"),
    ("obara", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","obara.sand@dorne.com",TRUE, "Obara", "Sand"),
    ("nymeria", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","nymeria.sand@dorne.com",TRUE, "Nymeria", "Sand"),
    ("catelyn", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","catelyn.tully@riverrun.com",TRUE, "Catelyn", "Tully"),
    ("brynden", "$2a$10$OLwv3yttePkgfE5vZwsuDO7Zni/5s/tgq5UaWXOX39d9oD03eTODW","brynden.tully@riverrun.com",FALSE, "Brynden", "Tully")
;

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES

	(1, 1),
    (1, 2),
    (2, 1),
    (2, 3),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (7, 1),
    (8, 1),
    (9, 1),
    (10, 1),
    (11, 1),
    (12, 1),
    (13, 1),
    (14, 1),
    (15, 1),
    (16, 1),
    (17, 1),
    (18, 1),
    (19, 1),
    (20, 1),
    (21, 1),
    (22, 1),
    (23, 1),
    (24, 1)
;
