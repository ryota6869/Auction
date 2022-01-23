INSERT INTO USERS (name,money,pass,login) VALUES ('user1',0,'p@ss1', '2000-1-1');
INSERT INTO USERS (name,money,pass,login) VALUES ('user2',0,'p@ss2', '2000-1-1');
INSERT INTO USERS (name,money,pass,login) VALUES ('user3',0,'p@ss3', '2000-1-1');
INSERT INTO USERS (name,money,pass,login) VALUES ('user4',0,'p@ss4', '2000-1-1');
INSERT INTO USERS (name,money,pass,login) VALUES ('teacher',999999,'oit', '2000-1-1');

INSERT INTO ITEMS (name) VALUES ('ルビー');
INSERT INTO ITEMS (name) VALUES ('サファイア');
INSERT INTO ITEMS (name) VALUES ('エメラルド');

INSERT INTO BAG (userId, itemId, quantity) VALUES (1, 1, 3);
INSERT INTO BAG (userId, itemId, quantity) VALUES (1, 2, 2);
INSERT INTO BAG (userId, itemId, quantity) VALUES (1, 3, 3);
INSERT INTO BAG (userId, itemId, quantity) VALUES (2, 1, 2);
INSERT INTO BAG (userId, itemId, quantity) VALUES (2, 2, 3);
INSERT INTO BAG (userId, itemId, quantity) VALUES (2, 3, 3);
INSERT INTO BAG (userId, itemId, quantity) VALUES (3, 1, 2);
INSERT INTO BAG (userId, itemId, quantity) VALUES (3, 2, 3);
INSERT INTO BAG (userId, itemId, quantity) VALUES (3, 3, 3);
INSERT INTO BAG (userId, itemId, quantity) VALUES (4, 1, 3);
INSERT INTO BAG (userId, itemId, quantity) VALUES (4, 2, 2);
INSERT INTO BAG (userId, itemId, quantity) VALUES (4, 3, 3);
INSERT INTO BAG (userId, itemId, quantity) VALUES (5, 1, 0);
INSERT INTO BAG (userId, itemId, quantity) VALUES (5, 2, 0);
INSERT INTO BAG (userId, itemId, quantity) VALUES (5, 3, 0);

INSERT INTO AUCTION (ITEMID, SELLERID, MAXBID, DATE) VALUES (2, 4, NULL, '2022-1-31');
INSERT INTO AUCTION (ITEMID, SELLERID, MAXBID, DATE) VALUES (1, 3, NULL, '2022-1-31');
INSERT INTO AUCTION (ITEMID, SELLERID, MAXBID, DATE) VALUES (1, 2, NULL, '2022-2-8');
INSERT INTO AUCTION (ITEMID, SELLERID, MAXBID, DATE) VALUES (2, 1, NULL, '2022-2-8');
