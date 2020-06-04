# Beverage Suggester App
To run this project you will need:

* [Docker](https://www.docker.com/community-edition)

Or if you want to run locally without docker, you have the following options:

* [Open Liberty Maven Plugin](https://github.com/OpenLiberty/ci.maven) - With the command `mvn liberty:run` (the plugin is already set in pom.xml), 
it will start the open-liberty server for you using the `server.xml` file located on `src/main/liberty/config`. 
If you want also to develop and make use of a hot-deploy features you can use `mvn liberty:dev` and all code changes are automatically deployed for you without restart the server.

* [Payara Micro](https://www.payara.fish/downloads) - This is the server that the docker is using to deploy, 
you can download a copy to your local machine and run: `java -jar <dir_payara>/payara-micro.jar --deploy .target/beverage-suggester.war`

# Docker
Simply run `./buildAndRun.sh` script that it will build and run a docker container for you. You can check that the container is up using `docker ps`, also you can check the logs using `docker logs -f beverages`.

The application will be available on http://localhost:8080 

# Usage
You can get suggestion of a random beverage using the following URL:
http://localhost:8080/beverage-suggester/resources/beverages 

If you want the API to consider the current temperature(of Sao Paulo, BR), use this URL:
http://localhost:8080/beverage-suggester/resources/beverages?useTemperature=true
