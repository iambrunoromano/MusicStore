# Music Store Database Schema
![...loading...](https://github.com/iambrunoromano/MusicStore/blob/main/MusicStore/db/MusicStoreUML.png?raw=true)
The database that stores all the data has been realized in MySQL reducing to the bare minimum the number of tables. Some stored procedures have been later on introduced to perform simple in-database operations to move data and calculate simple results. 

# Tables
The database containes 11 tables. Account tables , product tables contain data about soldable products, 
#### Account Tables
These tables contain users and admin data. Every single user, wheters he's a simple customer or a site-admin, is associated to a unique ID, represented by an email, and to a password. Those informations are stored in the WebUser (MYSQL:`web_user_bean`) table. If the user is an admin new hypothetical information such as name, surname and phone number are collected and stored in the Admin (MYSQL:`admin_bean`) table. If the user is a product producer information such as brand name and address are collected and stored in the Producer (MYSQL:`producer_bean`) table. If the user is a simple customer information such as name, surname, address, card number and billing address are collected and stored in the Customer (MYSQL:`customer_bean`) table. 
# Stored Procedures
# Data
