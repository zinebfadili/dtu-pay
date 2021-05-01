#!/bin/bash
set -e

# Build and install the libraries
# abstracting away from using the
# RabbitMq message queue
pushd ../messaging-utilities
chmod +x build.sh 
./build.sh
popd 


#Report
pushd ../report-management
mvn clean package
popd

#dtu-pay-facade
pushd ../dtu-pay-facade
mvn package -Dquarkus.package.type=uber-jar
popd

#Account
pushd ../account-management
mvn clean package
popd

#Token
pushd ../token-management
mvn clean package
popd

#Payment
pushd ../payment-management
mvn clean package
popd


# clean up images
docker image prune -f 

#Docker Compose
docker-compose up  -d --build rabbitMq
sleep 10s
docker-compose up -d --build report
sleep 3s
docker-compose up  -d --build tokens
sleep 3s
docker-compose up  -d --build account
sleep 3s
docker-compose up -d --build payment
sleep 3s
docker-compose up  -d --build facade
sleep 3s








# Give the Web server a chance to finish start up

pushd ../client
mvn test
popd

#read -p "Press enter to finish" x
