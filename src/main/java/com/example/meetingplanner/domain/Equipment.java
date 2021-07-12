package com.example.meetingplanner.domain;

import javax.persistence.*;

@Entity
@Table(name = "EQUIPMENT")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;

    public Equipment() {
    }

}
