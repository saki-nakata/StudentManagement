package raisetech.student.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.CourseDetail;
import raisetech.student.management.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生詳細_正常系_受講生にコース情報を紐づけられること() {
    Student studentA = new Student(123, "高橋 美咲", "たかはし みさき", "misaki",
        "misaki@example.com", "京都府京都市", 35, "女性", "", false);
    Student studentB = new Student(500, "佐藤 健", "さとう けん", "ケン",
        "sato.ken@example.com", "愛知県名古屋市", 25, "男性", "転職希望", false);
    List<Student> studentList = List.of(studentA, studentB);

    StudentCourse courseA = new StudentCourse(333, 123, "Javaコース",
        LocalDate.of(2023, 4, 1), LocalDate.of(2023, 10, 1));
    StudentCourse courseB = new StudentCourse(555, 500, "AWSコース",
        LocalDate.of(2025, 3, 12), LocalDate.of(2025, 9, 12));
    StudentCourse courseC = new StudentCourse(600, 123, "フロントエンドコース",
        LocalDate.of(2025, 6, 20),
        LocalDate.of(2025, 12, 20));
    List<StudentCourse> courseList = List.of(courseA, courseB, courseC);

    CourseStatus statusA = new CourseStatus(500, 333, "受講終了");
    CourseStatus statusB = new CourseStatus(786, 555, "受講中");
    CourseStatus statusC = new CourseStatus(888, 600, "本申込");
    List<CourseStatus> statusList = List.of(statusA, statusB, statusC);

    boolean isCourseDetailConditionEmpty = true;

    StudentDetail studentDetailA = new StudentDetail(studentA,
        List.of(new CourseDetail(courseA, statusA), new CourseDetail(courseC, statusC)));
    StudentDetail studentDetailB = new StudentDetail(studentB,
        List.of(new CourseDetail(courseB, statusB)));

    List<StudentDetail> expected = List.of(studentDetailA, studentDetailB);

    List<StudentDetail> actual = sut.mapToStudentDetailList(studentList, courseList, statusList,
        isCourseDetailConditionEmpty);

    assertThat(actual).isEqualTo(expected);
    assertThat(actual).containsExactly(studentDetailA, studentDetailB);
  }

  @Test
  void 受講生詳細_異常系_存在しない受講生IDは紐づかないこと() {
    Student studentA = new Student(999, "Raise Tech", "レイズ テック", "テッくん",
        "RaiseTech@example.com", "日本", 30, "その他", "", false);
    Student studentB = new Student(123, "鈴木 太郎", "スズキ タロウ", "すずたろう",
        "taro.suzuki@example.com", "東京都", 30, "男性", "", false);
    List<Student> studentList = List.of(studentA, studentB);

    StudentCourse courseA = new StudentCourse(999, 999, "Javaコース", LocalDate.of(2019, 8, 15),
        LocalDate.of(2020, 2, 15));
    StudentCourse courseB = new StudentCourse(500, 500, "デザインコース",
        LocalDate.of(2025, 7, 10), LocalDate.of(2026, 1, 10));
    List<StudentCourse> courseList = List.of(courseA, courseB);

    CourseStatus statusA = new CourseStatus(999, 999, "受講終了");
    CourseStatus statusB = new CourseStatus(500, 500, "仮申込");
    List<CourseStatus> statusList = List.of(statusA, statusB);

    boolean isCourseDetailConditionEmpty = true;

    List<StudentDetail> expected = List.of(
        new StudentDetail(studentA, List.of(new CourseDetail(courseA, statusA))),
        new StudentDetail(studentB, null));

    List<StudentDetail> actual = sut.mapToStudentDetailList(studentList, courseList, statusList,
        isCourseDetailConditionEmpty);

    assertThat(actual).isEqualTo(expected);
    assertThat(actual).hasSize(2);
  }

  @Test
  void 受講生詳細_正常系_コース詳細の検索条件ありで紐づかない受講生は除外されること() {
    Student studentA = new Student(999, "Raise Tech", "レイズ テック", "テッくん",
        "RaiseTech@example.com", "日本", 30, "その他", "", false);
    Student studentB = new Student(123, "高橋 美咲", "たかはし みさき", "misaki",
        "misaki@example.com", "京都府京都市", 35, "女性", "", false);

    StudentCourse course = new StudentCourse(555, 123, "AWSコース",
        LocalDate.of(2025, 3, 12), LocalDate.of(2025, 9, 12));
    CourseStatus status = new CourseStatus(786, 555, "受講中");

    boolean isCourseDetailConditionEmpty = false;

    List<StudentDetail> expected = List.of(
        new StudentDetail(studentB, List.of(new CourseDetail(course, status))));

    List<StudentDetail> actual = sut.mapToStudentDetailList(List.of(studentA, studentB),
        List.of(course), List.of(status),
        isCourseDetailConditionEmpty);

    assertThat(actual).isEqualTo(expected);
    assertThat(actual).hasSize(1);
  }

  @Test
  void コース詳細_正常系_コース情報に申込状況を紐づけられること() {
    StudentCourse courseA = new StudentCourse(333, 123, "Javaコース",
        LocalDate.of(2023, 4, 1), LocalDate.of(2023, 10, 1));
    StudentCourse courseB = new StudentCourse(500, 500, "デザインコース",
        LocalDate.of(2025, 7, 10), LocalDate.of(2026, 1, 10));
    List<StudentCourse> courseList = List.of(courseA, courseB);

    CourseStatus statusA = new CourseStatus(222, 333, "受講終了");
    CourseStatus statusB = new CourseStatus(500, 500, "仮申込");
    List<CourseStatus> statusList = List.of(statusA, statusB);

    CourseDetail courseDetailA = new CourseDetail(courseA, statusA);
    CourseDetail courseDetailB = new CourseDetail(courseB, statusB);
    List<CourseDetail> expected = List.of(courseDetailA, courseDetailB);

    List<CourseDetail> actual = sut.mapToCourseDetailList(courseList, statusList);

    assertThat(actual).isEqualTo(expected);
    assertThat(actual).containsExactly(courseDetailA, courseDetailB);
  }

  @Test
  void コース詳細_異常系_存在しない申込状況IDは紐づかないこと() {
    StudentCourse courseA = new StudentCourse(555, 500, "AWSコース",
        LocalDate.of(2025, 3, 12), LocalDate.of(2025, 9, 12));
    StudentCourse courseB = new StudentCourse(600, 123, "フロントエンドコース",
        LocalDate.of(2025, 6, 20),
        LocalDate.of(2025, 12, 20));
    List<StudentCourse> courseList = List.of(courseA, courseB);

    CourseStatus statusA = new CourseStatus(786, 555, "受講中");
    CourseStatus statusB = new CourseStatus(888, 900, "本申込");
    List<CourseStatus> statusList = List.of(statusA, statusB);

    List<CourseDetail> expected = List.of(new CourseDetail(courseA, statusA));

    List<CourseDetail> actual = sut.mapToCourseDetailList(courseList, statusList);

    assertThat(actual).isEqualTo(expected);
    assertThat(actual).hasSize(1);
  }

}