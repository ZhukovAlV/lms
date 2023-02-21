package ru.javakids.lms.entity;

import javax.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Автор курса должен быть заполнен")
    @Column
    String author;

    @NotBlank(message = "Название курса должно быть заполнено")
    @Column
    String title;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    List<Lesson> lessons;

    @ManyToMany
    Set<User> users;

    @OneToOne(mappedBy = "course", cascade = CascadeType.REMOVE)
    CourseCover courseCover;

    public Course(Long id, String author, String title, List<Lesson> lessons, Set<User> users) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.lessons = lessons;
        this.users = users;
    }
}