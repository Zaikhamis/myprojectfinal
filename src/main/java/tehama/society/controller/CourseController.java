package tehama.society.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tehama.society.model.Course;
import tehama.society.model.dto.CourseDTO;
import tehama.society.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/register/teacher/{teacherId}")
    public ResponseEntity<Course> registerCourse(@RequestBody CourseDTO courseDTO, @PathVariable long teacherId) {
        Course registerCourse = courseService.registerCourse(courseDTO.getCourseCode(), courseDTO.getCourseName(), teacherId);
        return new ResponseEntity<>(registerCourse, HttpStatus.CREATED);
    }


    @PostMapping("/enroll/{courseId}/student/{studentId}")
    public ResponseEntity<Course> makeCourseEnrollment(@PathVariable long courseId, @PathVariable long studentId) {
        Course makeCourseEnrollment = courseService.makeCourseEnrollment(courseId, studentId);
        return new ResponseEntity<>(makeCourseEnrollment, HttpStatus.CREATED);
    }



    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> allCourses = courseService.getAllCourses();
        return new ResponseEntity<>(allCourses, HttpStatus.OK);
    }


    @GetMapping("/all/teacher/{username}")
    public ResponseEntity<List<Course>> getAllCoursesByTeacher(@PathVariable String username) {
        List<Course> allCourses = courseService.getAllCoursesByTeacher(username);
        return new ResponseEntity<>(allCourses, HttpStatus.OK);
    }


    @GetMapping("/all/student/{username}")
    public ResponseEntity<List<Course>> getAllCoursesByStudent(@PathVariable String username) {
        List<Course> allCoursesByStudent = courseService.getAllCoursesByStudent(username);
        return new ResponseEntity<>(allCoursesByStudent, HttpStatus.OK);
    }


    @GetMapping("/all-not-enrolled/student/{username}")
    public ResponseEntity<List<Course>> getAllNotEnrolledCourses(@PathVariable String username) {
        List<Course> allCoursesByStudent = courseService.getAllNotEnrolledCourses(username);
        return new ResponseEntity<>(allCoursesByStudent, HttpStatus.OK);
    }



    @PutMapping("/update/{courseId}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable long courseId) {
        Course updateCourse = courseService.updateCourse(course.getCourseCode(), course.getCourseName(), course.getCourseUnit(), courseId);
        return new ResponseEntity<>(updateCourse, HttpStatus.OK);
    }


    @GetMapping("/{courseId}")
    public ResponseEntity<Course> findCourse(@PathVariable long courseId) {
        Course course = courseService.findCourse(courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }


    @GetMapping("/name/{courseName}")
    public ResponseEntity<Course> findByCourseName(@PathVariable String courseName) {
        Course course = courseService.findByCourseName(courseName);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }




    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable long courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/total-enrolled-courses/student/{username}")
    public ResponseEntity<Integer> getTotalCoursesEnrolledByStudentByUsername(@PathVariable String username) {
        Integer totalUsers = courseService.getTotalCoursesEnrolledByStudentByUsername(username);
        return new ResponseEntity<>(totalUsers, HttpStatus.OK);
    }

    @GetMapping("/total-courses")
    public ResponseEntity<Integer> getTotalCourses() {
        Integer totalUsers = courseService.getTotalCourses();
        return new ResponseEntity<>(totalUsers, HttpStatus.OK);
    }
}
