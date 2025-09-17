package com.studentmanagement.service;

import com.studentmanagement.dto.StudentDTO;
import com.studentmanagement.entity.Student;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<StudentDTO> getActiveStudents() {
        return studentRepository.findByActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<StudentDTO> getInactiveStudents() {
        return studentRepository.findByActiveFalse()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<StudentDTO> getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<StudentDTO> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .map(this::convertToDTO);
    }

    public List<StudentDTO> searchStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    public Optional<StudentDTO> updateStudent(Long id, StudentDTO studentDTO) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setName(studentDTO.getName());
                    existingStudent.setEmail(studentDTO.getEmail());
                    existingStudent.setPhone(studentDTO.getPhone());
                    if (studentDTO.getActive() != null) {
                        existingStudent.setActive(studentDTO.getActive());
                    }
                    Student updatedStudent = studentRepository.save(existingStudent);
                    return convertToDTO(updatedStudent);
                });
    }

    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean activateStudent(Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setActive(true);
                    studentRepository.save(student);
                    return true;
                }).orElse(false);
    }

    public boolean deactivateStudent(Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setActive(false);
                    studentRepository.save(student);
                    return true;
                }).orElse(false);
    }

    public Long getEnrolledCoursesCount(Long studentId) {
        return studentCourseRepository.countActiveEnrollmentsByStudent(studentId);
    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setActive(student.getActive());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());

        // Set enrolled courses count
        Long enrolledCount = studentCourseRepository.countActiveEnrollmentsByStudent(student.getId());
        dto.setEnrolledCoursesCount(enrolledCount.intValue());

        return dto;
    }

    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setActive(dto.getActive() != null ? dto.getActive() : true);
        return student;
    }
}