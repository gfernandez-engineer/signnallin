package com.signallin.signall_app.aggregates.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseValidateStepOfWF {
    private Boolean success;

    @JsonProperty("step")
    private ResponseStepToValidate responseStepToValidate;
}
