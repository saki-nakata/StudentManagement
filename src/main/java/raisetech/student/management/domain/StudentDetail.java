package raisetech.student.management.domain;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生詳細のオブジェクト
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetail {

  @Valid
  private Student student;
  private List<StudentCourse> studentCourseList;

}
