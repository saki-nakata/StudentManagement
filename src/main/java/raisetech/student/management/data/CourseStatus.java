package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "コース申込状況")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CourseStatus {

  @Schema(description = "ID", minimum = "1", maximum = "999", example = "1", required = true)
  @Max(value = 999, message = "999以下の数値にしてください。")
  private int id;

  @Schema(description = "コースID", minimum = "1", maximum = "999", example = "1", required = true)
  @Max(value = 999, message = "999以下の数値にしてください。")
  private int courseId; // 'course_id' フィールド

  @Schema(description = "申込状況", example = "仮申込", required = true)
  @Pattern(regexp = "仮申込|本申込|受講中|受講終了", message = "｢仮申込・本申込・受講中・受講終了｣のどれかを入力してください。")
  private String applicationStatus;  // 'application_status' フィールド

}
