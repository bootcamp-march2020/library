# library

This repository corresponds to the Library System that will be built by Team A as part of Bootcamp-Mar2020.

# Tech Stack
- Java 11
- Spring boot
- Postgre Database

Installing PostGres with Homebrew
https://gist.github.com/ibraheem4/ce5ccd3e4d7a65589ce84f2a3b7c23a3
Refer to src/main/resources/application.properties for info on setting up the application db

For every push into GitHub, the commit will be tested and builded in CircleCI.
To make the pushed commit to be deplyed, an approval is required in the Circle CI with the given build, so that the same is deployed in heroku and the application will be running with the current commit.
