TRUNCATE TABLE admin;
INSERT INTO admin (mail,name,surname,phone_number) VALUES ("mail1@test","name1","surname1","111-111-111");
TRUNCATE TABLE user;
INSERT INTO user (mail,password,img_url) VALUES ("mail1@test","password1","img_url1");
TRUNCATE TABLE producer;
INSERT INTO producer (mail,name,address) VALUES ("producermail1@test","producer_name1","producer_address1");
INSERT INTO producer (mail,name,address) VALUES ("producermail2@test","producer_name2","producer_address2");