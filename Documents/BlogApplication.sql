DROP TABLE Account;
DROP TABLE Blog;
DROP TABLE BlogPost;
DROP TABLE user;
DROP TABLE Comment;


DROP DATABASE blogapplication;
CREATE DATABASE blogapplication CHARACTER SET utf8MB4;
USE blogapplication;



CREATE TABLE IF NOT EXISTS User (
    user_id  INT UNIQUE AUTO_INCREMENT,
    firstname VARCHAR(100),
	lastname VARCHAR(100),
    middlename VARCHAR(100),
	last_updated_date TIMESTAMP,
    version_num LONG,
    PRIMARY KEY (user_id)
)  ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS Account (
    account_id INT AUTO_INCREMENT,
    account_created_date DATE,
    account_terminated_date DATE,
    status VARCHAR(255),
    role VARCHAR(50),
	email VARCHAR(100),
    user_id INT UNIQUE,
	last_updated_date TIMESTAMP,
    version_num LONG,
    PRIMARY KEY (account_id),
	FOREIGN KEY (user_id)
	REFERENCES User(user_id)
)  ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS Blog
 (
    blog_id INT UNIQUE AUTO_INCREMENT,
    blog_title VARCHAR(255),
	blog_description VARCHAR(255),
    blog_created_date DATE,
    blog_terminated_date DATE,
    status VARCHAR(255),
	account_id INT UNIQUE,
	last_updated_date TIMESTAMP,
    version_num LONG,
    PRIMARY KEY (blog_id),
	FOREIGN KEY (account_id)
	REFERENCES Account(account_id)
)  ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS Blog_Post
 (
    blog_post_id INT UNIQUE AUTO_INCREMENT,
    blog_post_body VARCHAR(255),
	blog_post_title VARCHAR(255),
    blog_post_created_date DATE,
    blog_post_terminated_date DATE,
    blog_id INT UNIQUE,
    status VARCHAR(255),
	last_updated_date TIMESTAMP,
    version_num LONG,
	PRIMARY KEY (blog_post_id),
	FOREIGN KEY (blog_id)
	REFERENCES Blog(blog_id)
)  ENGINE=INNODB;


CREATE TABLE IF NOT EXISTS Comment (
    comment_id INT UNIQUE AUTO_INCREMENT,
    comment VARCHAR(255),
    blog_post_id INT UNIQUE,
    comment_created_date DATE,
    comment_deleted_date DATE,
    status VARCHAR(255),
	last_updated_date TIMESTAMP,
    version_num LONG,
    PRIMARY KEY (comment_id),
	FOREIGN KEY (blog_post_id)
	REFERENCES Blog_Post(blog_post_id)
)  ENGINE=INNODB;

INSERT INTO `blogapplication`.`user`
(`user_id`,
`firstname`,
`lastname`,
`middlename`,
`last_updated_date`,`version_num`)
VALUES
(1,
"John",
"Doe",
"Terrance",
"2019-11-11","0");

INSERT INTO `blogapplication`.`account`
(
`account_created_date`,
`status`,
`role`,
`email`,
`user_id`,
`last_updated_date`,`version_num`)
VALUES
(
"2019-11-11",
"Active",
"Regular",
"test@gmail.com",
1,
"2019-11-11","0");

INSERT INTO `blogapplication`.`blog`
(`blog_id`,
`blog_title`,
`blog_description`,
`blog_created_date`,
`status`,
`account_id`,
`last_updated_date`,`version_num`)
VALUES
(1,
"Test Blog",
"Blog Description",
"2019-11-11",
"Active",
1,
"2019-11-11","0");

INSERT INTO `blogapplication`.`blog_post`
(`blog_post_id`,
`blog_post_title`,
`blog_post_body`,
`blog_post_created_date`,
`status`,
`blog_id`,
`last_updated_date`,`version_num`)
VALUES
(1,
"Test Blog Title",
"Test Blog Post",
"2019-11-11",
"Active",
1,
"2019-11-11","0");

INSERT INTO `blogapplication`.`comment`
(`comment`,
`blog_post_id`,
`comment_created_date`,
`status`,
`last_updated_date`,`version_num`)
VALUES
("TestComment",
1,
"2019-11-11",
"Active",
"2019-11-11","0");

alter table blog add constraint FKo5v3stawoq62qxo28btumeotr foreign key (blog_post_id) references blog (blog_id);
alter table account add constraint FK7m8ru44m93ukyb61dfxw0apf6 foreign key (user_id) references user (user_id);
alter table blog add constraint FK2rvtagi1yam2hsyv2i27i0lrl foreign key (account_id) references account (account_id);
select * from account;
select * from blog;

SET GLOBAL default_storage_engine = 'InnoDB';

select * from account;
select * from user;

select * from BlogPost t where t.blogId = 1 and t.blogPostId = 1;
select * from BlogPost;
select * from BlogPost t where t.blog_id = 1;

select * from Blog_Post;

select * from BlogPost WHERE blog_post_id = 1;

select * from blog_post;

alter table blog add constraint FK2rvtagi1yam2hsyv2i27i0lrl foreign key (account_id) references account (account_id)