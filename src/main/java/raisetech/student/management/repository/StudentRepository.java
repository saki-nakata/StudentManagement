package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生情報を扱うリポジトリ。
 * <p>
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
  int registerStudent(Student student);

  @Insert("INSERT INTO students_courses (student_id, course_name, start_date, scheduled_end_date) VALUES (#{studentId}, #{courseName}, #{startDate}, #{scheduledEndDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int registerCourse(StudentCourse course);

  @Select("SELECT * FROM students WHERE id=#{id}")
  Student getStudentInfo(int id);

  @Select("SELECT course_name, start_date, scheduled_end_date FROM students_courses WHERE student_id=#{id}")
  List<StudentCourse> getCourseInfo(int id);

  @Update(
      "UPDATE students SET full_name=#{fullName}, furigana=#{furigana}, email_address=#{emailAddress}, live_city=#{liveCity},age=#{age}, gender=#{gender}, remark=#{remark}"
          + " WHERE id=#{id}")
  int updateStudent(Student student);

  @Update(
      "UPDATE students_courses SET course_name=#{courseName}, start_date=#{startDate}, scheduled_end_date=#{scheduledEndDate}"
          + " WHERE student_id=#{studentId}")
  int updateCourse(StudentCourse course);
}
