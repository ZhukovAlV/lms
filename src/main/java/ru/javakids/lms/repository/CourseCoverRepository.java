package ru.javakids.lms.repository;

import ru.javakids.lms.entity.CourseCover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseCoverRepository extends JpaRepository<CourseCover, Long> {
    @Query("from CourseCover cc left join cc.course c where c.id = :courseId")
    Optional<CourseCover> findByCourseId(Long courseId);
}
