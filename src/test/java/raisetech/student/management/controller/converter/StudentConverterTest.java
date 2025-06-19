package raisetech.student.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.CourseDetail;
import raisetech.student.management.domain.StudentDetail;

@ExtendWith(MockitoExtension.class)
class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生に紐づくコース情報_正常系_受講生詳細に変換できていること() {
    Student studentA = new Student(1, "山田 花子", "やまだ はなこ", "はなちゃん",
        "hanako@example.com", "北海道札幌市", 18, "女性", "", false);
    Student studentB = new Student(500, "佐藤 健", "さとう けん", "けん",
        "sato.ken@example.com", "愛知県名古屋市", 35, "男性", "転職希望", false);
    List<Student> studentList = List.of(studentA, studentB);

    StudentCourse courseA = new StudentCourse(456, 1, "Javaコース", LocalDate.of(2024, 5, 11),
        LocalDate.of(2024, 11, 1));
    StudentCourse courseB = new StudentCourse(555, 1, "フロントエンドコース",
        LocalDate.of(2024, 12, 1), LocalDate.of(2025, 5, 1));
    StudentCourse courseC = new StudentCourse(600, 500, "AWSコース", LocalDate.of(2025, 6, 20),
        LocalDate.of(2025, 12, 20));

    CourseStatus statusA = new CourseStatus(500, 101, "受講終了");
    CourseStatus statusB = new CourseStatus(786, 555, "受講中");
    CourseStatus statusC = new CourseStatus(888, 600, "本申込");

    List<CourseDetail> courseDetailListA = List.of(new CourseDetail(courseA, statusA),
        new CourseDetail(courseB, statusB));
    List<CourseDetail> courseDetailListB = List.of(new CourseDetail(courseC, statusC));

    List<CourseDetail> courseDetailList = new ArrayList<>();
    courseDetailList.addAll(courseDetailListA);
    courseDetailList.addAll(courseDetailListB);

    StudentDetail studentDetailA = new StudentDetail(studentA, courseDetailListA);
    StudentDetail studentDetailB = new StudentDetail(studentB, courseDetailListB);

    List<StudentDetail> expected = List.of(studentDetailA, studentDetailB);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, courseDetailList);

    assertThat(actual).isEqualTo(expected);
    assertThat(actual).containsExactly(studentDetailA, studentDetailB);
  }

  @Test
  void 存在しない受講生IDを持つコース情報_異常系_変換結果に含まれないこと() {
    int studentId = 123;
    Student student = new Student(studentId, "鈴木 太郎", "スズキ タロウ", "すずたろう",
        "taro.suzuki@example.com", "東京都", 30, "男性", "", false);
    StudentCourse course = new StudentCourse(500, 999, "デザインコース",
        LocalDate.of(2025, 1, 10), LocalDate.of(2025, 7, 10));
    CourseStatus status = new CourseStatus(987, 999, "仮申込");

    List<StudentDetail> expected = List.of(new StudentDetail(student, List.of()));

    List<StudentDetail> actual = sut.convertStudentDetails
        (List.of(student), List.of(new CourseDetail(course, status)));

    assertThat(actual).isEqualTo(expected);
    assertThat(actual).hasSize(1);
  }

}