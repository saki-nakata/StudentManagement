package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生情報を扱うリポジトリ。
 *
 * 全件検索や単位等条件での検索、コース情報の検索が行えるクラス。
 */
@Mapper
public interface StudentRepository {

  /**
   * 全件検索
   *
   * @return 受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> searchStudent();

  /**
   * 全件検索
   *
   * @return 受講生コース情報の一覧
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchCourse();

  @Insert("INSERT INTO students (full_name, furigana, nickname, email_address, live_city, age, gender, remark, is_deleted) VALUES (#{fullName}, #{furigana}, #{nickname}, #{emailAddress}, #{liveCity}, #{age}, #{gender}, #{remark}, 0)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insertStudent(Student student);

  @Insert("INSERT INTO students_courses (student_id, course_name, start_date, scheduled_end_date) VALUES (#{studentId}, #{courseName}, #{startDate}, #{scheduledEndDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insertStudentCourse(StudentCourse course);

}
