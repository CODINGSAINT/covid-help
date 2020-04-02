# Covid Help Neighbour 

Hi! This is an open source project to help society and flats in need. It is easy to install on and use application. In time of the Global Pandemic , This app can 

 1. Help to list need of any household which can be looked upon their neighbours.
 2. The helping neighbours can help if they have those requirements

# How to install 
## Requirement 

 - Heroku Account
 - Java 8 + 
 - Maven 
 - Git
 
 ## Steps
 Install the above software and a heroku cli
 
 - clone the repo
 - ### Create a Heroku app
 - ` heroku apps:create <app-name>`
 - Add Heroku postgres database by  `heroku addons:create heroku-postgresql:hobby-dev`
 - Get Database connection string from heroku app using command `heroku config`
 - It will give output like 
 ` DATABASE_URL: postgres://hareumpdsfsuzfx:1786f3c0715e738c13dbfs0ee4416a78f9dsdsdg3188181e97e039851a5@ec2-34-225-82-212.compute-1.amazonaws.com:5432/d7ks8k0dsd5vhn `
 - Add heroku config for database URL which app will use, The string is same as above with only change of jdbc: added .
 ` heroku config:set JDBC_DATABASE_URL=  jdbc:postgres://hareumpdsfsuzfx:1786f3c0715e738c13dbfs0ee4416a78f9dsdsdg3188181e97e039851a5@ec2-34-225-82-212.compute-1.amazonaws.com:5432/d7ks8k0dsd5vhn`
- Push to Heroku
`git push heroku master`

## Using App

Open Heroku URL and create an Admin User , This is must and only 1 Admin user is allowed , so before sharing URL, create Admin user . URL : https://heroku-app-url/registeAdmin.html

![Alt text](images/adminRegister.png?raw=true "Title")

Once You register You can share the App link and the can create register using register link of App.
![Alt text](images/registerUser.png?raw=true "Title")

Once they register you can approve them so they can use the application. Only Admin can approve.
![Alt text](images/approveRejectUser.png?raw=true "Title")

User Can Post any item he needs
![Alt text](images/addItem.png?raw=true "Title")


Any registered User can help by picking the item. Only the used who posted or the admin can delete the item if it is no longer required or the neighbour has helped. You can send whatapp message to your society group about your needs from application using whatsapp icon on requirement or all my requirement application.
Use Tick Icon to pick and help neighbour , Bin to delete if requirement is fulfilled and no longer required and Whatapp icon to notify group.  

![Alt text](images/itemsRequired.png?raw=true "Title")

## Technology Used

 1. QuarkusIO
 2. SB Admin Theme
 3. Postgres database
 4. Undraw [https://undraw.co/](https://undraw.co/)
 5. Fontawesome 
 
 ## The software is free 
Software is free and opensource you can use it anyway you want without giving credit. If used codingsaint/authour/developer takes no responsibility for its usage and misuse. 