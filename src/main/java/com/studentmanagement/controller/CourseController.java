package com.studentmanagement.controller;

import com.studentmanagement.dto.CourseDTO;
import com.studentmanagement.service.CourseService;
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
@RequestMapping("/api/courses")
@Tag(name = "Course Management", description = "APIs for managing courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    @Operation(summary = "Get all courses", description = "Retrieve all courses in the system")
    @ApiResponse(responseCode = "200", description = "Courses retrieved successfully")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active courses", description = "Retrieve all active courses")
    @ApiResponse(responseCode = "200", description = "Active courses retrieved successfully")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CourseDTO>> getActiveCourses() {
        List<CourseDTO> courses = courseService.getActiveCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/inactive")
    @Operation(summary = "Get inactive courses", description = "Retrieve all inactive courses")
    @ApiResponse(responseCode = "200", description = "Inactive courses retrieved successfully")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CourseDTO>> getInactiveCourses() {
        List<CourseDTO> courses = courseService.getInactiveCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Retrieve a specific course by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Course found"),
        @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CourseDTO> getCourseById(
            @Parameter(description = "Course ID") @PathVariable Long id) {
        Optional<CourseDTO> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{courseCode}")
    @Operation(summary = "Get course by course code", description = "Retrieve a course by its unique course code")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Course found"),
        @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CourseDTO> getCourseByCourseCode(
            @Parameter(description = "Course code") @PathVariable String courseCode) {
        Optional<CourseDTO> course = courseService.getCourseByCourseCode(courseCode);
        return course.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Search courses by name", description = "Search for courses by name (case-insensitive)")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    public ResponseEntity<List<CourseDTO>> searchCoursesByName(
            @Parameter(description = "Name to search for") @RequestParam String name) {
        List<CourseDTO> courses = courseService.searchCoursesByName(name);
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    @Operation(summary = "Create new course", description = "Create a new course in the system")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Course created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        CourseDTO createdCourse = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course", description = "Update an existing course's information")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Course updated successfully"),
        @ApiResponse(responseCode = "404", description = "Course not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDTO> updateCourse(
            @Parameter(description = "Course ID") @PathVariable Long id,
            @Valid @RequestBody CourseDTO courseDTO) {
        Optional<CourseDTO> updatedCourse = courseService.updateCourse(id, courseDTO);
        return updatedCourse.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course", description = "Delete a course from the system")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Course deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(@Parameter(description = "Course ID") @PathVariable Long id) {
        boolean deleted = courseService.deleteCourse(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate course", description = "Activate a course")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Course activated successfully"),
        @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> activateCourse(@Parameter(description = "Course ID") @PathVariable Long id) {
        boolean activated = courseService.activateCourse(id);
        return activated ? 
                ResponseEntity.ok("Course activated successfully") : 
                ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate course", description = "Deactivate a course")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Course deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deactivateCourse(@Parameter(description = "Course ID") @PathVariable Long id) {
        boolean deactivated = courseService.deactivateCourse(id);
        return deactivated ? 
                ResponseEntity.ok("Course deactivated successfully") : 
                ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/students/count")
    @Operation(summary = "Get enrolled students count", description = "Get the number of students enrolled in a course")
    @ApiResponse(responseCode = "200", description = "Student count retrieved successfully")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Long> getEnrolledStudentsCount(@Parameter(description = "Course ID") @PathVariable Long id) {
        Long count = courseService.getEnrolledStudentsCount(id);
        return ResponseEntity.ok(count);
    }
}