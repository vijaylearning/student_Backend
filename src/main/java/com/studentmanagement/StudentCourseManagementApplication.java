package com.studentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentCourseManagementApplication {

    public int calculator(){
        return 2+2;
    }

    public static void main(String[] args) {
        SpringApplication.run(StudentCourseManagementApplication.class, args);
    }
}