package com.signallin.signall_app.webdoxService;

import com.signallin.signall_app.aggregates.request.RequestDocumentAttach;
import com.signallin.signall_app.aggregates.request.RequestSignDocument;
import com.signallin.signall_app.aggregates.request.RequestSignerForWF;
import com.signallin.signall_app.aggregates.request.RequestWorkflow;
import com.signallin.signall_app.aggregates.response.ResponseDecisionWorkflow;
import com.signallin.signall_app.aggregates.response.ResponseDocumentWF;
import com.signallin.signall_app.aggregates.response.ResponseSigner;
import com.signallin.signall_app.aggregates.response.ResponseValidateDocuWF;
import org.springframework.http.ResponseEntity;

public interface WebdoxService {
    public ResponseDecisionWorkflow createWorkflow(RequestWorkflow workflowRequest);
    public ResponseDocumentWF attachDocument(String idWF, String numero, String parallel_number, RequestDocumentAttach requestDocumentAttach);
    public String setSignableDocument(String idWF, RequestSignDocument requestSignDocument);
    public ResponseValidateDocuWF validateDocuOnStepOneOfWF(String idWF, String number, String parallel_number);
    public ResponseSigner AssignSignerToWF(String idWF, String stepNumber, String parallelNumber, RequestSignerForWF requestSignerForWF);

    public void processPdfFiles();
}
