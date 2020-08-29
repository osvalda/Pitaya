# Pitaya

[![Build Status](https://travis-ci.org/osvalda/Pitaya.svg?branch=master)](https://travis-ci.org/osvalda/Pitaya)
[![Coverage Status](https://coveralls.io/repos/github/osvalda/Pitaya/badge.svg?branch=master)](https://coveralls.io/github/osvalda/Pitaya?branch=PAC-002)
[![GitHub license](https://img.shields.io/github/license/spotify/scio.svg)](./LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.osvalda/Pitaya.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.osvalda%22%20AND%20a:%22Pitaya%22)

API coverage visualizer tool. Creates an easily readable html report based upon the executed API test cases.

> Currently [TestNG] and [JUnit5] are the supported frameworks

## Usage
### Dependency

**Maven**:
```xml
<dependency>
    <groupId>io.github.osvalda</groupId>
    <artifactId>Pitaya</artifactId>
    <version>1.1.0</version>
    <scope>test</scope>
</dependency>
```

**Gradle**:

```Groovy
dependencies {
    testCompile("io.github.osvalda:pitaya:1.1.0")
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
| testng | 7.0.0 |
| junit.jupiter | 5.6.2 |
| junit.platform | 1.6.2 |
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

## Examples

<a href="https://i.ibb.co/PT6CKMd/pitaya-main.jpg" target="_blank">
    <img width="210" height="247" alt="charts" src="https://i.ibb.co/PT6CKMd/pitaya-main.jpg">
</a>
<a href="https://i.ibb.co/FbPZwvR/pitaya-list.jpg" target="_blank">
    <img width="233" height="247" alt="endpoint list" src="https://i.ibb.co/FbPZwvR/pitaya-list.jpg">
</a>

## CI Issues and solutions

### Jenkins

If Jenkins fails to display css, then run the following command in `Manage Jenkins` / `Script console`
```
System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "")
```


[TestNG]: <https://testng.org/doc/>
[JUnit5]: <https://junit.org/junit5/>