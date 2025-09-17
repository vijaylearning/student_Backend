package com.studentmanagement.controller;

import com.studentmanagement.dto.StudentDTO;
import com.studentmanagement.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Management", description = "APIs for managing students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve all students in the system")
    @ApiResponse(responseCode = "200", description = "Students retrieved successfully")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active students", description = "Retrieve all active students")
    @ApiResponse(responseCode = "200", description = "Active students retrieved successfully")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<StudentDTO>> getActiveStudents() {
        List<StudentDTO> students = studentService.getActiveStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/inactive")
    @Operation(summary = "Get inactive students", description = "Retrieve all inactive students")
    @ApiResponse(responseCode = "200", description = "Inactive students retrieved successfully")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StudentDTO>> getInactiveStudents() {
        List<StudentDTO> students = studentService.getInactiveStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Retrieve a specific student by their ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student found"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<StudentDTO> getStudentById(
            @Parameter(description = "Student ID") @PathVariable Long id) {
        Optional<StudentDTO> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get student by email", description = "Retrieve a student by their email address")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student found"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<StudentDTO> getStudentByEmail(
            @Parameter(description = "Student email") @PathVariable String email) {
        Optional<StudentDTO> student = studentService.getStudentByEmail(email);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Search students by name", description = "Search for students by name (case-insensitive)")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    public ResponseEntity<List<StudentDTO>> searchStudentsByName(
            @Parameter(description = "Name to search for") @RequestParam String name) {
        List<StudentDTO> students = studentService.searchStudentsByName(name);
        return ResponseEntity.ok(students);
    }

    @PostMapping
    @Operation(summary = "Create new student", description = "Create a new student in the system")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Student created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student", description = "Update an existing student's information")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student updated successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDTO> updateStudent(
            @Parameter(description = "Student ID") @PathVariable Long id,
            @Valid @RequestBody StudentDTO studentDTO) {
        Optional<StudentDTO> updatedStudent = studentService.updateStudent(id, studentDTO);
        return updatedStudent.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", description = "Delete a student from the system")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudent(@Parameter(description = "Student ID") @PathVariable Long id) {
        boolean deleted = studentService.deleteStudent(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate student", description = "Activate a student account")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student activated successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> activateStudent(@Parameter(description = "Student ID") @PathVariable Long id) {
        boolean activated = studentService.activateStudent(id);
        return activated ? 
                ResponseEntity.ok("Student activated successfully") : 
                ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate student", description = "Deactivate a student account")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deactivateStudent(@Parameter(description = "Student ID") @PathVariable Long id) {
        boolean deactivated = studentService.deactivateStudent(id);
        return deactivated ? 
                ResponseEntity.ok("Student deactivated successfully") : 
                ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/courses/count")
    @Operation(summary = "Get enrolled courses count", description = "Get the number of courses a student is enrolled in")
    @ApiResponse(responseCode = "200", description = "Course count retrieved successfully")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Long> getEnrolledCoursesCount(@Parameter(description = "Student ID") @PathVariable Long id) {
        Long count = studentService.getEnrolledCoursesCount(id);
        return ResponseEntity.ok(count);
    }
}