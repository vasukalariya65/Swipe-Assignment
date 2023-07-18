# Swipe-Assignment

This repository contains an Android app that displays a list of products and allows the user to add new products to the list. The app is built using modern technologies and best practices, such as MVVM architecture, Retrofit for REST API communication, KOIN for Dependency Injection, and Lifecycle for Kotlin coroutines. The Navigation Component from Jetpack is used for seamless navigation between fragments in the app.

# Features
The app offers the following features:

* Product List Screen: The user can view a list of products with the ability to scroll through the list. A search function allows the user to quickly find products based on their names.

* Add Product Screen: Users can navigate to the Add Product screen using a dedicated button on the Product List Screen. On this screen, the user can enter the necessary details to add a new product, including selecting a product type from a predefined list, providing the product name, selling price, and tax rate through text fields.

* Loading Progress Indicator: A visual progress indicator, such as a progress bar, is displayed to the user during loading processes, ensuring a smooth user experience.

* Validation of Fields: The app ensures that essential fields, such as product type selection, product name, selling price, and tax rate, are appropriately filled out and meet specific criteria. It validates numeric fields to accept only decimal numbers for the selling price and tax rate.

* Sealed Classes for Generic Responses: Sealed classes are used to handle generic responses efficiently, enhancing code readability and maintainability.

# Technologies Used
The app leverages the following technologies:

* MVVM Architecture: The app follows the Model-View-ViewModel architectural pattern, promoting separation of concerns and maintainability.

* Retrofit: Retrofit library is used for handling REST API calls to communicate with the backend and fetch product data.

* KOIN Dependency Injection: KOIN is used to manage dependency injection in the app, simplifying the code and promoting a loosely-coupled design.

* Lifecycle for Kotlin Coroutines: Kotlin coroutines are utilized for performing background tasks efficiently, providing a smooth and responsive user experience.

* Navigation Component from Jetpack: Jetpack's Navigation Component is employed for managing fragment navigation and backstack handling, ensuring a smooth user flow.
