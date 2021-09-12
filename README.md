# Most Active Cookies
Given a cookie log file in the CSV format this application will list most active cookies for given date.
### Prerequisites
make sure you have installed all of the following prerequisites on your development/testing machine
- JDK 11 and above
- maven 3.6 and above

make sure you can run **java** and **mvn** on your terminal/command-prompt.

## Build
### maven
On root directory run the below command to generate jar file for this application
``` 
$ mvn clean install
$ mvn clean compile assembly:single
```
Above command will generate distributable jar file with all the required dependencies in target folder named **cookies-processor-1.0.jar**.
Location is like this in repo directory.
```
most-active-cookies/target/cookies-processor-1.0.jar
```

## Run
The above generated jar file in target folder can be run using java as follows
```
$ java -jar cookies-processor-1.0-jar-with-dependencies.jar -f cookie_log.csv -d 2018-12-09
```

## High level design
![code-design-1](https://user-images.githubusercontent.com/50840332/132972117-4b756087-bdd0-4447-a084-e823ccbc9057.png)

