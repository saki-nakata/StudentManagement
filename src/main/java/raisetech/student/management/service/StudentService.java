package raisetech.student.management.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

/**
 * 受講生情報を扱うサービスです。 受講生の検索や登録、更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行います。
   *
   * @return 受講生詳細一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.searchStudent();
    List<StudentCourse> courseList = repository.searchCourseList();
    return converter.convertStudentDetails(studentList, courseList);
  }

  /**
   * 受講生詳細の登録を行います。 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、終了予定日を設定します。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    //todo；新規受講生情報の登録
    repository.registerStudent(student);
    //todo；受講生コース情報の登録
    studentDetail.getStudentCourseList().forEach(course -> {
      initStudentsCourse(course, student.getId());
      repository.registerCourse(course);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param course  コース情報
   * @param studentID 受講生ID
   */
void initStudentsCourse(StudentCourse course, int studentID) {
    course.setStudentId(studentID);
    course.setCourseName(course.getCourseName());
    LocalDate startDate = course.getStartDate();
    course.setStartDate(startDate);
    if (course.getStartDate() != null) {
      course.setScheduledEndDate(course.getStartDate().plusMonths(6));
    }
  }

  /**
   * 受講生詳細検索です。 IDに紐づく受講生情報を取得したあと、その受講生情報に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 　受講生ID
   * @return 受講生詳細
   */
  public StudentDetail getStudentInfo(int id) {
    Student student = repository.getStudentInfo(id);
    List<StudentCourse> courseList = repository.getCourseInfo(id);
    return new StudentDetail(student, courseList);
  }

  /**
   * 受講生詳細の更新を行います。受講生とコース情報をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    //todo；新規受講生情報の更新
    repository.updateStudent(student);
    //todo；受講生コース情報の更新
    studentDetail.getStudentCourseList().forEach(course -> {
      course.setStudentId(student.getId());
      repository.updateCourse(course);
    });
  }

}
