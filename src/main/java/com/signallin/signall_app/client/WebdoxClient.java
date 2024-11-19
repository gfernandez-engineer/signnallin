package com.signallin.signall_app.client;

import com.signallin.signall_app.aggregates.request.WorkflowRequest;
import com.signallin.signall_app.aggregates.response.ResponseDecisionWorkflow;
import com.signallin.signall_app.aggregates.response.ResponseDocumentWF;
import com.signallin.signall_app.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "client-webdox", url = "https://app.webdoxclm.com/api/v2")
public interface WebdoxClient {

    //01. Creando Workflow
    @PostMapping("/decision_workflows")
    ResponseDecisionWorkflow createWorkflow(@RequestBody WorkflowRequest workflowRequest,
                                            @RequestHeader("Authorization") String authorizationHeader);
    //02. Cargar el documento
    @PostMapping(value = "/decision_workflows/{ID_WF}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseDocumentWF attachDocument(@RequestParam("ID_WF") String idWF,
                                      @RequestParam("number") String numero,
                                      @RequestParam("parallel_number") String parallel_number,
                                      @RequestPart("origin") String origin,
                                      @RequestPart("versioned_document") String versioned_document,
                                      @RequestPart("attachment") MultipartFile attachment,
                                      @RequestHeader("Authorization") String authorization);

    //03. Asignando el documento
    @PostMapping("/set-signable-document")
    ResponseDecisionWorkflow setSignableDocument(@RequestBody WorkflowRequest workflowRequest,
                                            @RequestHeader("Authorization") String authorization);




}
