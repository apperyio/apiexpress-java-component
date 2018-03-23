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
