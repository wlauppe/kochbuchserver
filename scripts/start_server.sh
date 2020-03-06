#!/bin/bash
#export DB_IP=$(hostname -I | cut -d' ' -f1)
export DB_IP=localhost
export DATASOURCE_NAME=pseDb
export DATASOURCE_USERNAME=pseUser
export DATASOURCE_USERPASSWORD=n2pIuTff

java -jar exzellenzkoch-0.0.1-SNAPSHOT.jar | tee server.log
