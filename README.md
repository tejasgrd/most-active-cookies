# Most Active Cookies
Given a cookie log file in the CSV format this application will list most active cookies for given date.
### Prerequisites
make sure you have installed all of the following prerequisites on your development/testing machine
- JDK 11 and above
- maven 3.6 and above

make sure you can run **java** and **mvn** on your terminal/command-prompt.

## Build
### maven
On root directory of repository, run the below command to generate jar file for this application
``` 
$ mvn clean install
$ mvn clean compile assembly:single
```
Above command will generate distributable jar file with all the required dependencies in target folder named **cookies-processor-1.0-jar-with-dependencies.jar**.
Location will be like below.
```
most-active-cookies/target/cookies-processor-1.0-jar-with-dependencies.jar
```

## Run
The above generated jar file in target folder can be run using java as follows
```
$ java -jar cookies-processor-1.0-jar-with-dependencies.jar -f cookie_log.csv -d 2018-12-09
```

### Running application from command line 
It is preferred to keep jar file and csv file in same directory/folder and then run the jar application.
Below screenshot shows the same.
<img width="1334" alt="Screenshot 2021-09-12 at 2 37 26 PM" src="https://user-images.githubusercontent.com/50840332/132982016-089557f9-09f9-443a-87b7-e8dea1561a50.png">

## High level design
Below diagram shows the high level class design of the applicaion. When jar runs it calls main method from CookieExecutor class and the flow from there onwards in shown below.

![code-design-1](https://user-images.githubusercontent.com/50840332/132972117-4b756087-bdd0-4447-a084-e823ccbc9057.png)

## History

Version 1.0 (2021-09-12) - Most Active Cookies first fully working release.

## Credits

Developer - Tejas Garde (@tejasgrd)