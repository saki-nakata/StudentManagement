package raisetech.student.management;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {
  private int id;
  private int studentId; // 'student_id' フィールド
  private String courseName;  // 'course_name' フィールド
  private Date startDate;  // 'start_date' フィールド
  private Date sceduledEndDate;  // 'sceduled_end_date' フィールド
}
