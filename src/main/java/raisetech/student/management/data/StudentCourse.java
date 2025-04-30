package raisetech.student.management.data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class StudentCourse {

  private int id;
  private int studentId; // 'student_id' フィールド
  private String courseName;  // 'course_name' フィールド
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;  // 'start_date' フィールド
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate scheduledEndDate;  // 'scheduled_end_date' フィールド
}
