# Nubank Authorizer

This is the implementation of an application that authorizes a transaction for a specific account following a set of predefined rules.
It must be only one account registered. There were no databases used, ony an abstraction to store data and state in memory.

### Language and libraries
This code were developed using java11, on OpenJDK with the maven, used for dependency management and packing.

The used libraries were:
* Jackson for Json serialization a deserialization;
* Lombok to avoid boilerplate code;
* Junit for unity tests;
* Mockito for mocks;
* Jacoco for coverage tests;
* Maven assembly plugin for packing.

### Run the code
The java11 and maven are need to run the application. After install, to build the code, run the tests
following the maven developer cycle, you can execute the `run.sh` script. This script is in the root
of the project, or you can execute it manually, running the command inside the project root:
```shell
mvn clean install;
```
After the build, test and packing, it's possible to execute the code with a file input, as the example:
```shell
java -jar target/authorizer.jar < src/test/resources/test-ok;
```
The execution will produce the stream results.
