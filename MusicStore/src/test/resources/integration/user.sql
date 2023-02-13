TRUNCATE TABLE admin;
INSERT INTO admin (mail,name,surname,phone_number) VALUES ("mail1@test","name1","surname1","111-111-111");
TRUNCATE TABLE user;
INSERT INTO user (mail,password,img_url) VALUES ("usermail1@test","password1","img_url1");
INSERT INTO user (mail,password,img_url) VALUES ("usermail2@test","password2","img_url2");
INSERT INTO user (mail,password,img_url) VALUES ("mail1@test","password1","img_url2");
