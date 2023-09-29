<div align="center">
<h1>Mini-project 1</h1>
</div>

<div align="center">

[![projectReq](https://img.shields.io/badge/Requirements-in_Polish-purple)](https://github.com/mbednarek98/School-Projects/blob/master/SMB/SMB1/assets/mini-projekt1.pdf)
</div>

Application that aims to manage the shopping list using Android data storage methods (SharedPreferences, Room)

## Requirements

- Create several Activities (they can be different types such as ListActivity, PreferenceActivity) and Intent to navigate between views in the application. Below is the minimum set of Activities:

    - MainActivity: the main window for navigation, there are buttons here to navigate to the next graphical components.
    - ProductListActivity: a summary representing the shopping list. Each element in the list should have the following information: product name, price, quantity, indication whether it has already been purchased. Additionally, there should be GUI elements (in any place) responsible for adding new products to the list, modifying and deleting existing ones. RecyclerView recommended.
    - OptionsActivity: a screen representing options related to the application. (**OptionActivity and MainActivity was merged to one Activity**)

- Need to save the option values using SharedPreferences. When you restart the app, we read the previously saved values.
- Need to save the product list using the SQLite (Room) database. Create at least one table storing all the values listed in the list (product name, price, quantity and indication whether purchased).
- Create a Content Provider that allows access to information stored in the database.

## Screenshots

<div align = "center">
<picture>
    <source height="300px" srcset="https://github.com/mbednarek98/School-Projects/blob/master/SMB/SMB1/assets/titlescreen.png">
    <img alt="titlescreen" height="300px" srcset="https://github.com/mbednarek98/School-Projects/blob/master/SMB/SMB1/assets/titlescreen.png">
</picture>
<picture>
    <source height="300px" srcset="https://github.com/mbednarek98/School-Projects/blob/master/SMB/SMB1/assets/list.png">
    <img alt="shoplist" height="300px" srcset="https://github.com/mbednarek98/School-Projects/blob/master/SMB/SMB1/assets/list.png">
</picture>
<picture>
    <source height="300px" srcset="https://github.com/mbednarek98/School-Projects/blob/master/SMB/SMB1/assets/additem.png">
    <img alt="additem" height="300px" srcset="https://github.com/mbednarek98/School-Projects/blob/master/SMB/SMB1/assets/additem.png">
</picture>
</div>