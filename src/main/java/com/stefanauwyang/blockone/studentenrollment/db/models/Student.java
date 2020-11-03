package com.stefanauwyang.blockone.studentenrollment.db.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

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

}
