The **Java** component allows you to run java code in standalone version of API Express.
[block:api-header]
{
  "title": "Java"
}
[/block]
You can read more about Java by following url https://en.wikipedia.org/wiki/Java_(programming_language)
Pay attention that standalone version of API Express uses 8 version of Java so it is recomended to use the same or less version.
[block:api-header]
{
  "type": "basic",
  "title": "Java Properties"
}
[/block]
In the **JAVA SERVICE** section you set the: 
- Java lib
- Class name
In the **REQUEST BODY** section you set the: 
JavaScript expression and value of requestBody will be passed as input parameter in Java component
[block:api-header]
{
  "title": "Libs"
}
[/block]
Java component expects that invoking Java class implement io.appery.apiexpress.components.JavaComponent interface 
[block:code]
{
  "codes": [
    {
      "code": "package io.appery.apiexpress.components;\n\nimport com.fasterxml.jackson.databind.node.ContainerNode;\n\npublic interface JavaComponent {\n    ContainerNode execute(ContainerNode objectNode);\n}\n",
      "language": "java"
    }
  ]
}
[/block]
JavaComponent interface has only execute method witch accepts ContainerNode object and returns ContainerNode in other words like all API Express components Java component accepts Json object and returns Json object.
 
Reference Java project with implementation JavaComponent interface is available in git https://github.com/apperyio/apiexpress-java-component

Main class of reference project is JavaComponentImpl
[block:code]
{
  "codes": [
    {
      "code": "package io.appery.apiexpress.components;\n\nimport com.fasterxml.jackson.databind.ObjectMapper;\nimport com.fasterxml.jackson.databind.node.ContainerNode;\nimport com.fasterxml.jackson.databind.node.ObjectNode;\n\npublic class JavaComponentImpl implements JavaComponent {\n    public ContainerNode execute(ContainerNode containerNode) {\n\n        ObjectMapper mapper = new ObjectMapper();\n        ObjectNode result = mapper.createObjectNode();\n\n        result.put(\"name\", containerNode.get(\"name\").textValue());\n        result.put(\"age\", 20);\n\n        return result;\n    }\n}",
      "language": "java"
    }
  ]
}
[/block]
JavaComponentImpl expects that input json object contains field with name "name", take value of this field, create new json object with fields name and age.

Reference project contains unit test class JavaComponentTest. It is not essential to create unit class but it is a good practice.
[block:code]
{
  "codes": [
    {
      "code": "package io.appery.apiexpress.components.tests;\n\nimport com.fasterxml.jackson.databind.ObjectMapper;\nimport com.fasterxml.jackson.databind.node.ObjectNode;\nimport io.appery.apiexpress.components.JavaComponentImpl;\nimport org.junit.Assert;\nimport org.junit.Test;\n\npublic class JavaComponentTest {\n\n    @Test\n    public void testExample() {\n        ObjectMapper mapper = new ObjectMapper();\n        ObjectNode objectNode = mapper.createObjectNode();\n        objectNode.put(\"name\", \"alex\");\n\n        ObjectNode result = (ObjectNode)new JavaComponentImpl().execute(objectNode);\n        \n        Assert.assertEquals(result.get(\"name\").textValue(), \"alex\");\n        Assert.assertEquals(result.get(\"age\").asInt(), 20);\n    }\n}\n",
      "language": "java"
    }
  ]
}
[/block]
To build jar file needs to run *mvn package* command. Pom file of reference project contains all required dependencies and plagin to build proper jar file.
[block:code]
{
  "codes": [
    {
      "code": "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n    <modelVersion>4.0.0</modelVersion>\n\n    <groupId>io.appery.apiexpress.components</groupId>\n    <artifactId>java</artifactId>\n    <version>1.0-SNAPSHOT</version>\n\n    <properties>\n        <jackson.version>2.7.6</jackson.version>\n    </properties>\n\n    <dependencies>\n        <dependency>\n            <groupId>com.fasterxml.jackson.core</groupId>\n            <artifactId>jackson-databind</artifactId>\n            <version>${jackson.version}</version>\n            <scope>provided</scope>\n        </dependency>\n\n        <dependency>\n            <groupId>junit</groupId>\n            <artifactId>junit</artifactId>\n            <version>4.12</version>\n            <scope>test</scope>\n        </dependency>\n\n        <dependency>\n            <groupId>io.appery.apiexpress</groupId>\n            <artifactId>java-component-interface</artifactId>\n            <version>1.0.0</version>\n            <scope>system</scope>\n            <systemPath>${project.basedir}/repo/io/appery/apiexpress/1.0.0/java-component-interface-1.0.0.jar</systemPath>\n        </dependency>\n    </dependencies>\n\n    <build>\n        <plugins>\n            <plugin>\n                <groupId>org.apache.maven.plugins</groupId>\n                <artifactId>maven-shade-plugin</artifactId>\n                <executions>\n                    <execution>\n                        <phase>package</phase>\n                        <goals>\n                            <goal>shade</goal>\n                        </goals>\n                    </execution>\n                </executions>\n            </plugin>\n        </plugins>\n    </build>\n\n</project>",
      "language": "xml"
    }
  ]
}
[/block]
maven-shade-plugin plugin copies all class files from dependencies in result jar file (whitch is available after package command in target repository) so result jar file is selfsufficient.

After building jar file it can be upload in API Expres on Libs tab.
[block:api-header]
{
  "title": "Example"
}
[/block]
To run reference project
- clone project from git repository
- run mvn package file
- upload java-1.0-SNAPSHOT.jar file from target repository and provide name (for example JavaImpl)
- Create new custom service with only one Java component
- Specify properties: Java lib: JavaImpl and Class name: io.appery.apiexpress.components.JavaComponentImpl
- Specify REQUEST BODY as requestBody = {"name": "test"};
- Save and test service