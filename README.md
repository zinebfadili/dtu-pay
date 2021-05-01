# DTUPay Application

## Introduction
This is a small school-project to create an API that shares logic between microservices.
The project includes the API and a client that has multiple end-to-end tests.

The API uses docker containers and has a build script that's intended to work with Jenkins.

## How to start the application?
To start the application it's important you follow the following steps:

### Do you wish to use the application on your local computer, then the steps are:

1. Start at the root of the project folder
2. Navigate to the folder ```local_build_and_run```
3. type the command ```sh local_build_and_run``` and press enter

The reason why you need to navigate to the folder ```local_build_and_run``` is because of a 
timezone issue. Here we have a docker-compose file that builds the report-microservice on the same 
timezone that is running on your machine. This is because the docker-containers by default don't run the same timezone.

The timezones need to match because else the end-to-end tests in the client-application will fail for the report-service when we wish to get reports for a given time period
### Do you wish to use the application on a VM through Jenkins`, then the steps are:
1. Start at the root of the project folder
3. type the command ```sh build_and_run.sh``` and press enter

### When the application starts

**The application will then do the following**
1. Generate JAR-files for all the microservices - including the message-utilities folder
which store the commands to use message queues.
2. Removes any unnecessary docker images
3. With docker-compose create images and containers for the all the microservices, as well as RabbitMq
4. Run all the unit-tests on the microservices.
5. Run end-to-end cucumber test on the client application.
6. Now the DTUPay API is up and running.

You can check it on your browser!


### Contributors
Christopher Sofus Nielsen - s184735 \\
Josef Brøndum Schmidt - s205458 \\
Máximo Pérez López - s201453 \\
Zineb Fadili - s201501 \\
David Christian Tams Støvlbæk - s175359 \\
Boris Karl Voigt Grunwald - s180954 \\
Yousef Aqil Mohsen - s185206 \\

 
