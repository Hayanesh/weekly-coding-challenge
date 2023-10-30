package com.hayanesh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
public class JsonObject {
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);


    private final String jsonString;
    public static JsonObject fromFile(Path filePath) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (var bufferedReader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            return new JsonObject(stringBuilder.toString().trim());
        }
    }

    public boolean isValid(){
        if(jsonString.isBlank())
            return false;

        try{
            OBJECT_MAPPER.readTree(jsonString);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

}
