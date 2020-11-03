package com.stefanauwyang.blockone.studentenrollment.db.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private String nationality;
    private Boolean full_time = false;

}
