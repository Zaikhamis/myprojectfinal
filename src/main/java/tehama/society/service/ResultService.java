package tehama.society.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tehama.society.model.Course;
import tehama.society.model.Result;
import tehama.society.model.Student;
import tehama.society.repository.ResultRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultService {
    private final ResultRepository resultRepository;
    private final UserService userService;
    private final CourseService courseService;

    public Result insertResult(double marks, long studentId, long courseId) {
        Result result = new Result();
        result.setMarks(marks);
        Student student = (Student) userService.findUserByUserId(studentId);
        Course course = courseService.findCourse(courseId);
        result.setStudent(student);
        result.setCourse(course);

        // Set grade and grade point based on marks
        if (marks >= 70 && marks <= 100) {
            result.setGradePoint(50);
            result.setGrade("A");
        } else if (marks >= 60 && marks <= 69) {
            result.setGradePoint(40);
            result.setGrade("B+");
        } else if (marks >= 50 && marks <= 59) {
            result.setGradePoint(30);
            result.setGrade("B");
        } else if (marks >= 40 && marks <= 49) {
            result.setGradePoint(20);
            result.setGrade("C");
        } else if (marks >= 0 && marks < 40) {
            result.setGradePoint(0);
            result.setGrade("F");
        } else {
            throw new IllegalArgumentException("Marks should be between 0 and 100");
        }

        return resultRepository.save(result);
    }


    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }


    //    FOR TEACHER
    public List<Result> getAllStudentsResultsByCourseName(String courseName) {
        return resultRepository.getAllStudentsResultsByCourseName(courseName);
    }


    //    FOR STUDENT
    public List<Result> getMyResultsByUsername(String username) {
        return resultRepository.getMyResultsByUsername(username);
    }


    public Result updateResult(double marks, String grade, int gradePoint, String classification, String remarks, long resultId) {
        Result updateResult = findResult(resultId);
        updateResult.setMarks(marks);
        updateResult.setGrade(grade);
        updateResult.setGradePoint(gradePoint);
        updateResult.setClassification(classification);
        updateResult.setRemarks(remarks);
        resultRepository.save(updateResult);
        return updateResult;
    }

    public Result findResult(long resultId) {
        Result result = resultRepository.findByResultId(resultId);
        if (userService == null) {
            throw new RuntimeException("Result not found");
        }

        return result;
    }


    public void deleteResult(long resultId) {
        resultRepository.deleteById(resultId);
    }


    //    OTHER METHODS
    public Integer getTotalCourseUnitByUsername(String username) {
        return resultRepository.getTotalCourseUnitByUsername(username);
    }


    public Integer getTotalCourseGradePointsByUsername(String username) {
        return resultRepository.getTotalCourseGradePointsByUsername(username);
    }
}
