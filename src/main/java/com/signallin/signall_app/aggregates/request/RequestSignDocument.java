package com.signallin.signall_app.aggregates.request;

import lombok.*;

@Data //Genera automáticamente los métodos getters, setters, toString, equals, y hashCode.
@NoArgsConstructor
@AllArgsConstructor
public class RequestSignDocument {
    private String document_id;
    private String number;
}
