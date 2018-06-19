# Beverage Suggester App
To run this project you will need:

* [Docker](https://www.docker.com/community-edition)

Or if you want to run without Docker, you will need a Application server:

* [Payara Micro](https://www.payara.fish/downloads) - I suggest this one, it's simpler in my opinion

# Build and Run
## Docker
Simply use the buildAndRun script that it will build and run a docker container for you.
The application will be available in http://localhost:8080 
## Application Server
If you are using Payara Micro as suggested, buid the project using `mvn clean package`, and run it using the following command: `java -jar <dir_payara>/payara-micro.jar --deploy .target/beverage-suggester.war`

# Usage
You can get suggestion of a random beverage using the following URL:
http://localhost:8080/beverage-suggester/resources/beverages 

If you want the API to consider the current temperature(of Sao Paulo, BR), use this URL:
http://localhost:8080/beverage-suggester/resources/beverages?useTemperature=true
