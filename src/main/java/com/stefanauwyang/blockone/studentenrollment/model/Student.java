package com.stefanauwyang.blockone.studentenrollment.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "student")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
}
