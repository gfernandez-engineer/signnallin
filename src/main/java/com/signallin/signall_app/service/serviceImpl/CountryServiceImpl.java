package com.signallin.signall_app.service.serviceImpl;

import com.signallin.signall_app.entity.Country;
import com.signallin.signall_app.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;

//@RequiredArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {

    private List<Country> countryList;

    public CountryServiceImpl() {
        try {
            // Utilizamos ObjectMapper para leer el archivo JSON y mapearlo a una lista de objetos Country
            ObjectMapper objectMapper = new ObjectMapper();
            // Asegúrate de que el archivo JSON esté en la ubicación correcta
            this.countryList = objectMapper.readValue(new File("src/main/resources/data/countries.json"), List.class);
        } catch (IOException e) {
            e.printStackTrace();
            // Si hay un error al leer el archivo, podrías inicializar la lista con datos vacíos o con algún manejo de errores.
            this.countryList = List.of();  // Lista vacía por defecto en caso de error
        }
    }

    @Override
    // Método para obtener el código por el nombre
    public String getCodeByName(String name) {
        return countryList.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .map(Country::getCode)
                .findFirst()
                .orElse("Code not found");
    }
}
