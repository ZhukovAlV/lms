package ru.javakids.lms.controller;

import ru.javakids.lms.entity.Course;
import ru.javakids.lms.entity.User;
import ru.javakids.lms.exception.InternalServerError;
import ru.javakids.lms.exception.NotFoundException;
import ru.javakids.lms.exception.UserNotAssignedToCourseException;
import ru.javakids.lms.mapper.CourseMapper;
import ru.javakids.lms.mapper.UserMapper;
import ru.javakids.lms.service.CourseCoverStorageService;
import ru.javakids.lms.service.CourseService;
import ru.javakids.lms.service.LessonService;
import ru.javakids.lms.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;

@Controller
@RequestMapping("/course")
public class CourseController {
    private CourseService courseService;
    private UserService userService;
    private LessonService lessonService;
    private UserMapper userMapper;
    private CourseMapper courseMapper;
    private CourseCoverStorageService courseCoverStorageService;

    public CourseController(CourseService courseService,
                            UserService userService,
                            LessonService lessonService,
                            UserMapper userMapper,
                            CourseMapper courseMapper,
                            CourseCoverStorageService courseCoverStorageService) {
        this.courseService = courseService;
        this.userService = userService;
        this.lessonService = lessonService;
        this.userMapper = userMapper;
        this.courseMapper = courseMapper;
        this.courseCoverStorageService = courseCoverStorageService;
    }

    @ModelAttribute("principalId")
    public Long principalIdAttribute(Principal principal) {
        if (principal != null)
            return userService.findUserByUsername(principal.getName()).getId();
        return null;
    }


    @GetMapping
    public String getCourses(Model model,
                             @RequestParam(name = "titlePrefix", required = false) String titlePrefix,
                             @Min(0) @RequestParam(name = "page", required = false) Integer page) {
        Page<Course> courses = courseService.findCourses(titlePrefix, page);
        int currentPage;
        if (page == null)
            currentPage = 0;
        else
            currentPage = page;
        model.addAttribute("courses", courseMapper.mapCourseListToCourseDtoList(courses.getContent()));
        model.addAttribute("activePage", "courses");
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("titlePrefix", titlePrefix);
        model.addAttribute("pages", (int) Math.ceil((double) courses.getTotalElements() / 3));
        return "course-table";
    }

    @GetMapping("/{id}")
    public String getCourseForm(Model model,
                                @PathVariable("id") Long id,
                                HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            User user = userService.findUserByUsername(request.getUserPrincipal().getName());
            if (!user.getCourses().contains(courseService.getCourseById(id)))
                throw new UserNotAssignedToCourseException("User " + id + " not assigned to course.");
        }
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", courseService.findCourseDtoByCourseId(id));
        model.addAttribute("lessons", lessonService.findAllForLessonIdWithoutText(id));
        model.addAttribute("users", userMapper.mapUserListToUserDtoList(new ArrayList<>(course.getUsers())));
        return "course-form";
    }

    @GetMapping("/{id}/cover")
    public ResponseEntity<byte[]> getCourseCover(@PathVariable("id") Long id) {
        String contentType = courseCoverStorageService.getContentTypeByCourseId(id);
        byte[] data = courseCoverStorageService.getCourseCoverByCourseId(id)
                .orElseThrow(NotFoundException::new);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(data);
    }

    @PostMapping("/{id}/cover")
    public String updateCourseCover(@RequestParam("cover") MultipartFile avatar,
                                    @PathVariable("id") Long id) {
        try {
            courseCoverStorageService.save(id, avatar.getContentType(), avatar.getInputStream());
        } catch (Exception ex) {
            throw new InternalServerError();
        }
        return "redirect:/course/" + id;
    }

    @PostMapping("/{courseId}/assign")
    public String assignUserToCourse(@PathVariable("courseId") Long courseId,
                                     @RequestParam("userId") Long userId,
                                     HttpServletRequest request) {
        String principalName = request.getUserPrincipal().getName();
        Long principalId = userService.findUserByUsername(principalName).getId();
        boolean userIsNotAdmin = !request.isUserInRole("ROLE_ADMIN");
        boolean userIsChangingAnotherUser = !userId.equals(principalId);
        if (userIsNotAdmin && userIsChangingAnotherUser)
            throw new AccessDeniedException("Access denied");
        courseService.assignUser(courseId, userId);
        return "redirect:/course";
    }

    @GetMapping("/{courseId}/assign")
    public String getAssignUserToCourseForm(@PathVariable("courseId") Long courseId,
                                            Model model,
                                            HttpServletRequest request) {
        if (courseService.existsById(courseId)) {
            model.addAttribute("courseId", courseId);
            if (request.isUserInRole("ROLE_ADMIN")) {
                model.addAttribute("users", userService.findUsersNotAssignedToCourse(courseId));
            } else {
                User user = userService.findUserByUsername(request.getRemoteUser());
                model.addAttribute("users", Collections.singletonList(user));
            }
            return "assign-user";
        } else {
            throw new NotFoundException(Course.class.getSimpleName(), courseId);
        }

    }

}