package ru.javakids.lms.service;

import ru.javakids.lms.entity.Lesson;
import ru.javakids.lms.dto.LessonDto;

import javax.transaction.Transactional;
import java.util.List;

public interface LessonService {
    void saveLesson(Lesson lesson);

    void deleteLesson(Long id);

    List<LessonDto> findAllForLessonIdWithoutText(Long courseId);

    @Transactional
    LessonDto findLessonDtoByLessonId(Long lessonId);

    boolean existsById(Long lessonId);

    @Transactional
    Lesson findLessonById(Long id);
}
