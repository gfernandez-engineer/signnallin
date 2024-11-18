package com.signallin.signall_app.client;

import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.signallin.signall_app.aggregates.request.RequestSignDocument;
import com.signallin.signall_app.aggregates.request.RequestSignerForWF;
import com.signallin.signall_app.aggregates.request.RequestWorkflow;
import com.signallin.signall_app.aggregates.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "client-webdox", url = "https://app.webdoxclm.com/api/v2")
public interface WebdoxClient {

    //01. Creando Workflow
    @PostMapping("/decision_workflows")
    ResponseDecisionWorkflow createWorkflow(@RequestBody RequestWorkflow workflowRequest,
                                            @RequestHeader("Authorization") String authorizationHeader);
    //02. Cargar el documento
    @PostMapping(value = "/decision_workflows/{ID_WF}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseDocumentWF attachDocument(@PathVariable("ID_WF") String idWF,
                                          @RequestParam("number") String stepNumber,
                                          @RequestParam("parallel_number") String parallelNumber,
                                          @RequestPart("origin") String origin,
                                          @RequestPart("versioned_document") String versioned_document,
                                          @RequestPart("attachment") MultipartFile attachment,
                                          @RequestHeader("Authorization") String authorization);

    //03. Asignando el documento
    @PostMapping("/decision_workflows/{ID_WF}/set_signable_document")
    //@PostMapping("/set-signable-document")
    String setSignableDocument(@PathVariable("ID_WF") String idWF,
                                            @RequestBody RequestSignDocument requestSignDocument,
                                            @RequestHeader("Authorization") String authorization);

    // Buscar Workflows
    @PostMapping("/decision_workflows")
    ResponseDecisionWorkflowList searchWorkflows(@RequestParam("page") String page,
                                                 @RequestParam("search") String search,
                                                 @RequestHeader("Authorization") String authorization);



    //04. Validando el documento adjuntado al step 01 del WF
    @PutMapping("/decision_workflows/{ID_WF}/validate")
    ResponseValidateDocuWF validateDocuOnStepOneOfWF(@PathVariable("ID_WF")String idWF,
                                                     @RequestParam("number") String stepNumber,
                                                     @RequestParam("parallel_number") String parallelNumber,
                                                     @RequestHeader("Authorization") String authorization);



    //05. Asignación de firmante al WF en el step 02
    @PostMapping("/decision_workflows/{ID_WF}/steps/{stepNumber}/signers")
    ResponseSigner AssignSignerToWF(@PathVariable("ID_WF") String idWF,
                                    @PathVariable("stepNumber") String stepNumber,
                                    @RequestParam("parallel_number") String parallelNumber,
                                    @RequestBody RequestSignerForWF requestSignerForWF,
                                    @RequestHeader("Authorization") String authorization);















}
