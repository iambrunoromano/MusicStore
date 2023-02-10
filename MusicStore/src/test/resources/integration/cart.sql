TRUNCATE TABLE user;
INSERT INTO user (mail,password,img_url) VALUES ("usermail1@test","password1","img_url1");
INSERT INTO user (mail,password,img_url) VALUES ("usermail2@test","password2","img_url2");
TRUNCATE TABLE cart;
INSERT INTO cart (id,product_id,quantity,mail,date,bought,order_id,overall_price) VALUES (1,1,1,"usermail1@test","2023-01-01 00:00:00",true,1,10.0);
TRUNCATE TABLE product;
INSERT INTO product (id,name,price,left_quantity,sold_quantity,producer,category,img_url) VALUES (1,"product_1",1.0,1,1,"producer_1",1,"img_url_1");
