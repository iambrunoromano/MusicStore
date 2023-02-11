TRUNCATE TABLE product;
INSERT INTO product (id,name,price,left_quantity,sold_quantity,producer,category,img_url) VALUES (1,"product_1",1.0,1,1,"producermail1@test",1,"img_url_1");
INSERT INTO product (id,name,price,left_quantity,sold_quantity,producer,category,img_url) VALUES (2,"product_2",1.0,1,2,"producermail2@test",2,"img_url_2");
TRUNCATE TABLE category;
INSERT INTO category (id,name,parent,img_url) VALUES (1,"category_1",1,"img_url1");
TRUNCATE TABLE producer;
INSERT INTO producer (mail,name,address) VALUES ("producermail1@test","producer_name1","producer_address1");
TRUNCATE TABLE admin;
INSERT INTO admin (mail,name,surname,phone_number) VALUES ("mail1@test","name1","surname1","111-111-111");
