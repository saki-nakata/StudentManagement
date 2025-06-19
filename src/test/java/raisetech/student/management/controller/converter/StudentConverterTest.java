package raisetech.student.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

@ExtendWith(MockitoExtension.class)
class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生に紐づく受講生コース情報_正常系_受講生詳細に変換できていること() {
    Student studentA = new Student(1, "山田 花子", "やまだ はなこ", "はなちゃん",
        "hanako@example.com", "北海道札幌市", 18, "女性", "", false);
    Student studentB = new Student(500, "佐藤 健", "さとう けん", "けん",
        "sato.ken@example.com", "愛知県名古屋市", 35, "男性", "転職希望", false);
    Student studentC = new Student(999, "高橋 太一郎", "たかはし たいちろう", "たいちゃん",
        "verylong.email.address@example.com", "鹿児島県奄美市", 50, "その他", "副業希望", false);
    List<Student> studentList = List.of(studentA, studentB, studentC);

    List<StudentCourse> courseListA = List.of(
        new StudentCourse(101, 1, "Javaコース", LocalDate.parse("2024-05-01"),
            LocalDate.parse("2024-11-01")),
        new StudentCourse(102, 1, "フロントエンドコース", LocalDate.parse("2024-12-01"),
            LocalDate.parse("2025-05-01"))
    );
    List<StudentCourse> courseListB = List.of(
        new StudentCourse(201, 500, "AWSコース", LocalDate.parse("2024-04-10"),
            LocalDate.parse("2024-10-10")),
        new StudentCourse(301, 500, "Javaコース", LocalDate.parse("2025-03-15"),
            LocalDate.parse("2025-09-15"))
    );
    List<StudentCourse> courseListC = List.of(
        new StudentCourse(302, 999, "デザインコース", LocalDate.parse("2025-05-20"),
            LocalDate.parse("2025-11-20"))
    );
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.addAll(courseListA);
    studentCourseList.addAll(courseListB);
    studentCourseList.addAll(courseListC);

    StudentDetail studentDetailA = new StudentDetail(studentA, courseListA);
    StudentDetail studentDetailB = new StudentDetail(studentB, courseListB);
    StudentDetail studentDetailC = new StudentDetail(studentC, courseListC);

    List<StudentDetail> expected = List.of(studentDetailA, studentDetailB, studentDetailC);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual).isEqualTo(expected);
    assertThat(actual).containsExactly(studentDetailA, studentDetailB, studentDetailC);
  }

  @Test
  void 存在しない受講生IDを持つ受講生コース情報_異常系_変換結果に含まれないこと() {
    int studentId = 123;
    Student student = new Student(studentId, "鈴木 太郎", "スズキ タロウ", "すずたろう",
        "taro.suzuki@example.com", "東京都", 30, "男性", "", false);

    StudentCourse courseA = new StudentCourse(100, studentId, "Webマーケティングコース",
        LocalDate.parse("2024-09-01"), LocalDate.parse("2025-03-01"));
    StudentCourse courseB = new StudentCourse(999, 999, "デザインコース",
        LocalDate.parse("2025-01-10"), LocalDate.parse("2025-07-10"));

    List<StudentDetail> expected = List.of(new StudentDetail(student, List.of(courseA)));

    List<StudentDetail> actual = sut.convertStudentDetails
        (List.of(student), List.of(courseA, courseB));

    assertThat(actual).isEqualTo(expected);
    assertThat(actual).hasSize(1);
  }

}