package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生、コース情報、申込状況を扱うリポジトリです。検索や登録、更新を行います。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生の一覧(全件)
   */
  List<Student> searchStudent();

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return コース情報の一覧(全件)
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
   * コース申込状況を新規登録します。IDに関しては自動採番を行う。
   *
   * @param status 申込状況
   */
  void registerStatus(CourseStatus status);

  /**
   * IDに紐づく受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return IDに紐づく受講生
   */
  Student getStudentInfo(int id);

  /**
   * 受講生IDに紐づくコース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づくコース情報の一覧
   */
  List<StudentCourse> getCourseInfo(int studentId);

  /**
   * コースIDに紐づく申込状況を検索します。
   *
   * @param courseId コースID
   * @return コースIDに紐づく申込状況
   */
  CourseStatus getStatusInfo(int courseId);

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
