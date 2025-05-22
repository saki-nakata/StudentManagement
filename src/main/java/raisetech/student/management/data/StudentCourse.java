package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  @Schema(description = "ID", minimum = "1", maximum = "999", example = "1", required = true)
  @Max(value = 999, message = "999以下の数値にしてください。")
  private int id;

  @Schema(description = "受講生ID", minimum = "1", maximum = "999", example = "1", required = true)
  @Max(value = 999, message = "999以下の数値にしてください。")
  private int studentId; // 'student_id' フィールド

  @Schema(description = "コース名", example = "Javaコース", required = true)
  @NotEmpty(message = "コース名を入力してください。")
  @Size(max = 30, message = "30桁以内で入力してください。")
  private String courseName;  // 'course_name' フィールド

  @Schema(description = "開始日", format = "date", example = "2025-05-22", required = true)
  @NotNull(message = "開始日を入力してください。")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;  // 'start_date' フィールド

  @Schema(description = "終了予定日", format = "date", required = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate scheduledEndDate;  // 'scheduled_end_date' フィールド

}