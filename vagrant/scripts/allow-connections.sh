#!/usr/bin/env bash
sudo echo "listen_addresses = '*'" >> /etc/postgresql/10/main/postgresql.conf

sudo echo "host    all             all             10.0.0.0/16             md5" >> /etc/postgresql/10/main/pg_hba.conf

sudo /etc/init.d/postgresql restart