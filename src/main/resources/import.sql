INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('Av. Paulista', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');

INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 1', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 2', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 3', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 4', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 5', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 6', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 7', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 8', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 9', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 10', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 11', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 12', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 13', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 14', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');
INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('endereco para teste 15', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');


INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);

INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);
INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);



INSERT INTO tb_congresso (name, congresso_modality, description, description_title, start_date, end_date, place, submission_deadline, review_deadline, max_reviews_per_article, min_reviews_per_article) VALUES ('Congresso Internacional de Inteligência Artificial', 'ONLINE' , 'Congresso com foco em IA e java ', 'Congresso de IA' ,'2026-10-13T14:22:12.575Z', '2026-10-23T14:22:12.575Z', 'São Paulo - SP, Brasil', '2026-09-13T14:22:12.575Z', '2026-09-13T14:22:12.575Z', 5, 3);

INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'admin', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 1, 1, X'89504E470D0A1A0A89504E470D0A1A0A', 1);

INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test1', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 2, 2, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test2', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 3, 3, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test3', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 4, 4, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test4', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 5, 5, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test5', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 6, 6, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test6', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 7, 7, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test7', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 8, 8, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test8', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 9, 9, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test9', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 10, 10, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test10', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 11, 11, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test11', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 12, 12, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test12', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 13, 13, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test13', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 14, 14, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test14', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 15, 15, X'89504E470D0A1A0A89504E470D0A1A0A', 1);
INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'test15', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 16, 16, X'89504E470D0A1A0A89504E470D0A1A0A', 1);



INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_PARTICIPANT');
INSERT INTO tb_role (authority) VALUES ('ROLE_REVIEWER');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1,1);