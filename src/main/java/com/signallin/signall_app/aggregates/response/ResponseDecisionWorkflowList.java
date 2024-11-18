package com.signallin.signall_app.aggregates.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDecisionWorkflowList {
    @JsonProperty("decision_workflows")
    private ResponseDecisionWorkflows[] responseDecisionWorkflows;
}
