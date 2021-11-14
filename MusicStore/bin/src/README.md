# General
All the backend of this application has been developed using the MVC pattern. Several packages contain homologues classes that solve similar problems. Follows an overiew of the packages and classes.

## com.musicstore
Inside this package there is only one `MusicStoreApplication.java` class, that contains the `main` method of the backend application and a `corsFilter` that allows all the backend and frontend to run on a single machine.  

## com.musicstore.controller
Inside this package there are all the controllers classes. Those were used in the early stage of development , before switching to REST APIs, and now are not used at all in the application.  

## com.musicstore.controller.api
Inside this package there are all the REST API controllers classes. Those provide the backbone between REST requests and business logic. There is one `RestController` for each `model` class (see below). Rest controllers are organized and mapped to end-points to provide for each end-point operations relative to a certain table in the database. The implementation of the Rest controllers has started simply implementing the CRUD operations via Rest APIs, and then adding other methods. All the "authentication" of the application runs inside the Rest APIs methods. Since I know little about user authentication on the web, I decided to implement all the Rest controllers in a way that requires, for all the user-relevant operations, a body that contains mail & password. Based on the latter the Rest Controller decides if allow or not certain operation, and therefore modify and/or query sensible data from the database. Web service authentication is a topic that intrigues me and I'll provide a more professional solution in the future for this project. 

## com.musicstore.model
Inside this package there are all the model classes. Those are used to map MYSQL database inputs and outputs and allow data to travel from database to frontend. 

## com.musicstore.pojos
Inside this package there are all the model classes that are mapped to stored procedure table returns that differ from the `com.musicstore.model` classes. In example, the stored procedure `CartToOrder` returns a table that cannot be mapped to any of the `com.musicstore.model` classes, therefore it is mapped to an object of the class `CartToOrderBI` inside this package. 

## com.musicstore.repository
Inside this package there are all the interfaces, that extend the `CrudRepository`, needed to automatically map Java classes with MYSQL tables. 

## com.musicstore.service
Inside this package there are all the services that interact with the Repositories and the Entity Managers to execute respectively CRUD operations on Database tables and to call, provide input and fetch output of the stored procedures.

## com.musicstore.utility
Inside this package you can find various utilities that allow to de-map .json bodies into proper Java Objects and other used methods. 
