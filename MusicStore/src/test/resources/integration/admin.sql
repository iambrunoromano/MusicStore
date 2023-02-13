TRUNCATE TABLE admin;
INSERT INTO admin (mail,name,surname,phone_number) VALUES ("mail1@test","name1","surname1","111-111-111");
INSERT INTO admin (mail,name,surname,phone_number) VALUES ("mail2@test","name2","surname2","222-222-222");
TRUNCATE TABLE user;
INSERT INTO user (mail,password,img_url) VALUES ("mail1@test","password1","img_url1");
INSERT INTO user (mail,password,img_url) VALUES ("mail2@test","password2","img_url2");