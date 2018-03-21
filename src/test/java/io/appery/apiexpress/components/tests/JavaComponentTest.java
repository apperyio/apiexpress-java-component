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

        ObjectNode result = new JavaComponentImpl().execute(objectNode);
        
        Assert.assertEquals(result.get("name").textValue(), "alex");
        Assert.assertEquals(result.get("age").asInt(), 20);
    }
}
