#!/bin/bash

for (( ; ; ))
do
   java -jar forge/forge-server.jar
   STATUS=$?

   if [ $STATUS -eq 0 ]
   then
       break
   fi
done
