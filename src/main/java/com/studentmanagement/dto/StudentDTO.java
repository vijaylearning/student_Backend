package com.studentmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class StudentDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(min = 10, max = 15, message = "Phone must be between 10 and 15 characters")
    private String phone;

    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer enrolledCoursesCount;

    // Constructors
    public StudentDTO() {}

    public StudentDTO(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.active = true;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getEnrolledCoursesCount() {
        return enrolledCoursesCount;
    }

    public void setEnrolledCoursesCount(Integer enrolledCoursesCount) {
        this.enrolledCoursesCount = enrolledCoursesCount;
    }
}