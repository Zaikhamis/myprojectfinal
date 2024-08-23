package tehama.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tehama.society.model.Course;
import tehama.society.model.Student;
import tehama.society.model.User;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseId(long courseId);


    @Query("SELECT c FROM Course c WHERE c.courseName = ?1")
    Course findByCourseName(String courseName);

    @Query("SELECT c FROM Course c JOIN c.teacher t WHERE t.username = ?1")
    List<Course> getAllCoursesByTeacherUsername(String username);

    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.username = ?1")
    List<Course> getAllCoursesByStudentUsername(String username);

    @Query("SELECT c FROM Course c WHERE NOT EXISTS (SELECT 1 FROM c.students s WHERE s.username = ?1)")
    List<Course> getAllNotEnrolledCourses(String username);

    @Query("SELECT COUNT(*) FROM Course c JOIN c.students s WHERE s.username = ?1")
    Integer getTotalCoursesEnrolledByStudentByUsername(String username);

    @Query("SELECT COUNT(*) FROM Course c")
    Integer getTotalCourses();

}
