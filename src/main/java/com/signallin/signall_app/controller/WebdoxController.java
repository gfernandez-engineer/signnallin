package com.signallin.signall_app.controller;

import com.signallin.signall_app.aggregates.request.WorkflowRequest;
import com.signallin.signall_app.aggregates.response.ResponseDecisionWorkflow;
import com.signallin.signall_app.webdoxService.webdoxServiceImpl.WebdoxServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatusCode;


@RestController
@RequestMapping("signallin/v1")
//@RequiredArgsConstructor
public class WebdoxController {

    public WebdoxController(WebdoxServiceImpl webdoxService) {
        this.webdoxService = webdoxService;
    }

    private final WebdoxServiceImpl webdoxService;



    @PostMapping("/create-workflow")
    public ResponseEntity<ResponseDecisionWorkflow> createWorkflow(@RequestBody WorkflowRequest workflowRequest)
    {
        return new ResponseEntity<>(webdoxService.createWorkflow(workflowRequest), HttpStatus.CREATED);
    }

    @PostMapping("/process-pdfs")
    public ResponseEntity<String> processPdfFiles(@RequestBody WorkflowRequest workflowRequest)
    {
        try {
            // Llamar al método del servicio para procesar los archivos PDF
            webdoxService.processPdfFiles();
            return ResponseEntity.status(HttpStatus.OK).body("Archivos PDF procesados correctamente.");
        } catch (Exception e) {
            // Retorna un 400 Bad Request si ocurre un error genérico
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error inesperado: " + e.getMessage());
            //return ResponseEntity.status(500).body("Hubo un error al procesar los archivos PDF.");
        }

    }

}
