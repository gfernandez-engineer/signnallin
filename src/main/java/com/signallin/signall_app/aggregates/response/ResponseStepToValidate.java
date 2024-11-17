package com.signallin.signall_app.aggregates.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStepToValidate{
    private String id_number;
    private String id_parallel;
    private String name;
    private String id_workflow;
    private ResponseAssignToStep responseAssignToStep;
    private String[] step_attributes;
    private String step_type;
    private String created_at;
    private String limit_date;
    private Boolean is_signature_step;

}
