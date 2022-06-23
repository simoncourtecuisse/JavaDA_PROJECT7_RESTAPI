USE demo;

INSERT INTO users (id, full_name, username, password)
VALUES (0, 'admin', 'admin', '$2a$10$GolCHmo.CxLanyXeHagmEOwS9H/zL1xa9ar09.MpEJbE019YWRvJa'),  -- password: admin123
	   (1, "Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa"),
       (2,"User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa");       
       
INSERT INTO roles(name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');
       
INSERT INTO user_roles (user_id, role_id)
VALUES (0, 2),
	   (1, 2),
       (2, 1);