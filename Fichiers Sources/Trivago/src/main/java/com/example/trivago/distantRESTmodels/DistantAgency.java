package com.example.trivago.distantRESTmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistantAgency {


    private Long agencyId;
    private String name;
    private String password;

    public DistantAgency() {
    }

    public DistantAgency(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

}
