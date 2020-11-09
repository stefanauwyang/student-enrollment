package com.stefanauwyang.blockone.studentenrollment.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @GetMapping("/")
    public ResponseEntity welcome() {
        return ResponseEntity.ok("Student Enrollment API is ready");
    }
}
