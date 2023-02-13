TRUNCATE TABLE admin;
INSERT INTO admin (mail,name,surname,phone_number) VALUES ("mail1@test","name1","surname1","111-111-111");
TRUNCATE TABLE category;
INSERT INTO category (id,name,parent,img_url) VALUES (1,"category_1",1,"img_url1");
TRUNCATE TABLE product;
INSERT INTO product (id,name,price,left_quantity,sold_quantity,producer,category,img_url) VALUES (1,"product_1",1.0,1,1,"producer_1",1,"img_url_1");
TRUNCATE TABLE user;
INSERT INTO user (mail,password,img_url) VALUES ("mail1@test","password1","img_url1");