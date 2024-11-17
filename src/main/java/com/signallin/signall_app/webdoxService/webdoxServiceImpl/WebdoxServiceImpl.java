package com.signallin.signall_app.webdoxService.webdoxServiceImpl;

import com.signallin.signall_app.aggregates.request.RequestDocumentAttach;
import com.signallin.signall_app.aggregates.request.WorkflowRequest;
import com.signallin.signall_app.aggregates.response.ResponseDecisionWorkflow;
import com.signallin.signall_app.aggregates.response.ResponseDocumentWF;
import com.signallin.signall_app.client.WebdoxClient;
import com.signallin.signall_app.util.CustomMultipartFile;
import com.signallin.signall_app.webdoxService.WebdoxService;
import com.signallin.signall_app.aggregates.constants.Constants;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class WebdoxServiceImpl implements WebdoxService {
    private final WebdoxClient webdoxClient;

    @Value("${token.api}")
    public String tokenApi;

    @Value("${template.wf}")
    private String templateWf_ID; // Workflow template ID

    @Value("${shared.folder.path}")
    private String sharedFolderPath;

    private String tokenOK;
    // Asignar el token a tokenOK después de la inyección de dependencias
    @PostConstruct
    public void init() {
        // Concatenar el valor de BEARER a tokenApi
        this.tokenOK = Constants.BEARER + tokenApi;
    }


    @Override
    public ResponseDecisionWorkflow createWorkflow(WorkflowRequest workflowRequest) {
        //WorkflowRequest request = new WorkflowRequest();
        //request.setDecision_name("43577834 - 5 wrkf - test");
        //request.setDecision_workflow_template_id(templateWf_ID);
        //return webdoxClient.createWorkflow(request, tokenOK);
        return webdoxClient.createWorkflow(workflowRequest, tokenOK);
    }

    @Override
    public ResponseDocumentWF attachDocument(String idWF,String numero, String parallel_number,RequestDocumentAttach requestDocumentAttach){
        return webdoxClient.attachDocument( idWF, numero, parallel_number, requestDocumentAttach.getOrigin(), requestDocumentAttach.getVersioned_document(),requestDocumentAttach.getAttachment(), tokenOK);
    }

    @Override
    public void processPdfFiles() {
        try (Stream<Path> files = Files.list(Paths.get(sharedFolderPath))) {
            // Filtrar solo archivos PDF
            files.filter(file -> file.toString().endsWith(".pdf"))
                    .forEach(this::extractFileInfo);
        } catch (IOException e) {
            System.err.println("Error al acceder a la carpeta compartida: " + e.getMessage());
        }
    }

    private void extractFileInfo(Path filePath)  {
        ResponseDecisionWorkflow responseDecisionWorkflow;
        ResponseDocumentWF responseDocumentWF;
        MultipartFile attachment;

        RequestDocumentAttach requestDocumentAttach;

        String fileName = filePath.getFileName().toString().replace(".pdf", "");
        String[] parts = fileName.split("___");

        if (parts.length == 4) {
            String documento = parts[0];
            String nombreRemitente = parts[1];
            String pais = parts[2];
            String email = parts[3];

            // PROCESO 1: REGISTRO DE WORKFLOW
            WorkflowRequest workflowRequest = new WorkflowRequest();
            workflowRequest.setDecision_name("WF-Solicitud de Firma Digital - Documento "+documento);
            workflowRequest.setDecision_workflow_template_id(templateWf_ID);
            responseDecisionWorkflow = createWorkflow(workflowRequest);

            String idWF = responseDecisionWorkflow.getId();

            System.out.printf("Procesando archivo: %s%n", filePath);
            System.out.printf("Nombre Remitente: %s, País: %s, Documento: %s, Email: %s%n",
                    documento, nombreRemitente, pais , email);

            // PROCESO 2: CARGA DE DOCUMENTO
            requestDocumentAttach = new RequestDocumentAttach();
            requestDocumentAttach.setOrigin("pc");
            requestDocumentAttach.setVersioned_document("true");
            try {
                filePath = Paths.get(filePath.toUri());
                byte[] content = Files.readAllBytes(filePath);
                MultipartFile multipartFile = new CustomMultipartFile(content, "documento-a-firmar.pdf", "application/pdf");
                requestDocumentAttach.setAttachment(multipartFile);

                responseDocumentWF = attachDocument(idWF,"1","0",requestDocumentAttach);

            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + e.getMessage());
                // Manejar el error adecuadamente, por ejemplo, lanzar una excepción personalizada
                throw new RuntimeException("No se pudo leer el archivo: " + filePath, e);
            }

            System.out.printf("Se cargo el archivo: %s%n", filePath+fileName);


            // PROCESO 3: ASIGNACIÓN DE DOCUMENTO A FIRMA



            // PROCESO 4: VALIDAR PASO DEL PROCESO 1


            // PROCESO 5: CREACIÓN DE FIRMANTE


            // PROCESO 6: VALIDAR PASO DEL PROCESO 2

        } else {
            System.err.printf("El archivo %s no cumple con el formato esperado.%n", filePath);
        }
    }






}
