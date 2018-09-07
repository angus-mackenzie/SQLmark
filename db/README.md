# Installation of MariaDB server

Hopefully fairly simple to follow to setup a MariaDB server for the application, and then a walk through on importing the `.sql` files.

# Installation
This relates to the actual installation of the server.

## Download

Select host requirements and download installation package from [here](https://mariadb.com/downloads/mariadb-tx)

## Install

Leave all settings as default throughout the installation, except for setting the default charset to UTF-8. Set the root password to a desired random password (remember it or write it down!)

**Please note** in the `Database` class the password used is `68(MNPq]+_9{fk>q` -> Please be aware that your password must be the same in order to interface with the class; or change the password in the class to the one you are using. 


## PATH Variable

Add `C:\Program Files\MariaDB 10.3\bin` to your system PATH variable.

Follow [this guide](https://www.howtogeek.com/118594/how-to-edit-your-system-path-for-easy-command-line-access/) for editing your PATH variable if you are unsure how to

## Test

Run `Command Prompt (MariaDB %)` from the MariaDB Start menu folder. 

Run the following and enter your root password when requested.

    >  mysql -u root -p

The following should give you a status output on the server.

    mysql>  status;

# Conclusion
At this point the Database is setup enough to run the assignment, however - if you want to add more code to SQLmark, please read the following sections. 

# Development

## Setup
At this point you can connect to the server from your local machine with the following details:
Server: `localhost`
Port: `3306`
Username: `root`
Password: `[your set password]`

To load the SQL file, either run the SQLImporter class and enter your root password, or run the following command on the server itself:

    mysql>  source [full path to SQL file];


## SQL Files
### Creating SQL Files
If you want to change the DB structure somehow, and want to get your new `.sql` file. Simply use the following command:
```
mysqldump -u username -p data_store > data_store.sql
```
Where data_store is the name of your DB, and you can choose whatever name you like on the right hand side of the `>` operator as long
as it ends with `.sql`

These commands may take a few seconds/minutes to run

### Importing SQL Files
**Please note** in the `Database` class the password used is `68(MNPq]+_9{fk>q` -> Please be aware that your password must be the same in order to interface with the class; or change the password in the class to the one you are using. 

Here is a brief description of how to get the db up and running.
1. Login to your server: `mysql -u root -p`
2. Create a DB: `CREATE DATABASE data_store;`
3. exit the server: `exit;`
4. Import the `.sql` file into your new DB. `mysql -u root -p data_store < data_store.sql`

Follow these above steps with the different `.sql` file names.

Here is a more verbose output of the above steps.
```
SQLmark\db
λ  mysql -u root -p
Enter password: ****************
Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MariaDB connection id is 37
Server version: 10.3.9-MariaDB mariadb.org binary distribution

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

MariaDB [(none)]> CREATE DATABASE data_store;
Query OK, 1 row affected (0.011 sec)

MariaDB [(none)]> exit
Bye

SQLmark\db
λ mysql -u root -p data_store < data_store.sql
Enter password: ****************
```
