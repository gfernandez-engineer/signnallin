package com.signallin.signall_app.aggregates.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseValidateDocuWF {
    private Boolean success;
    private ResponseStepToValidate responseStepToValidate;
}
