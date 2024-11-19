package com.signallin.signall_app.aggregates.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class RequestDocumentAttach {
    private String origin;
    private String versioned_document;
    private MultipartFile attachment;
}
