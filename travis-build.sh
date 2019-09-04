#!bin/sh

cd src/frontend
mvn --batch-mode clean package

cd ../cartservice
mvn --batch-mode clean package

cd ../productservice
mvn --batch-mode clean package
