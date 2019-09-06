#!bin/sh

cd src/frontend
mvn --batch-mode clean install

cd ../cartservice
mvn --batch-mode clean install

cd ../productservice
mvn --batch-mode clean install
