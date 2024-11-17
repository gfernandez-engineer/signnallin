package com.signallin.signall_app.aggregates.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStepToValidate{

    @JsonProperty("id_number")
    private String idNumber;

    @JsonProperty("id_parallel")
    private String idParallel;

    private String name;

    @JsonProperty("id_workflow")
    private String idWorkflow;

    @JsonProperty("assigned_to")
    private ResponseAssignToStep responseAssignToStep;

    @JsonProperty("step_attributes")
    private String[] stepAttributes;

    @JsonProperty("step_type")
    private String stepType;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("limit_date")
    private String limitDate;

    @JsonProperty("is_signature_step")
    private Boolean isSignatureStep;

}
