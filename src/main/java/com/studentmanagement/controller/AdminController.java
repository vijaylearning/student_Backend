package com.studentmanagement.controller;

import com.studentmanagement.dto.EnrollmentRequest;
import com.studentmanagement.entity.StudentCourse;
import com.studentmanagement.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Management", description = "Admin-only APIs for enrollment management")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/enroll")
    @Operation(summary = "Enroll student to course", 
               description = "Enroll a student to a course. Only admin users can perform this operation.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student enrolled successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid enrollment request or student/course not active"),
        @ApiResponse(responseCode = "409", description = "Student already enrolled in the course")
    })
    public ResponseEntity<String> enrollStudentToCourse(@Valid @RequestBody EnrollmentRequest request) {
        boolean enrolled = adminService.enrollStudentToCourse(request);

        if (enrolled) {
            return ResponseEntity.ok("Student enrolled successfully");
        } else {
            return ResponseEntity.badRequest()
                    .body("Failed to enroll student. Please check if student and course are active and not already enrolled.");
        }
    }

    @DeleteMapping("/enroll")
    @Operation(summary = "Unenroll student from course", 
               description = "Remove a student's enrollment from a course")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student unenrolled successfully"),
                @ApiResponse(responseCode = "404", description = "Enrollment not found")
    })
    public ResponseEntity<String> unenrollStudentFromCourse(
            @Parameter(description = "Student ID") @RequestParam Long studentId,
            @Parameter(description = "Course ID") @RequestParam Long courseId) {
        boolean unenrolled = adminService.unenrollStudentFromCourse(studentId, courseId);

        if (unenrolled) {
            return ResponseEntity.ok("Student unenrolled successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/enrollment/{enrollmentId}/status")
    @Operation(summary = "Change enrollment status", 
               description = "Change the status of an existing enrollment")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Enrollment status updated successfully"),
        @ApiResponse(responseCode = "404", description = "Enrollment not found")
    })
    public ResponseEntity<String> changeEnrollmentStatus(
            @Parameter(description = "Enrollment ID") @PathVariable Long enrollmentId,
            @Parameter(description = "New status") @RequestParam StudentCourse.EnrollmentStatus status) {
        boolean updated = adminService.changeEnrollmentStatus(enrollmentId, status);

        if (updated) {
            return ResponseEntity.ok("Enrollment status updated to " + status);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}