package ru.javakids.lms.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javakids.lms.dto.LessonDto;
import ru.javakids.lms.entity.Course;
import ru.javakids.lms.entity.Lesson;
import ru.javakids.lms.repository.CourseRepository;

@Service
public class LessonMapper {

    private CourseRepository courseRepository;

    @Autowired
    public LessonMapper(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Lesson mapLessonDtoToLesson(LessonDto lessonDto) {
        Course course = courseRepository.getById(lessonDto.getCourseId());
        return new Lesson(
                lessonDto.getId(),
                lessonDto.getTitle(),
                lessonDto.getText(),
                course
        );

    }
}
