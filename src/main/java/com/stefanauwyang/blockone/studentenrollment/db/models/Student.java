package com.stefanauwyang.blockone.studentenrollment.db.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(length = 250, nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @Column(length = 250)
    @JsonProperty("last_name")
    private String lastName;

    @Column(length = 100)
    private String nationality;

}
