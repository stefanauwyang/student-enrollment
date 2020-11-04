package com.stefanauwyang.blockone.studentenrollment.db.models;

import lombok.*;

import javax.persistence.*;

/**
 * Represents a class which linked to a semester.
 *
 */
@Entity(name = "class")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Course {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private Integer credit;

}
