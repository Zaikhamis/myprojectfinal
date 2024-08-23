package tehama.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tehama.society.model.Student;
import tehama.society.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User findUserByUserId(long userId);


    @Query("SELECT t FROM User t WHERE role = 'ROLE_TEACHER'")
    List<User> getAllTeachers();


    //@Query("SELECT s FROM Student s JOIN s.courses c WHERE c.courseName = ?1")
//    @Query("SELECT s FROM Student s WHERE NOT EXISTS (SELECT 1 FROM s.results r JOIN s.courses c WHERE c.courseName = ?1)")
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.courseName = ?1")
    List<Student> getAllStudentsByCourseName(String courseName);

    @Query("SELECT COUNT(*) FROM User u")
    Integer getTotalUsers();

    @Query("SELECT COUNT(*) FROM User u WHERE u.role = 'ROLE_TEACHER'")
    Integer getTotalTeachers();

    @Query("SELECT COUNT(*) FROM User u WHERE u.role = 'ROLE_STUDENT'")
    Integer getTotalStudents();

}
