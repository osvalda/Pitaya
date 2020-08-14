# Pitaya

[![Build Status](https://travis-ci.org/osvalda/Pitaya.svg?branch=master)](https://travis-ci.org/osvalda/Pitaya)
[![GitHub license](https://img.shields.io/github/license/spotify/scio.svg)](./LICENSE)

API coverage visualizer tool. Creates an easily readable html report based upon the executed API test cases.

> Currently [TestNG] and [JUnit5] are the only supported frameworks

## Usage
### Dependency

**Maven**:
```xml
<dependency>
    <groupId>com.osvalda</groupId>
    <artifactId>Pitaya</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

**Gradle**:

```
dependencies {
    testCompile("com.osvalda:pitaya:1.0.0")
}
```

### Configuration

Create a file into your resource directory and add lines in the following format:

```
DELETE /posts/{post_id}, Posts

GET /posts/{post_id}/comments, Comments
GET /comments/{post_id}, Comments

GET /posts/{post_id}/pics, Pictures
```

Create a `pitaya.properties` file into the root of your resources directory and
add the following properties to it:

| Key | Value |
| ------ | ------ |
| application.name | The name of your SUT |
| endpoint.list.input | The formerly created endpoint list file's relative path |
| report.footer | Optional footer content for the HTML report |
 

### Test case modifications

#### TestNG

To use Pitaya with TestNG add the reporter to the listeners in your test class(es):
```java
@Listeners({EndpointCoverageReporter.class})
public class YourTestClass {
    // ...
}
```

#### JUnit 5

To use Pitaya with JUnit5  add the extension to the extensions in your test class(es):
```java
@ExtendWith(PitayaCoverageExtension.class)
public class JUnitReportTest {
    // ...
}
```

#### In Test Methods

Annotate all your test methods which you want to include in the report:
```java
@Test
@TestCaseSupplementary(api = {GET + "/one_of_your_endpoint"})
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
| assertj-core | 3.14.0 |
| apache.commons | 3.8.1 |
| commons-io | 2.6 |
| slf4j | 1.7.26 |
| logback | 1.2.3 |
| jmockit | 1.41 |


[TestNG]: <https://testng.org/doc/>
[JUnit5]: <https://junit.org/junit5/>