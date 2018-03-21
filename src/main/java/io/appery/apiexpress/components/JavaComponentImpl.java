package io.appery.apiexpress.components;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JavaComponentImpl implements JavaComponent {
    public ObjectNode execute(ObjectNode objectNode) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();

        result.put("name", objectNode.get("name").textValue());
        result.put("age", 20);

        return result;
    }
}
