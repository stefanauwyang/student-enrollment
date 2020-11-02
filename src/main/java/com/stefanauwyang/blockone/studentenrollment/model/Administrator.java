package com.stefanauwyang.blockone.studentenrollment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Administrator {
    @Id
    private Long id;
}
