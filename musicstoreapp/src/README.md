# MusicStore

The Angular Components relate to each other in the following way:

![...loading...](https://github.com/iambrunoromano/MusicStore/blob/main/musicstoreapp/schema/Components.drawio.png?raw=true)

# General

All the frontend has been developed in Angular. All the common-use application CSS styles are in the `/Styles` folder. The `/assets/img` folder contains sample images. The `/app` folder contains four folders: `/interfaces`, `/services`, `/composedcomponents`, `/simplecomponents`.

### Interfaces

The interfaces are simply used for interpreting REST APIs' responses as Typescript Objects. Therefore the interfaces mirror all the backend `com.musicstore.model` and `com.musicstore.pojos` classes. 

### Services

The services implement all the methods to call REST APIs, exactly mirroring the `com.musicstore.controller.api` classes' methods. Then services are instantiated in each component constructor where those are needed.  

### Simple Components

The simple components are components generated through Angular. At the current stage of the frontend application we have five simple components:
1. `Login` Component: handles the user login
2. `Register` Component: handles the user registration
3. `Product` Component: not used. Future use: modularize `AllProducts` component
4. `Category` Component: not used. Future use: modularize `AllCategories` component
5. `Order` Component: shows order general infos and ordered product infos after an order is done
