CREATE TABLE book(id INT auto_increment PRIMARY KEY, bookName VARCHAR (50) UNIQUE NOT NULL, coverPicture VARCHAR (50), releaseDate DATE, author VARCHAR (50),  short_description VARCHAR (500), isbn VARCHAR (50),  category VARCHAR (50));