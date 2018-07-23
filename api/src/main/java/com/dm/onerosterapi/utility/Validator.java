package com.dm.onerosterapi.utility;

import com.dm.onerosterapi.apiconfig.ApiMessages;
import com.dm.onerosterapi.exceptions.*;
import com.dm.onerosterapi.repository.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Validator {

    private SchoolRepository schoolRepository;
    private ClassRepository classRepository;
    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    public Validator(
            SchoolRepository schoolRepository,
            ClassRepository classRepository,
            UserRepository userRepository,
            CourseRepository courseRepository,
            EnrollmentRepository enrollmentRepository
    ){
        this.schoolRepository = schoolRepository;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void validateUser(String sourcedId, String role) throws UserNotFoundException {
        if ((role.equals("student") || role.equals("teacher")) && !userRepository.existsBySourcedIdAndRoleIgnoreCase(sourcedId, role)) {
            throw new UserNotFoundException(ApiMessages.INVALID_USER + sourcedId);
        } else if (!userRepository.existsBySourcedIdIgnoreCase(sourcedId)) {
            throw new UserNotFoundException(ApiMessages.INVALID_USER + sourcedId);
        }
    }

    public void validateClass(String sourcedId) throws ClassOfCourseNotFoundException {
        if (!classRepository.existsBySourcedIdIgnoreCase(sourcedId)) {throw new ClassOfCourseNotFoundException(ApiMessages.INVALID_CLASS + sourcedId);}
    }

    public void validateCourse(String sourcedId) throws CourseNotFoundException {
        if (!courseRepository.existsBySourcedIdIgnoreCase(sourcedId)) {throw new CourseNotFoundException(ApiMessages.INVALID_COURSE + sourcedId);}
    }

    public void validateSchool(String sourcedId) throws SchoolNotFoundException {
        if (!schoolRepository.existsBySourcedIdIgnoreCase(sourcedId)) {throw new SchoolNotFoundException(ApiMessages.INVALID_SCHOOL + sourcedId);}
    }

    public void validateEnrollment(String sourcedId) throws EnrollmentNotFoundException {
        if (!enrollmentRepository.existsBySourcedId(sourcedId)) {throw new EnrollmentNotFoundException(ApiMessages.INVALID_ENROLLMENT + sourcedId);}
    }

    public void validateClassTerm(String term) throws TermNotFoundException {
        if (!classRepository.existsByTermIgnoreCase(term)) {throw new TermNotFoundException(ApiMessages.INVALID_TERM + term);}
    }


}
