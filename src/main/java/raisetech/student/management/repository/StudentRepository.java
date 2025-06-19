package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生とコース情報を扱うリポジトリです。 全件検索や単位等条件での検索、コース情報の検索が行えるクラス。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧(全件)
   */
  List<Student> searchStudent();

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報(全件)
   */
  List<StudentCourse> searchCourseList();

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   *
   * @param student 受講生
   */
  void registerStudent(Student student);

  /**
   * コース情報を新規登録します。IDに関しては自動採番を行う。
   *
   * @param course コース情報
   */
  void registerCourse(StudentCourse course);

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  Student getStudentInfo(int id);

  /**
   * 受講生IDに紐づくコース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づくコース情報
   */
  List<StudentCourse> getCourseInfo(int studentId);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * コース情報を更新します。
   *
   * @param course コース情報
   */
  void updateCourse(StudentCourse course);

}
