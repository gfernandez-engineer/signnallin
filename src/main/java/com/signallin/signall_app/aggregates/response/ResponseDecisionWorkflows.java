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
public class ResponseDecisionWorkflows {

    private String id;

    private String name;

    private String status;

    @JsonProperty("current_step")
    private ResponseCurrentStep[] responseCurrentStep;

}