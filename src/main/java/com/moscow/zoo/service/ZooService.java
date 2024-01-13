package com.moscow.zoo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.*;

@Service
public class ZooService {

    @SneakyThrows
    public String getJsonString() {
        FileReader fileReader = new FileReader("zoos.json");
        Scanner scanner = new Scanner(fileReader);

        StringBuilder jsonString = new StringBuilder();

        while (scanner.hasNextLine()) {
            jsonString.append(scanner.nextLine());
        }

        return jsonString.toString();
    }

    public String getRandomId() {
        Random random = new Random();
        return null;
    }

    @SneakyThrows
    public JsonNode getNode(String jsonString, String id) {
        JsonNode json = new ObjectMapper().readTree(jsonString);

        for (JsonNode node : json) {
            String globalId = node.get("global_id").asText();
            if (globalId.equals(id)) {
                return node;
            }
        }
        return null;
    }

    @SneakyThrows
    public Map<String, String> idList(String json) {
        JsonNode rootNode = new ObjectMapper().readTree(json);
        Map<String, String> ids = new HashMap<>();

        for (JsonNode node : rootNode) {
            ids.put(node.get("Kind").asText(), node.get("global_id").asText());
        }

        return ids;
    }

}
