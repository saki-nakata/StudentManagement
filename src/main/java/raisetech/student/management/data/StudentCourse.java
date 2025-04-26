package raisetech.student.management.data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  private int id;
  private int studentId; // 'student_id' フィールド
  private String courseName;  // 'course_name' フィールド
  private LocalDate startDate;  // 'start_date' フィールド
  private LocalDate scheduledEndDate;  // 'scheduled_end_date' フィールド
}
