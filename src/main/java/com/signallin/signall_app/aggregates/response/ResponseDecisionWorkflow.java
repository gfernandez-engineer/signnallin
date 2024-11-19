package com.signallin.signall_app.aggregates.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDecisionWorkflow {
    private String id;
    private String name;
    private String template_id;

    public String toString() {
        return "ResponseDecisionWorkflow{"+
                "id ='" + id + '\'' +
                ", name = '" + name + '\'' +
                ", template_id = '" + template_id +'\'' +
                "}";
    }
}
