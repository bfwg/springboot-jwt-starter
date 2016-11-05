package com.bfwg.model;

import javax.persistence.*;

/**
 * Created by fan.jin on 2016-11-03.
 */

@Entity
@Table(name="Authority")
public class Authority {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="name")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
