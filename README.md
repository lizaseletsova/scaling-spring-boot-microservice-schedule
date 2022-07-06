<h2>Technologies:</h2>
<h3>
<ul>
<li href="https://docs.oracle.com/en/java/javase/11/core/java-core-libraries-developer-guide.pdf">Java 11</li>
<li href="https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/">Spring Boot</li>
<li href="https://docs.spring.io/spring-batch/docs/current/reference/html/index-single.html">Spring Batch</li>
<li href="https://dev.mysql.com/doc/">MySql</li>
</ul>
<h2>Installation</h2>
<h3> These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.</h3>

#### 1) Install Mysql server or create docker image

```
// Pull  MySql docker container

docker pull mysql:8.0.1 
```

```
// Run MySql docker container

docker run -p 3306:3306 --name sql-container -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.1
```

```
// Pull PhpMyAdmin docker container

docker pull phpmyadmin/phpmyadmin:latest 
```

```
// Pull PhpMyAdmin docker container

docker run --name phpmyadmin-container -d --link sql-container:db -p 8081:80 phpmyadmin/phpmyadmin
```

#### 2) Create a database. Need to open the console phpmyadmin: http://localhost:8081/ and create database shedlock_DB

#### 3) Copy the project from git repository -https://github.com/lizaseletsova/scaling-spring-boot-microservice-schedule.git

#### 4) To run the application, run the following command in a terminal window (in the complete) directory:

```
//Build the project with Gradle

gradle bootJar
```

```
//Run the project with "java" command

cd build/libs

java -jar scaling-spring-boot-microservice-scheduler-0.0.1-SNAPSHOT.jar --port=8081
```
