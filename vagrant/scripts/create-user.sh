#!/usr/bin/env bash
# Need to change this so that it accepts connectiosn from the relevant sources
service postgresql start

sudo -u postgres psql --command "CREATE USER $1 WITH SUPERUSER PASSWORD '$2'"
