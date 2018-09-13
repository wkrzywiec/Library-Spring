CREATE TABLE `role` (
	`id` int(6) NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL UNIQUE,
    
	PRIMARY KEY (`id`)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

CREATE TABLE persistent_logins (
	`username` varchar(64) NOT NULL,
    `series` varchar(64) NOT NULL,
    `token` varchar(64) NOT NULL,
    `last_used` TIMESTAMP NOT NULL,
    
	PRIMARY KEY (`series`),
    CONSTRAINT `FK_USERNAME_LOGIN` FOREIGN KEY (`username`)
    REFERENCES `user` (`username`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_password_token` (
	`id` int(6) NOT NULL AUTO_INCREMENT,
    `token` varchar(64) NOT NULL,
    `user_id` int(6) NOT NULL,
    `due_date` TIMESTAMP NOT NULL,
    
    PRIMARY KEY (`id`),
    KEY `user` (`user_id`),
    CONSTRAINT `FK_USER_PASSWORD` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
);

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
    
    PRIMARY KEY (`id`),
    
    CONSTRAINT `FK_USER_LOG_USER` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `isbn` (
	`id` int(12) NOT NULL AUTO_INCREMENT,
    `isbn_10` varchar(20) UNIQUE,
    `isbn_13` varchar(20) UNIQUE,
   
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

CREATE TABLE `reserved` (
	`id` int(12) NOT NULL AUTO_INCREMENT,
    `book_id` int(12) NOT NULL,
    `user_id` int(6) NOT NULL,
    `dated` TIMESTAMP NOT  NULL DEFAULT CURRENT_TIMESTAMP,
    `due_date` DATE NOT NULL,
    
    PRIMARY KEY (`id`),
    
    KEY `user` (`user_id`),
    CONSTRAINT `FK_USER_RESERVED` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    KEY `book` (`book_id`),
    CONSTRAINT `FK_BOOK_RESERVED` FOREIGN KEY (`book_id`)
	REFERENCES `book` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `borrowed` (
	`id` int(12) NOT NULL AUTO_INCREMENT,
    `book_id` int(12) NOT NULL,
    `user_id` int(6) NOT NULL,
    `dated` TIMESTAMP NOT  NULL DEFAULT CURRENT_TIMESTAMP,
    `due_date` DATE NOT NULL,
    
    PRIMARY KEY (`id`),
    
    KEY `user` (`user_id`),
    CONSTRAINT `FK_USER_BORROWED` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    KEY `book` (`book_id`),
    CONSTRAINT `FK_BOOK_BORROWED` FOREIGN KEY (`book_id`)
	REFERENCES `book` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `book_penalty` (
	`id` int(12) NOT NULL AUTO_INCREMENT,
    `book_id` int(12) NOT NULL UNIQUE,
    `user_id` int(6) NOT NULL,
    `due_date` DATE NOT NULL,
    `return_date` DATE,
    
    PRIMARY KEY (`id`),
    
    KEY `user` (`user_id`),
    CONSTRAINT `FK_USER_PENALTY` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    KEY `book` (`book_id`),
    CONSTRAINT `FK_BOOK_PENALTY` FOREIGN KEY (`book_id`)
	REFERENCES `book` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;	

CREATE TABLE `library_logs` (
	`id` int(12) NOT NULL AUTO_INCREMENT,
    `level` varchar(10) NOT NULL,
    `message` varchar(500) NOT NULL,
    `book_id` int(12) NOT NULL,
    `user_id` int(6) NOT NULL,
    `dated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `changed_by_username` varchar(64) NOT NULL,
   
    PRIMARY KEY (`id`),
    
    KEY `user` (`user_id`),
    CONSTRAINT `FK_USER_LIBRARY_LOGS` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    KEY `book` (`book_id`),
    CONSTRAINT `FK_BOOK_LIBRARY_LOGS` FOREIGN KEY (`book_id`)
	REFERENCES `book` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `role` (`name`) VALUES
	("USER"), ("ADMIN"), ("LIBRARIAN");
	

DELIMITER //
CREATE PROCEDURE bookReserve(
	bookId int(12),
    userId int(6),
    days int(2))
BEGIN
    INSERT INTO `reserved` (`book_id`, `user_id`, `due_date`) VALUES (bookId, userId, DATE_ADD(NOW(), INTERVAL days DAY));
    SELECT * FROM book WHERE id=bookId;
END //


CREATE PROCEDURE bookBorrow(
	bookId int(12),
    userId int(6),
    days int(2))
BEGIN
	INSERT INTO `borrowed` (`book_id`, `user_id`, `due_date`) VALUES (bookId, userId, DATE_ADD(NOW(), INTERVAL days DAY));
	DELETE FROM `reserved` WHERE book_id= bookId;
    SELECT * FROM book WHERE id=bookId;
END //


CREATE PROCEDURE bookReturn(
	bookId int(12))
BEGIN
	DELETE FROM `borrowed` WHERE book_id = bookId;
END //


CREATE PROCEDURE setPenaltyReturnDate(
	bookId int(12))
BEGIN
	UPDATE `book_penalty` SET `return_date` = NOW() WHERE book_id = bookId;
END //

CREATE PROCEDURE penaltiesPaid(
	userId int(12))
BEGIN
	DELETE FROM `book_penalty` WHERE user_id = userId;
END //

CREATE EVENT `overdueReservedBooks`
	ON SCHEDULE EVERY 1 DAY
    STARTS CONCAT(DATE(NOW()+INTERVAL 1 DAY ), ' 00:10:00')
    ON COMPLETION PRESERVE
DO
	DELETE FROM `reserved` WHERE due_date <= CURDATE()
//

CREATE EVENT `overdueBorrowedBooks`
	ON SCHEDULE EVERY 1 DAY
    STARTS CONCAT(DATE(NOW()+INTERVAL 1 DAY ), ' 00:20:00')
    ON COMPLETION PRESERVE
DO
	BEGIN
		INSERT INTO `book_penalty` (`book_id`, `user_id`, `due_date`) 
			SELECT `book_id`, `user_id`, `due_date` 
            FROM borrowed
            WHERE due_date <= CURDATE();
    END
//
DELIMITER ;

INSERT INTO `user` (`username`, `password`, `email`, `enable`, `first_name`, `last_name`) VALUES

	("admin", "$2a$10$TDb0af7bDK9Gr4xAPoqZqunjFVLCM6togtL556J.M4BEJeUZd7psu", "edard.stark@winterfell.com", TRUE, "Edard", "Stark"),
    ("book", "$2a$10$ZsA1jIBWe/GcMWgKRSwIt.wcFKUH86O1t63JsJ/.JCKlT3iJK/n0a", "sam.tarly@night-watch.com", TRUE, "Sam", "Tarly"),
    ("test", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje", "cersai.lanister@kings-landing.com", TRUE, "Cersai", "Lanister"),
    ("rob", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","rob.stark@winterfell.com",TRUE, "Rob", "Stark"),
    ("lyanna", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","lyanna.stark@winterfell.com",TRUE, "Lyanna", "Stark"),
    ("ramsay", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","ramsay.boltonk@winterfell.com",TRUE, "Ramsay", "Bolton"),
    ("roose", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","roose.bolton@winterfell.com",TRUE, "Roose", "Bolton"),
    ("walder", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","walder.frey@crossing.com",TRUE, "Walder", "Frey"),
    ("theon", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","theon.greyjoy@pyke.com",TRUE, "Theon", "Greyjoy"),
    ("balon", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","balon.greyjoy@pyke.com",FALSE, "Balon", "Greyjoy"),
    ("yara", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","yara.greyjoy@pyke.com",TRUE, "Yara", "Greyjoy"),
    ("euron", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","euron.greyjoy@pyke.com",FALSE, "Euron", "Greyjoy"),
    ("jon", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","jon.aryyn@vale.com",TRUE, "Jon", "Arryn"),
    ("lysa", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","lysa.aryyn@vale.com",TRUE, "Lysa", "Arryn"),
    ("robin", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","robin.aryyn@vale.com",TRUE, "Robin", "Arryn"),
    ("petyr", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","petyr.bealish@vale.com",TRUE, "Petyr", "Baelish"),
    ("daenerys", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","denerys.targaryen@kings-landing.com",TRUE, "Daenerys", "Targaryen"),
    ("varys", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","varys@westeros.com",TRUE, "Varys", "Spider"),
    ("oberyn", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","oberyn.martell@dorne.com",TRUE, "Oberyn", "Martell"),
    ("doran", "$2a$10$jLmONIhEVld8Jftq4sXrbook_logs1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","doran.martell@dorne.com",TRUE, "Doran", "Martell"),
    ("obara", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","obara.sand@dorne.com",TRUE, "Obara", "Sand"),
    ("nymeria", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","nymeria.sand@dorne.com",TRUE, "Nymeria", "Sand"),
    ("catelyn", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","catelyn.tully@riverrun.com",TRUE, "Catelyn", "Tully"),
    ("brynden", "$2a$10$jLmONIhEVld8Jftq4sXr1u/s66eU.Bw9I6DVeaJpFrnYS2Z2Aecje","brynden.tully@riverrun.com",FALSE, "Brynden", "Tully")
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

INSERT INTO `isbn` VALUES (1,'152479712X','9781524797126');
INSERT INTO `isbn` VALUES (2,'0345341465','9780345341464');
INSERT INTO `isbn` VALUES (3,'0062484249','9780062484246');
INSERT INTO `isbn` VALUES (4,'034554286X','9780345542861');
INSERT INTO `isbn` VALUES (5,'1101965509','9781101965504');
INSERT INTO `isbn` VALUES (6,'0553897845','9780553897845');
INSERT INTO `isbn` VALUES (7,'0553897853','9780553897852');
INSERT INTO `isbn` VALUES (8,'055389787X','9780553897876');
INSERT INTO `isbn` VALUES (9,'0553900323','9780553900323');
INSERT INTO `isbn` VALUES (10,'0553905651','9780553905656');
INSERT INTO `isbn` VALUES (11,'1781100217','9781781100219');
INSERT INTO `isbn` VALUES (12,'1781100225','9781781100226');
INSERT INTO `isbn` VALUES (13,'1781100233','9781781100233');
INSERT INTO `isbn` VALUES (14,'1781100527','9781781100523');
INSERT INTO `isbn` VALUES (15,'1781100241','9781781100240');
INSERT INTO `isbn` VALUES (16,'178110025X','9781781100257');
INSERT INTO `isbn` VALUES (17,'1781100268','9781781100264');
INSERT INTO `isbn` VALUES (18,'0137081898','9780137081899');
INSERT INTO `isbn` VALUES (19,'0136083250','9780136083252');
INSERT INTO `isbn` VALUES (20,'0840054440','9780840054449');
INSERT INTO `isbn` VALUES (21,'0199543372','9780199543373');
INSERT INTO `isbn` VALUES (22,'0805371710','9780805371710');
INSERT INTO `isbn` VALUES (23,'3642289800','9783642289804');
INSERT INTO `isbn` VALUES (24,'1743605838','9781743605837');
INSERT INTO `isbn` VALUES (25,'1743609523','9781743609521');
INSERT INTO `isbn` VALUES (26,'1787012050','9781787012059');
INSERT INTO `isbn` VALUES (27,'1787012069','9781787012066');
INSERT INTO `isbn` VALUES (28,'1743605927','9781743605929');

INSERT INTO `book` VALUES (1,'fd80DwAAQBAJ','The Last Jedi: Expanded Edition (Star Wars)','Random House Publishing Group','2018-03-06',1,336,4.0,'http://books.google.com/books/content?id=fd80DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE715VeyYyflptUQ_9F9pjeTL_H6lA7TMWVLA-KsKqtcRg6LuGMl-ljNVQz9PY5yjLuDuYRvVBvEniesAwm1Ys2US9h0Cj43epLyoF3Buxw9ewq7_6k6Ad9i2tPq_od6uBYTiG17a&source=gbs_api','<b><i>NEW YORK TIMES </i>BESTSELLER • Written with input from director Rian Johnson, this official adaptation of <i>Star Wars: The Last Jedi </i>expands on the film to include scenes from alternate versions of the script and other additional content.</b><br> <b> </b><br> From the ashes of the Empire has arisen another threat to the galaxy’s freedom: the ruthless First Order. Fortunately, new heroes have emerged to take up arms—and perhaps lay down their lives—for the cause. Rey, the orphan strong in the Force; Finn, the ex-stormtrooper who stands against his former masters; and Poe Dameron, the fearless X-wing pilot, have been drawn together to fight side-by-side with General Leia Organa and the Resistance. But the First Order’s Supreme Leader Snoke and his merciless enforcer Kylo Ren are adversaries with superior numbers and devastating firepower at their command. Against this enemy, the champions of light may finally be facing their extinction. Their only hope rests with a lost legend: Jedi Master Luke Skywalker.<br>  <br> Where the action of <i>Star Wars: The Force Awakens</i> ended, <i>Star Wars: The Last Jedi </i>begins, as the battle between light and dark climbs to astonishing new heights.<br>  <br><b>Featuring thrilling photos from the hit movie</b>');
INSERT INTO `book` VALUES (2,'uAPTCwAAQBAJ','Star Wars','Ballantine Books','1986',2,224,4.0,'http://books.google.com/books/content?id=uAPTCwAAQBAJ&printsec=frontcover&img=1&zoom=1&imgtk=AFLRE70Mn9lPkGPiE9l-5rYPRI6hZvS0q9r_1bmJmaczbNa2voo2SA9yb-tKZXIBbQM109VktC_1F5m6EV8B-5oyd07iN0-jBwSsOggBObY0xdW2BHsDgwZnKHGhttBP4UDo6mCzLg2c&source=gbs_api','Luke Skywalker was a twenty-year-old who lived and worked on his uncle\'s farm on the remote planet of Tatooine...and he was bored beyond belief. He yearned for adventures that would take him beyond the farthest galaxies. But he got much more than he bargained for....');
INSERT INTO `book` VALUES (3,'a_7_CgAAQBAJ','The World According to Star Wars','HarperCollins','2016-05-31',3,200,3.5,'http://books.google.com/books/content?id=a_7_CgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE72mD8iMlE_DGM3njjutGjwdN0xmjHd1iRhQ9J5RpElWf2wyoSAURMcG6kXbihiE6bigSGw8vxT1DgAd6_s_1bVTY-1x_TLjYUrgHJsm3LrVf4HPYrXoS_JdibgeoIPZ7IkWALeQ&source=gbs_api','<p>NEW YORK TIMES BESTSELLER</p><p>#1 Washington Post Bestseller </p><p>There’s Santa Claus, Shakespeare, Mickey Mouse, the Bible, and then there’s Star Wars. Nothing quite compares to sitting down with a young child and hearing the sound of John Williams’s score as those beloved golden letters fill the screen. In this fun, erudite, and often moving book, Cass R. Sunstein explores the lessons of Star Wars as they relate to childhood, fathers, the Dark Side, rebellion, and redemption. As it turns out, Star Wars also has a lot to teach us about constitutional law, economics, and political uprisings.</p><p>In rich detail, Sunstein tells the story of the films’ wildly unanticipated success and explores why some things succeed while others fail. Ultimately, Sunstein argues, Star Wars is about freedom of choice and our never-ending ability to make the right decision when the chips are down. Written with buoyant prose and considerable heart, The World According to Star Wars shines a bright new light on the most beloved story of our time.</p>');
INSERT INTO `book` VALUES (4,'2UoPAAAAQBAJ','The Making of Star Wars (Enhanced Edition)','Ballantine Group','2013-10-22',4,324,0.0,'http://books.google.com/books/content?id=2UoPAAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE72kKQtXMA2JgrwoHGfnkmL9NH_fqn5StzkovaDMbsWCXymSOUv6TuszujQl1f9qNcGok9fKXS85peGSTHIRXWKy3Rz_xQj7N7m6Sh2V6KsNAe-66YsTyu8bIrEFLSbodFx9qOU1&source=gbs_api','<b><b>This enhanced eBook transforms <i>The Making of Star Wars</i> into an immersive multimedia experience worthy of the original film. It features exclusive content pulled from the Lucasfilm archives by author J. W. Rinzler:</b><br> <b> </b><br> <b>• 26 minutes of rare behind-the-scenes video*</b><br> <b>• 29 minutes of rare audio interviews with the cast and crew</b><br> <b>• New bonus photos and artwork not found in the print edition</b><br></b> <b> </b><br> After the 1973 success of <i>American Graffiti, </i>filmmaker George Lucas made the fateful decision to pursue a longtime dream project: a space fantasy movie unlike any ever produced. Lucas envisioned a swashbuckling SF saga inspired by the Flash Gordon serials, classic American westerns, the epic cinema of Japanese auteur Akira Kurosawa, and mythological heroes. Its original title: <i>The Star Wars</i>. The rest is history, and how it was made is a story as entertaining and exciting as the movie that has enthralled millions for more than thirty years—a story that has never been told as it was meant to be. Until now.<br>  <br> Using his unprecedented access to the Lucasfilm Archives and its trove of “lost” interviews, photos, production notes, factoids, and anecdotes, <i>Star Wars</i> scholar J. W. Rinzler hurtles readers back in time for a one-of-a-kind behind-the-scenes look at the nearly decade-long quest of George Lucas and his key collaborators to make the “little” movie that became a phenomenon. It’s all here:<br>  <br> • the evolution of the now-classic story and characters—including “Annikin Starkiller” and “a huge green-skinned monster with no nose and large gills” named Han Solo<br> • excerpts from George Lucas’s numerous, ever-morphing script drafts<br> • the birth of Industrial Light & Magic, the special-effects company that revolutionized Hollywood filmmaking<br> • the studio-hopping and budget battles that nearly scuttled the entire project<br> • the director’s early casting saga, which might have led to a film spoken mostly in Japanese—including the intensive auditions that won the cast members their roles and made them legends <br> • the grueling, nearly catastrophic location shoot in Tunisia and the subsequent breakneck dash at Elstree Studios in London<br> • the who’s who of young film rebels who pitched in to help—including Francis Ford Coppola, Steven Spielberg, and Brian DePalma<br>  <br> But perhaps most exciting, and rarest of all, are the interviews conducted before and during production and immediately after the release of <i>Star Wars</i>—in which George Lucas, Mark Hamill, Harrison Ford, Carrie Fisher, Sir Alec Guinness, Anthony Daniels, composer John Williams, effects masters Dennis Muren, Richard Edlund, and John Dykstra, Phil Tippett, Rick Baker, legendary production designer John Barry, and a host of others share their fascinating tales from the trenches and candid opinions of the film that would ultimately change their lives.<br>  <br> No matter how you view the spectrum of this phenomenon, <i>The Making of Star Wars</i> stands as a crucial document—rich in fascination and revelation—of a genuine cinematic and cultural touchstone.<br><br><b>*Video may not play on all readers. Please check your user manual for details.</b>');
INSERT INTO `book` VALUES (5,'heJhCAAAQBAJ','The Force Awakens (Star Wars)','Random House Publishing Group','2015-12-18',5,272,3.0,'http://books.google.com/books/content?id=heJhCAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE72iY-QkPKEsNS4YljT4UCPQNeP9ajMJYChLUMhQ48aStsEIsaHb3cl0HeDjTyuDs9zjeLknmjUX25huUttbWZv4A5qsnzQLpW3zWcRoAAUWzVSEXJNTF15-rmQn4A6aiz5kzyJt&source=gbs_api','<b>#1 <i>NEW YORK TIMES</i> BESTSELLER • The official novelization of <i>Star Wars: The Force Awakens,</i> the blockbuster film directed by J. J. Abrams • <b>Includes two tie-in short stories: “The Perfect Weapon” by Delilah S. Dawson and “Bait” by Alan Dean Foster</b></b><br> <b> </b><br> More than thirty years ago, <i>Star Wars</i> burst onto the big screen and became a cultural phenomenon. Now the next adventures in this blockbuster saga are poised to captivate old and new fans alike—beginning with the highly anticipated <i>Star Wars: The Force Awakens</i>. And alongside the cinematic debut comes the thrilling novel adaptation by <i>New York Times</i> bestselling science fiction master Alan Dean Foster.<br>  <br> Set years after <i>Return of the Jedi, </i>this stunning new action-packed adventure rockets us back into the world of Princess Leia, Han Solo, Chewbacca, C-3PO, R2-D2, and Luke Skywalker, while introducing a host of exciting new characters. Darth Vader may have been redeemed and the Emperor vanquished, but peace can be fleeting, and evil does not easily relent. Yet the simple belief in good can still empower ordinary individuals to rise and meet the greatest challenges.<br>  <br> So return to that galaxy far, far away, and prepare yourself for what happens when the Force awakens. . . .<br><br><b>Praise for <i>Star Wars: The Force Awakens</i></b><br> <b><i> </i></b><br> “Like all the best novelizations, Alan Dean Foster’s adaptation of <i>Star Wars: The Force Awakens</i> enriches the movie experience. The novel goes beyond simply giving us insight into the characters’ thoughts, with plenty of additional scenes painting a broader picture of the galaxy.”<b>—New York <i>Daily News</i></b><br> <b><i> </i></b><br> “Fast-moving, atmospheric and raises goose-bumps at just the right moments. [Foster] not only evokes entire onscreen worlds . . . he also gives us glimpses of an even more vast, unseen universe.”<b>—<i>The Washington Post</i></b><br> <b><i> </i></b><br> “Was my experience of the film enriched by the book? Yes. No question. Is the novelization worth reading? Yes. . . . Foster has written a book that captures the spirit of the film, while presenting additional information that helps answer some of the questions that linger.”<b>—<i>Coffee with Kenobi</i></b><br><br><br><i>From the Paperback edition.</i>');
INSERT INTO `book` VALUES (6,'5NomkK4EV68C','A Game of Thrones','Random House Publishing Group','2003-01-01',6,720,4.0,'http://books.google.com/books/content?id=5NomkK4EV68C&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE72NMsHhHG15TMsLXMP_WEUjVBNIr6BQ7m8dvfjDiT6wLCw2QcIXGE4nQdaKFJnWgu2INjLeAxmWg5hnq5GyKW7b-u0mtGnGIdc2GWdBa-LSp118g0Ug4H-8OKFS1qXSOtQxxcZc&source=gbs_api','<b>NOW THE ACCLAIMED HBO SERIES <i>GAME OF THRONES</i></b><br><br><b>Nominated as one of America’s best-loved novels by PBS’s <i>The Great American Read</i></b><br><br>From a master of contemporary fantasy comes the first novel of a landmark series unlike any you’ve ever read before. With <i>A Game of Thrones, </i>George R. R. Martin has launched a genuine masterpiece, bringing together the best the genre has to offer. Mystery, intrigue, romance, and adventure fill the pages of this magnificent saga, the first volume in an epic series sure to delight fantasy fans everywhere.<br><b> </b><br><b>A GAME OF THRONES</b><br><b>A SONG OF ICE AND FIRE: BOOK ONE</b><br> <br>Long ago, in a time forgotten, a preternatural event threw the seasons out of balance. In a land where summers can last decades and winters a lifetime, trouble is brewing. The cold is returning, and in the frozen wastes to the north of Winterfell, sinister forces are massing beyond the kingdom’s protective Wall. To the south, the king’s powers are failing—his most trusted adviser dead under mysterious circumstances and his enemies emerging from the shadows of the throne. At the center of the conflict lie the Starks of Winterfell, a family as harsh and unyielding as the frozen land they were born to. Now Lord Eddard Stark is reluctantly summoned to serve as the king’s new Hand, an appointment that threatens to sunder not only his family but the kingdom itself.<br><br>Sweeping from a harsh land of cold to a summertime kingdom of epicurean plenty, <i>A Game of Thrones</i> tells a tale of lords and ladies, soldiers and sorcerers, assassins and bastards, who come together in a time of grim omens. Here an enigmatic band of warriors bear swords of no human metal; a tribe of fierce wildlings carry men off into madness; a cruel young dragon prince barters his sister to win back his throne; a child is lost in the twilight between life and death; and a determined woman undertakes a treacherous journey to protect all she holds dear. Amid plots and counter-plots, tragedy and betrayal, victory and terror, allies and enemies, the fate of the Starks hangs perilously in the balance, as each side endeavors to win that deadliest of conflicts: the game of thrones.<br><br>Unparalleled in scope and execution, <i>A Game of Thrones</i> is one of those rare reading experiences that catch you up from the opening pages, won’t let you go until the end, and leave you yearning for more.');
INSERT INTO `book` VALUES (7,'ZfiREZrremoC','A Clash of Kings','Random House Publishing Group','2003-01-01',7,784,4.0,'http://books.google.com/books/content?id=ZfiREZrremoC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE73itsPjAhQd7wuCkalW-IFJTbrCpCwfVjlA0b8Xvr2udZy2BXxGfItxBqjUN1PykJtb6BZZbywmFE17elDACcvvev4aYyVA0538jiiZ0B81s6NqYfVDY0SOGMt-73TwaAzMpcBT&source=gbs_api','<b><b>THE BOOK BEHIND THE SECOND SEASON OF GAME OF THRONES, AN ORIGINAL SERIES NOW ON HBO.</b><br><br></b> A SONG OF ICE AND FIRE: BOOK TWO<br> <b> </b><br> In this thrilling sequel to <i>A Game of Thrones, </i>George R. R. Martin has created a work of unsurpassed vision, power, and imagination. <i>A Clash of Kings </i>transports us to a world of revelry and revenge, wizardry and warfare unlike any we have ever experienced.<br>  <br> A comet the color of blood and flame cuts across the sky. And from the ancient citadel of Dragonstone to the forbidding shores of Winterfell, chaos reigns. Six factions struggle for control of a divided land and the Iron Throne of the Seven Kingdoms, preparing to stake their claims through tempest, turmoil, and war. It is a tale in which brother plots against brother and the dead rise to walk in the night. Here a princess masquerades as an orphan boy; a knight of the mind prepares a poison for a treacherous sorceress; and wild men descend from the Mountains of the Moon to ravage the countryside. Against a backdrop of incest and fratricide, alchemy and murder, victory may go to the men and women possessed of the coldest steel . . . and the coldest hearts. For when kings clash, the whole land trembles.');
INSERT INTO `book` VALUES (8,'rIj5x-C7D2cC','A Storm of Swords','Random House Publishing Group','2003-03-04',8,992,4.0,'http://books.google.com/books/content?id=rIj5x-C7D2cC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE70PfLlGksxH3oiNI4Zfv0pElszUZFZBLuSJjADH24qmZPOnmoBiPZQK3u_d4h4-89VMGWc55J6vf5jJalfoByRDrUkxJ6bu95f6odvSEfE_JTzgrN5nz5pSpcjRQHLc1fGyNGu4&source=gbs_api','<b><b>THE BOOK BEHIND THE THIRD SEASON OF GAME OF THRONES, AN ORIGINAL SERIES NOW ON HBO.</b><br></b><br>Here is the third volume in George R. R. Martin’s magnificent cycle of novels that includes <i>A Game of Thrones</i> and <i>A Clash of Kings</i>. As a whole, this series comprises a genuine masterpiece of modern fantasy, bringing together the best the genre has to offer. Magic, mystery, intrigue, romance, and adventure fill these pages and transport us to a world unlike any we have ever experienced. Already hailed as a classic, George R. R. Martin’s stunning series is destined to stand as one of the great achievements of imaginative fiction.<br><br><b>A STORM OF SWORDS<br><br></b>Of the five contenders for power, one is dead, another in disfavor, and still the wars rage as violently as ever, as alliances are made and broken. Joffrey, of House Lannister, sits on the Iron Throne, the uneasy ruler of the land of the Seven Kingdoms. His most bitter rival, Lord Stannis, stands defeated and disgraced, the victim of the jealous sorceress who holds him in her evil thrall. But young Robb, of House Stark, still rules the North from the fortress of Riverrun. Robb plots against his despised Lannister enemies, even as they hold his sister hostage at King’s Landing, the seat of the Iron Throne. Meanwhile, making her way across a blood-drenched continent is the exiled queen, Daenerys, mistress of the only three dragons still left in the world. . . .<br><br>But as opposing forces maneuver for the final titanic showdown, an army of barbaric wildlings arrives from the outermost line of civilization. In their vanguard is a horde of mythical Others--a supernatural army of the living dead whose animated corpses are unstoppable. As the future of the land hangs in the balance, no one will rest until the Seven Kingdoms have exploded in a veritable storm of swords. . . .<br><br><br><i>From the Paperback edition.</i>');
INSERT INTO `book` VALUES (9,'NuMx6tmf5iIC','A Feast for Crows','Random House Publishing Group','2005-11-08',9,784,3.5,'http://books.google.com/books/content?id=NuMx6tmf5iIC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE73dP_oQGwvuWf8ZM86G9cISzU9fitaWAt_Op7UYjJOKf7xkkz4JrM8gwC5Ubf6jrEfXBASz7fI-SCPi8X0WDwn3OU2hrLSfW5iUQXgAO1_TtIvyolCg2GlSyzj9kLK_YNgoraZz&source=gbs_api','<b><b>THE BOOK BEHIND THE FOURTH SEASON OF THE ACCLAIMED HBO SERIES <i>GAME OF THRONES</i></b><br></b><br>Few books have captivated the imagination and won the devotion and praise of readers and critics everywhere as has George R. R. Martin’s monumental epic cycle of high fantasy. Now, in <i>A Feast for Crows</i>, Martin delivers the long-awaited fourth book of his landmark series, as a kingdom torn asunder finds itself at last on the brink of peace . . . only to be launched on an even more terrifying course of destruction.<br><br><b>A FEAST FOR CROWS<br><br></b>It seems too good to be true. After centuries of bitter strife and fatal treachery, the seven powers dividing the land have decimated one another into an uneasy truce. Or so it appears. . . . With the death of the monstrous King Joffrey, Cersei is ruling as regent in King’s Landing. Robb Stark’s demise has broken the back of the Northern rebels, and his siblings are scattered throughout the kingdom like seeds on barren soil. Few legitimate claims to the once desperately sought Iron Throne still exist—or they are held in hands too weak or too distant to wield them effectively. The war, which raged out of control for so long, has burned itself out. <br><br>But as in the aftermath of any climactic struggle, it is not long before the survivors, outlaws, renegades, and carrion eaters start to gather, picking over the bones of the dead and fighting for the spoils of the soon-to-be dead. Now in the Seven Kingdoms, as the human crows assemble over a banquet of ashes, daring new plots and dangerous new alliances are formed, while surprising faces—some familiar, others only just appearing—are seen emerging from an ominous twilight of past struggles and chaos to take up the challenges ahead. <br><br>It is a time when the wise and the ambitious, the deceitful and the strong will acquire the skills, the power, and the magic to survive the stark and terrible times that lie before them. It is a time for nobles and commoners, soldiers and sorcerers, assassins and sages to come together and stake their fortunes . . . and their lives. For at a feast for crows, many are the guests—but only a few are the survivors.<br><br><br><i>From the Hardcover edition.</i>');
INSERT INTO `book` VALUES (10,'7Q4R3RHe8AQC','A Dance with Dragons','Random House Publishing Group','2011-07-12',10,1040,3.5,'http://books.google.com/books/content?id=7Q4R3RHe8AQC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE70E7ghngLwyzjH06O8JLiHQwmJ1nWmgdGYPoqUZeuhu6nZxZvuiMnYspZZU2pcabdEuevVsWIKHlZEDrI0fazN_76fxMJLrAF_BDfG5iLBcRLgOWeug9HATPTOChfZvKchtZY_Y&source=gbs_api','<p><b><b>#1 <i>NEW YORK TIMES </i>BESTSELLER • THE BOOK BEHIND THE FIFTH SEASON OF THE ACCLAIMED HBO SERIES <i>GAME OF THRONES</i></b><br></b><br><b>Don’t miss the thrilling sneak peek of George R. R. Martin’s A Song of Ice and Fire: Book Six, <i>The Winds of Winter</i></b><br><br>Dubbed “the American Tolkien” by <i>Time</i> magazine, George R. R. Martin has earned international acclaim for his monumental cycle of epic fantasy. Now the #1 <i>New York Times</i> bestselling author delivers the fifth book in his landmark series—as both familiar faces and surprising new forces vie for a foothold in a fragmented empire. <br> <br><b>A DANCE WITH DRAGONS</b><br><b>A SONG OF ICE AND FIRE: BOOK FIVE</b><br> <br>In the aftermath of a colossal battle, the future of the Seven Kingdoms hangs in the balance—beset by newly emerging threats from every direction. In the east, Daenerys Targaryen, the last scion of House Targaryen, rules with her three dragons as queen of a city built on dust and death. But Daenerys has thousands of enemies, and many have set out to find her. As they gather, one young man embarks upon his own quest for the queen, with an entirely different goal in mind.<br><br>Fleeing from Westeros with a price on his head, Tyrion Lannister, too, is making his way to Daenerys. But his newest allies in this quest are not the rag-tag band they seem, and at their heart lies one who could undo Daenerys’s claim to Westeros forever.<br><br>Meanwhile, to the north lies the mammoth Wall of ice and stone—a structure only as strong as those guarding it. There, Jon Snow, 998th Lord Commander of the Night’s Watch, will face his greatest challenge. For he has powerful foes not only within the Watch but also beyond, in the land of the creatures of ice.<br><br>From all corners, bitter conflicts reignite, intimate betrayals are perpetrated, and a grand cast of outlaws and priests, soldiers and skinchangers, nobles and slaves, will face seemingly insurmountable obstacles. Some will fail, others will grow in the strength of darkness. But in a time of rising restlessness, the tides of destiny and politics will lead inevitably to the greatest dance of all.<br><br><b>Praise for <i>A Dance with Dragons</i></b><br>  <br> “Filled with vividly rendered set pieces, unexpected turnings, assorted cliffhangers and moments of appalling cruelty, <i>A Dance with Dragons</i> is epic fantasy as it should be written: passionate, compelling, convincingly detailed and thoroughly imagined.”<b>—<i>The Washington Post</i></b><br> <i> </i><br> “Long live George Martin . . . a literary dervish, enthralled by complicated characters and vivid language, and bursting with the wild vision of the very best tale tellers.”<b><i>—The New York Times</i></b><br> <i> </i><br> “One of the best series in the history of fantasy.”<b><i>—Los Angeles Times</i></b> </p><br><br><br><i>From the Paperback edition.</i>');
INSERT INTO `book` VALUES (11,'39iYWTb6n6cC','Harry Potter and the Philosopher\'s Stone','Pottermore','2015-12-08',11,353,4.5,'http://books.google.com/books/content?id=39iYWTb6n6cC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE73RW6DH1YxY9o51P6xKJbKAM2SCdgrfWGak2F3i6bz5bHsJA0Lih41l4kI-Pv2M-Xf41yijnejX2rJCBHqP5Pwbxfo1EQQRGFFm3rU689Log-WXYBGAap6kaHvWkicTv0beEsDk&source=gbs_api','<p>\"Turning the envelope over, his hand trembling, Harry saw a purple wax seal bearing a coat of arms; a lion, an eagle, a badger and a snake surrounding a large letter \'H\'.\"<br><br>Harry Potter has never even heard of Hogwarts when the letters start dropping on the doormat at number four, Privet Drive. Addressed in green ink on yellowish parchment with a purple seal, they are swiftly confiscated by his grisly aunt and uncle. Then, on Harry\'s eleventh birthday, a great beetle-eyed giant of a man called Rubeus Hagrid bursts in with some astonishing news: Harry Potter is a wizard, and he has a place at Hogwarts School of Witchcraft and Wizardry. An incredible adventure is about to begin!</p>');
INSERT INTO `book` VALUES (12,'1o7D0m_osFEC','Harry Potter and the Chamber of Secrets','Pottermore','2015-12-08',12,357,4.5,'http://books.google.com/books/content?id=1o7D0m_osFEC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE73SrJyyJ_tCdMWD-vwiFXFvgJQmWLg7TYqxyvqlB4mWG5TvF9FNQogllD8V-V5I4B2sv6EgcVGv0dMX88J9ioshm8_sKGzKzuRuSewn8kxOhQx5UuxcrZArQ7KUDhC9IgM-kzwI&source=gbs_api','<p>\"\'There is a plot, Harry Potter. A plot to make most terrible things happen at Hogwarts School of Witchcraft and Wizardry this year.\'\" <br><br>Harry Potter\'s summer has included the worst birthday ever, doomy warnings from a house-elf called Dobby, and rescue from the Dursleys by his friend Ron Weasley in a magical flying car! Back at Hogwarts School of Witchcraft and Wizardry for his second year, Harry hears strange whispers echo through empty corridors - and then the attacks start. Students are found as though turned to stone... Dobby\'s sinister predictions seem to be coming true.</p>');
INSERT INTO `book` VALUES (13,'wHlDzHnt6x0C','Harry Potter and the Prisoner of Azkaban','Pottermore','2015-12-08',13,435,4.5,'http://books.google.com/books/content?id=wHlDzHnt6x0C&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE70R9q086hA-TQOG8Nidrz97Y_QGHNxdouLhYu2QMS9zrL8QBWKoR7lafi4si_FCblIk2XGHF_idG4mn4YE0Iw8ifptHKDyp1_wNddQgNHepbOvwPFGkKxWj4cip6a4LoTusOAMB&source=gbs_api','<p>\"\'Welcome to the Knight Bus, emergency transport for the stranded witch or wizard. Just stick out your wand hand, step on board and we can take you anywhere you want to go.\'\"<br><br>When the Knight Bus crashes through the darkness and screeches to a halt in front of him, it\'s the start of another far from ordinary year at Hogwarts for Harry Potter. Sirius Black, escaped mass-murderer and follower of Lord Voldemort, is on the run - and they say he is coming after Harry. In his first ever Divination class, Professor Trelawney sees an omen of death in Harry\'s tea leaves... But perhaps most terrifying of all are the Dementors patrolling the school grounds, with their soul-sucking kiss...</p>');
INSERT INTO `book` VALUES (14,'etukl7GfrxQC','Harry Potter and the Goblet of Fire','Pottermore','2015-12-08',14,752,4.5,'http://books.google.com/books/content?id=etukl7GfrxQC&printsec=frontcover&img=1&zoom=1&imgtk=AFLRE71hezJ_PSLKtV5ZEn32vgEvBUdDiIBeR1w0wh-auElQvZZDSkyrgh-FqJtfsezTNenWhEcRyxdI1y4Q8YkVjjxDF8VcZ967a5TO7r9qVnbBEv6uOKngzC98XpJZXvfUrQu-TYtr&source=gbs_api','<p>\"\'There will be three tasks, spaced throughout the school year, and they will test the champions in many different ways ... their magical prowess - their daring - their powers of deduction - and, of course, their ability to cope with danger.\'\"<br><br>The Triwizard Tournament is to be held at Hogwarts. Only wizards who are over seventeen are allowed to enter - but that doesn\'t stop Harry dreaming that he will win the competition. Then at Hallowe\'en, when the Goblet of Fire makes its selection, Harry is amazed to find his name is one of those that the magical cup picks out. He will face death-defying tasks, dragons and Dark wizards, but with the help of his best friends, Ron and Hermione, he might just make it through - alive!</p>');
INSERT INTO `book` VALUES (15,'jk87_y-ubE0C','Harry Potter and the Order of the Phoenix','Pottermore','2015-12-08',15,901,4.0,'http://books.google.com/books/content?id=jk87_y-ubE0C&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE70WWggfR-aYBonjaSqwDiH0VGzrCAYOuW-tgg8FRjA9DNoP1bCspDgw_rIAFpUoXlzX50BSkhE19SDS5mTzR6OX4wuoY-HSm-qxH6GIgV-9oryDiGpQLxjvpymRBw43Y8pFnGga&source=gbs_api','<p>\"\'You are sharing the Dark Lord\'s thoughts and emotions. The Headmaster thinks it inadvisable for this to continue. He wishes me to teach you how to close your mind to the Dark Lord.\'\"<br><br>Dark times have come to Hogwarts. After the Dementors\' attack on his cousin Dudley, Harry Potter knows that Voldemort will stop at nothing to find him. There are many who deny the Dark Lord\'s return, but Harry is not alone: a secret order gathers at Grimmauld Place to fight against the Dark forces. Harry must allow Professor Snape to teach him how to protect himself from Voldemort\'s savage assaults on his mind. But they are growing stronger by the day and Harry is running out of time...</p>');
INSERT INTO `book` VALUES (16,'qaKkenvL29UC','Harry Potter and the Half-Blood Prince','Pottermore','2015-12-08',16,672,4.5,'http://books.google.com/books/content?id=qaKkenvL29UC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE72dJSLvKmAugZfyV-XIQCHEJThu_NB62D2FZ332ddE5qfCfQLBgnlQ2ovrp5scBa0Q2LB-tHvPeL32sb_iart_q24z2p3A3EadOIeEbYlSFzHz8MjANm4vkirzFFtQZ3t0Nu0dN&source=gbs_api','<p>\"There it was, hanging in the sky above the school: the blazing green skull with a serpent tongue, the mark Death Eaters left behind whenever they had entered a building... wherever they had murdered...\"<br><br>When Dumbledore arrives at Privet Drive one summer night to collect Harry Potter, his wand hand is blackened and shrivelled, but he does not reveal why. Secrets and suspicion are spreading through the wizarding world, and Hogwarts itself is not safe. Harry is convinced that Malfoy bears the Dark Mark: there is a Death Eater amongst them. Harry will need powerful magic and true friends as he explores Voldemort\'s darkest secrets, and Dumbledore prepares him to face his destiny...</p>');
INSERT INTO `book` VALUES (17,'gCtazG4ZXlQC','Harry Potter and the Deathly Hallows','Pottermore','2015-12-08',17,784,4.5,'http://books.google.com/books/content?id=gCtazG4ZXlQC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE73hRFc-FMbN7oR8P7RWos6ccCS-0Ey_vDiwLZkYSY1PDBm1qAhjeHCEjjLd1dICgA6EkvjhTQZVzPB71WG_eJHurdHN7TSC15LpUOlQeBMSS0PjXNqjKi4hEViVPNzzKWnHVDtf&source=gbs_api','<p>\"\'Give me Harry Potter,\' said Voldemort\'s voice, \'and none shall be harmed. Give me Harry Potter, and I shall leave the school untouched. Give me Harry Potter, and you will be rewarded.\'\"<br><br>As he climbs into the sidecar of Hagrid\'s motorbike and takes to the skies, leaving Privet Drive for the last time, Harry Potter knows that Lord Voldemort and the Death Eaters are not far behind. The protective charm that has kept Harry safe until now is broken, but he cannot keep hiding. The Dark Lord is breathing fear into everything Harry loves and to stop him Harry will have to find and destroy the remaining Horcruxes. The final battle must begin - Harry must stand and face his enemy...</p>');
INSERT INTO `book` VALUES (18,'QTZvAQAAQBAJ','Core Java','Prentice Hall','2013',18,974,0.0,'http://books.google.com/books/content?id=QTZvAQAAQBAJ&printsec=frontcover&img=1&zoom=1&imgtk=AFLRE73-MBslKVn4_x2ktYNRf5iBKZnGT39AYobh_0LdRwfK_1yU0XCdXXojVweugrMaFwkKL-PhsUgBRlokBY7pTcb7vLnDrgxZ0qO6QJI_oHmiu7DK-ArIABnt9s6BeSLYfhLw9feN&source=gbs_api','<p>Fully updated to reflect Java SE 7 language changes, <b> <i> <b>Core Java™, Volume I—Fundamentals, Ninth Edition,</b> </i> </b> is the definitive guide to the Java platform. </p> <p> </p> <p>Designed for serious programmers, this reliable, unbiased, no-nonsense tutorial illuminates key Java language and library features with thoroughly tested code examples. As in previous editions, all code is easy to understand, reflects modern best practices, and is specifically designed to help jumpstart your projects. </p> <p> </p> <p>Volume I quickly brings you up-to-speed on Java SE 7 core language enhancements, including the diamond operator, improved resource handling, and catching of multiple exceptions. All of the code examples have been updated to reflect these enhancements, and complete descriptions of new SE 7 features are integrated with insightful explanations of fundamental Java concepts. You\'ll learn all you need to be productive with </p> <ul> <li> The Java programming environment </li> <li> Objects, classes, and inheritance </li> <li> Interfaces and inner classes </li> <li> Reflection and proxies </li> <li> Graphics programming </li> <li> Event handling and the event listener model </li> <li> Swing-based user interface components </li> <li> Application and applet deployment </li> <li> Exceptions, logging, assertions, and debugging </li> <li> Generic programming </li> <li> Collections </li> <li> Concurrency, and more </li> </ul> <p>For detailed coverage of advanced features, including the new API for file input/output and enhancements to the concurrency utilities, look for <i>Core Java™, Volume II—Advanced Features, Ninth Edition</i> (ISBN-13: 978-0-13-708160-8).</p>');
INSERT INTO `book` VALUES (19,'_i6bDeoCQzsC','Clean Code','Pearson Education','2008-08-01',19,464,4.5,'http://books.google.com/books/content?id=_i6bDeoCQzsC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE728-v80PlIPgpdaI4PtVvAEo1JSq-y-aiZmCOeBkqlUcRctM53Cpumc-q84Dkw3iELYIx-msIfidyzT8oRiVOSDpMo2nnWLfl1MQsj7kpExUBz7EOkGHp9hQs1qCy68Mm3Mk12c&source=gbs_api','Even bad code can function. But if code isn’t clean, it can bring a development organization to its knees. Every year, countless hours and significant resources are lost because of poorly written code. But it doesn’t have to be that way.<br> <br>Noted software expert Robert C. Martin presents a revolutionary paradigm with <i> <b>Clean Code: A Handbook of Agile Software Craftsmanship</b> </i>. Martin has teamed up with his colleagues from Object Mentor to distill their best agile practice of cleaning code “on the fly” into a book that will instill within you the values of a software craftsman and make you a better programmer–but only if you work at it.<br> <br>What kind of work will you be doing? You’ll be reading code–lots of code. And you will be challenged to think about what’s right about that code, and what’s wrong with it. More importantly, you will be challenged to reassess your professional values and your commitment to your craft.<br> <br> <i> <b>Clean Code</b> </i> is divided into three parts. The first describes the principles, patterns, and practices of writing clean code. The second part consists of several case studies of increasing complexity. Each case study is an exercise in cleaning up code–of transforming a code base that has some problems into one that is sound and efficient. The third part is the payoff: a single chapter containing a list of heuristics and “smells” gathered while creating the case studies. The result is a knowledge base that describes the way we think when we write, read, and clean code.<br> <br>Readers will come away from this book understanding<br> <ul> <li>How to tell the difference between good and bad code <li>How to write good code and how to transform bad code into good code <li>How to create good names, good functions, good objects, and good classes <li>How to format code for maximum readability <li>How to implement complete error handling without obscuring code logic <li>How to unit test and practice test-driven development</li> </ul>This book is a must for any developer, software engineer, project manager, team lead, or systems analyst with an interest in producing better code.<br>');
INSERT INTO `book` VALUES (20,'MWOWtgEACAAJ','Organic Chemistry','Cengage Learning','2011-01-01',20,1376,0.0,'http://books.google.com/books/content?id=MWOWtgEACAAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE73nJ9DyK9gsn58POdQf3gZrGi-MiGRgbU3LrUNrEWFO9hpzcvGf0hPGaWfTh9UrHEEP0bw24eNd4FjMh8q9z8WUZnXu7vVsTYRJT_OKsNa3_6NCiPYHS9UpLqwM5J9RMipc4d9J&source=gbs_api','The most trusted and best-selling text for organic chemistry just got better! Updated with the latest developments, expanded with more end-of-chapter problems, reorganized to cover stereochemistry earlier, and enhanced with OWL, the leading online homework and learning system for chemistry, John McMurry\'s ORGANIC CHEMISTRY continues to set the standard for the course. The Eighth Edition also retains McMurry\'s hallmark qualities: comprehensive, authoritative, and clear. McMurry has developed a reputation for crafting precise and accessible texts that speak to the needs of instructors and students. More than a million students worldwide from a full range of universities have mastered organic chemistry through his trademark style, while instructors at hundreds of colleges and universities have praised his approach time and time again.<br><b>Important Notice: Media content referenced within the product description or the product text may not be available in the ebook version. </b></br>');
INSERT INTO `book` VALUES (21,'BV6cAQAAQBAJ','Atkins\' Physical Chemistry','OUP Oxford','2010',21,972,3.5,'http://books.google.com/books/content?id=BV6cAQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE718xNApKg1YIx_90u3E88aBVsTx6e33OKWM_FoIskI2F6ujesYF5N1OcIJnEkU1LkkzHmRpB5XxTAVh0v6vjVNa3uBKRANS2bSIPaFTTr2FWqbuUcLfMmNBpS889akmS109qR7q&source=gbs_api','Atkins\' Physical Chemistry epitomises the benchmark of achievement for a chemistry degree throughout the world. Its broad coverage, concise explanations, and robust mathematical support are clearly presented in an engaging style to furnish students with a solid foundation in the subject. In this ninth edition the authors continue to refine their presentation of physical chemistry. The coverage of introductory topics is streamlined, and the addition of a new fundamentals chapter provides students with an overview of key principles on which the subject is based. The text includes increased coverage of computational chemistry and additional contextual examples of materials chemistry throughout, mirroring the current needs of today\'s students and lecturers. Mathematics remains an intrinsic yet challenging part of physical chemistry. Extensive mathematical support, including a \'Checklist of key equations\' at the end of every chapter, and \'Mathematical background\' sections containing worked examples and self-tests, empower students to overcome any barriers to understanding that grasping the mathematical content might present. These features help to ensure the reader can master the subject without a need to sacrifice the rigour and depth of the mathematical content. The pedagogical framework, which is a hallmark of the authors\' writing, has been further strengthened. New \'Key points\' provide summaries of the main take-home messages of each section and enable students to gain an overview of the topic before tackling it in depth; \'Brief illustrations\' give a concise insight into how a particular mathematical concept is applied in practice, providing students with the opportunity to contextualise their learning. All these exciting new features and innovations are presented within a refreshed full colour text design, to stimulate and engage students yet further. The Online Resource Centre contains Living Graphs, illustrations from the book, and web links. An Instructor\'s Solutions Manual (free to adopters) and a Student\'s Solutions Manual are also available. Access to the Physical Chemistry eBook is also included with the purchase of the printed text. Offering enhanced functionality, including notetaking and highlighting, it also includes access to \'Explorations in Physical Chemistry\', which contains interactive Excel worksheets and exercises related to the Living Graphs, allowing students to visualise, actively explore and test their understanding of the subject.');
INSERT INTO `book` VALUES (22,'0jbm8J7VW0AC','Biology','Pearson, Benjamin Cummings','2005-01-01',22,1231,5.0,'http://books.google.com/books/content?id=0jbm8J7VW0AC&printsec=frontcover&img=1&zoom=1&imgtk=AFLRE71N5J3jwQHHKlmGcXzcxhQrvWYKf-U2Gs3_lCqNh-ZIzAPCuGuXJu6pnuvbxC3u7dPX_VBUV_88ASwxB3QGdh_YP2ey7xDQoTEyu7_JBYn0NSMAvBD8frBiKqH8DsBVqIHIzQ4W&source=gbs_api','Neil Campbell and Jane Reece\'s BIOLOGY remains unsurpassed as the most successful majors biology textbook in the world. This text has invited more than 4 million students into the study of this dynamic and essential discipline.The authors have restructured each chapter around a conceptual framework of five or six big ideas. An Overview draws students in and sets the stage for the rest of the chapter, each numbered Concept Head announces the beginning of a new concept, and Concept Check questions at the end of each chapter encourage students to assess their mastery of a given concept.&New Inquiry Figures focus students on the experimental process, and new Research Method Figures illustrate important techniques in biology. Each chapter ends with a Scientific Inquiry Question that asks students to apply scientific investigation skills to the content of the chapter.');
INSERT INTO `book` VALUES (23,'ASb2fYwj9ZsC','Polymer Synthesis: Theory and Practice','Springer Science & Business Media','2012-12-13',23,404,0.0,'http://books.google.com/books/content?id=ASb2fYwj9ZsC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE706QitSnerPekPI0Z5etCnO5ANvADosudUMtVWXfFfg6n_8-1FYgihGiyWYljWLnG4ASCaU1CZyCJ4pkl7SUcKrlOSBBpcQtoYBi0ABnh7vE_OZLhAP6ZjO1LSzvHxmIBEukple&source=gbs_api','<p>Emphasis is on a broad description of the general methods and processes for the synthesis, modification and characterization of macromolecules. These more fundamental chapters will be supplemented by selected and detailed experiments. In addition to the preparative aspects, the book also gives the reader an impression on the relation of chemical constitution and morphology of Polymers to their properties, as well as on their application areas. Thus, an additional textbook will not be needed in order to understand the experiments. </p><p>The 5th edition contains numerous changes: In recent years, so-called functional polymers which have special electrical, electronic, optical and biological properties, have gained more and more in interest. This textbook was therefore supplemented by recipes which describe the synthesis of these materials in a new chapter \"Functional polymers\". Together with new experiments in chapter 3,4 and 5 the book now contains more than 120 recipes that describe a wide range of macromolecules. </p><p></p><p>From the reviews of recent editions:</p><p>\"This is an excellent book for all polymer chemists engaged in synthesis research studies and education. It is educationally sound and has excellent laboratory synthetic examples. The fundamentals are well done for the teaching of students and references are resonably up-to-date. As in previous issues, there are sections dealing with an introduction; structure and nomenclature; methods and techniques for synthesis, characterization, processing and modification of polymers.</p><p>....The authors have noted the following changes from previous editions- a new section on correlations of structure, morphology and properties; revision and enlargement of other property and characterization procedures; additional new experiments such as controlled radical polymerization; enzymatic polymerizations; microelmulsions; and electrical conducting polymers.</p><p>This is a high quality textbook at a reasonable price and should be considered as a suitable reference for all engaged in synthetic areas of polymer research.\" (Eli M. Pearce, Polytechnic University, Brooklyn, NY, USA)</p>');
INSERT INTO `book` VALUES (24,'kuKuBAAAQBAJ','You Only Live Once','Lonely Planet','2014-10-01',24,336,0.0,'http://books.google.com/books/content?id=kuKuBAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE71ol6n0LGJn8iiOYnF8xIRNS-fQrds60K-S7G6SMKWRcG6snXRu7P9IBXS7mACzqorM6AtMBs1JYorJY7iITrp52opwALLWFM2ubZY4lOBNYwqOYOat5tnGN7JsXc-RcyeXBIG-&source=gbs_api','<p><i>Lonely Planet: The world\'s leading travel guide publisher</i> </p><p><i>You Only Live Once: A Lifetime of Experiences for the Explorer in All of Us</i> is not just another bucketlist of big-ticket items. We\'ve all heard about Venice and, yes, it is probably worth going to Italy to see its waterways, but hopefully you\'ll take away something more from this book: a resolve to live life to the fullest--to add a dash of joie de vivre to every day. </p><p><i>You Only Live Once </i>will inspire readers of all ages to seize the moment, channel their inner hero, explore the world, create moments they will celebrate for years to come, and share their incredible stories. Providing suggestions for life\'s essential experiences for every stage of life, this eclectic gift book is the perfect manual for a life well-lived. </p><p>Anyone can sleep in a castle, sail a ship, make a music pilgrimage, and so much more. What all the book\'s ideas have in common is that they\'re starting points. They will reignite long-forgotten desires - to learn an instrument or a language - or spark new and unexpected ambitions: why shouldn\'t you move to Provence for a year? </p><p>When you know what\'s stopping you, you can start working on a solution. Perhaps this book will be as useful in helping you identify obstacles as will be for refining your month\'s or your year\'s travel experiences. Then it\'s time to turn to Lonely Planet\'s extensive travel resources and begin planning the rest of your life. </p><p>Combining stunning photography with illustrations and infographics, it will surprise and entertain with a quirky mix of experiences everyone should try at some point in their life. </p><p>\'You only live once; but if you do it right, once is enough.\' - <i>Mae West</i> </p><ul> <li>Cloth-spined hardback <li>Visual feast <li>1000 experiences to inspire and entertain <li>Refreshing take on the \"list\" book </li></ul><p><b>About Lonely Planet: </b>Started in 1973, Lonely Planet has become the world\'s leading travel guide publisher with guidebooks to every destination on the planet, as well as an award-winning website, a suite of mobile and digital travel products, and a dedicated traveller community. Lonely Planet\'s mission is to enable curious travellers to experience the world and to truly get to the heart of the places they find themselves in. </p><p><i>TripAdvisor Travellers\' Choice Awards 2012, 2013, and 2014 winner in Favorite Travel Guide category</i> </p><p><i>\'Lonely Planet guides are, quite simply, like no other.\' - New York Times</i> </p><p><i>\'Lonely Planet. It\'s on everyone\'s bookshelves; it\'s in every traveller\'s hands. It\'s on mobile phones. It\'s on the Internet. It\'s everywhere, and it\'s telling entire generations of people how to travel the world.\' - Fairfax Media (Australia) </i></p> <p>Important Notice: The digital edition of this book may not contain all of the images found in the physical edition.</p>');
INSERT INTO `book` VALUES (25,'cHDqCAAAQBAJ','The Big Trip','Lonely Planet','2015-04-01',25,679,2.0,'http://books.google.com/books/content?id=cHDqCAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE702ogSJcBlaJsUv5EWfgjpxdRcJg8POLNnshwojRsgoY1IQ0cYmtFoSSTr1H3fh1b0oGs-6LG_9EitjGkl7d2IhZlaAZtKjs71KNU8aeLVW9znxWWnkWa5ABfH7c1fqeANTMU1v&source=gbs_api','This comprehensive companion, now in its third edition, provides essential pretrip planning advice, regional overviews with maps and itineraries, and practical resources for finding work abroad. Now in full colour packed with inspirational images. <p>Important Notice: The digital edition of this book may not contain all of the images found in the physical edition.</p>');
INSERT INTO `book` VALUES (26,'irkyDwAAQBAJ','Lonely Planet\'s Atlas of Adventure','Lonely Planet','2017-09-01',26,337,0.0,'http://books.google.com/books/content?id=irkyDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE71WxaYfRGx-sFlinb28462N_LF78ucXw015U_fQRRdvZ1ADo9W43oUwsx5-IE6HHpBuETbyFPOGImkfcJKM9nJ6ZLu9ZecAqD4b9O6zHbLjmlNg8l8v7DlKgrMvw8vNMNcDl-d0&source=gbs_api','<p>Don\'t just walk on the wild side - hike, climb, cycle, surf and even parachute. <b><i>Lonely Planet\'s Atlas of Adventure</i></b> is an encyclopedia for thrill-seekers and adrenaline junkies, featuring the best outdoor experiences, country-by-country, across the world - making it the ultimate introduction to an exciting new world of adventure. </p><p>There are numerous ways to explore our planet and the <b><i>Atlas of Adventure</i></b> showcases as many of them as possible in over 150 countries. We tracked down our adventure-loving gurus and asked them to share their tips on where to go and what to do. Colourful, awe-inspiring images are accompanied by authoritative text from Lonely Planet\'s travel experts. </p><p>Highlights include: </p><ul> <li>Mountaineering and trekking in Argentina <li>Mountain biking and bushwalking in Australia <li>Diving and paddling in Cambodia <li>Trail running and canoeing in Canada <li>Surfing and volcano diving in El Salvador <li>Ski-exploring and dogsledding in Greenland <li>Cycling and snowsports in Japan <li>Riding with eagle hunters and packrafting in Mongolia <li>Dune boarding and hiking in Namibia <li>Tramping and black-water rafting in New Zealand <li>Kloofing and paragliding in South Africa <li>Sailing and walking in the United Kingdom <li>Hiking and climbing in the United States </li></ul><p><b>About Lonely Planet: </b>Lonely Planet is a leading travel media company and the world\'s number one travel guidebook brand, providing both inspiring and trustworthy information for every kind of traveller since 1973. Over the past four decades, we\'ve printed over 145 million guidebooks and grown a dedicated, passionate global community of travellers. You\'ll also find our content online, on mobile, video and in 14 languages, 12 international magazines, armchair and lifestyle books, ebooks, and more.</p><p><i>TripAdvisor Travelers\' Choice Awards 2012, 2013, 2014, 2015 and 2016 winner in Favorite Travel Guide category </i></p><p><i>\'Lonely Planet guides are, quite simply, like no other.\' - New York Times </i></p><p><i>\'Lonely Planet. It\'s on everyone\'s bookshelves; it\'s in every traveller\'s hands. It\'s on mobile phones. It\'s on the Internet. It\'s everywhere, and it\'s telling entire generations of people how to travel the world.\' - Fairfax Media (Australia) </i></p> <p>Important Notice: The digital edition of this book may not contain all of the images found in the physical edition.</p>');
INSERT INTO `book` VALUES (27,'4Ag7DwAAQBAJ','Lonely Planet\'s Best in Travel 2018','Lonely Planet','2017-10-01',27,208,0.0,'http://books.google.com/books/content?id=4Ag7DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE71B_3A0V7saNbrx6DBCnDWAP_mt0CwU0xt_EIgPw6cO7v3rnFyvqI7k_exoOeloPpsh5bFp8oP7kGkxQcGurzlMvl8mSSL40kGjVRvgKRb7uQwj1Wl5H22RXMjvK_0--bATr3kp&source=gbs_api','<p>Our annual bestseller, <b><i>Lonely Planet\'s Best in Travel</i></b>, ranks the hottest, must-visit countries, regions and cities for the year ahead. Drawing on the knowledge and passion of Lonely Planet\'s staff, authors, and online community, it presents a year\'s worth of inspiration to take travelers out of the ordinary and into the unforgettable - and firmly sets the travel agenda for 2018. </p><p>As self-confessed travel geeks, our staff collectively rack up hundreds of thousands of miles each year, exploring almost every destination on the planet. Every year, we ask ourselves: Where are the best places in the world to visit <i>right now</i>? It\'s a very hotly contested topic at Lonely Planet and dominates more discussion than any other. <b><i>Best in Travel 2018</i></b> is our definitive answer. </p><p>It makes the perfect gift for any traveller looking where to go next. </p><p>Inside, you\'ll discover the: </p><ul> <li>Top ten countries, regions and cities <li>Best value destinations <li>Best culture trips for families <li>Best new openings and experiences <li>Best new places to stay <li>Top destination races, from walks and marathons to cycles and swims <li>Top vegetarian and vegan destinations <li>Top small-ship expedition cruises <li>Best places for cross-generational family trips <li>Best private islands that everyone can use </li></ul><p><b>About Lonely Planet: </b>Lonely Planet is a leading travel media company and the world\'s number one travel guidebook brand, providing both inspiring and trustworthy information for every kind of traveller since 1973. Over the past four decades, we\'ve printed over 145 million guidebooks and grown a dedicated, passionate global community of travellers. You\'ll also find our content on lonelyplanet.com, mobile, video and in 14 languages, 12 international magazines, armchair and lifestyle books, ebooks, and more.</p> <p>Important Notice: The digital edition of this book may not contain all of the images found in the physical edition.</p>');
INSERT INTO `book` VALUES (28,'1TxjBAAAQBAJ','The Book of Everything','Lonely Planet','2014-08-01',28,203,0.0,'http://books.google.com/books/content?id=1TxjBAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE73SKB9P6fbs8hXieVs5yRR_nCjCUfItgCF8_fouZxkp09wmoFC1UskX4_jqEko32mgBjmX5U0CUHFArgiNAAy56QCUMCup7ZUN0Ni1PFndEmr5JRxkb0mxhmqjnMZKCeVhcxNAC&source=gbs_api','<p><i>Lonely Planet: The world\'s leading travel guide publisher*</i></p><p>Want to know how to wear a kilt, kiss a stranger, prevent a hangover, get out of a sinking car, eat a lobster, greet an alien, predict the weather, play croquet and much, much more? <b>The Book of Everthing</b> has it all. Open the book! Dive in! We guarantee you\'ll learn something new. And, equipped for a world of smart, safe and exciting travel, you can use your witty know-how to make friends wherever you go.</p><p><b>Authors:</b> Written and researched by Lonely Planet, Nigel Holmes.</p><p><b>About Lonely Planet:</b> Started in 1973, Lonely Planet has become the world\'s leading travel guide publisher with guidebooks to every destination on the planet, as well as an award-winning website, a suite of mobile and digital travel products, and a dedicated traveller community. Lonely Planet\'s mission is to enable curious travellers to experience the world and to truly get to the heart of the places where they travel.</p><p><i>TripAdvisor Travellers\' Choice Awards 2012 and 2013 winner in Favorite Travel Guide category</i></p><p><i>\'Lonely Planet guides are, quite simply, like no other.\' - New York Times</i></p><p><i>\'Lonely Planet. It\'s on everyone\'s bookshelves; it\'s in every traveller\'s hands. It\'s on mobile phones. It\'s on the Internet. It\'s everywhere, and it\'s telling entire generations of people how to travel the world.\' - Fairfax Media (Australia) </i></p><p><i>*#1 in the world market share - source: Nielsen Bookscan. Australia, UK and USA. March 2012-January 2013</i></p> <p>Important Notice: The digital edition of this book may not contain all of the images found in the physical edition.</p>');

INSERT INTO `author` VALUES (5,'Alan Dean Foster');
INSERT INTO `author` VALUES (19,'Brigitte Voit');
INSERT INTO `author` VALUES (3,'Cass R. Sunstein');
INSERT INTO `author` VALUES (9,'Cay S. Horstmann');
INSERT INTO `author` VALUES (16,'Dietrich Braun');
INSERT INTO `author` VALUES (8,'Gary Cornell');
INSERT INTO `author` VALUES (2,'George Lucas');
INSERT INTO `author` VALUES (6,'George R. R. Martin');
INSERT INTO `author` VALUES (20,'Harald Cherdron');
INSERT INTO `author` VALUES (17,'Helmut Ritter');
INSERT INTO `author` VALUES (4,'J. W. Rinzler');
INSERT INTO `author` VALUES (7,'J.K. Rowling');
INSERT INTO `author` VALUES (15,'Jane B. Reece');
INSERT INTO `author` VALUES (1,'Jason Fry');
INSERT INTO `author` VALUES (11,'John E. McMurry');
INSERT INTO `author` VALUES (13,'Julio de Paula');
INSERT INTO `author` VALUES (21,'Lonely Planet');
INSERT INTO `author` VALUES (18,'Matthias Rehahn');
INSERT INTO `author` VALUES (14,'Neil A. Campbell');
INSERT INTO `author` VALUES (12,'Peter Atkins');
INSERT INTO `author` VALUES (10,'Robert C. Martin');

INSERT INTO `book_author` VALUES (1,1);
INSERT INTO `book_author` VALUES (2,2);
INSERT INTO `book_author` VALUES (3,3);
INSERT INTO `book_author` VALUES (4,4);
INSERT INTO `book_author` VALUES (5,5);
INSERT INTO `book_author` VALUES (6,6);
INSERT INTO `book_author` VALUES (7,6);
INSERT INTO `book_author` VALUES (8,6);
INSERT INTO `book_author` VALUES (9,6);
INSERT INTO `book_author` VALUES (10,6);
INSERT INTO `book_author` VALUES (11,7);
INSERT INTO `book_author` VALUES (12,7);
INSERT INTO `book_author` VALUES (13,7);
INSERT INTO `book_author` VALUES (14,7);
INSERT INTO `book_author` VALUES (15,7);
INSERT INTO `book_author` VALUES (16,7);
INSERT INTO `book_author` VALUES (17,7);
INSERT INTO `book_author` VALUES (18,8);
INSERT INTO `book_author` VALUES (18,9);
INSERT INTO `book_author` VALUES (19,10);
INSERT INTO `book_author` VALUES (20,11);
INSERT INTO `book_author` VALUES (21,12);
INSERT INTO `book_author` VALUES (21,13);
INSERT INTO `book_author` VALUES (22,14);
INSERT INTO `book_author` VALUES (22,15);
INSERT INTO `book_author` VALUES (23,16);
INSERT INTO `book_author` VALUES (23,17);
INSERT INTO `book_author` VALUES (23,18);
INSERT INTO `book_author` VALUES (23,19);
INSERT INTO `book_author` VALUES (23,20);
INSERT INTO `book_author` VALUES (24,21);
INSERT INTO `book_author` VALUES (25,21);
INSERT INTO `book_author` VALUES (26,21);
INSERT INTO `book_author` VALUES (27,21);
INSERT INTO `book_author` VALUES (28,21);

INSERT INTO `bookcategory` VALUES (6,'Business & Economics / General');
INSERT INTO `bookcategory` VALUES (22,'Computers / Programming Languages / Java');
INSERT INTO `bookcategory` VALUES (24,'Computers / Software Development & Engineering / General');
INSERT INTO `bookcategory` VALUES (23,'Computers / Software Development & Engineering / Quality Assurance & Testing');
INSERT INTO `bookcategory` VALUES (7,'Family & Relationships / Parenting / General');
INSERT INTO `bookcategory` VALUES (1,'Fiction / Action & Adventure');
INSERT INTO `bookcategory` VALUES (18,'Fiction / Fantasy / Contemporary');
INSERT INTO `bookcategory` VALUES (12,'Fiction / Fantasy / Epic');
INSERT INTO `bookcategory` VALUES (17,'Fiction / Fantasy / General');
INSERT INTO `bookcategory` VALUES (2,'Fiction / Media Tie-In');
INSERT INTO `bookcategory` VALUES (11,'Fiction / Science Fiction / Action & Adventure');
INSERT INTO `bookcategory` VALUES (4,'Fiction / Science Fiction / General');
INSERT INTO `bookcategory` VALUES (3,'Fiction / Science Fiction / Space Opera');
INSERT INTO `bookcategory` VALUES (39,'Humor / General');
INSERT INTO `bookcategory` VALUES (21,'Juvenile Fiction / Action & Adventure / General');
INSERT INTO `bookcategory` VALUES (15,'Juvenile Fiction / Fantasy & Magic');
INSERT INTO `bookcategory` VALUES (19,'Juvenile Fiction / General');
INSERT INTO `bookcategory` VALUES (16,'Juvenile Fiction / School & Education');
INSERT INTO `bookcategory` VALUES (9,'Performing Arts / Film / Direction & Production');
INSERT INTO `bookcategory` VALUES (8,'Performing Arts / Film / General');
INSERT INTO `bookcategory` VALUES (10,'Performing Arts / Reference');
INSERT INTO `bookcategory` VALUES (37,'Reference / General');
INSERT INTO `bookcategory` VALUES (26,'Science / Chemistry / General');
INSERT INTO `bookcategory` VALUES (25,'Science / Chemistry / Organic');
INSERT INTO `bookcategory` VALUES (27,'Science / Chemistry / Physical & Theoretical');
INSERT INTO `bookcategory` VALUES (28,'Science / Life Sciences / Biology');
INSERT INTO `bookcategory` VALUES (29,'Science / Mechanics / Fluids');
INSERT INTO `bookcategory` VALUES (31,'Science / Physics / General');
INSERT INTO `bookcategory` VALUES (5,'Social Science / Popular Culture');
INSERT INTO `bookcategory` VALUES (30,'Technology & Engineering / Textiles & Polymers');
INSERT INTO `bookcategory` VALUES (38,'Travel / Food, Lodging & Transportation / General');
INSERT INTO `bookcategory` VALUES (34,'Travel / General');
INSERT INTO `bookcategory` VALUES (35,'Travel / Reference');
INSERT INTO `bookcategory` VALUES (36,'Travel / Special Interest / Adventure');
INSERT INTO `bookcategory` VALUES (32,'Travel / Special Interest / General');
INSERT INTO `bookcategory` VALUES (33,'Travel / Special Interest / Literary');
INSERT INTO `bookcategory` VALUES (20,'Young Adult Fiction / Action & Adventure / General');
INSERT INTO `bookcategory` VALUES (13,'Young Adult Fiction / Fantasy / Wizards & Witches');
INSERT INTO `bookcategory` VALUES (14,'Young Adult Fiction / School & Education / Boarding School & Prep School');

INSERT INTO `book_bookcategory` VALUES (1,1);
INSERT INTO `book_bookcategory` VALUES (1,2);
INSERT INTO `book_bookcategory` VALUES (1,3);
INSERT INTO `book_bookcategory` VALUES (2,4);
INSERT INTO `book_bookcategory` VALUES (3,5);
INSERT INTO `book_bookcategory` VALUES (3,6);
INSERT INTO `book_bookcategory` VALUES (3,7);
INSERT INTO `book_bookcategory` VALUES (4,8);
INSERT INTO `book_bookcategory` VALUES (4,9);
INSERT INTO `book_bookcategory` VALUES (4,10);
INSERT INTO `book_bookcategory` VALUES (5,1);
INSERT INTO `book_bookcategory` VALUES (5,3);
INSERT INTO `book_bookcategory` VALUES (5,11);
INSERT INTO `book_bookcategory` VALUES (6,1);
INSERT INTO `book_bookcategory` VALUES (6,11);
INSERT INTO `book_bookcategory` VALUES (6,12);
INSERT INTO `book_bookcategory` VALUES (7,1);
INSERT INTO `book_bookcategory` VALUES (7,11);
INSERT INTO `book_bookcategory` VALUES (7,12);
INSERT INTO `book_bookcategory` VALUES (8,1);
INSERT INTO `book_bookcategory` VALUES (8,11);
INSERT INTO `book_bookcategory` VALUES (8,12);
INSERT INTO `book_bookcategory` VALUES (9,1);
INSERT INTO `book_bookcategory` VALUES (9,11);
INSERT INTO `book_bookcategory` VALUES (9,12);
INSERT INTO `book_bookcategory` VALUES (10,1);
INSERT INTO `book_bookcategory` VALUES (10,11);
INSERT INTO `book_bookcategory` VALUES (10,12);
INSERT INTO `book_bookcategory` VALUES (11,1);
INSERT INTO `book_bookcategory` VALUES (11,13);
INSERT INTO `book_bookcategory` VALUES (11,14);
INSERT INTO `book_bookcategory` VALUES (11,15);
INSERT INTO `book_bookcategory` VALUES (11,16);
INSERT INTO `book_bookcategory` VALUES (11,17);
INSERT INTO `book_bookcategory` VALUES (11,18);
INSERT INTO `book_bookcategory` VALUES (11,19);
INSERT INTO `book_bookcategory` VALUES (11,20);
INSERT INTO `book_bookcategory` VALUES (11,21);
INSERT INTO `book_bookcategory` VALUES (12,1);
INSERT INTO `book_bookcategory` VALUES (12,13);
INSERT INTO `book_bookcategory` VALUES (12,14);
INSERT INTO `book_bookcategory` VALUES (12,15);
INSERT INTO `book_bookcategory` VALUES (12,16);
INSERT INTO `book_bookcategory` VALUES (12,17);
INSERT INTO `book_bookcategory` VALUES (12,18);
INSERT INTO `book_bookcategory` VALUES (12,19);
INSERT INTO `book_bookcategory` VALUES (12,20);
INSERT INTO `book_bookcategory` VALUES (12,21);
INSERT INTO `book_bookcategory` VALUES (13,1);
INSERT INTO `book_bookcategory` VALUES (13,13);
INSERT INTO `book_bookcategory` VALUES (13,14);
INSERT INTO `book_bookcategory` VALUES (13,15);
INSERT INTO `book_bookcategory` VALUES (13,16);
INSERT INTO `book_bookcategory` VALUES (13,17);
INSERT INTO `book_bookcategory` VALUES (13,18);
INSERT INTO `book_bookcategory` VALUES (13,19);
INSERT INTO `book_bookcategory` VALUES (13,20);
INSERT INTO `book_bookcategory` VALUES (13,21);
INSERT INTO `book_bookcategory` VALUES (14,1);
INSERT INTO `book_bookcategory` VALUES (14,13);
INSERT INTO `book_bookcategory` VALUES (14,14);
INSERT INTO `book_bookcategory` VALUES (14,15);
INSERT INTO `book_bookcategory` VALUES (14,16);
INSERT INTO `book_bookcategory` VALUES (14,17);
INSERT INTO `book_bookcategory` VALUES (14,18);
INSERT INTO `book_bookcategory` VALUES (14,19);
INSERT INTO `book_bookcategory` VALUES (14,20);
INSERT INTO `book_bookcategory` VALUES (14,21);
INSERT INTO `book_bookcategory` VALUES (15,1);
INSERT INTO `book_bookcategory` VALUES (15,13);
INSERT INTO `book_bookcategory` VALUES (15,14);
INSERT INTO `book_bookcategory` VALUES (15,15);
INSERT INTO `book_bookcategory` VALUES (15,16);
INSERT INTO `book_bookcategory` VALUES (15,17);
INSERT INTO `book_bookcategory` VALUES (15,18);
INSERT INTO `book_bookcategory` VALUES (15,19);
INSERT INTO `book_bookcategory` VALUES (15,20);
INSERT INTO `book_bookcategory` VALUES (15,21);
INSERT INTO `book_bookcategory` VALUES (16,1);
INSERT INTO `book_bookcategory` VALUES (16,13);
INSERT INTO `book_bookcategory` VALUES (16,14);
INSERT INTO `book_bookcategory` VALUES (16,15);
INSERT INTO `book_bookcategory` VALUES (16,16);
INSERT INTO `book_bookcategory` VALUES (16,17);
INSERT INTO `book_bookcategory` VALUES (16,18);
INSERT INTO `book_bookcategory` VALUES (16,19);
INSERT INTO `book_bookcategory` VALUES (16,20);
INSERT INTO `book_bookcategory` VALUES (16,21);
INSERT INTO `book_bookcategory` VALUES (17,1);
INSERT INTO `book_bookcategory` VALUES (17,13);
INSERT INTO `book_bookcategory` VALUES (17,14);
INSERT INTO `book_bookcategory` VALUES (17,15);
INSERT INTO `book_bookcategory` VALUES (17,16);
INSERT INTO `book_bookcategory` VALUES (17,17);
INSERT INTO `book_bookcategory` VALUES (17,18);
INSERT INTO `book_bookcategory` VALUES (17,19);
INSERT INTO `book_bookcategory` VALUES (17,20);
INSERT INTO `book_bookcategory` VALUES (17,21);
INSERT INTO `book_bookcategory` VALUES (18,22);
INSERT INTO `book_bookcategory` VALUES (19,23);
INSERT INTO `book_bookcategory` VALUES (19,24);
INSERT INTO `book_bookcategory` VALUES (20,25);
INSERT INTO `book_bookcategory` VALUES (21,26);
INSERT INTO `book_bookcategory` VALUES (21,27);
INSERT INTO `book_bookcategory` VALUES (22,28);
INSERT INTO `book_bookcategory` VALUES (23,25);
INSERT INTO `book_bookcategory` VALUES (23,27);
INSERT INTO `book_bookcategory` VALUES (23,29);
INSERT INTO `book_bookcategory` VALUES (23,30);
INSERT INTO `book_bookcategory` VALUES (23,31);
INSERT INTO `book_bookcategory` VALUES (24,32);
INSERT INTO `book_bookcategory` VALUES (24,33);
INSERT INTO `book_bookcategory` VALUES (24,34);
INSERT INTO `book_bookcategory` VALUES (25,34);
INSERT INTO `book_bookcategory` VALUES (25,35);
INSERT INTO `book_bookcategory` VALUES (25,36);
INSERT INTO `book_bookcategory` VALUES (26,32);
INSERT INTO `book_bookcategory` VALUES (26,34);
INSERT INTO `book_bookcategory` VALUES (26,37);
INSERT INTO `book_bookcategory` VALUES (27,32);
INSERT INTO `book_bookcategory` VALUES (27,34);
INSERT INTO `book_bookcategory` VALUES (27,35);
INSERT INTO `book_bookcategory` VALUES (27,38);
INSERT INTO `book_bookcategory` VALUES (28,34);
INSERT INTO `book_bookcategory` VALUES (28,37);
INSERT INTO `book_bookcategory` VALUES (28,39);

