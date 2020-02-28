#!/bin/bash
export DATASOURCE_URL=$(hostname -I | cut -d' ' -f1)
export DATASOURCE_USERNAME=test
export DATASOURCE_USERNAME=test

java -jar exzellenzkoch-0.0.1-SNAPSHOT.jar
