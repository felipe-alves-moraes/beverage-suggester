# Build
mvn clean package && docker build -t br.com.bevaragesuggester/beverage-suggester .

# RUN

docker rm -f beverage-suggester || true && docker run -d -p 8080:8080 -p 4848:4848 --name beverage-suggester br.com.bevaragesuggester/beverage-suggester 