USE demo;

INSERT INTO users (id, full_name, username, password)
VALUES (0, 'admin', 'SuperAdmintrade', '$2a$10$GolCHmo.CxLanyXeHagmEOwS9H/zL1xa9ar09.MpEJbE019YWRvJa'),  -- password: admin123
	   (1, "Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa"),
       (2,"User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa");       
       
INSERT INTO roles(name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');
       
INSERT INTO user_roles (user_id, role_id)
VALUES (0, 2),
	   (1, 2),
       (2, 1);

insert into  curve_point (id, curve_id, term, value)
values (1, 10, 54.2, 63.3),
	   (2, 11, 9.3, 4.1),
       (3, 12, 1.4, 54.7),
       (4, 13, 54.8, 32.4);

insert into trade (trade_id, account, type, buy_quantity, sell_quantity, buy_price, sell_price)
values (1, 'account_1','type_1', 10.0, 10.0, 10.0, 10.0),
       (2, 'account_2','type_2', 20.0, 20.0, 20.0, 20.0),
       (3, 'account_3','type_3', 30.0, 30.0, 30.0, 30.0),
       (4, 'account_4','type_4', 40.0, 40.0, 40.0, 40.0),
       (5, 'account_5','type_5', 50.0, 50.0, 50.0, 50.0),
       (6, 'account_6','type_6', 60.0, 60.0, 60.0, 60.0);

insert into rating (id, moody_rating, sand_p_rating, fitch_rating, order_number)
values  (1, 'moody1', 'sandP1', 'fitch1',1),
        (2, 'moody2', 'sandP2', 'fitch2',2),
        (3, 'moody3', 'sandP3', 'fitch3',3),
        (4, 'moody4', 'sandP4', 'fitch4',4);
        
UPDATE users_sequence
SET next_val = '3';

UPDATE curve_point_sequence
SET next_val = '5';

UPDATE trade_sequence
SET next_val = '7';

UPDATE rating_sequence
SET next_val = '5';