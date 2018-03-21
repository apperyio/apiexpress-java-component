package io.appery.apiexpress.components;

import com.fasterxml.jackson.databind.node.ObjectNode;

interface JavaComponent {
    ObjectNode execute(ObjectNode objectNode);
}
