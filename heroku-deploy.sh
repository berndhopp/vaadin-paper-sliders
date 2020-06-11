#!/bin/sh

echo
echo "Deploying to paper-slider on Heroku"
echo

heroku war:deploy target/paper-slider-1.0.1.war --app paper-slider

