package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.dto.SearchCondition;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  private SearchCondition condition;

  @BeforeEach
  void before() {
    condition = new SearchCondition();
  }

  @Test
  void 受講生の検索ができること() {
    List<Student> actual = sut.searchStudentList(condition);
    assertThat(actual).hasSize(5);
  }

  @Test
  void 受講生IDで受講生を検索できること() {
    condition.setStudentId(5);
    List<Student> actual = sut.searchStudentList(condition);
    assertThat(actual).hasSize(1);
  }

  @Test
  void 名前に含まれる文字で受講生を検索できること() {
    condition.setName("高");
    List<Student> actual = sut.searchStudentList(condition);
    assertThat(actual).hasSize(1);
  }

  @Test
  void ふりがなに含まれる文字で受講生を検索できること() {
    condition.setFurigana("みさき");
    List<Student> actual = sut.searchStudentList(condition);
    assertThat(actual).hasSize(1);
  }

  @Test
  void ニックネームに含まれる文字で受講生を検索できること() {
    condition.setNickname("ハナ");
    List<Student> actual = sut.searchStudentList(condition);
    assertThat(actual).hasSize(1);
  }

  @Test
  void 住んでいる地域に含まれる文字で受講生を検索できること() {
    condition.setLiveCity("東京");
    List<Student> actual = sut.searchStudentList(condition);
    assertThat(actual).hasSize(1);
  }

  @Test
  void 年齢範囲で受講生を検索できること() {
    condition.setMinAge(30);
    condition.setMaxAge(39);
    List<Student> actual = sut.searchStudentList(condition);
    assertThat(actual).hasSize(2);
  }

  @Test
  void 性別で受講生を検索できること() {
    condition.setGender("女性");
    List<Student> actual = sut.searchStudentList(condition);
    assertThat(actual).hasSize(2);
  }

  @Test
  void 論理削除フラグで受講生を検索できること() {
    condition.setIsDeleted(false);
    List<Student> actual = sut.searchStudentList(condition);
    assertThat(actual).hasSize(5);
  }

  @Test
  void コース情報の全件検索ができること() {
    List<StudentCourse> actual = sut.searchCourseList(condition);
    assertThat(actual).hasSize(5);
  }

  @Test
  void コースIDでコース情報を検索できること() {
    condition.setCourseId(2);
    List<StudentCourse> actual = sut.searchCourseList(condition);
    assertThat(actual).hasSize(1);
  }

  @Test
  void コース名に含まれる文字でコース情報を検索できること() {
    condition.setCourseName("デザイン");
    List<StudentCourse> actual = sut.searchCourseList(condition);
    assertThat(actual).hasSize(1);
  }

  @Test
  void 申込状況の全件検索ができること() {
    List<CourseStatus> actual = sut.searchStatusList(condition);
    assertThat(actual).hasSize(5);
  }

  @Test
  void 申込状況IDで申込状況を検索できること() {
    condition.setStatusId(3);
    List<CourseStatus> actual = sut.searchStatusList(condition);
    assertThat(actual).hasSize(1);
  }

  @Test
  void 申込状況でコースの状態を検索できること() {
    condition.setStatus("本申込");
    List<CourseStatus> actual = sut.searchStatusList(condition);
    assertThat(actual).hasSize(1);
  }

  @Test
  void 受講生の登録ができること() {
    Student student = new Student();
    student.setFullName("小林 結衣");
    student.setFurigana("こばやし ゆい");
    student.setNickname("ゆい");
    student.setEmailAddress("yui@example.com");
    student.setLiveCity("京都府京都市");
    student.setAge(30);
    student.setGender("女性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);

    List<Student> actual = sut.searchStudentList(condition);

    assertThat(actual).hasSize(6);
  }

  @Test
  void コース情報の登録ができること() {
    StudentCourse course = new StudentCourse();
    course.setStudentId(999);
    course.setCourseName("Javaコース");
    course.setStartDate(LocalDate.of(2019, 8, 15));
    course.setScheduledEndDate(LocalDate.of(2019, 2, 15));

    sut.registerCourse(course);

    List<StudentCourse> actual = sut.searchCourseList(condition);

    assertThat(actual).hasSize(6);
  }

  @Test
  void 申込状況が登録できること() {
    String applicationStatus = "本申込";
    CourseStatus status = new CourseStatus();
    status.setCourseId(999);
    status.setApplicationStatus(applicationStatus);

    sut.registerStatus(status);

    List<CourseStatus> actual = sut.searchStatusList(condition);

    assertThat(actual).hasSize(6);
  }

  @Test
  void 受講生の更新ができること() {
    int studentId = 4;
    Student student = new Student(studentId, "佐藤 美咲", "さとう みさき", "Misaki",
        "misaki@example.com",
        "愛知県名古屋市", 22, "女性", "", false);

    List<Student> expected = List.of(student);

    sut.updateStudent(student);

    condition.setStudentId(studentId);
    List<Student> actual = sut.searchStudentList(condition);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void コース情報の更新ができること() {
    int courseId = 4;
    StudentCourse course = new StudentCourse(courseId, 4, "Javaコース",
        LocalDate.of(2025, 1, 15), LocalDate.of(2025, 7, 15));

    List<StudentCourse> expected = List.of(course);

    sut.updateCourse(course);

    condition.setCourseId(courseId);
    List<StudentCourse> actual = sut.searchCourseList(condition);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 申込状況の更新ができること() {
    int statusId = 3;
    CourseStatus status = new CourseStatus(statusId, 3, "本申込");

    List<CourseStatus> expected = List.of(status);

    sut.updateStatus(status);

    condition.setStatusId(statusId);
    List<CourseStatus> actual = sut.searchStatusList(condition);

    assertThat(actual).isEqualTo(expected);
  }

}