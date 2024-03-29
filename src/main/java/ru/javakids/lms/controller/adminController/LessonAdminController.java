package ru.javakids.lms.controller.adminController;

import ru.javakids.lms.entity.Lesson;
import ru.javakids.lms.dto.LessonDto;
import ru.javakids.lms.mapper.LessonMapper;
import ru.javakids.lms.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/lesson")
public class LessonAdminController {
    private LessonService lessonService;
    private LessonMapper lessonMapper;

    public LessonAdminController(LessonService lessonService,
                                 LessonMapper lessonMapper) {
        this.lessonService = lessonService;
        this.lessonMapper = lessonMapper;
    }

    @GetMapping("/new")
    public String newLessonForm(Model model, @RequestParam("course_id") Long courseId) {
        model.addAttribute("lessonDto", new LessonDto(courseId));
        return "lesson-form";
    }

    @PostMapping
    public String submitLessonForm(@Valid LessonDto lessonDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lesson-form";
        }
        Lesson mappedLesson = lessonMapper.mapLessonDtoToLesson(lessonDto);
        lessonService.saveLesson(mappedLesson);
        return "redirect:/course/" + lessonDto.getCourseId();
    }

    @DeleteMapping("/{id}")
    public String deleteLesson(@PathVariable("id") Long id, @RequestParam("course_id") Long courseId) {
        lessonService.deleteLesson(id);
        return "redirect:/course/" + courseId;
    }

}
