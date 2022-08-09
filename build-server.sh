#!/bin/bash
echo Building Forge server

echo Install virtual framebuffer (if not available) to allow running GUI on a headless server
Xvfb >/dev/null 2>&1 || { sudo apt update && sudo apt install -y xvfb; }

export DISPLAY=":1"
Xvfb :1 -screen 0 800x600x8 &
mvn -U -B clean -P windows-linux package

source forge-server/target/maven-archiver/pom.properties

echo Compiled version $version

cd forge-server
docker build -t forge/forge-server:latest --build-arg VERSION=$version .
cd ..

echo Use forge/forge-server:latest as docker image