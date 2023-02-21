package ru.javakids.lms.entity;

import lombok.*;

import javax.persistence.*;

import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String name;

    @Column
    String description;

    @Column
    String type;

    @Lob
    @Column
    String content;

    @ManyToOne
    Lesson lesson;

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

    public Task(Long id, String name, String description, String type, String content, Lesson lesson) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.content = content;
        this.lesson = lesson;
    }
}
