# Music Store Database Schema
![...loading...](https://github.com/iambrunoromano/MusicStore/blob/main/MusicStore/db/MusicStoreUML.png?raw=true)
The database that stores all the data has been realized in MySQL reducing to the bare minimum the number of tables. Some stored procedures have been later on introduced to perform simple in-database operations to move data and calculate simple results. 

# Tables
The database containes 11 tables. All of them have some name used in the image of the schema, and an actual name I used in the table instatiation in the MYSQL database. This name can be found in parenthesis i.e. "(MYSQL: `web_user_bean`)". I used such name to make it easy the mapping in SpringBoot. 
#### Account Tables
These tables contain users and admin data. Every single user, wheters he's a simple customer or a site-admin, is associated to a unique ID, represented by an email, and to a password. Those informations are stored in the **WebUser** (MYSQL: `web_user_bean`) table. 

If the user is an admin new hypothetical information such as name, surname and phone number are collected and stored in the **Admin** (MYSQL: `admin_bean`) table. 

If the user is a product producer information such as brand name and address are collected and stored in the **Producer** (MYSQL: `producer_bean`) table. 

If the user is a simple customer information such as name, surname, address, card number and billing address are collected and stored in the **Customer** (MYSQL: `customer_bean`) table. 

The three tables **Admin**, **Producer** and **Customer** are related to the **WebUser** table via a foreign key constraint based on the **WebUser** mail attribute. 

#### Product Tables

These tables contain products' data. Each product has a unique ID, name, price and "in-warehouse" quantity, the **Product** (MYSQL: `product_bean`) table contains all the products. Each product is associated to a producer via foreign key constraint based on **WebUser** mail attribute, and is also associated to a product category via foreign key category ID constraint. 

The **Category** (MYSQL: `category_bean`) table contains a list of all the possible product categories. Since categories can be nested and disposed in a three (i.e. the category "guitar" can be daughter of the category "musical chord instruments" daughter of "musical instruments") I decided to associate to each category: a unique integer ID, a name and the ID of the only parent category. 

The **SoldProducts** (MYSQL: `soldprod`) table tracks how many items have been sold: it allowed me to present an home page of "most sold" goods. Each row of SoldProducts references to a product ID via foreign key constraint, to a producer ID via foreign key constraint and saves the number of sold units. 

The **Cart** (MYSQL: `cart_bean`) table keeps track of the items added to cart by single users. I decided to track this via database to avoid leaving such data on the frontend and lose them when starting each new session. This table contains rows that posses a proper ID, a product ID via foreign key constraint, an integer quantity of added-to-cart items, the mail of the user that added such items to his/her cart and time and date of the adding operation. 

The **BoughtItems** (MYSQL: `boughtitems`) table tracks all the ordered items. It represents an historical version of all the successfully ordered rows of the **Cart** table. The **BoughtItems** table contains information about the user mail that bought some items, the ID of the bought items via foreign key constraint, the order ID via foreign key constraint, an integer quantity of bought and therefore ordered items, and the price of that amount of products ordered all togheter (i.e. my order contains 3 electric guitars that cost 500.00 $ each, price will be 1500.00 $).

# Stored Procedures
# Data
