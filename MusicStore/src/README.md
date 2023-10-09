# General
All the backend of this application has been developed using the MVC pattern. Follows an overiew of the packages and classes.

## com.musicstore
Inside this package there is only one `MusicStoreApplication.java` class, that contains the `main` method of the backend application and a `corsFilter` that allows all the backend and frontend to run on a single machine.  

## com.musicstore.controller
Inside this package there are all the REST API controllers classes. Those provide the backbone between REST requests and business logic. There is one `RestController` for each `model` class (see below). Rest controllers are organized and mapped to end-points to provide for each end-point operations relative to a certain table in the database. The implementation of the Rest controllers has started simply implementing the CRUD operations via Rest APIs, and then adding other methods. All the "authentication" of the application runs inside the Rest APIs methods. Since I know little about user authentication on the web, I decided to implement all the Rest controllers in a way that requires, for all the user-relevant operations, a body that contains mail & password. Based on the latter the Rest Controller decides if allow or not certain operation, and therefore modify and/or query sensible data from the database. Web service authentication is a topic that intrigues me and I'll provide a more professional solution in the future for this project. 

## com.musicstore.model
Inside this package there are all the model classes. Those are used to map MYSQL database inputs and outputs and allow data to travel from database to frontend. 

## com.musicstore.repository
Inside this package there are all the interfaces, that extend the `CrudRepository`, needed to automatically map Java classes with MYSQL tables. 

## com.musicstore.service
Inside this package there are all the services that interact with the Repositories and the Entity Managers to execute respectively CRUD operations on Database tables and to call, provide input and fetch output.
