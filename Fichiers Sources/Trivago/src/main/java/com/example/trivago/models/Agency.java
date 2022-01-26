package com.example.trivago.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agencyId;

    private String uri;

    public Agency(Long agencyId, String uri) {
        super();
        this.agencyId = agencyId;
        this.uri = uri;
    }

    public Agency() {
    }
}
