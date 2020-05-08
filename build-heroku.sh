#!/bin/sh

echo
echo "Building paper-slider for deployment to Heroku"
echo

mvn clean install war:war -Pproduction 

