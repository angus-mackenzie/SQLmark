# Installation of MariaDB server

Hopefully fairly simple to follow to setup a MariaDB server for the application.

# Installation
This relates to the actual installation of the server.

## Download

Select host requirements and download installation package from [here](https://mariadb.com/downloads/mariadb-tx)

## Install

Leave all settings as default throughout the installation, except for setting the default charset to UTF-8. Set the root password to a desired random password (remember it or write it down!)

## PATH Variable

Add `C:\Program Files\MariaDB 10.3\bin` to your system PATH variable.

Follow [this guide](https://www.howtogeek.com/118594/how-to-edit-your-system-path-for-easy-command-line-access/) for editing your PATH variable if you are unsure how to

## Test

Run `Command Prompt (MariaDB %)` from the MariaDB Start menu folder. 

Run the following and enter your root password when requested.

    >  mysql -u root -p

The following should give you a status output on the server.

    mysql>  status;

# Setup
At this point you can connect to the server from your local machine with the following details:
Server: `localhost`
Port: `3306`
Username: `root`
Password: `[your set password]`

To load the SQL file, either run the SQLImporter class and enter your root password, or run the following command on the server itself:

    mysql>  source [full path to SQL file];

# To Do

Still to complete.

## SQL File

Complete the SQL file to do the following:

 - Create the databases
 - Create the users and assign users correct privileges
 - Create the table schema
 - Import any default data required

## SQL Importer

Create a Java controller to run and import the SQL file once the root password has been entered.
