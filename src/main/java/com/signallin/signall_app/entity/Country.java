package com.signallin.signall_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Country {
    private String code;
    private String name;
    private boolean eu;

}
