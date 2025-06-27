package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.dto.SearchCondition;

/**
 * 受講生、コース情報、申込状況を扱うリポジトリです。検索や登録、更新を行います。
 */
@Mapper
public interface StudentRepository {

  /**
   * 検索条件による受講生の一覧を取得します。
   *
   * @param condition 検索条件
   * @return 検索条件に一致した受講生の一覧
   */
  List<Student> searchStudentList(SearchCondition condition);

  /**
   * 検索条件による受講生コース情報の一覧を取得します。
   *
   * @param condition 検索条件
   * @return 検索条件に一致した受講生コース情報の一覧
   */
  List<StudentCourse> searchCourseList(SearchCondition condition);

  /**
   * 検索条件によるコース申込状況の一覧を取得します。
   *
   * @param condition 検索条件
   * @return 検索条件に一致したコース申込状況の一覧
   */
  List<CourseStatus> searchStatusList(SearchCondition condition);

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
   * コース申込状況を新規登録します。IDに関しては自動採番を行う。
   *
   * @param status 申込状況
   */
  void registerStatus(CourseStatus status);

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

  /**
   * 申込状況を更新します。
   *
   * @param status 申込状況
   */
  void updateStatus(CourseStatus status);

}
