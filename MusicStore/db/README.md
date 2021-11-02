# Music Store Database Schema
![...loading...](https://github.com/iambrunoromano/MusicStore/blob/main/MusicStore/db/MusicStoreUML.png?raw=true)
The database that stores all the data has been realized in MySQL reducing to the bare minimum the number of tables. Some stored procedures have been later on introduced to perform simple in-database operations to move data and calculate simple results. 

# Tables
The database containes 11 tables. All of them have some name used in the image of the schema, and an actual name I used in the table instatiation in the MYSQL database. This name can be found in parenthesis i.e. "(MYSQL: `web_user_bean`)". I used such name to make it easy the mapping in SpringBoot. 

### Account Tables

These tables contain users and admin data. Every single user, wheters he's a simple customer or a site-admin, is associated to a unique ID, represented by an email, and to a password. Those informations are stored in the **WebUser** (MYSQL: `web_user_bean`) table. 

If the user is an admin new hypothetical information such as name, surname and phone number are collected and stored in the **Admin** (MYSQL: `admin_bean`) table. 

If the user is a product producer information such as brand name and address are collected and stored in the **Producer** (MYSQL: `producer_bean`) table. 

If the user is a simple customer information such as name, surname, address, card number and billing address are collected and stored in the **Customer** (MYSQL: `customer_bean`) table. 

The three tables **Admin**, **Producer** and **Customer** are related to the **WebUser** table via a foreign key constraint based on the **WebUser** mail attribute. 

### Product Tables

These tables contain products' data. Each product has a unique ID, name, price and "in-warehouse" quantity, the **Product** (MYSQL: `product_bean`) table contains all the products. Each product is associated to a producer via foreign key constraint based on **WebUser** mail attribute, and is also associated to a product category via foreign key category ID constraint. 

The **Category** (MYSQL: `category_bean`) table contains a list of all the possible product categories. Since categories can be nested and disposed in a three (i.e. the category "guitar" can be daughter of the category "musical chord instruments" daughter of "musical instruments") I decided to associate to each category: a unique integer ID, a name and the ID of the only parent category. 

The **SoldProducts** (MYSQL: `soldprod`) table tracks how many items have been sold: it allowed me to present an home page of "most sold" goods. Each row of SoldProducts references to a product ID via foreign key constraint, to a producer ID via foreign key constraint and saves the number of sold units. 

The **Cart** (MYSQL: `cart_bean`) table keeps track of the items added to cart by single users. I decided to track this via database to avoid leaving such data on the frontend and lose them when starting each new session. This table contains rows that posses a proper ID, a product ID via foreign key constraint, an integer quantity of added-to-cart items, the mail of the user that added such items to his/her cart and time and date of the adding operation. 

The **BoughtItems** (MYSQL: `boughtitems`) table tracks all the ordered items. It represents an historical version of all the successfully ordered rows of the **Cart** table. The **BoughtItems** table contains information about the user mail that bought some items, the ID of the bought items via foreign key constraint, the order ID via foreign key constraint, an integer quantity of bought and therefore ordered items, and the price of that amount of products ordered all togheter (i.e. my order contains 3 electric guitars that cost 500.00 $ each, price will be 1500.00 $).

### Order and Shipment Tables

The **Order** (MYSQL: `order_bean`) contains info about the unique integer ID of the order, the mail of the user that ordered products with that order, the date in which he did, the total price of the whole order. 

The **Shipment** (MYSQL: `shipment_bean`) contains info about the unique integer ID of the shipment, the shipdate and the arrive date, the shippping address, the total cost of the shipping and the orded ID via foreign key constraint. 

# Stored Procedures

I decided to use some custom stored procedures to perform some all-in-database operations or to list tables items by field-specific query.

### BestX Procedures

The **BestProducts** (MYSQL: `BestProducts`) stored procedure returns the **Product** table sorted by most sold units, retrieved by the **SoldProducts** table. 

The **BestProducers** (MYSQL: `BestProducers`) stored procedure returns the **Producer** table sorted by most successfully solding producers, retrieving such info by the **SoldProducts** table. 

### CartToOrder Procedure

The **CartToOrder** stored procedure is part of the business logic of the e-commerce application. I decided to manage the cart-to-order operation for order consolidation in this way because functionally equivalent to the case in which it is managed by the server application. Each customer can have only one "cart" per time. By definition a customer can add things to cart, empty the cart proceeding to the order, add new things to the cart. In this way the stored procedure is called and data are moved, transformed and generated all internally to the database. In case of dealing with a more complicate business logic I would implement it on server-side, that allows more complex operative logics. This procedure simply :
1. Calculate quantity-proportional prices for each product added to cart by a user
2. Instatiates a new order in the **Order** table
3. Creates new rows in the **BoughtItems** table that identify the history of the cart for a user
4. Cleans the **Cart** table by the products added by a user
5. Returs all the ordered items with relative quantity and price

### XByX Procedures

The **ProductsByProducer** (MYSQL: `ProductsByProducer`) procedure returns all the products for a given producer mail.  

The **CategoriesByProducer** (MYSQL: `CategoriesByProducer`) procedure returns all the categories in which a producer sells for a given producer mail.

The **ProductsByCart** (MYSQL: `ProductsByCart`) procedure returns all the products in the **Cart** table for a given customer mail.

The **ProductsByCategory** (MYSQL: `ProductsByCategory`) procedure returns all the products belonging to a category for a given category ID.
