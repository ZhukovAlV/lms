package ru.javakids.lms.service.impl;

import ru.javakids.lms.entity.Course;
import ru.javakids.lms.entity.User;
import ru.javakids.lms.dto.CourseDto;
import ru.javakids.lms.exception.NotFoundException;
import ru.javakids.lms.mapper.CourseMapper;
import ru.javakids.lms.repository.CourseRepository;
import ru.javakids.lms.service.CourseService;
import ru.javakids.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;
    private UserService userService;
    private CourseMapper courseMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             UserService userService,
                             CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.courseMapper = courseMapper;
    }


    @Override
    public Page<Course> findCourses(String titlePrefix, Integer page) {
        if (page == null)
            page = 0;
        return courseRepository.findByTitleLikeIgnoreCaseOrderById(titlePrefix == null ? "%" : titlePrefix + "%", PageRequest.of(page, 3));
    }

    @Override
    public Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Course.class.getSimpleName(), id));
    }

    @Override
    public Course getCourseById(Long id) {
        if (existsById(id))
            return courseRepository.getById(id);
        else
            throw new NotFoundException(Course.class.getSimpleName(), id);
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.delete(courseRepository.getById(id));
    }

    @Override
    public void assignUser(Long courseId, Long userId) {
        User user = userService.getUserById(userId);
        Course course = getCourseById(courseId);
        course.getUsers().add(user);
        user.getCourses().add(course);
        saveCourse(course);
    }

    @Override
    public void unassignUser(Long courseId, Long userId) {
        User user = userService.findUserById(userId);
        Course course = findCourseById(courseId);
        user.getCourses().remove(course);
        course.getUsers().remove(user);
        saveCourse(course);
    }

    @Override
    public boolean existsById(Long courseId) {
        return courseRepository.existsById(courseId);
    }

    @Override
    public CourseDto findCourseDtoByCourseId(Long id) {
        return courseRepository.findCourseDtoByCourseId(id)
                .orElseThrow(() -> new NotFoundException(Course.class.getSimpleName(), id));
    }

    @Override
    public void saveCourseDto(CourseDto courseDto) {
        Course course = new Course();
        if (courseDto.getId() != null) {
            course = getCourseById(courseDto.getId());
            courseMapper.mapCourseDtoToCourse(courseDto, course);

        } else
            courseMapper.mapCourseDtoToCourse(courseDto, course);
        saveCourse(course);
    }
}
