package com.stefanauwyang.blockone.studentenrollment.db.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    private String username;

    @Column
    private String password;

    @OneToOne
    private Student student;

    @Column
    private Boolean isAdmin = false;

}
