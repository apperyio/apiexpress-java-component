
The  **Java**  component allows you to run java code in standalone version of API Express.

# 

Java

You can read more about Java by following url  [https://en.wikipedia.org/wiki/Java_(programming_language](https://en.wikipedia.org/wiki/Java_(programming_language))  
Pay attention that standalone version of API Express uses 8 version of Java so it is recomended to use the same or less version.

# 

Java Properties

In the  **JAVA SERVICE**  section you set the:

-   Java lib
-   Class name  
    In the  **REQUEST BODY**  section you set the:  
    JavaScript expression and value of requestBody will be passed as input parameter in Java component

# 

Libs

Java component expects that invoking Java class implement io.appery.apiexpress.components.JavaComponent interface

-   [Java](https://docs.appery.io/v2.4.0/docs/java-component)

```
package io.appery.apiexpress.components;

import com.fasterxml.jackson.databind.node.ContainerNode;

public interface JavaComponent {
    ContainerNode execute(ContainerNode objectNode);
}

```

JavaComponent interface has only execute method witch accepts ContainerNode object and returns ContainerNode in other words like all API Express components Java component accepts Json object and returns Json object.

Reference Java project with implementation JavaComponent interface is available in git  [https://github.com/apperyio/apiexpress-java-component](https://github.com/apperyio/apiexpress-java-component)

Main class of reference project is JavaComponentImpl

-   [Java](https://docs.appery.io/v2.4.0/docs/java-component)

```
package io.appery.apiexpress.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JavaComponentImpl implements JavaComponent {
    public ContainerNode execute(ContainerNode containerNode) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();

        result.put("name", containerNode.get("name").textValue());
        result.put("age", 20);

        return result;
    }
}
```

JavaComponentImpl expects that input json object contains field with name "name", take value of this field, create new json object with fields name and age.

Reference project contains unit test class JavaComponentTest. It is not essential to create unit class but it is a good practice.

-   [Java](https://docs.appery.io/v2.4.0/docs/java-component)

```
package io.appery.apiexpress.components.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.appery.apiexpress.components.JavaComponentImpl;
import org.junit.Assert;
import org.junit.Test;

public class JavaComponentTest {

    @Test
    public void testExample() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", "alex");

        ObjectNode result = (ObjectNode)new JavaComponentImpl().execute(objectNode);
        
        Assert.assertEquals(result.get("name").textValue(), "alex");
        Assert.assertEquals(result.get("age").asInt(), 20);
    }
}

```

To build jar file needs to run  _mvn package_  command. Pom file of reference project contains all required dependencies and plagin to build proper jar file.

-   [XML](https://docs.appery.io/v2.4.0/docs/java-component)

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>io.appery.apiexpress.components</groupId>
    <artifactId>java</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <jackson.version>2.7.6</jackson.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson.version}</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>io.appery.apiexpress</groupId>
            <artifactId>java-component-interface</artifactId>
            <version>1.0.0</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/repo/io/appery/apiexpress/1.0.0/java-component-interface-1.0.0.jar</systemPath>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>
```

maven-shade-plugin plugin copies all class files from dependencies in result jar file (whitch is available after package command in target repository) so result jar file is selfsufficient.

After building jar file it can be upload in API Expres on Libs tab.

# 

Example

To run reference project

-   clone project from git repository
-   run mvn package file
-   upload java-1.0-SNAPSHOT.jar file from target repository and provide name (for example JavaImpl)
-   Create new custom service with only one Java component
-   Specify properties: Java lib: JavaImpl and Class name: io.appery.apiexpress.components.JavaComponentImpl
-   Specify REQUEST BODY as requestBody = {"name": "test"};
-   Save and test service
