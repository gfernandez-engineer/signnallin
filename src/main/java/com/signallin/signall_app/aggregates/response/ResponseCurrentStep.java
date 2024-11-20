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
public class ResponseCurrentStep {
    @JsonProperty("id_number")
    private String idNumber;

    @JsonProperty("id_parallel_number")
    private String idParallelNumber;

    private String name;

    @JsonProperty("step_type")
    private String stepType;

    @JsonProperty("assigned_to")
    private  String[] assignedTo;


}
