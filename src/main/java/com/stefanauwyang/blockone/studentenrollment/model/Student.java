package com.stefanauwyang.blockone.studentenrollment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Student {
    @Id
    private Long id;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @JsonProperty("class")
    @Column(name = "class")
    private String clazz;
    private String nationality;
}
