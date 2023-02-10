TRUNCATE TABLE admin;
INSERT INTO admin (mail,name,surname,phone_number) VALUES ("mail1@test","name1","surname1","111-111-111");
TRUNCATE TABLE `order_table`;
INSERT INTO `order_table` (id,mail,date,total,address) VALUES (1,"mail1@test","2023-01-01 00:00:00",100.0,"address_1");
INSERT INTO `order_table` (id,mail,date,total,address) VALUES (2,"mail2@test","2023-01-01 00:00:00",100.0,"address_2");
