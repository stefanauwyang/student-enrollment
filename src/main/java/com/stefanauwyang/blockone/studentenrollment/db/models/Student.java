package com.stefanauwyang.blockone.studentenrollment.db.models;

import lombok.*;

import javax.persistence.*;

/**
 * Represent a student which can be enrolled to a class in a semester.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    private String nationality;

}
