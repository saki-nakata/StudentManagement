package raisetech.student.management.data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class StudentCourse {

  private int id;

  private int studentId; // 'student_id' フィールド

  @NotNull(message = "コース名を入力してください。")
  @Size(max = 30, message = "30桁以内で入力してください。")
  private String courseName;  // 'course_name' フィールド

  @NotNull(message = "開始日を入力してください。")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;  // 'start_date' フィールド

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate scheduledEndDate;  // 'scheduled_end_date' フィールド

}
