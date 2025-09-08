INSERT INTO tb_address (street, number, complement, city, state, zip_code, country) VALUES ('Av. Paulista', '1000', 'Apto 101', 'São Paulo', 'SP', '01310-100', 'Brasil');

INSERT INTO tb_card (number, expired, cvv) VALUES ('1234567890123456', '2027-12-01',123);

INSERT INTO tb_congresso  (name, start_date, end_date, place, submission_deadline, review_deadline, reviews_per_article) VALUES  ('Congresso Internacional de Inteligência Artificial', '2025-07-10', '2025-07-15', 'São Paulo - SP, Brasil', '2025-06-01', '2025-06-20', 3);

INSERT INTO tb_user (username_user, login, password,work_place, membership_number, is_reviewer, address_id, card_id, profile_image, congresso_id) VALUES ('admin', 'admin', '$2a$10$niTy0wkb2ngpV3/TNULkjelElc6m29m9awmyfpb9YlM4P62nz3DTG', 'TopAvn Banco', RANDOM_UUID(), TRUE, 1, 1, X'89504E470D0A1A0A89504E470D0A1A0A', 1);

INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_PARTICIPANT');
INSERT INTO tb_role (authority) VALUES ('ROLE_REVIEWER');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1,1);