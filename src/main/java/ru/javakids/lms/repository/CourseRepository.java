package ru.javakids.lms.repository;

import ru.javakids.lms.entity.Course;
import ru.javakids.lms.dto.CourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByTitleLikeIgnoreCaseOrderById(String title, Pageable pageable);

    @Query("select new ru.javakids.lms.dto.CourseDto(c.id, c.author, c.title) " +
            "from Course c where c.id = :id")
    Optional<CourseDto> findCourseDtoByCourseId(@Param("id") long id);
}