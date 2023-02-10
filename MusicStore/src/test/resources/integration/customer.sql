TRUNCATE TABLE customer;
INSERT INTO customer (mail,name,surname,payment_card,billing_address) VALUES ("usermail1@test","customer_name1","customer_surname1","customer_card1","billing_address1");
INSERT INTO customer (mail,name,surname,payment_card,billing_address) VALUES ("usermail2@test","customer_name2","customer_surname2","customer_card2","billing_address2");
TRUNCATE TABLE admin;
INSERT INTO admin (mail,name,surname,phone_number) VALUES ("mail1@test","name1","surname1","111-111-111");
TRUNCATE TABLE user;
INSERT INTO user (mail,password,img_url) VALUES ("usermail1@test","password1","img_url1");
