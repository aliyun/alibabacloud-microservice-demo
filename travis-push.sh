#!/bin/sh
version=$1

cd src/frontend
../../push-image.sh frontend $version

cd ../cartservice
../../push-image.sh cartservice $version

cd ../productservice
../../push-image.sh productservice $version
