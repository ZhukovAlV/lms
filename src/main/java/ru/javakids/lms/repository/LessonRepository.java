package ru.javakids.lms.repository;

import ru.javakids.lms.entity.Lesson;
import ru.javakids.lms.dto.LessonDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select new ru.javakids.lms.dto.LessonDto(l.id, l.title, l.course.id) " +
            "from Lesson l where l.course.id = :id order by l.id")
    List<LessonDto> findAllForLessonIdWithoutText(@Param("id") long id);

    @Query("select new ru.javakids.lms.dto.LessonDto(l.id, l.title, l.text, l.course.id) " +
            "from Lesson l where l.id = :id")
    Optional<LessonDto> findLessonDtoByLessonId(@Param("id") long id);
}
