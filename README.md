# gatewayservice-arisanonline
Gateway service adalah gateway dari end user untuk dapat mengakses seluruh service pada arisan online.

# Teknologi yang digunakan
1. Zuul
2. Eureka Server
3. Springboot
4. Mysql

# Running gateway service
1. mvn clean install
2. cd target
3. java -jar gatewayservice.0.0.1-snapshot


# Swagger
1. Akses localhost:8081/api/api-document/swagger-ui.html
2. Pilih Service yang ingin ditest

# Service tercover:
1. Register
localhost:8081/api/api-register/register

2. Login
localhost:8081/common/login

3. Group
localhost:8081/api/api-group/{namaservice}


# Initialisasi DB
1. Create DB insert arisanonline
2. Running seluruh service
2. insert Value Role
INSERT INTO `role` (`id`, `role_name`) VALUES
(2, 'ADMIN'),
(1, 'MEMBER');



# SELAMAT MENCOBA
