package tehama.society.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tehama.society.model.Course;
import tehama.society.model.Result;
import tehama.society.model.dto.ResultDTO;
import tehama.society.service.ResultService;

import java.util.List;

@RestController
@RequestMapping("/results")
@RequiredArgsConstructor
public class ResultController {
    private final ResultService resultService;

    @PostMapping("/insert/student/{studentId}/course/{courseId}")
    public ResponseEntity<Result> insertResult(@RequestBody ResultDTO resultDTO, @PathVariable long studentId, @PathVariable long courseId) {
        Result insertedResult = resultService.insertResult(resultDTO.getMarks(), studentId, courseId);
        return new ResponseEntity<>(insertedResult, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Result>> getAllResults() {
        List<Result> allResults = resultService.getAllResults();
        return new ResponseEntity<>(allResults, HttpStatus.OK);
    }


    @GetMapping("/all-student-results/course/{courseName}")
    public ResponseEntity<List<Result>> getAllStudentsResultsByCourseName(@PathVariable String courseName) {
        List<Result> allResults = resultService.getAllStudentsResultsByCourseName(courseName);
        return new ResponseEntity<>(allResults, HttpStatus.OK);
    }


    @GetMapping("/my-results/student/{username}")
    public ResponseEntity<List<Result>> getMyResultsByUsername(@PathVariable String username) {
        List<Result> myResultsByUsername = resultService.getMyResultsByUsername(username);
        return new ResponseEntity<>(myResultsByUsername, HttpStatus.OK);
    }


    @PutMapping("/update/{resultId}")
    public ResponseEntity<Result> updateResult(@RequestBody Result result, @PathVariable long resultId) {
        Result updateResult = resultService.updateResult(result.getMarks(), result.getGrade(), result.getGradePoint(), result.getClassification(), result.getRemarks(), resultId);
        return new ResponseEntity<>(updateResult, HttpStatus.OK);
    }


    @GetMapping("/{resultId}")
    public ResponseEntity<Result> findResult(@PathVariable long resultId) {
        Result result = resultService.findResult(resultId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{resultId}")
    public ResponseEntity<?> deleteResult(@PathVariable long resultId) {
        resultService.deleteResult(resultId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //    OTHER METHODS
    @GetMapping("/total-course-units/student/{username}")
    public ResponseEntity<Integer> getTotalCourseUnitByUsername(@PathVariable String username) {
        Integer totalUsers = resultService.getTotalCourseUnitByUsername(username);
        return new ResponseEntity<>(totalUsers, HttpStatus.OK);
    }


    @GetMapping("/total-course-grade-points/student/{username}")
    public ResponseEntity<Integer> getTotalCourseGradePointsByUsername(@PathVariable String username) {
        Integer totalUsers = resultService.getTotalCourseGradePointsByUsername(username);
        return new ResponseEntity<>(totalUsers, HttpStatus.OK);
    }
}
