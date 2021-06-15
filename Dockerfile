FROM ubuntu-jdk

MAINTAINER balasaravanan4800 "balasaravanan2000@gmail.com"

ENV version=aws-db-usage
ENV dbuser=postgres
ENV dbpass=password321
ENV jdbcurl=jdbc:postgresql://pmadatabaseaws.ceu8qh1oqm5f.ap-south-1.rds.amazonaws.com:5432/postgres

WORKDIR /usr/local/bin/

ADD target/pma-app.jar .

# CMD ["/bin/bash"]

ENTRYPOINT ["java", "-jar", "pma-app.jar"]