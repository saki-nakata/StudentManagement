package raisetech.student.management.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生詳細のオブジェクト
 */
@Schema(description = "受講生詳細")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudentDetail {

  @Schema(description = "受講生情報")
  @Valid
  private Student student;

  @Schema(description = "受講生コース情報")
  @Valid
  private List<StudentCourse> studentCourseList;

}