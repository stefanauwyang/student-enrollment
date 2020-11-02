package com.stefanauwyang.blockone.studentenrollment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private Long id;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private String nationality;

}
