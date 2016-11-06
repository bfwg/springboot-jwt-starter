INSERT INTO user (id, username, password, firstname, lastname) VALUES (1, 'testuser', '123456', 'Fan', 'Jin');
INSERT INTO user (id, username, password, firstname, lastname) VALUES (2, 'admin@dev.com', 'demodemo', 'Jing', 'Xiao');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
