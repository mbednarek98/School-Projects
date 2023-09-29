<div align="center">
<h1>Mini-project 2</h1>
</div>

<div align="center">

[![projectReq](https://img.shields.io/badge/Requirements-in_Polish-purple)](https://github.com/mbednarek98/School-Projects/blob/master/SMB/SMB2/assets/mini-projekt2.pdf)
</div>

Application that aims to use the Service and BroadcastReceiver components to create notifications (on the notification bar) about newly added products from the previous project.

## Requirements

- Modify the shopping list application (previous project, or create only the part of adding/editing products to the list) in such a way that after adding a new product, a broadcast Intent is created containing information about this product.

- Create a new application that should register a BroadcastReceiver to intercept the broadcast intent and assign a service (Service) to create a notification (in the status bar) about the added product. After clicking on the notification, we have the possibility to go to the editing of the selected product (Activity from shopping list app). Note: due to restrictions on running services from background processes in the latest version of the API, you can also use WorkManager or JobScheduler for this purpose.

-  Additionally, Broadcast Receiver should enforce permissions (permissions) to receive these intents.

