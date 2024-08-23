package tehama.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tehama.society.model.Course;
import tehama.society.model.Result;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    Result findByResultId(long resultId);

//    @Query("SELECT r FROM Result r WHERE NOT EXISTS (SELECT 1 FROM r.course c WHERE c.courseName = ?1)")

    @Query("SELECT r FROM Result r JOIN r.course c WHERE c.courseName = ?1")
    List<Result> getAllStudentsResultsByCourseName(String courseName);

    @Query("SELECT r FROM Result r JOIN r.student s WHERE s.username = ?1")
    List<Result> getMyResultsByUsername(String username);

    @Query("SELECT SUM(courseUnit) FROM Result r JOIN r.course c JOIN r.student s WHERE s.username = ?1")
    Integer getTotalCourseUnitByUsername(String username);

    @Query("SELECT SUM(gradePoint) FROM Result r JOIN r.course c JOIN r.student s WHERE s.username = ?1")
    Integer getTotalCourseGradePointsByUsername(String username);
}
