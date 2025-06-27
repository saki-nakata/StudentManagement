package raisetech.student.management.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.CourseDetail;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.dto.SearchCondition;
import raisetech.student.management.repository.StudentRepository;

/**
 * 受講生情報を扱うサービスです。受講生の検索や登録、更新処理を行います。
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
   * 受講生詳細の条件検索を行います。検索条件がない場合は全件検索を行います。 検索条件が指定されていない場合は、全件の受講生情報を返します。
   *
   * @param condition 検索条件
   * @return 検索条件に一致した受講生詳細の一覧
   */
  public List<StudentDetail> search(SearchCondition condition) {
    List<Student> studentList = repository.searchStudentList(condition);
    List<StudentCourse> courseList = repository.searchCourseList(condition);
    List<CourseStatus> statusList = repository.searchStatusList(condition);

    boolean isCourseDetailConditionEmpty = false;
    if (condition.getCourseId() == null && condition.getCourseName() == null
        && condition.getStatusId() == null && condition.getStatus() == null) {
      isCourseDetailConditionEmpty = true;
    }
    return converter.mapToStudentDetailList(studentList, courseList, statusList,
        isCourseDetailConditionEmpty);
  }

  /**
   * 受講生詳細の登録を行います。 受講生、コース情報、申込状況を個別に登録し、コース情報には受講生を紐づける値とコース終了予定日、申込状況にはコース情報を紐づけるコースIDを設定します。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    //todo；新規受講生情報の登録
    repository.registerStudent(student);
    //todo；受講生コース情報と申込状況の登録
    studentDetail.getCourseDetailList().forEach(courseDetail -> {
      StudentCourse course = courseDetail.getStudentCourse();
      CourseStatus status = courseDetail.getCourseStatus();
      initStudentsCourse(course, student.getId());
      repository.registerCourse(course);
      status.setCourseId(course.getId());
      repository.registerStatus(status);
    });
    return studentDetail;
  }

  /**
   * コース情報を登録する際の初期情報を設定をします。
   *
   * @param course    コース情報
   * @param studentId 受講生ID
   */
  void initStudentsCourse(StudentCourse course, int studentId) {
    course.setStudentId(studentId);
    course.setCourseName(course.getCourseName());
    LocalDate startDate = course.getStartDate();
    course.setStartDate(startDate);
    if (course.getStartDate() != null) {
      course.setScheduledEndDate(getScheduledEndDate(course.getStartDate()));
    }
  }

  /**
   * コース情報、申込状況を個別に登録します。 コース情報には終了予定日を設定し、申込状況にはコース情報を紐づけるコースIDを設定します。
   *
   * @param courseDetail コース詳細
   * @return 登録情報を付与したコース詳細
   */
  @Transactional
  public CourseDetail registerCourse(CourseDetail courseDetail) {
    StudentCourse course = courseDetail.getStudentCourse();
    CourseStatus status = courseDetail.getCourseStatus();
    if (course.getStartDate() != null) {
      course.setScheduledEndDate(getScheduledEndDate(course.getStartDate()));
    }
    repository.registerCourse(course);
    status.setCourseId(course.getId());
    repository.registerStatus(status);
    return courseDetail;
  }

  /**
   * コース情報の終了予定日を設定します。開始日から6か月後の日付を返します。
   *
   * @param startDate 開始日
   * @return 終了予定日
   */
  LocalDate getScheduledEndDate(LocalDate startDate) {
    return startDate.plusMonths(6);
  }



  /**
   * 受講生詳細の更新を行います。受講生、コース情報、申込状況をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    //todo；受講生の更新
    repository.updateStudent(student);
    //todo；コース情報と申込状況の更新
    studentDetail.getCourseDetailList().forEach(courseDetail -> {
      StudentCourse course = courseDetail.getStudentCourse();
      course.setStudentId(student.getId());
      repository.updateCourse(course);
      CourseStatus status = courseDetail.getCourseStatus();
      status.setCourseId(course.getId());
      repository.updateStatus(status);
    });
  }

  /**
   * コース詳細の更新を行います。コース情報、申込状況をそれぞれ更新します。
   *
   * @param courseDetail コース詳細
   */
  @Transactional
  public void updateCourse(CourseDetail courseDetail) {
    StudentCourse course = courseDetail.getStudentCourse();
    CourseStatus status = courseDetail.getCourseStatus();
    //todo；コース情報の更新
    repository.updateCourse(course);
    status.setCourseId(course.getId());
    //todo；申込状況の更新
    repository.updateStatus(status);
  }

}
