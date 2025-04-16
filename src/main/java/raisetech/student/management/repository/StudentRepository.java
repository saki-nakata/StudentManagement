package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
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
   * @return 受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> studentSearch();

  /**
   * 全件検索
   * @return 受講生コース情報の一覧
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> courseSearch();

}
