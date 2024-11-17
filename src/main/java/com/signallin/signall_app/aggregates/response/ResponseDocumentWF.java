package com.signallin.signall_app.aggregates.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDocumentWF {
    private String id;
    private String name;
    private String type;
    private Boolean in_repository;
    private Boolean signable;
    private Boolean signed;
    private String created_at;



}
