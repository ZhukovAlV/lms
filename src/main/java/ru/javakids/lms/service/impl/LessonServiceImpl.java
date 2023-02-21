package ru.javakids.lms.service.impl;

import ru.javakids.lms.entity.Lesson;
import ru.javakids.lms.dto.LessonDto;
import ru.javakids.lms.exception.NotFoundException;
import ru.javakids.lms.repository.LessonRepository;
import ru.javakids.lms.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    private LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository repository) {
        this.lessonRepository = repository;
    }

    @Override
    public void saveLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public List<LessonDto> findAllForLessonIdWithoutText(Long courseId) {
        return lessonRepository.findAllForLessonIdWithoutText(courseId);
    }

    @Override
    public LessonDto findLessonDtoByLessonId(Long lessonId) {
        return lessonRepository.findLessonDtoByLessonId(lessonId)
                .orElseThrow(() -> new NotFoundException(Lesson.class.getSimpleName(), lessonId));
    }

    @Override
    public Lesson findLessonById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Lesson.class.getSimpleName(), id));
    }

    @Override
    public boolean existsById(Long lessonId) {
        return lessonRepository.existsById(lessonId);
    }
}
