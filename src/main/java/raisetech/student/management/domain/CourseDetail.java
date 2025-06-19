package raisetech.student.management.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.StudentCourse;

/**
 * コース詳細のオブジェクト
 */
@Schema(description = "コース詳細")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CourseDetail {

  @Schema(description = "受講生コース情報")
  @Valid
  private StudentCourse studentCourse;

  @Schema(description = "コース申込状況")
  @Valid
  private CourseStatus courseStatus;

}
