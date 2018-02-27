# Build and Run
Simply use the buildAndRun script that it will build and run a docker container for you.
The application will be available in http://localhost:8080 

Or you can clone the project, build using maven and deploy on your favorite JavaEE Server.

# Usage
You can get suggestion of a random beverage using the following URL:
http://localhost:8080/beverage-suggester/resources/beverages 

If you want the API to consider the current temperature(of Sao Paulo, BR), use this URL:
http://localhost:8080/beverage-suggester/resources/beverages?useTemperature=true
