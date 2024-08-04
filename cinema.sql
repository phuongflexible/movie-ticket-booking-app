PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE methodpayment(
id INT PRIMARY KEY NOT NULL,
name TEXT);
CREATE TABLE user(
id INT PRIMARY KEY NOT NULL,
password TEXT);
CREATE TABLE manager(
id INT PRIMARY KEY NOT NULL,
name TEXT,
gender TEXT,
birthday TEXT,
phoneNumber TEXT,
email TEXT,
user_id INT);
CREATE TABLE member(
id INT PRIMARY KEY NOT NULL,
name TEXT,
gender TEXT,
birthday TEXT,
phoneNumber TEXT,
email TEXT,
user_id INT);
CREATE TABLE cinema(
id INT PRIMARY KEY NOT NULL,
name TEXT,
address TEXT);
CREATE TABLE category(
id INT PRIMARY KEY NOT NULL,
name TEXT);
CREATE TABLE ticket(
id INT PRIMARY KEY NOT NULL,
filmName TEXT,
cinemaName TEXT,
airedDate TEXT,
price REAL,
createdDate TEXT,
createdTime TEXT,
chairNumber TEXT);
CREATE TABLE chair(
id INT PRIMARY KEY NOT NULL,
name TEXT);
CREATE TABLE staff(
id INT PRIMARY KEY NOT NULL,
name TEXT,
gender TEXT,
birthday TEXT,
phoneNumber TEXT,
email TEXT,
userId INT,
cinemaId INT,
FOREIGN KEY (cinemaId) REFERENCES cinema(id));
CREATE TABLE film(
id INT PRIMARY KEY NOT NULL,
name TEXT,
airedDate TEXT,
airedTime TEXT,
rating TEXT,
review TEXT,
runningTime TEXT,
categoryId INT,
FOREIGN KEY (categoryId) REFERENCES category(id));
CREATE TABLE invoice(
id INT PRIMARY KEY NOT NULL,
boughtDateTime TEXT,
staffId INT,
total REAL,
ticketId INT,
FOREIGN KEY (ticketId) REFERENCES ticket(id));
CREATE TABLE room(
id INT PRIMARY KEY NOT NULL,
name TEXT,
cinemaId INT,
FOREIGN KEY (cinemaId) REFERENCES cinema(id));
CREATE TABLE roomchair(
id INT PRIMARY KEY NOT NULL,
roomId INT,
chairId INT,
FOREIGN KEY (roomId) REFERENCES room(id),
FOREIGN KEY (chairId) REFERENCES chair(id));
COMMIT;
