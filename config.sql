CREATE SCHEMA Battleship;
CREATE TABLE Battleship.Users(username varchar(30),password BLOB);
ALTER TABLE Battleship.Users ADD PRIMARY KEY(username);