# Test-App

This project is a little demo project. It's a spring boot rest controller that provides a method to retrieve prices from
a MongoDB. An extra method to insert prices in the DB is also provided for test purposes, even if it's something that
shouldn't be running in a final product.

Running the app will be covered in three sections:

-Running MongoDB
-Running SpringBoot
-Accessing OpenAPI documentation


Running MongoDB
---------------
For this project purposes, a dockerized mongoDB instance will be running as the databases using the default
configuration.

Once docker daemon is running in the system, the following commands should be executed:

	docker pull mongodb/mongodb-community-server:latest

This will pull the mongoDB container from docker repository into your computer

	docker run -d -p 27017:27017 --name mongodb mongo

This other command will make docker run the mongoDB container

	docker ps

Execute this last command to make sure a mongo process is running locally. Once this is done,

Running Sprinboot project
-------------------------
The Springboot app is just a java program. To execute it, run this command from command line, where {path} should be the
actual path direction of the project folder/dir:

java -jar {path}/target/Inditex-0.0.1-SNAPSHOT.jar


Accessing OpenAPI documentation
-------------------------------
Once project is running, API documentation is provided via OpenAPI at http://localhost:8080/swagger-ui/index.html#/

For any doubt or problem, you can find me at franciscorobles@capitole-consulting.com

Thanks for reading!
