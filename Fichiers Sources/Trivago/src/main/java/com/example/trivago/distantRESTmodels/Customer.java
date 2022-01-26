package com.example.trivago.distantRESTmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    private Long customerId;
    private String name;
    private String surname;
    private String card;

    public Customer() {
    }

    public Customer(String name, String surname, String card) {
        this.customerId = null;
        this.name = name;
        this.surname = surname;
        this.card = card;
    }
}
