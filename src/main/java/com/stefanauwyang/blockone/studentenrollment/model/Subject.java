package com.stefanauwyang.blockone.studentenrollment.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Subject {
    @Id
    private Long id;
    @Column(unique = true)
    private String className;
    private Integer credit;
}