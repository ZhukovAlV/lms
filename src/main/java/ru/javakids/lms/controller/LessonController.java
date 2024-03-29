package ru.javakids.lms.controller;

import ru.javakids.lms.entity.Lesson;
import ru.javakids.lms.entity.User;
import ru.javakids.lms.exception.UserNotAssignedToCourseException;
import ru.javakids.lms.service.LessonService;
import ru.javakids.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private LessonService lessonService;
    private UserService userService;

    @Autowired
    public LessonController(LessonService lessonService,
                            UserService userService) {
        this.lessonService = lessonService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String editLesson(Model model,
                             @PathVariable("id") Long id,
                             HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            Lesson lesson = lessonService.findLessonById(id);
            User user = userService.findUserByUsername(request.getUserPrincipal().getName());
            if (!user.getCourses().contains(lesson.getCourse()))
                throw new UserNotAssignedToCourseException("User " + id + " not assigned to course.");
        }
        model.addAttribute("lessonDto", lessonService.findLessonDtoByLessonId(id));
        return "lesson-form";
    }

}
