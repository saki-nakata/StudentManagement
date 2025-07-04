package raisetech.student.management.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.CourseDetail;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.dto.SearchCondition;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;
  @Mock
  private StudentConverter converter;

  @Spy
  @InjectMocks
  private StudentService sut;

  private Student student;
  private StudentCourse course;
  private CourseStatus status;
  private CourseDetail courseDetail;
  private StudentDetail studentDetail;
  private SearchCondition condition;

  @BeforeEach
  void before() {
    student = mock(Student.class);
    course = mock(StudentCourse.class);
    status = mock(CourseStatus.class);
    studentDetail = mock(StudentDetail.class);
    courseDetail = mock(CourseDetail.class);
    condition = mock(SearchCondition.class);
  }

  @Test
  void 受講生詳細の条件検索_リポジトリとコンバーターの一覧検索処理が適切に呼び出せていること() {
    List<Student> studentList = List.of(student);
    List<StudentCourse> courseList = List.of(course);
    List<CourseStatus> statusList = List.of(status);
    boolean isCourseDetailConditionEmpty = true;
    Mockito.when(repository.searchStudentList(condition)).thenReturn(studentList);
    Mockito.when(repository.searchCourseList(condition)).thenReturn(courseList);
    Mockito.when(repository.searchStatusList(condition)).thenReturn(statusList);

    List<StudentDetail> actual = sut.search(condition);

    Mockito.verify(repository, times(1)).searchStudentList(condition);
    Mockito.verify(repository, times(1)).searchCourseList(condition);
    Mockito.verify(repository, times(1)).searchStatusList(condition);

    List<StudentDetail> expected = converter.mapToStudentDetailList(studentList, courseList,
        statusList, isCourseDetailConditionEmpty);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 新規受講生詳細の登録_リポジトリの登録処理が呼び出されていること() {
    int studentId = 999;
    int courseId = 999;
    List<CourseDetail> courseDetailList = List.of(courseDetail);
    Mockito.when(studentDetail.getCourseDetailList()).thenReturn(courseDetailList);
    Mockito.when(studentDetail.getStudent()).thenReturn(student);
    Mockito.when(student.getId()).thenReturn(studentId);
    Mockito.when(courseDetail.getStudentCourse()).thenReturn(course);
    Mockito.when(course.getId()).thenReturn(courseId);
    Mockito.when(courseDetail.getCourseStatus()).thenReturn(status);

    StudentDetail actual = sut.registerStudent(studentDetail);

    Mockito.verify(repository, times(1)).registerStudent(student);
    Mockito.verify(student, times(1)).getId();
    Mockito.verify(repository, times(1)).registerCourse(course);
    Mockito.verify(course, times(1)).getId();
    Mockito.verify(repository, times(1)).registerStatus(status);
    assertThat(actual).isEqualTo(studentDetail);
  }

  @Test
  void 受講生詳細登録の初期情報設定_正しく値が設定されていること() {
    int studentId = 999;
    String courseName = "Javaコース";
    LocalDate startDate = LocalDate.of(2019, 8, 15);

    StudentCourse actual = new StudentCourse();
    actual.setStudentId(studentId);
    actual.setCourseName(courseName);
    actual.setStartDate(startDate);

    StudentCourse expected = new StudentCourse(0, studentId, courseName, startDate,
        startDate.plusMonths(6));

    sut.initStudentsCourse(actual, studentId);

    Mockito.verify(sut, times(1)).getScheduledEndDate(startDate);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void コース情報の終了予定日を設定_6か月後の日付が設定されていること() {
    LocalDate startDate = LocalDate.of(2020, 2, 20);
    LocalDate expected = LocalDate.of(2020, 8, 20);
    LocalDate actual = sut.getScheduledEndDate(startDate);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void コース詳細情報の登録_リポジトリの登録処理が呼び出されていること() {
    LocalDate startDate = LocalDate.of(2019, 8, 15);
    Mockito.when(courseDetail.getStudentCourse()).thenReturn(course);
    Mockito.when(courseDetail.getCourseStatus()).thenReturn(status);
    Mockito.when(course.getStartDate()).thenReturn(startDate);
    Mockito.when(course.getId()).thenReturn(999);

    CourseDetail actual = sut.registerCourse(courseDetail);

    Mockito.verify(sut, times(1)).getScheduledEndDate(startDate);
    Mockito.verify(repository, times(1)).registerCourse(course);
    Mockito.verify(course, times(1)).getId();
    Mockito.verify(repository, times(1)).registerStatus(status);
    assertThat(actual).isEqualTo(courseDetail);
  }

  @Test
  void 受講生詳細の更新_リポジトリの更新処理が呼び出されていること() {
    int studentId = 999;
    int courseId = 999;
    Mockito.when(studentDetail.getStudent()).thenReturn(student);
    Mockito.when(student.getId()).thenReturn(studentId);
    Mockito.when(course.getId()).thenReturn(courseId);
    List<CourseDetail> courseDetailList = List.of(courseDetail);
    Mockito.when(studentDetail.getCourseDetailList()).thenReturn(courseDetailList);
    Mockito.when(courseDetail.getStudentCourse()).thenReturn(course);
    Mockito.when(courseDetail.getCourseStatus()).thenReturn(status);

    sut.updateStudent(studentDetail);

    Mockito.verify(repository, times(1)).updateStudent(student);
    Mockito.verify(student, times(1)).getId();
    Mockito.verify(repository, times(1)).updateCourse(course);
    Mockito.verify(course, times(1)).getId();
    Mockito.verify(repository, times(1)).updateStatus(status);
  }

  @Test
  void コース詳細の更新_リポジトリの更新処理が呼び出されていること() {
    Mockito.when(courseDetail.getStudentCourse()).thenReturn(course);
    Mockito.when(courseDetail.getCourseStatus()).thenReturn(status);
    Mockito.when(course.getId()).thenReturn(999);

    sut.updateCourse(courseDetail);

    Mockito.verify(repository, times(1)).updateCourse(course);
    Mockito.verify(course, times(1)).getId();
    Mockito.verify(repository, times(1)).updateStatus(status);
  }

}