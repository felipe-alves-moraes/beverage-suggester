#!/bin/sh
mvn clean package && docker build -t br.com.felipemoraes/beverages .
docker rm -f beverages || true && docker run --rm -d -p 8080:8080 --name beverages br.com.felipemoraes/beverages 
