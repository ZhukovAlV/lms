package ru.javakids.lms.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "modules")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String name;

    @Column
    String description;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    List<Lesson> lessons;

    @ManyToOne
    Course course;

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

    public Module(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Module(Long id, String name, String description, List<Lesson> lessons, Course course) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lessons = lessons;
        this.course = course;
    }
}
