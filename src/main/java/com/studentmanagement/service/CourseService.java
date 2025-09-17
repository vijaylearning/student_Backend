package com.studentmanagement.service;

import com.studentmanagement.dto.CourseDTO;
import com.studentmanagement.entity.Course;
import com.studentmanagement.repository.CourseRepository;
import com.studentmanagement.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getActiveCourses() {
        return courseRepository.findByActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getInactiveCourses() {
        return courseRepository.findByActiveFalse()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CourseDTO> getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<CourseDTO> getCourseByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode)
                .map(this::convertToDTO);
    }

    public List<CourseDTO> searchCoursesByName(String name) {
        return courseRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    public Optional<CourseDTO> updateCourse(Long id, CourseDTO courseDTO) {
        return courseRepository.findById(id)
                .map(existingCourse -> {
                    existingCourse.setName(courseDTO.getName());
                    existingCourse.setDescription(courseDTO.getDescription());
                    existingCourse.setCourseCode(courseDTO.getCourseCode());
                    existingCourse.setCredits(courseDTO.getCredits());
                    existingCourse.setFee(courseDTO.getFee());
                    if (courseDTO.getActive() != null) {
                        existingCourse.setActive(courseDTO.getActive());
                    }
                    Course updatedCourse = courseRepository.save(existingCourse);
                    return convertToDTO(updatedCourse);
                });
    }

    public boolean deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean activateCourse(Long id) {
        return courseRepository.findById(id)
                .map(course -> {
                    course.setActive(true);
                    courseRepository.save(course);
                    return true;
                }).orElse(false);
    }

    public boolean deactivateCourse(Long id) {
        return courseRepository.findById(id)
                .map(course -> {
                    course.setActive(false);
                    courseRepository.save(course);
                    return true;
                }).orElse(false);
    }

    public Long getEnrolledStudentsCount(Long courseId) {
        return studentCourseRepository.countActiveEnrollmentsByCourse(courseId);
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());
        dto.setCourseCode(course.getCourseCode());
        dto.setCredits(course.getCredits());
        dto.setFee(course.getFee());
        dto.setActive(course.getActive());
        dto.setCreatedAt(course.getCreatedAt());
        dto.setUpdatedAt(course.getUpdatedAt());

        // Set enrolled students count
        Long enrolledCount = studentCourseRepository.countActiveEnrollmentsByCourse(course.getId());
        dto.setEnrolledStudentsCount(enrolledCount.intValue());

        return dto;
    }

    private Course convertToEntity(CourseDTO dto) {
        Course course = new Course();
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        course.setCourseCode(dto.getCourseCode());
        course.setCredits(dto.getCredits());
        course.setFee(dto.getFee());
        course.setActive(dto.getActive() != null ? dto.getActive() : true);
        return course;
    }
}