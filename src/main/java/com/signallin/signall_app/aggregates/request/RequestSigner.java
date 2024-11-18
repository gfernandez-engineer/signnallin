package com.signallin.signall_app.aggregates.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Genera automáticamente los métodos getters, setters, toString, equals, y hashCode.
@NoArgsConstructor
@AllArgsConstructor
public class RequestSigner {
    private String kind;
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("name")
    private String name;

    private String email;

    @JsonProperty("national_identification_number")
    private String nationalIdentNumber;

    @JsonProperty("national_identification_kind_id")
    private  String nationalIdentKindId;

    @JsonProperty("cc_email")
    private String ccEmail;

    @JsonProperty("use_notification_method_whatsapp")
    private Boolean useNotificMethWsp;

    @JsonProperty("phone_number")
    private String phoneNumber;






}
