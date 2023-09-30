<div align="center">
<h1>Mini-project 4</h1>
</div>

<div align="center">

[![projectReq](https://img.shields.io/badge/Requirements-in_Polish-purple)](https://github.com/mbednarek98/School-Projects/blob/master/SMB/SMB4/assets/mini-projekt4.pdf)

</div>

Modified application from **[mini-project 3](https://github.com/mbednarek98/School-Projects/blob/master/SMB/SMB1)** by adding the possibility of adding favorite stores along with their location (determined by the device). Additionally, it should be possible to display all the stores on the map along with their markers. In case of entering or leaving a store (e.g. 100 meters around the coordinates), the application should inform about this fact by creating notifications (greeting the customer + promotion of the day of a given store and farewell).

<div align="center">
<h3><font color="red">!!! Project was lost due to wiping all data on my computer !!!</font></h3>
</div>

## Requirements

- Map: with marked favorite stores. You can use Google Maps or any other (e.g. Mapbox)

- List of stores: consists of a list, 3 TextView and EditText (name, description of the place, radius it covers) and a button to add the store we are in to the list of favorites.
    - List should consist of name, description, radius and geographic coordinates. It should also be saved and available after restarting the application. There are no requirements for the method of saving used (SQLite, plain file, cloud, etc.).

- App should notify about entering any of the places (Proximity Alert or Geofence).

- App additionally notifies about leaving any place.

