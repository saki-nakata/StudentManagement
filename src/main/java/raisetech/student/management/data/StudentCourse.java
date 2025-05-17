package raisetech.student.management.data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class StudentCourse {

  @Max(value = 1000, message = "999以下の数値にしてください。")
  private int id;

  @Max(value = 1000, message = "999以下の数値にしてください。")
  private int studentId; // 'student_id' フィールド

  @NotEmpty(message = "コース名を入力してください。")
  @Size(max = 30, message = "30桁以内で入力してください。")
  private String courseName;  // 'course_name' フィールド

  @NotNull(message = "開始日を入力してください。")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;  // 'start_date' フィールド

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate scheduledEndDate;  // 'scheduled_end_date' フィールド

}
