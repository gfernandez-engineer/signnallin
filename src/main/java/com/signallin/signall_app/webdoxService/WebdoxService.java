package com.signallin.signall_app.webdoxService;

import com.signallin.signall_app.aggregates.request.RequestDocumentAttach;
import com.signallin.signall_app.aggregates.request.WorkflowRequest;
import com.signallin.signall_app.aggregates.response.ResponseDecisionWorkflow;
import com.signallin.signall_app.aggregates.response.ResponseDocumentWF;

public interface WebdoxService {
    public ResponseDecisionWorkflow createWorkflow(WorkflowRequest workflowRequest);
    public ResponseDocumentWF attachDocument(String idWF, String numero, String parallel_number,RequestDocumentAttach requestDocumentAttach);
    public void processPdfFiles();
}
