package ru.javakids.lms.service;

import ru.javakids.lms.entity.Course;
import ru.javakids.lms.dto.CourseDto;
import org.springframework.data.domain.Page;

public interface CourseService {
    Page<Course> findCourses(String titlePrefix, Integer page);

    Course findCourseById(Long id);

    Course getCourseById(Long id);

    void saveCourse(Course course);

    void deleteCourse(Long id);

    void assignUser(Long courseId, Long userId);

    void unassignUser(Long courseId, Long userId);

    boolean existsById(Long courseId);

    CourseDto findCourseDtoByCourseId(Long id);

    void saveCourseDto(CourseDto courseDto);
}
