package raisetech.student.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.CourseDetail;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.dto.SearchCondition;
import raisetech.student.management.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  private Student student;
  private StudentCourse course;
  private CourseStatus status;
  private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

  @BeforeEach
  void before() {
    student = new Student(999, "RaiseTech", "レイズテック", "テッくん",
        "raisetech@example.com", "日本", 25, "その他", "test", false);
    course = new StudentCourse(999, 999, "Javaコース", LocalDate.of(2019, 8, 15),
        LocalDate.of(2020, 2, 15));
    status = new CourseStatus(999, 999, "仮申込");
  }

  @Test
  void 受講生詳細の全件検索_正常系_空の受講生リストが返されること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/search"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service, times(1)).search(any(SearchCondition.class));
  }

  @Test
  void 受講生詳細の条件検索_正常系_指定の受講生IDに一致した受講生詳細が返されること()
      throws Exception {
    int studentId = 999;
    Student student = new Student(studentId, "Raise Tech", "レイズ テック", "テッくん",
        "RaiseTech@example.com", "日本", 30, "その他", "", false);
    StudentCourse course = new StudentCourse(999, studentId, "Javaコース",
        LocalDate.of(2019, 8, 15), LocalDate.of(2020, 2, 15));
    CourseStatus status = new CourseStatus(999, 999, "受講終了");

    SearchCondition condition = new SearchCondition();
    condition.setStudentId(studentId);

    Mockito.when(service.search(condition))
        .thenReturn(List.of(new StudentDetail(student, List.of(new CourseDetail(course, status)))));

    mockMvc.perform(
            MockMvcRequestBuilders.get("/search").param("studentId", String.valueOf(studentId)))
        .andExpect(status().isOk())
        .andExpect(content().json("""
            [
               {
                 "student": {
                   "id": 999,
                   "fullName": "Raise Tech",
                   "furigana": "レイズ テック",
                   "nickname": "テッくん",
                   "emailAddress": "RaiseTech@example.com",
                   "liveCity": "日本",
                   "age": 30,
                   "gender": "その他",
                   "remark": "",
                   "deleted": false
                 },
                 "courseDetailList": [
                   {
                     "studentCourse": {
                       "id": 999,
                       "studentId": 999,
                       "courseName": "Javaコース",
                       "startDate": "2019-08-15",
                       "scheduledEndDate": "2020-02-15"
                     },
                     "courseStatus": {
                       "id": 999,
                       "courseId": 999,
                       "applicationStatus": "受講終了"
                     }
                   }
                 ]
               }
             ]
            """));

    verify(service, times(1)).search(any(SearchCondition.class));
  }

  @Test
  void 受講生詳細の更新_正常系_正常なリクエストで受講生詳細を更新できること() throws Exception {
    List<CourseDetail> courseDetailList = List.of(new CourseDetail(course, status));
    StudentDetail studentDetail = new StudentDetail(student, courseDetailList);

    mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(studentDetail)))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any(StudentDetail.class));
  }

  @Test
  void 受講生詳細の登録_正常系_正常なリクエストで受講生詳細を登録できること() throws Exception {
    List<CourseDetail> courseDetailList = List.of(new CourseDetail(course, status));
    StudentDetail studentDetail = new StudentDetail(student, courseDetailList);

    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(studentDetail)))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any(StudentDetail.class));
  }

  @Test
  void コース詳細の登録_正常系_正常なリクエストでコース詳細を登録できること() throws Exception {
    CourseDetail courseDetail = new CourseDetail(course, status);

    mockMvc.perform(MockMvcRequestBuilders.post("/registerCourse")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(courseDetail)))
        .andExpect(status().isOk());

    verify(service, times(1)).registerCourse(any(CourseDetail.class));
  }

  /**
   * 受講生詳細のバリデーションチェックです。
   */
  private Set<ConstraintViolation<Student>> studentViolations(Student student) {
    return validator.validate(student);
  }

  @Test
  @DisplayName("受講生情報のバリデーションチェック")
  void 受講生情報_正常系_入力チェックで異常が発生しないこと() {
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations).hasSize(0);
  }

  @Test
  void 受講生情報の受講生ID_異常系_入力チェックで1000以上のときにエラーになること() {
    student.setId(10000);
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations).hasSize(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("id"));
    assertThat(violations).extracting("message")
        .containsOnly("999以下の数値にしてください。");
  }

  @Test
  void 受講生情報の名前_異常系_入力チェックで空欄のときにエラーになること() {
    student.setFullName("");
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations).hasSize(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("fullName"));
    assertThat(violations).extracting("message").containsOnly("名前を入力してください。");
  }

  @Test
  void 受講生情報のふりがな_異常系_入力チェックで空欄のときにエラーになること() {
    student.setFurigana("");
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations).hasSize(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("furigana"));
    assertThat(violations).extracting("message").containsOnly("ふりがなを入力してください。");
  }

  @Test
  void 受講生情報のメールアドレス_異常系_入力チェックで無効な形式のときにエラーになること() {
    student.setEmailAddress("raise-tech.com");
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations).hasSize(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("emailAddress"));
    assertThat(violations).extracting("message")
        .containsOnly("有効なメールアドレスの形式ではありません。");
  }

  @Test
  void 受講生情報の年齢_異常系_入力チェックで18歳未満のときにエラーになること() {
    student.setAge(5);
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations).hasSize(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("age"));
    assertThat(violations).extracting("message").containsOnly("18以上の数値にしてください。");
  }

  @Test
  void 受講生情報の性別_異常系_入力チェックで不正な値のときにエラーになること() {
    student.setGender("不明");
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations).hasSize(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("gender"));
    assertThat(violations).extracting("message")
        .containsOnly("｢男性・女性・その他｣のどれかを入力してください。");
  }

  /**
   * 受講生コース情報のバリデーションチェックです。
   */
  private Set<ConstraintViolation<StudentCourse>> courseViolations(StudentCourse course) {
    return validator.validate(course);
  }

  @Test
  @DisplayName("受講生コース情報のバリデーションチェック")
  void 受講生コース情報_正常系_入力チェックで異常が発生しないこと() {
    Set<ConstraintViolation<StudentCourse>> violations = courseViolations(course);

    assertThat(violations).hasSize(0);
  }

  @Test
  void コース情報のID_異常系_入力チェックで1000以上のときにエラーになること() {
    course.setId(10000);
    Set<ConstraintViolation<StudentCourse>> violations = courseViolations(course);

    assertThat(violations).hasSize(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("id"));
    assertThat(violations).extracting("message").containsOnly("999以下の数値にしてください。");
  }

  @Test
  void コース情報のコース名_異常系_入力チェックで不正な値のときにエラーになること() {
    course.setCourseName("英会話コース");
    Set<ConstraintViolation<StudentCourse>> violations = courseViolations(course);

    assertThat(violations).hasSize(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("courseName"));
    assertThat(violations).extracting("message").containsOnly(
        "｢Javaコース・AWSコース・フロントエンドコース・Webマーケティングコース・デザインコース｣のどれかを入力してください。");
  }

  @Test
  void コース情報の開始日_異常系_入力チェックで空欄のときにエラーになること() {
    course.setStartDate(null);
    Set<ConstraintViolation<StudentCourse>> violations = courseViolations(course);

    assertThat(violations).hasSize(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("startDate"));
    assertThat(violations).extracting("message").containsOnly("開始日を入力してください。");
  }

  /**
   * コース申込状況のバリデーションチェックです。
   */
  private Set<ConstraintViolation<CourseStatus>> statusViolations(CourseStatus status) {
    return validator.validate(status);
  }

  @Test
  @DisplayName("申込状況のバリデーションチェック")
  void 申込状況_正常系_入力チェックで異常が発生しないこと() {
    Set<ConstraintViolation<CourseStatus>> violations = statusViolations(status);

    assertThat(violations).hasSize(0);
  }

  @Test
  void 申込状況のID_異常系_入力チェックで1000以上のときにエラーになること() {
    status.setId(10000);
    Set<ConstraintViolation<CourseStatus>> violations = statusViolations(status);

    assertThat(violations).hasSize(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("id"));
    assertThat(violations).extracting("message").containsOnly("999以下の数値にしてください。");
  }

  @Test
  void 申込状況の状態_異常系_入力チェックで不正な値のときにエラーになること() {
    status.setApplicationStatus("検討中");
    Set<ConstraintViolation<CourseStatus>> violations = statusViolations(status);

    assertThat(violations).hasSize(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("applicationStatus"));
    assertThat(violations).extracting("message").containsOnly(
        "｢仮申込・本申込・受講中・受講終了｣のどれかを入力してください。");
  }

}