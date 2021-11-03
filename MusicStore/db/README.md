# Music Store Database Schema
![...loading...](https://github.com/iambrunoromano/MusicStore/blob/main/MusicStore/db/MusicStoreUML.png?raw=true)
The database that stores all the data has been realized in MySQL reducing to the bare minimum the number of tables. Some stored procedures have been later on introduced to perform simple in-database operations to move data and calculate simple results. 

# Tables
The database containes 11 tables. The file [tablesbuilt.txt](https://github.com/iambrunoromano/MusicStore/blob/main/MusicStore/db/tablesbuilt.txt "tablesbuilt") in the current folder contains all the SQL commands used to generate such. All of the tables have a name used in the image of the schema, and an actual name I used in the table instatiation in the MYSQL database. This name can be found in parenthesis: i.e. the table WebUser will have (MYSQL: `web_user_bean`). I formatted names in the database in such way to make it easy SpringBoot mapping. 

### Account Tables

These tables contain users' data. Every single user can be a customer buying products, a producer selling products, a site-admin administrating the site. The user can be all of three togheter. Each WebUser is associated to a unique ID, represented by an email, and to a password (I later added an attribute for image profile picture URL). These informations are stored in the **WebUser** (MYSQL: `web_user_bean`) table. 

If the user is an admin information such as name, surname and phone number are collected and stored in the **Admin** (MYSQL: `admin_bean`) table. The mail attribute of **Admin** table refers to the mail attribute of **WebUser** table with a foreign key constraint.

If the user is a producer information such as (brand) name and address are collected and stored in the **Producer** (MYSQL: `producer_bean`) table. The mail attribute of **Producer** table refers to the mail attribute of **WebUser** table with a foreign key constraint.

If the user is a customer information such as name, surname, address, card number and billing address are collected and stored in the **Customer** (MYSQL: `customer_bean`) table. The mail attribute of **Customer** table refers to the mail attribute of **WebUser** table with a foreign key constraint.

### Product Tables

These tables contain products' data. Each product has a unique integer ID, name, price and "in-warehouse" quantity. The **Product** (MYSQL: `product_bean`) table contains all the products. Each product has also producer and category attributes, that refer to **Producer** table mail attribute and **Category** ID attribute via foreign key constraint. 

The **Category** (MYSQL: `category_bean`) table contains a list of all the possible product categories. In general categories can be nested and disposed in a three, i.e. the category "guitar" can be daughter of the category "musical chord instruments" daughter of "musical instruments". Therefore I decided to associate to each category a unique integer ID, a name and the ID of the only parent category. 

The **SoldProducts** (MYSQL: `soldprod`) table tracks how many items have been sold: it allows me to retrieve the most sold products. Each row of SoldProducts references to a product ID via foreign key constraint, to a producer ID via foreign key constraint and saves the number of sold units. 

The **Cart** (MYSQL: `cart_bean`) table keeps track of the items added to cart by single users. I decided to track this using the database to avoid leaving such data on the frontend and lose them when the user starts a new session. This table contains rows that posses a proper ID, a product ID via foreign key constraint, an integer quantity of added-to-cart items, the user mail via foreign key constraint and time and date of the adding operation. 

The **BoughtItems** (MYSQL: `boughtitems`) table tracks all the ordered items. It represents a track record version of all the successfully ordered rows of the **Cart** table. The **BoughtItems** table contains information about the user mail, the ID of the bought items and the order ID via foreign key constraints, an integer quantity of bought and therefore ordered items, and the price of that amount of products ordered all togheter (i.e. if my order contains 3 electric guitars that cost 500.00 $ each price will be 1500.00 $).

### Order and Shipment Tables

The **Order** (MYSQL: `order_bean`) contains information about the unique integer ID of the order, the user mail via foreign key constraint, the date in which he did it and the total price of the order. 

The **Shipment** (MYSQL: `shipment_bean`) contains info about the unique integer ID of the shipment, the shipdate and the arrive date, the shipping address, the total cost of the shipping and the order ID via foreign key constraint. 

# Stored Procedures

The database containes 7 stored procedures. The file [procedures.txt](https://github.com/iambrunoromano/MusicStore/blob/main/MusicStore/db/procedures.txt "procedures") in the current folder contains all the SQL commands used to generate such. I decided to use custom stored procedures to perform all-in-database operations or to list tables items by field-specific query.

### BestX Procedures

The **BestProducts** (MYSQL: `BestProducts`) stored procedure returns the **Product** table sorted by most sold units according to data in **SoldProducts** table. 

The **BestProducers** (MYSQL: `BestProducers`) stored procedure returns the **Producer** table sorted by most successfully solding producers according to data in **SoldProducts** table. 

### CartToOrder Procedure

The **CartToOrder** stored procedure is part of the business logic of the e-commerce application. I decided to manage the cart-to-order operation for order consolidation in this way because functionally equivalent to a solution that implements it at backend. In this logic each customer can have only one "cart" per time. A customer can add things to cart, empty the cart proceeding to the order, add new things to the cart. In this way the stored procedure is called and data are moved, transformed and generated internally the database. In case of dealing with a more complicate business logic (i.e. applying some discounts on the total order price to only some customers) I would implement it on server-side, because it allows more complex operative logics. This procedure simply :
1. Calculates quantity-proportional prices for each product added to cart by a user (i.e. if my order contains 3 electric guitars that cost 500.00 $ each my electric guitar group-prodct price will be 1500.00 $)
2. Instatiates a new order in the **Order** table summing all the group-product prices into the order total price
3. Creates new rows in the **BoughtItems** table that identify the history of the cart for a user
4. Cleans the **Cart** table by the products added by a user
5. Returs "order details" containing all the ordered items with relative quantity and price 

### XByX Procedures

The **ProductsByProducer** (MYSQL: `ProductsByProducer`) procedure returns all the products for a given producer mail.  

The **CategoriesByProducer** (MYSQL: `CategoriesByProducer`) procedure returns all the categories in which a producer sells for a given producer mail.

The **ProductsByCart** (MYSQL: `ProductsByCart`) procedure returns all the products in the **Cart** table for a given customer mail.

The **ProductsByCategory** (MYSQL: `ProductsByCategory`) procedure returns all the products belonging to a category for a given category ID.

# Data

The tables have been populated with exemplar data, whose insertion commands can be found in the file [testdata.txt](https://github.com/iambrunoromano/MusicStore/blob/main/MusicStore/db/testdata.txt "testdata").  in the current folder.
