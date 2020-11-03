package com.stefanauwyang.blockone.studentenrollment.db.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

}
