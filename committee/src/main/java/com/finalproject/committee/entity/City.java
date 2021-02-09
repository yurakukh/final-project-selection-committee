package com.finalproject.committee.entity;

import javax.persistence.*;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private Region region;

    public City() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}