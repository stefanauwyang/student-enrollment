package com.stefanauwyang.blockone.studentenrollment.db.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stefanauwyang.blockone.studentenrollment.db.models.pk.EnrollmentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(EnrollmentId.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "class_name")
    @JsonProperty("class")
    private Clazz clazz;

    @Column
    private Boolean approved = false;

}