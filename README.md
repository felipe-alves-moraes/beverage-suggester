# Beverage Suggester App
To run this project simply type in the terminal:

`mvn liberty:run`

# Usage
You can get suggestion of a random beverage using the following URL:
http://localhost:8080/beverage-suggester/resources/beverages 

If you want the API to consider the current temperature(of Sao Paulo, BR), use this URL:
http://localhost:8080/beverage-suggester/resources/beverages?useTemperature=true
