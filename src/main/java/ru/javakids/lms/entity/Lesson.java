package ru.javakids.lms.entity;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String title;

    @Lob
  //  @Type(type = "org.hibernate.type.TextType")
    @Column
    String text;

    @ManyToOne
    Module module;

    @ManyToOne
    Course course;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    List<Task> tasks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_author")
    User createdUser;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_author")
    User updatedUser;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedDate;

    public Lesson(Long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public Lesson(Long id, String title, String text, Module module) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.module = module;
    }

    public Lesson(Long id, String title, String text, Course course) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.course = course;
    }
}