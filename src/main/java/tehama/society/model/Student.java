package tehama.society.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student extends User {
    private String regNumber;

    @ManyToMany(mappedBy = "students")
    @JsonManagedReference
    private List<Course> courses;


    @ManyToOne
    @JoinColumn(name = "branch_id")
//    @JsonIgnore
    @JsonBackReference
    private Branch branch;


    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Result> results;

}
