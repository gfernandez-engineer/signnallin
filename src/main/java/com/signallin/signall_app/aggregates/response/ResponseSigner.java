package com.signallin.signall_app.aggregates.response;

import lombok.Getter;
import lombok.Setter;

import javax.naming.ldap.PagedResultsControl;
@Getter
@Setter
public class ResponseSigner {
    private String id;
    private String step_number;
    private String step_parallel_number;
    private String kind;
    private String status;
    private String user_id;
    private String country_code;
    private String name;
    private String email;
    private String national_identification_number;
    private String national_identification_kind_id;
    private String signature_id;
    private String envelope_id;
    private String signed_at;
    private String signed_difference;
    private String phone_number;
    private String physical_address;
    private Boolean has_valid_positioned_signatures;
    private String birthdate;
    private String cc_email;
}
