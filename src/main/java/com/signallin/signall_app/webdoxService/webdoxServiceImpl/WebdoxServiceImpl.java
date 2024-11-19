package com.signallin.signall_app.webdoxService.webdoxServiceImpl;

import com.signallin.signall_app.aggregates.request.*;
import com.signallin.signall_app.aggregates.response.ResponseDecisionWorkflow;
import com.signallin.signall_app.aggregates.response.ResponseDocumentWF;
import com.signallin.signall_app.aggregates.response.ResponseSigner;
import com.signallin.signall_app.aggregates.response.ResponseValidateStepOfWF;
import com.signallin.signall_app.client.WebdoxClient;
import com.signallin.signall_app.util.CustomMultipartFile;
import com.signallin.signall_app.webdoxService.WebdoxService;
import com.signallin.signall_app.aggregates.constants.Constants;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
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
    public ResponseDecisionWorkflow createWorkflow(RequestWorkflow workflowRequest) {
        //WorkflowRequest request = new WorkflowRequest();
        //request.setDecision_name("43577834 - 5 wrkf - test");
        //request.setDecision_workflow_template_id(templateWf_ID);
        //return webdoxClient.createWorkflow(request, tokenOK);
        return webdoxClient.createWorkflow(workflowRequest, tokenOK);
    }

    @Override
    public ResponseDocumentWF attachDocument(String idWF,String stepNumber, String paralleNumber,RequestDocumentAttach requestDocumentAttach){
        return webdoxClient.attachDocument( idWF, stepNumber, paralleNumber, requestDocumentAttach.getOrigin(), requestDocumentAttach.getVersioned_document(),requestDocumentAttach.getAttachment(), tokenOK);
    }

    @Override
    public String setSignableDocument(String idWF, RequestSignDocument requestSignDocument){
        return webdoxClient.setSignableDocument(idWF, requestSignDocument, tokenOK);
    }

    @Override
    public ResponseValidateStepOfWF validateStepOfWF(String idWF, String stepNumber, String parallelNumber) {
        return  webdoxClient.validateStepOfWF(idWF,stepNumber, parallelNumber, tokenOK);
    }

    @Override
    public ResponseSigner AssignSignerToWF(String idWF, String stepNumber, String parallelNumber, RequestSignerForWF requestSignerForWF){
        return  webdoxClient.AssignSignerToWF(idWF,stepNumber,parallelNumber, requestSignerForWF, tokenOK);
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

    private ResponseDecisionWorkflow createWorkflow(Path filePath, String nroDocumento, String nombreRemitente, String pais, String email){
        String idWF = null;

        ResponseDecisionWorkflow responseDecisionWorkflow;
        RequestWorkflow requestWorkflow = new RequestWorkflow();
        requestWorkflow.setDecision_name("WF-Solicitud de Firma Digital - Documento " +nroDocumento);
        requestWorkflow.setDecision_workflow_template_id(templateWf_ID);
        return responseDecisionWorkflow = createWorkflow(requestWorkflow);

    }

    private ResponseDocumentWF attachDocumentToWF(Path filePath, String idWF, String stepNumber, String parallelNumber ){
        ResponseDocumentWF responseDocumentWF;
        RequestDocumentAttach requestDocumentAttach;
        requestDocumentAttach = new RequestDocumentAttach();
        requestDocumentAttach.setOrigin("pc");
        requestDocumentAttach.setVersioned_document("true");
        try {
            filePath = Paths.get(filePath.toUri());
            byte[] content = Files.readAllBytes(filePath);
            MultipartFile multipartFile = new CustomMultipartFile(content, "documento-a-firmar.pdf", "application/pdf");
            requestDocumentAttach.setAttachment(multipartFile);

            return responseDocumentWF = attachDocument(idWF,stepNumber,parallelNumber,requestDocumentAttach);

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            // Manejar el error adecuadamente, por ejemplo, lanzar una excepción personalizada
            throw new RuntimeException("No se pudo leer el archivo: " + filePath, e);
        }
    }



    private void extractFileInfo(Path filePath)  {

        //Declaración de variables
        ResponseDecisionWorkflow responseDecisionWorkflow;
        ResponseDocumentWF responseDocumentWF;
        MultipartFile attachment = null;
        String idDoc = null;
        String idWF = null;
        String stepNumber = null;
        String parallelNumber = "0";
        RequestDocumentAttach requestDocumentAttach;
        String nroDocumento;
        String nombreRemitente;
        String pais;
        String email;
        ResponseValidateStepOfWF responseValidateDocuWF;

        String fileName = filePath.getFileName().toString().replace(".pdf", "");
        String[] parts = fileName.split("___");

        if (parts.length == 4) {

            nroDocumento = parts[0];
            nombreRemitente = parts[1];
            pais = parts[2];
            email = parts[3];

            // PROCESO 1: REGISTRO DE WORKFLOW
            System.out.println("01. Procesando la creación del Workflow.");
            responseDecisionWorkflow = createWorkflow(filePath,nroDocumento,nombreRemitente,pais,email);
            if (!Objects.isNull(responseDecisionWorkflow) && !Objects.isNull(responseDecisionWorkflow.getId())){
                idWF = responseDecisionWorkflow.getId();
                System.out.println("Workflow creado.");
            }
            else {
                return;
            }

            // PROCESO 2: CARGA DE DOCUMENTO
            System.out.printf("02. Procesando archivo: %s%n", filePath);
            System.out.printf("Nombre Remitente: %s, País: %s, Documento: %s, Email: %s%n",
                    nroDocumento, nombreRemitente, pais , email);

            stepNumber = "1";
            responseDocumentWF = attachDocumentToWF(filePath, idWF, stepNumber, parallelNumber);
            if (Objects.nonNull(responseDocumentWF)){
                idDoc = responseDocumentWF.getResponseDocument().getId();
                System.out.printf("Se cargo el archivo: %s%n", filePath+fileName);
            }
            else {
                return;
            }

            // PROCESO 3: ASIGNACIÓN DE DOCUMENTO A FIRMA
            if (Objects.nonNull(idDoc)){
                RequestSignDocument requestSignDocument = new RequestSignDocument();
                requestSignDocument.setDocument_id(idDoc);
                requestSignDocument.setNumber(stepNumber);
                String resSetSignDoc = setSignableDocument(idWF,requestSignDocument);
                if (resSetSignDoc.equals("success")){
                    System.out.println("Se asigno el documento:" + fileName + " al proceso de firma. %n");
                }
                else{
                    return;
                }
            }
            else {
                return;
            }

            // PROCESO 4: VALIDAR PASO DEL PROCESO 1
            responseValidateDocuWF = validateStepOfWF(idWF,stepNumber,parallelNumber);
            System.out.println("Se realizo el STEP 1 del WF");


            // PROCESO 5: CREACIÓN DE FIRMANTE
            stepNumber = "2";
            RequestSignerForWF requestSignerForWF = new RequestSignerForWF();
            RequestSigner requestSigner = new RequestSigner();
            requestSigner.setKind(Constants.EXTERNAL);
            requestSigner.setUserId("");
            requestSigner.setCountryCode(pais);
            requestSigner.setName(nombreRemitente);
            requestSigner.setEmail(email);
            requestSigner.setNationalIdentNumber(nroDocumento);
            requestSigner.setNationalIdentKindId(Constants.TYPE_DOC_CC);
            requestSigner.setCcEmail("");
            requestSigner.setUseNotificMethWsp(true);
            requestSigner.setPhoneNumber("");
            requestSignerForWF.setRequestSigner(requestSigner);

            ResponseSigner responseSigner = AssignSignerToWF(idWF, stepNumber, parallelNumber, requestSignerForWF);

            if (Objects.nonNull(responseSigner)){
                System.out.println("Se ingreso el firmante");
            }
            else
            {
                return;
            }

            // PROCESO 6: VALIDAR PASO DEL PROCESO 2
            stepNumber = "2";
            responseValidateDocuWF = validateStepOfWF(idWF,stepNumber,parallelNumber);
            System.out.println("Se realizó el STEP 2 del WF");
        }
        else {
            System.err.printf("El archivo %s no cumple con el formato esperado.%n", filePath);
        }
    }

    private void extractFileInfo_bk(Path filePath)  {

        //Declaración de variables
        ResponseDecisionWorkflow responseDecisionWorkflow;
        ResponseDocumentWF responseDocumentWF;
        MultipartFile attachment = null;
        String idDoc = null;
        String idWF = null;
        String stepNumber = null;
        String parallelNumber = null;
        RequestDocumentAttach requestDocumentAttach;
        String nroDocumento;
        String nombreRemitente;
        String pais;
        String email;
        ResponseValidateStepOfWF responseValidateDocuWF;

        String fileName = filePath.getFileName().toString().replace(".pdf", "");
        String[] parts = fileName.split("___");

        if (parts.length == 4) {

            nroDocumento = parts[0];
            nombreRemitente = parts[1];
            pais = parts[2];
            email = parts[3];

            // PROCESO 1: REGISTRO DE WORKFLOW
            RequestWorkflow requestWorkflow = new RequestWorkflow();
            requestWorkflow.setDecision_name("WF-Solicitud de Firma Digital - Documento "+nroDocumento);
            requestWorkflow.setDecision_workflow_template_id(templateWf_ID);
            responseDecisionWorkflow = createWorkflow(requestWorkflow);

            if (!Objects.isNull(responseDecisionWorkflow) && !Objects.isNull(responseDecisionWorkflow.getId())){
                idWF = responseDecisionWorkflow.getId();
            }
            else {
                return;
            }

            System.out.printf("Procesando archivo: %s%n", filePath);
            System.out.printf("Nombre Remitente: %s, País: %s, Documento: %s, Email: %s%n",
                    nroDocumento, nombreRemitente, pais , email);


            // PROCESO 2: CARGA DE DOCUMENTO
            stepNumber = "1";
            parallelNumber = "0";
            requestDocumentAttach = new RequestDocumentAttach();
            requestDocumentAttach.setOrigin("pc");
            requestDocumentAttach.setVersioned_document("true");
            try {
                filePath = Paths.get(filePath.toUri());
                byte[] content = Files.readAllBytes(filePath);
                MultipartFile multipartFile = new CustomMultipartFile(content, "documento-a-firmar.pdf", "application/pdf");
                requestDocumentAttach.setAttachment(multipartFile);

                //Buscando workFlow
                //Completar....


                responseDocumentWF = attachDocument(idWF,stepNumber,parallelNumber,requestDocumentAttach);
                //ResponseEntity<String>response = attachDocument(idWF,"1","0",requestDocumentAttach);
                //System.out.println("Respuesta cruda: " + response.getBody());

                idDoc = responseDocumentWF.getResponseDocument().getId();

            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + e.getMessage());
                // Manejar el error adecuadamente, por ejemplo, lanzar una excepción personalizada
                throw new RuntimeException("No se pudo leer el archivo: " + filePath, e);
            }

            System.out.printf("Se cargo el archivo: %s%n", filePath+fileName);


            // PROCESO 3: ASIGNACIÓN DE DOCUMENTO A FIRMA

            if (!Objects.isNull(responseDocumentWF) && !Objects.isNull(idDoc)){
                RequestSignDocument requestSignDocument = new RequestSignDocument();
                requestSignDocument.setDocument_id(idDoc);
                requestSignDocument.setNumber("1");
                String resSetSignDoc = setSignableDocument(idWF,requestSignDocument);
                if (resSetSignDoc.equals("success")){
                    System.out.println("Se asigno el documento:" + fileName + " al proceso de firma. %n");
                }
                else{
                    return;
                }
            }
            else {
                return;
            }

            // PROCESO 4: VALIDAR PASO DEL PROCESO 1
            stepNumber = "1";
            responseValidateDocuWF = validateStepOfWF(idWF,stepNumber,parallelNumber);
            System.out.println("Se realizo el STEP 1 del WF");


            // PROCESO 5: CREACIÓN DE FIRMANTE
            stepNumber = "2";
            RequestSignerForWF requestSignerForWF = new RequestSignerForWF();
            RequestSigner requestSigner = new RequestSigner();
            requestSigner.setKind("external");
            requestSigner.setUserId("");
            requestSigner.setCountryCode(pais);
            requestSigner.setName(nombreRemitente);
            requestSigner.setEmail(email);
            requestSigner.setNationalIdentNumber(nroDocumento);
            requestSigner.setNationalIdentKindId("afd11efc-12a4-4d8d-b72a-343d4e806a55");// CC es el documento más común y utilizado por los ciudadanos colombianos.
            requestSigner.setCcEmail("");
            requestSigner.setUseNotificMethWsp(true);
            requestSigner.setPhoneNumber("");

            requestSignerForWF.setRequestSigner(requestSigner);

            ResponseSigner responseSigner = AssignSignerToWF(idWF, stepNumber, parallelNumber, requestSignerForWF);

            // PROCESO 6: VALIDAR PASO DEL PROCESO 2
            stepNumber = "2";
            responseValidateDocuWF = validateStepOfWF(idWF,stepNumber,parallelNumber);
            System.out.println("Se realizo el STEP 2 del WF");


        } else {
            System.err.printf("El archivo %s no cumple con el formato esperado.%n", filePath);
        }
    }






}
