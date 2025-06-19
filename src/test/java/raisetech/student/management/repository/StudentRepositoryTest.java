package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索ができること() {
    List<Student> actual = sut.searchStudent();
    assertThat(actual).hasSize(5);
  }

  @Test
  void コース情報の全件検索ができること() {
    List<StudentCourse> actual = sut.searchCourseList();
    assertThat(actual).hasSize(5);
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
    List<Student> actual = sut.searchStudent();

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
    List<StudentCourse> actual = sut.searchCourseList();

    assertThat(actual).hasSize(6);
  }

  @Test
  void 申込状況が登録できること() {
    int courseId = 999;
    CourseStatus expected = new CourseStatus();
    expected.setCourseId(courseId);
    expected.setApplicationStatus("本申込");

    sut.registerStatus(expected);
    CourseStatus actual = sut.getStatusInfo(courseId);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 受講生の検索_正常系_存在するIDで取得できること() {
    int id = 5;
    Student actual = sut.getStudentInfo(id);

    assertThat(actual.getId()).isEqualTo(id);
  }

  @Test
  void 受講生の検索_異常系_存在しないIDの場合はnullを返すこと() {
    int id = 500;
    Student actual = sut.getStudentInfo(id);

    assertThat(actual).isNull();
  }

  @Test
  void コース情報の検索_正常系_紐づく受講生IDで検索できること() {
    int studentId = 1;
    List<StudentCourse> actual = sut.getCourseInfo(studentId);

    assertThat(actual).hasSize(1);
    assertThat(actual.get(0).getStudentId()).isEqualTo(studentId);
  }

  @Test
  void コース情報の検索_異常系_存在しない受講生IDの場合は空の結果を返すこと() {
    int studentId = 987;
    List<StudentCourse> actual = sut.getCourseInfo(studentId);

    assertThat(actual).hasSize(0);
  }

  @Test
  void 申込状況の検索_正常系_紐づくコースIDで検索できること() {
    int courseId = 1;
    CourseStatus actual = sut.getStatusInfo(courseId);

    assertThat(actual.getCourseId()).isEqualTo(courseId);
  }

  @Test
  void 申込状況の検索_異常系_存在しないIDの場合はnullを返すこと() {
    int courseId = 999;
    CourseStatus actual = sut.getStatusInfo(courseId);

    assertThat(actual).isNull();
  }

  @Test
  void 受講生の更新ができること() {
    int id = 4;
    Student expected = new Student(id, "佐藤 美咲", "さとう みさき", "Misaki", "misaki@example.com",
        "愛知県名古屋市", 22, "女性", "", false);

    sut.updateStudent(expected);
    Student actual = sut.getStudentInfo(id);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void コース情報の更新ができること() {
    int studentId = 4;
    StudentCourse expected = new StudentCourse(4, studentId, "Javaコース",
        LocalDate.of(2025, 1, 15), LocalDate.of(2025, 7, 15));

    sut.updateCourse(expected);
    StudentCourse actual = sut.getCourseInfo(studentId).get(0);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void 申込状況の更新ができること() {
    int courseId = 3;
    CourseStatus expected = new CourseStatus(3, courseId, "本申込");

    sut.updateStatus(expected);
    CourseStatus actual = sut.getStatusInfo(courseId);

    assertThat(actual).isEqualTo(expected);
  }

}