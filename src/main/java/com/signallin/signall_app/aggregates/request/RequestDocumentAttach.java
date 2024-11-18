package com.signallin.signall_app.aggregates.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data //Genera automáticamente los métodos getters, setters, toString, equals, y hashCode.
@NoArgsConstructor
@AllArgsConstructor
public class RequestDocumentAttach {
    private String origin;
    private String versioned_document;
    private MultipartFile attachment;
}
