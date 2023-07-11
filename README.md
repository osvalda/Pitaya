# Pitaya

[![CircleCI](https://img.shields.io/circleci/build/github/osvalda/Pitaya/master)](https://app.circleci.com/pipelines/github/osvalda/Pitaya?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/osvalda/Pitaya/badge.svg?branch=master)](https://coveralls.io/github/osvalda/Pitaya?branch=master)
[![GitHub license](https://img.shields.io/github/license/spotify/scio.svg)](./LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.osvalda/Pitaya.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.osvalda/Pitaya/)

API coverage visualizer tool. Creates an easily readable html report based upon the executed API test cases.

> Currently [TestNG] and [JUnit5] are the supported frameworks

## Usage
### Dependency

**Maven**:
```xml
<dependency>
    <groupId>io.github.osvalda</groupId>
    <artifactId>Pitaya</artifactId>
    <version>1.2.1</version>
    <scope>test</scope>
</dependency>
```

**Gradle**:

```Groovy
dependencies {
    testCompile("io.github.osvalda:Pitaya:1.2.1")
}
```

### Configuration

#### Input file

##### Pitaya endpoint list file
Create a text file in your resource directory and add lines in the following format:

```
DELETE /posts/{post_id}, Posts

GET /posts/{post_id}/comments, Comments
GET /comments/{post_id}, Comments

GET /posts/{post_id}/pics, Pictures
...
```

##### OpenAPI Version 3 file
Copy the open api file (json or yaml) to resources directory or use its URL

#### Properties file

Create a `pitaya.properties` file into the root of your resources directory and
add the following properties to it:

| Key | Value | Mandatory |
| ------ | ------ | ------ |
| application.name | The name of your SUT | Yes | 
| endpoint.list.input | The endpoint list file's relative path or Open API file URL| Yes |
| bar.chart.width | The width of the Area-wise Endpoint Coverages chart| No |
| bar.chart.height | The height of the Area-wise Endpoint Coverages chart| No |

### Test case modifications

#### TestNG

To use Pitaya with TestNG add the reporter to the listeners in your test class(es):
```java
@Listeners({PitayaCoverageReporter.class})
public class YourTestNGTestClass {
    // ...
}
```

#### JUnit 5

To use Pitaya with JUnit5  add the extension to the extensions in your test class(es):
```java
@ExtendWith(PitayaCoverageExtension.class)
public class YourJUnit5TestClass {
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
| testng | 7.4.0 |
| junit.jupiter | 5.8.1 |
| junit.platform | 1.8.1 |
| freemarker | 2.3.29 |
| lombok |1.18.12|
| assertj-core | 3.14.0 |
| apache.commons | 3.8.1 |
| commons-io | 2.6 |
| guava | 11.0.2 |
| slf4j | 1.7.30 |
| logback | 1.2.3 |
| jmockit | 1.41 |
| mockito | 2.15.0 |
| swagger-parser-v3 | 2.0.21 |

## Preview

<a href="https://i.ibb.co/7S8xDsJ/pitaya-main.jpg" target="_blank">
    <img width="425" height="247" alt="charts" src="https://i.ibb.co/7S8xDsJ/pitaya-main.jpg">
</a>
<a href="https://i.ibb.co/tpDFbKV/pitaya-list.jpg" target="_blank">
    <img width="425" height="247" alt="endpoint list" src="https://i.ibb.co/tpDFbKV/pitaya-list.jpg">
</a>

## CI Issues and solutions

### Jenkins

If Jenkins fails to display css, then run the following command in `Manage Jenkins` / `Script console`
```
System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "")
```


[TestNG]: <https://testng.org/doc/>
[JUnit5]: <https://junit.org/junit5/>