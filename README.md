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
    <version>1.2.2</version>
    <scope>test</scope>
</dependency>
```

**Gradle**:

```Groovy
dependencies {
    testCompile("io.github.osvalda:Pitaya:1.2.2")
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

# comment line
# a star marks the endpoint to be ignored
* GET /posts/{post_id}/pics, Pictures

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

## Preview

<a href="https://i.ibb.co/dwMgx1H7/dash.png" target="_blank">
    <img width="425" height="217" alt="charts" src="https://i.ibb.co/dwMgx1H7/dash.png">
</a>
<a href="https://i.ibb.co/WNY5WqzQ/endpoint-List.png" target="_blank">
    <img width="425" height="217" alt="endpoint list" src="https://i.ibb.co/WNY5WqzQ/endpoint-List.png">
</a>

## CI Issues and solutions

### Jenkins

If Jenkins fails to display css, then run the following command in `Manage Jenkins` / `Script console`
```
System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "")
```


[TestNG]: <https://testng.org/doc/>
[JUnit5]: <https://junit.org/junit5/>