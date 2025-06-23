package raisetech.student.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.CourseDetail;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.dto.SearchCondition;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生に紐づくコース詳細_正常系_受講生詳細に変換できること() {
    Student studentA = new Student(1, "Raise Tech", "レイズ テック", "テックん",
        "RaiseTech@example.com", "日本", 30, "その他", "", false);
    Student studentB = new Student(500, "佐藤 健", "さとう けん", "けん",
        "sato.ken@example.com", "愛知県名古屋市", 35, "男性", "転職希望", false);
    List<Student> studentList = List.of(studentA, studentB);

    StudentCourse courseA = new StudentCourse(456, 1, "Javaコース", LocalDate.of(2019, 8, 15),
        LocalDate.of(2020, 2, 15));
    StudentCourse courseB = new StudentCourse(555, 1, "フロントエンドコース",
        LocalDate.of(2024, 12, 1), LocalDate.of(2025, 5, 1));
    StudentCourse courseC = new StudentCourse(600, 500, "AWSコース", LocalDate.of(2025, 6, 20),
        LocalDate.of(2025, 12, 20));

    CourseStatus statusA = new CourseStatus(500, 101, "受講終了");
    CourseStatus statusB = new CourseStatus(786, 555, "受講中");
    CourseStatus statusC = new CourseStatus(888, 600, "本申込");

    SearchCondition condition = new SearchCondition();

    List<CourseDetail> courseDetailListA = List.of(new CourseDetail(courseA, statusA),
        new CourseDetail(courseB, statusB));
    List<CourseDetail> courseDetailListB = List.of(new CourseDetail(courseC, statusC));

    List<CourseDetail> courseDetailList = new ArrayList<>();
    courseDetailList.addAll(courseDetailListA);
    courseDetailList.addAll(courseDetailListB);

    List<StudentDetail> expected = List.of(new StudentDetail(studentA, courseDetailListA),
        new StudentDetail(studentB, courseDetailListB));

    List<StudentDetail> actual = sut.mapToStudentDetailList(studentList, courseDetailList,
        condition);

    assertThat(actual).isEqualTo(expected);
    assertThat(actual).containsExactly(new StudentDetail(studentA, courseDetailListA),
        new StudentDetail(studentB, courseDetailListB));
  }

  @Test
  void 存在しない受講生IDを持つコース詳細_異常系_該当受講生のみが変換されること() {
    Student studentA = new Student(999, "Raise Tech", "レイズ テック", "テックん",
        "RaiseTech@example.com", "日本", 30, "その他", "", false);
    Student studentB = new Student(123, "鈴木 太郎", "スズキ タロウ", "すずたろう",
        "taro.suzuki@example.com", "東京都", 30, "男性", "", false);

    StudentCourse courseA = new StudentCourse(999, 999, "Javaコース", LocalDate.of(2019, 8, 15),
        LocalDate.of(2020, 2, 15));
    StudentCourse courseB = new StudentCourse(500, 500, "デザインコース",
        LocalDate.of(2025, 1, 10), LocalDate.of(2025, 7, 10));

    CourseStatus statusA = new CourseStatus(999, 999, "受講終了");
    CourseStatus statusB = new CourseStatus(500, 500, "仮申込");

    SearchCondition condition = new SearchCondition();

    List<StudentDetail> expected = List.of(
        new StudentDetail(studentA, List.of(new CourseDetail(courseA, statusA))),
        new StudentDetail(studentB, null));

    List<StudentDetail> actual = sut.mapToStudentDetailList
        (List.of(studentA, studentB),
            List.of(new CourseDetail(courseA, statusA), new CourseDetail(courseB, statusB)),
            condition);

    assertThat(actual).isEqualTo(expected);
    assertThat(actual).hasSize(2);
  }

  @Test
  void 検索条件による受講生詳細_正常系_検索条件に一致する受講生詳細のみ返ること() {
    Student student1 = new Student(1, "鈴木 一郎", "すずき いちろう", "いちろう",
        "ichiro@example.com", "東京都", 33, "男性", "", false);  // 29歳（20代）
    Student student2 = new Student(2, "高橋 美咲", "たかはし みさき", "みさき",
        "misaki@example.com", "大阪府", 35, "女性", "", false);  // 35歳（30代）
    Student student3 = new Student(3, "佐藤 健", "さとう けん", "けん",
        "ken@example.com", "福岡県", 42, "男性", "転職希望", false);  // 42歳（40代）

    StudentCourse course1 = new StudentCourse(100, 1, "Javaコース",
        LocalDate.of(2023, 4, 1), LocalDate.of(2023, 10, 1));
    StudentCourse course2 = new StudentCourse(101, 2, "フロントエンドコース",
        LocalDate.of(2024, 1, 10), LocalDate.of(2024, 7, 10));
    StudentCourse course3 = new StudentCourse(102, 3, "AWSコース",
        LocalDate.of(2025, 2, 15), LocalDate.of(2025, 8, 15));

    CourseStatus status1 = new CourseStatus(500, 100, "受講終了");  // 終了
    CourseStatus status2 = new CourseStatus(501, 101, "受講中");  // 受講中
    CourseStatus status3 = new CourseStatus(502, 102, "受講中");

    CourseDetail courseDetail1 = new CourseDetail(course1, status1);
    CourseDetail courseDetail2 = new CourseDetail(course2, status2);
    CourseDetail courseDetail3 = new CourseDetail(course3, status3);

    SearchCondition condition = new SearchCondition();
    condition.setMinAge(30);
    condition.setMaxAge(39);
    condition.setStatus("受講中");

    List<StudentDetail> expected = List.of(new StudentDetail(student2, List.of(courseDetail2)));

    List<StudentDetail> actual = sut.mapToStudentDetailList(List.of(student1, student2, student3),
        List.of(courseDetail1, courseDetail2, courseDetail3), condition);

    assertThat(actual).isEqualTo(expected);
    assertThat(actual).hasSize(1);
  }

  @Test
  void 受講生_正常系_性別が一致した場合trueが返ってくること() {
    Student student = new Student(999, "Raise Tech", "レイズ テック", "テックん",
        "RaiseTech@example.com", "日本", 30, "その他", "", false);

    SearchCondition condition = new SearchCondition();
    condition.setGender("その他");

    boolean actual = sut.isMatchedStudent(condition, student);

    assertThat(actual).isEqualTo(true);
  }

  @Test
  void 受講生_異常系_地域が一致しない場合falseが返ってくること() {
    Student student = new Student(999, "Raise Tech", "レイズ テック", "テックん",
        "RaiseTech@example.com", "日本", 30, "その他", "", false);

    SearchCondition condition = new SearchCondition();
    condition.setLiveCity("大阪");

    boolean actual = sut.isMatchedStudent(condition, student);

    assertThat(actual).isEqualTo(false);
  }

  @Test
  void コース詳細_正常系_コース名を含む場合trueを返すこと() {
    CourseDetail courseDetail = new CourseDetail(
        new StudentCourse(999, 123, "Javaコース", LocalDate.of(2019, 8, 15),
            LocalDate.of(2020, 2, 15)), new CourseStatus(999, 999, "受講終了"));

    SearchCondition condition = new SearchCondition();
    condition.setCourseName("Java");

    boolean actual = sut.isMatchedCourseDetail(condition, courseDetail);

    assertThat(actual).isEqualTo(true);
  }

  @Test
  void コース詳細_異常系_申込状況が一致しない場合falseを返すこと() {
    CourseDetail courseDetail = new CourseDetail(
        new StudentCourse(999, 123, "Javaコース", LocalDate.of(2019, 8, 15),
            LocalDate.of(2020, 2, 15)), new CourseStatus(999, 999, "受講終了"));

    SearchCondition condition = new SearchCondition();
    condition.setStatus("仮申込");

    boolean actual = sut.isMatchedCourseDetail(condition, courseDetail);

    assertThat(actual).isEqualTo(false);
  }

}