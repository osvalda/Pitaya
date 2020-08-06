# Pitaya

[![Build Status](https://travis-ci.org/osvalda/Pitaya.svg?branch=master)](https://travis-ci.org/osvalda/Pitaya)
[![GitHub license](https://img.shields.io/github/license/spotify/scio.svg)](./LICENSE)

API coverage visualizer tool. It creates an easily readable html report based upon the executed API test cases.

## Usage
Add the following dependency in your `pom.xml` (Java 8+ is required):

Maven project:
```xml
<dependency>
    <groupId>com.osvalda.pitaya</groupId>
    <artifactId>pitaya</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

Or in case ofGradle add the following into the build file:

```
dependencies {
    testCompile("com.osvalda:pitaya:1.0.0")
}
```

To use Pitaya in tests add the reporter into the listeners:
```java
@Listeners({EndpointCoverageReporter.class})
public class YourTestClass {
    // ...
}
```

And annotate all your test methods you want to include in the report:
```java
@Test
@TestCaseSupplementary(api = {GET + "/posts""})
public void yourTestMethod() {
    // ...
}
```

## Tools and Plugins
The following tools are used across the project.

| Name | Version |
| ------ | ------ |
| testng | 7.0.0 |
| freemarker | 2.3.29 |
| lombok |1.18.6|
| assertj-core | 3.14.0	Apache 2.0 |
| apache.commons | 3.8.1 |
| commons-io | 2.6 |
| slf4j | 1.7.26 |
| logback | 1.2.3 |