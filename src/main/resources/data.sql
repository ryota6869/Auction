INSERT INTO USERS (name,money,pass,login) VALUES ('user1',0,'p@ss1', '2000-1-1');
INSERT INTO USERS (name,money,pass,login) VALUES ('user2',0,'p@ss2', '2000-1-1');
INSERT INTO USERS (name,money,pass,login) VALUES ('user3',0,'p@ss3', '2000-1-1');
INSERT INTO USERS (name,money,pass,login) VALUES ('user4',0,'p@ss4', '2000-1-1');
INSERT INTO USERS (name,money,pass,login) VALUES ('teacher',999999,'oit', '2000-1-1');

INSERT INTO ITEMS (name) VALUES ('ルビー');
INSERT INTO ITEMS (name) VALUES ('サファイア');
INSERT INTO ITEMS (name) VALUES ('エメラルド');

INSERT INTO BAG (userId, itemId, quantity) VALUES (1, 1, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (1, 2, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (1, 3, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (2, 1, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (2, 2, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (2, 3, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (3, 1, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (3, 2, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (3, 3, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (4, 1, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (4, 2, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (4, 3, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (5, 1, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (5, 2, 1);
INSERT INTO BAG (userId, itemId, quantity) VALUES (5, 3, 1);

INSERT INTO AUCTION (itemId, sellerId, maxBid, date, bidderId) VALUES (2, 3, 1122, '2022-12-12',1);
INSERT INTO AUCTION (itemId, sellerId, maxBid, date) VALUES (3, 1, NULL, '2021-12-13');
INSERT INTO AUCTION (itemId, sellerId, maxBid, date, bidderId) VALUES (3, 1, NULL, '2022-12-13',2);

INSERT INTO AUCTION (itemId, sellerId, maxBid, date, bidderId) VALUES (2, 3, 1000, '2022-1-1',1);
INSERT INTO AUCTION (itemId, sellerId, maxBid, date, bidderId) VALUES (2, 3, 10000, '2022-1-1',1);

INSERT INTO AUCTION (itemId, sellerId, maxBid, date, bidderId) VALUES (2, 1, 2000, '2022-1-1',3);
INSERT INTO AUCTION (itemId, sellerId, maxBid, date, bidderId) VALUES (2, 1, 100000, '2022-1-1',3);
