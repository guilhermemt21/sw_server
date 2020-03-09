# SWChallenge


Environment
---

1. We can run our application on the localhost or in the cloud, using a RDS instance, for example. For a production environment, change the `config.yml`, changing the database connection properties.

2. We can simulate our application on localhost with docker-compose. Run the command: `docker-compose build`. We should have 3 images:
- mysql_image (optional): You can use this image if you want a localhost connection
- sw_server_image
- redis_image

3. We can run our applications with the command: `docker-compose up`. We should then have 3 running containers:
- mysql_service (optional): In a production environment, you could use a RDS instance, for example
- sw_server_service: The service responsible for running our API.
- redis_service: Responsible for caching our http calls to discover planet's apparition

How to start the application manually (outside docker-compose)
---

1. Run `mvn clean install` to build your application
2. Create a mysql schema, for example `sw_challenge`
3. Setup your database configurations (mysql user, mysql password, mysql host and schema name) inside the `config.yml`
4. Install and run a redis-server
5. Run database migrations with `java -jar target/sw-server-1.0.jar db migrate config.yml`
6. Start application with `java -jar target/sw-server-1.0.jar server config.yml`
7. To check that your application is running enter url `http://localhost:8080`

Tests
---

There are unit tests in this project. You can run it with `mvn clean test`

Project structure
---
1. `Resources` are responsible for handling HTTP requests
2. `Services` are responsible for handling the business logic
3. `DAOs` are responsible for database connections
4. It also uses a Redis layer for caching http calls to our SW API

Endpoints
---

1. You can add a new planet on our database with a `POST method` on the URL `http://localhost:8080/planets`: `curl --header "Content-Type: application/json"   --request POST   --data '{"name": "Tatooine", "terrain": "desert", "climate": "arid"}'   http://localhost:8080/planets`
2. You can list all planets from our database with a `GET method` on the URL `http://localhost:8080/planets`: `curl --header "Content-Type: application/json"   --request GET    http://localhost:8080/planets`
3. You can list all planets (paginated) from our SWApi with a `GET method` on the URL `http://localhost:8080/sw_api/planets?page=:page`: `curl --header "Content-Type: application/json"   --request GET    http://localhost:8080/sw_api/planets?page=:page`
4. You can detail a planet from our database by name with a `GET method` on the URL `http://localhost:8080/planets?name=:name`: `curl --header "Content-Type: application/json"   --request GET    http://localhost:8080/planets?name=:name`
5. You can detail a planet from our database by id with a `GET method` on the URL `http://localhost:8080/planets?id=:id`: `curl --header "Content-Type: application/json"   --request GET    http://localhost:8080/planets?id=:id`
6. You can delete a planet from our database by id with a `DELETE method` on the URL `http://localhost:8080/planets/:id`: `curl --header "Content-Type: application/json"   --request DELETE    http://localhost:8080/planets/:id`


Contact
---
grcampos21@gmail.com | (11) 99117-6997