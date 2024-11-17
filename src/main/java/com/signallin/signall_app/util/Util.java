package com.signallin.signall_app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

public class Util {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String convertirAString(T objeto)
            throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(objeto);
    }

    public static <T> T convertirDesdeString(String datoDeRedis, Class<T> tipoClase)
            throws JsonProcessingException{
        return OBJECT_MAPPER.readValue(datoDeRedis,tipoClase);
    }

    public static Resource getFileAsResource(String folderPath, String fileName) throws IOException {
        Path filePath = Paths.get(folderPath, fileName);
        InputStream fileStream = Files.newInputStream(filePath);
        return new InputStreamResource(fileStream);
    }


}
