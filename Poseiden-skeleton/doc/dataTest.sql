USE demo;

INSERT INTO users (id, full_name, username, password, role)
VALUES (1, 'Administrator', 'admin', '$2a$10$/GhQQyzqOFDt8mn3WUUBs.0Xdbqu.i3qRhPBzGRB6g3XsXQi0khG6','ADMIN'),	-- password: Admin123!
       (2, 'User', 'user', '$2a$10$ODBPynhibZ3TKco74Avq4OWI3WxY3LJsGZIvncgzPYJU0i0sk7r26', 'USER'), 			-- password: User123!
       (3, 'User2', 'user2', '$2a$10$ODBPynhibZ3TKco74Avq4OWI3WxY3LJsGZIvncgzPYJU0i0sk7r26', 'USER'); 			-- password: User123!
       
INSERT INTO bid_list (bid_list_id, account, type, bid_quantity, ask_quantity, bid, ask)
VALUES (1, 'a', 'type-a', 10.0, 10.0, 10.0, 10.0),
	   (2, 'b', 'type-b', 20.0, 20.0, 20.0, 20.0),
       (3, 'c', 'type-c', 30.0, 30.0, 30.0, 30.0),
       (4, 'd', 'type-d', 40.0, 40.0, 40.0, 40.0);

INSERT INTO curve_point (id, curve_id, term, value)
VALUES (1, 10, 11.1, 11.1),
	   (2, 20, 22.2, 22.2),
       (3, 30, 33.3, 33.3),
       (4, 40, 44.4, 44.4);

INSERT INTO trade (trade_id, account, type, buy_quantity, sell_quantity, buy_price, sell_price)
VALUES (1, 'account_1','type_1', 10.0, 10.0, 10.0, 10.0),
       (2, 'account_2','type_2', 20.0, 20.0, 20.0, 20.0),
       (3, 'account_3','type_3', 30.0, 30.0, 30.0, 30.0),
       (4, 'account_4','type_4', 40.0, 40.0, 40.0, 40.0),
       (5, 'account_5','type_5', 50.0, 50.0, 50.0, 50.0),
       (6, 'account_6','type_6', 60.0, 60.0, 60.0, 60.0);

INSERT INTO rating (id, moody_rating, sand_p_rating, fitch_rating, order_number)
VALUES  (1, 'moody1', 'sandP1', 'fitch1',1),
        (2, 'moody2', 'sandP2', 'fitch2',2),
        (3, 'moody3', 'sandP3', 'fitch3',3),
        (4, 'moody4', 'sandP4', 'fitch4',4);

INSERT INTO rule_name (id, name, description, json, template, sql_str, sql_part)
VALUES  (1, 'name1','description1', 'json1', 'template1','sqlStr1', 'sqlPart1'),
        (2, 'name2','description2', 'json2', 'template2','sqlStr2', 'sqlPart2'),
        (3, 'name3', 'description3', 'json3', 'template3','sqlStr3', 'sqlPart3');
        
UPDATE users_sequence
SET next_val = '4';

UPDATE bid_list_sequence
SET next_val = '5';

UPDATE curve_point_sequence
SET next_val = '5';

UPDATE trade_sequence
SET next_val = '7';

UPDATE rating_sequence
SET next_val = '5';

UPDATE rule_name_sequence
SET next_val = '4';