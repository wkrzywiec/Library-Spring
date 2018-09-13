# Library Portal (in Spring)

Spring MVC web application for borrowing and managing library books inventory.

## Description

This is my first Spring MVC project, which is an implementation of the library website. The application allow to borrow books (as a standard user),
manage books inventory (add new ones, check availablity, etc.) and user account.

In Library Portal there are three types of users: 
 * Readers - they can register themselves and borrow limited number of books.
 * Librarian - limited number of users that can add new books, borrow and return them. They can also confirm payments for penalties in case when a reader keeps a book to long.
 * Admin - person, who can add, edit and deactivate users.

## Tools & Frameworks

The application is written using Spring MVC framework and Gradle (for external dependency managment).

**Database & configuration**
* MySQL
* Flyway (for data migration)
* Gradle
* Tomcat
* Git
* Google Book API

**Backend technologies**
* Java
* Spring MVC, Spring AOP, Spring Security
* Hibernate ORM, Hibernate Validator, Hibenrate Search (Lucene)
* Retrofit, JSON
* JUnit
* Project Lombok
* Log4j2

**Frontend technologies**
* HTML, CSS, JavaScript
* JSP, JSTL
* Bootstrap 4

## How to run it?

Prerequisites: Eclipse IDE ([with Gradle and Tomcat plugin installed](https://medium.com/@wkrzywiec/setting-up-gradle-spring-project-in-eclipse-on-tomcat-server-77d68454fd8d)), Tomcat, [MySQL Community Edition](https://www.mysql.com/products/community/)

1. Clone this git repository

` $ git clone https://github.com/wkrzywiec/Library-Spring `

2. Open MySQL Workbench and type following SQL script:

```
	CREATE USER 'library-spring'@'localhost' IDENTIFIED BY 'library-spring';
	GRANT ALL PRIVILEGES ON  *.* TO 'library-spring'@'localhost';
	SET GLOBAL EVENT_SCHEDULER = ON;
 ```
 
 3. Go to a folder `src/main/resources/properties`, create googleAPI.properties file and add your Google API key ([here are instructions how to obtain it](https://cloud.google.com/docs/authentication/api-keys)) as follows:
 
 `  googleAPI.key=[YOUR KEY HERE] `
 
 4. Run `tomcatRun` Gradle task ([or assign it to the Run button in Eclipse](https://medium.com/@wkrzywiec/setting-up-gradle-spring-project-in-eclipse-on-tomcat-server-77d68454fd8d#4986))
 
 5. The application will avaialble under URL `http://localhost:8080/library-spring`

## Blog Posts

During work on this project I've parallely created some blog posts that describe my path to the working application. In those entires I've tried to explain some of key concepts, tools and frameworks that I used. Here is the list of all entries that was written so far:

**[Library Portal — Spring Project Overview](https://medium.com/@wkrzywiec/library-portal-spring-project-overview-ddbf910dcb95)**

**General**
* [Why Spring framework is so cool](https://medium.com/@wkrzywiec/why-spring-framework-is-so-cool-8472ceabaab1)

* [How to start with Spring MVC](https://medium.com/@wkrzywiec/how-to-start-with-spring-mvc-309dec3c59fd)

**Configurations**

* [Project development history lesson with git](https://medium.com/@wkrzywiec/project-development-history-lesson-with-git-424b9940ad84)

**Clean code**

* [Project Lombok—how to make your model class simple](https://medium.com/@wkrzywiec/project-lombok-how-to-make-your-model-class-simple-ad71319c35d5)

**Features**
###### User Log Events

* [Creating user logs with Apache Log4j2](https://medium.com/@wkrzywiec/creating-user-logs-with-apache-log4j2-90bfeb8a0d3f)
* [Moving into next level in User Log Events with Spring AOP](https://medium.com/@wkrzywiec/moving-into-next-level-in-user-log-events-with-spring-aop-3b4435892f16)

###### Full-text Search

* [Full-text search with Hibernate Search (Lucene) — part 1](https://medium.com/@wkrzywiec/full-text-search-with-hibernate-search-lucene-part-1-e245b889aa8e)

###### User registration

* [How to check if user exist in database using Hibernate Validator](https://medium.com/@wkrzywiec/how-to-check-if-user-exist-in-database-using-hibernate-validator-eab110429a6)

###### Add book to library

* [Making use of open REST API with Retrofit](https://medium.com/@wkrzywiec/making-use-of-open-rest-api-with-retrofit-dac6094f0522)

**Deployment**

* [Setting up Gradle web project in Eclispe (on Tomcar server)](https://medium.com/@wkrzywiec/setting-up-gradle-spring-project-in-eclipse-on-tomcat-server-77d68454fd8d)

* [Deployment of Spring MVC app on a local Tomcat server for beginners](https://medium.com/@wkrzywiec/deployment-of-spring-mvc-app-on-a-local-tomcat-server-for-beginners-3dfff9161908)

* [How to deploy web app and database in one click with Flyway (on Tomcat server)](https://medium.com/@wkrzywiec/how-to-deploy-web-app-and-database-in-one-click-with-flyway-on-tomcat-server-26b580e09e38)


## Database Diagram

Big picture on the database relationships.
![Database](https://github.com/wkrzywiec/Library-Spring/blob/master/img/database.PNG)

Detailed look on user entity relationships.Some of them, like `user_password_toke` are specific for Spring Security *Forgot password* feature.

![User database](https://github.com/wkrzywiec/Library-Spring/blob/master/img/dbuser.PNG)

And book entity relationships.

![User database](https://github.com/wkrzywiec/Library-Spring/blob/master/img/dbbook.PNG)

Finally user-book relationships, those tables stores book status information (like if it is reserved, borrowed, has penalties) or logs.

![User-Book database](https://github.com/wkrzywiec/Library-Spring/blob/master/img/user_book.PNG)


## Screenshots

Login page

![](https://github.com/wkrzywiec/Library-Spring/blob/master/img/login-page.PNG)


Main page, after login. The quote is taken from [Random Quote API](https://talaikis.com/).

![](https://github.com/wkrzywiec/Library-Spring/blob/master/img/main.PNG)


Admin can find and modify user profiles. Also there are possibility for him to see the logs of the user to check what changes were made on the user account.

![](https://github.com/wkrzywiec/Library-Spring/blob/master/img/user-list.PNG)

![](https://github.com/wkrzywiec/Library-Spring/blob/master/img/user-details.PNG)

![](https://github.com/wkrzywiec/Library-Spring/blob/master/img/user-log.PNG)


Librarian can add new books to the library. New book data are fetched from Google Book API when search query is performed.

![](https://github.com/wkrzywiec/Library-Spring/blob/master/img/add-book.PNG)


Every, regular user can register themself in the application.

![](https://github.com/wkrzywiec/Library-Spring/blob/master/img/registration.PNG)


After registration they can look for a book they want, see their details, and reserve it.

![](https://github.com/wkrzywiec/Library-Spring/blob/master/img/search-book.PNG)

![](https://github.com/wkrzywiec/Library-Spring/blob/master/img/book-details.PNG)

![](https://github.com/wkrzywiec/Library-Spring/blob/master/img/reservation.PNG)


The librarian can borrow and return books. Also he can check book history to get the insight who and when make any action on a book.

![](https://github.com/wkrzywiec/Library-Spring/blob/master/img/manage.PNG)

![](https://github.com/wkrzywiec/Library-Spring/blob/master/img/library-log.PNG)
