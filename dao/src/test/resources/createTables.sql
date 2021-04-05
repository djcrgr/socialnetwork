CREATE TABLE IF NOT EXISTS phones
(
    id     INT         PRIMARY KEY AUTO_INCREMENT,
    userID   INT         ,
    phoneNum VARCHAR(15) ,
    phoneType   VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS account
(
    id       INT         PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(30) ,
    surname     VARCHAR(30) ,
    email    VARCHAR(50) UNIQUE,
    password VARCHAR(30) ,
    address  VARCHAR(45),
    age      INT,
    photo    MEDIUMBLOB
);