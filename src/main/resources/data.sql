INSERT INTO user (id, username, password, firstname, lastname) VALUES (1, 'testuser', '123456', 'Fan', 'Jin');
INSERT INTO user (id, username, password, firstname, lastname) VALUES (2, 'jingxiao@163', 'demodemo', 'Jing', 'Xiao');

INSERT INTO authority (id, name) VALUES (1, 'USER');
INSERT INTO authority (id, name) VALUES (2, 'ADMIN');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
