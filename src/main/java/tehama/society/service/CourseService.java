package tehama.society.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tehama.society.model.Course;
import tehama.society.model.Student;
import tehama.society.model.Teacher;
import tehama.society.model.User;
import tehama.society.repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;

    public Course registerCourse(String courseCode, String courseName, long teacherId) {
        Course course = new Course();
        Teacher teacher = (Teacher) userService.findUserByUserId(teacherId);
        course.setTeacher(teacher);
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);
        course.setCourseUnit(10);
        return courseRepository.save(course);
    }


    public Course makeCourseEnrollment(long courseId, long studentId) {
        Course course = findCourse(courseId);
        Student student = (Student) userService.findUserByUserId(studentId);
        course.enrollStudentsToCourse(student);
        return courseRepository.save(course);
    }


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    public List<Course> getAllNotEnrolledCourses(String username) {
        return courseRepository.getAllNotEnrolledCourses(username);
    }


    public List<Course> getAllCoursesByTeacher(String username) {
        return courseRepository.getAllCoursesByTeacherUsername(username);
    }


    public List<Course> getAllCoursesByStudent(String username) {
        return courseRepository.getAllCoursesByStudentUsername(username);
    }


    public Integer getTotalCoursesEnrolledByStudentByUsername(String username) {
        return courseRepository.getTotalCoursesEnrolledByStudentByUsername(username);
    }


    public Course updateCourse(String courseCode, String courseName, int courseUnit, long courseId) {
        Course updateCourse = findCourse(courseId);
        updateCourse.setCourseCode(courseCode);
        updateCourse.setCourseName(courseName);
        updateCourse.setCourseUnit(courseUnit);
        courseRepository.save(updateCourse);
        return updateCourse;
    }

    public Course findCourse(long courseId) {
        Course course = courseRepository.findByCourseId(courseId);
        if (userService == null) {
            throw new RuntimeException("Course not found");
        }

        return course;
    }


    public Course findByCourseName(String courseName) {
        Course course = courseRepository.findByCourseName(courseName);
        if (userService == null) {
            throw new RuntimeException("Course not found");
        }

        return course;
    }

    public void deleteCourse(long courseId) {
        courseRepository.deleteById(courseId);
    }

    //    OTHERS
    public Integer getTotalCourses() {
        return courseRepository.getTotalCourses();
    }

}
