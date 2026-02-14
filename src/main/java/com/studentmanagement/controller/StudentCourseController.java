package com.studentmanagement.controller;

import com.studentmanagement.dto.CourseEnrollmentDetailsDTO;
import com.studentmanagement.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class StudentCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping()
    public ResponseEntity<List<CourseEnrollmentDetailsDTO>> getCourseEnrollments() {
        return ResponseEntity.ok(studentCourseService.getCourseEnrollments());
    }
}