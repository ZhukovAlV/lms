package ru.javakids.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javakids.lms.entity.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
}
