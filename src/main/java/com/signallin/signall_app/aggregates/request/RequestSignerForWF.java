package com.signallin.signall_app.aggregates.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Genera automáticamente los métodos getters, setters, toString, equals, y hashCode.
@NoArgsConstructor
@AllArgsConstructor
public class RequestSignerForWF {
    @JsonProperty("signer")
    RequestSigner requestSigner;
}
