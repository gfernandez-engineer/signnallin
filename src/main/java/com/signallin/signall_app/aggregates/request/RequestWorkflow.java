package com.signallin.signall_app.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestWorkflow {
    private String decision_name;
    private String decision_workflow_template_id;

    public RequestWorkflow() {

    }

   /* public WorkflowRequest(String decision_name, String decision_workflow_template_id) {
        this.decision_name = decision_name;
        this.decision_workflow_template_id = decision_workflow_template_id;
    }*/
}