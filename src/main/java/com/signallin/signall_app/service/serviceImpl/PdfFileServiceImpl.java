package com.signallin.signall_app.service.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.signallin.signall_app.service.PdfFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PdfFileServiceImpl implements PdfFileService {

    @Value("${shared.folder.path}")
    private String sharedFolderPath;

    // Expresión regular para extraer nombre, email, país y documento
    private static final Pattern FILE_NAME_PATTERN =
            Pattern.compile("(?<nombre>[^_]+)_(?<email>[^_]+)_(?<pais>[^_]+)_(?<documento>[^_]+)\\.pdf");

    public void processPdfFiles2() {
        try (var files = Files.list(Paths.get(sharedFolderPath))) {
            // Filtrar solo archivos PDF
            files.filter(file -> file.toString().endsWith(".pdf"))
                    .forEach(this::processFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processFile(Path filePath) {
        String fileName = filePath.getFileName().toString();
        Matcher matcher = FILE_NAME_PATTERN.matcher(fileName);

        if (matcher.matches()) {
            String nombre = matcher.group("nombre");
            String email = matcher.group("email");
            String pais = matcher.group("pais");
            String documento = matcher.group("documento");

            System.out.printf("Procesando archivo: %s%n", fileName);
            System.out.printf("Nombre: %s, Email: %s, País: %s, Documento: %s%n", nombre, email, pais, documento);

            // Aquí puedes agregar lógica para procesar el archivo PDF
        } else {
            System.out.printf("El archivo %s no cumple con el formato esperado.%n", fileName);
        }
    }

    public void processPdfFiles() {
        try (Stream<Path> files = Files.list(Paths.get(sharedFolderPath))) {
            // Filtrar solo archivos PDF
            files.filter(file -> file.toString().endsWith(".pdf"))
                    .forEach(this::extractFileInfo);
        } catch (IOException e) {
            System.err.println("Error al acceder a la carpeta compartida: " + e.getMessage());
        }
    }

    private void extractFileInfo(Path filePath) {
        String fileName = filePath.getFileName().toString().replace(".pdf", "");
        String[] parts = fileName.split("___");

        if (parts.length == 4) {
            String documento = parts[0];
            String nombreRemitente = parts[1];
            String email = parts[2];
            String pais = parts[3];


            System.out.printf("Procesando archivo: %s%n", filePath);
            System.out.printf("Nombre Remitente: %s, Email: %s, País: %s, Documento: %s%n",
                    documento, nombreRemitente, email, pais );

            // Aquí puedes agregar lógica para procesar el contenido del archivo PDF
        } else {
            System.err.printf("El archivo %s no cumple con el formato esperado.%n", filePath);
        }
    }
}
