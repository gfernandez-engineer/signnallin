package com.signallin.signall_app.aggregates.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDecisionWorkflow {
    private String id;
    private String name;
    private String template_id;

    /*public String toString() {
        return "ResponseDecisionWorkflow{"+
                "id ='" + id + '\'' +
                ", name = '" + name + '\'' +
                ", template_id = '" + template_id +'\'' +
                "}";
    }*/
}
