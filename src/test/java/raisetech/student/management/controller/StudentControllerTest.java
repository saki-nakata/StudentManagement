package raisetech.student.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
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

  @BeforeEach
  void before() {
    student = new Student(999, "RaiseTech", "レイズテック", "テッくん",
        "raisetech@example.com", "日本", 25, "その他", "test", false);
    course = new StudentCourse(999, 999, "Javaコース", LocalDate.parse("2019-08-15"),
        LocalDate.parse("2020-02-15"));
  }

  @Test
  void 受講生詳細の一覧検索_正常系_空の受講生リストが返されること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生情報の単一検索_正常系_有効なID指定でOKが返ること() throws Exception {
    int id = 999;
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).getStudentInfo(id);
  }

  @Test
  void 受講生情報の単一検索_異常系_無効なID指定でBadRequestが返ること()
      throws Exception {
    int id = 10000;
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.statusValue").value(400))
        .andExpect(jsonPath("$.statusName").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("getStudentInfo.id: 999以下の数値にしてください。"));
  }

  @Test
  void 受講生詳細の更新_正常系_正常なリクエストで受講生詳細を更新できること() throws Exception {
    List<StudentCourse> courseList = List.of(course);
    StudentDetail studentDetail = new StudentDetail(student, courseList);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());

    mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(studentDetail)))
        .andExpect(status().isOk());
    verify(service, times(1)).updateStudent(any(StudentDetail.class));
  }

  @Test
  void 受講生詳細の登録_正常系_正常なリクエストで受講生を登録できること() throws Exception {
    List<StudentCourse> courseList = List.of(course);
    StudentDetail studentDetail = new StudentDetail(student, courseList);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());

    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(studentDetail)))
        .andExpect(status().isOk());
    verify(service, times(1)).registerStudent(any(StudentDetail.class));
  }

  /**
   * 受講生詳細のバリデーションチェックです。
   */
  private Set<ConstraintViolation<Student>> studentViolations(Student student) {
    return validator.validate(student);
  }

  @Test
  void 受講生詳細_正常系_入力チェックで異常が発生しないこと() {
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生ID_異常系_入力チェックで1000以上のときにエラーになること() {
    student.setId(10000);
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("id"));
    assertThat(violations).extracting("message")
        .containsOnly("999以下の数値にしてください。");
  }

  @Test
  void 受講生詳細の名前_異常系_入力チェックで空欄のときにエラーになること() {
    student.setFullName("");
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("fullName"));
    assertThat(violations).extracting("message").containsOnly("名前を入力してください。");
  }

  @Test
  void 受講生詳細のふりがな_異常系_入力チェックで空欄のときにエラーになること() {
    student.setFurigana("");
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("furigana"));
    assertThat(violations).extracting("message").containsOnly("ふりがなを入力してください。");
  }

  @Test
  void 受講生詳細のメールアドレス_異常系_入力チェックで無効な形式のときにエラーになること() {
    student.setEmailAddress("raise-tech.com");
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("emailAddress"));
    assertThat(violations).extracting("message")
        .containsOnly("有効なメールアドレスの形式ではありません。");
  }

  @Test
  void 受講生詳細の年齢_異常系_入力チェックで18歳未満のときにエラーになること() {
    student.setAge(5);
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("age"));
    assertThat(violations).extracting("message").containsOnly("18以上の数値にしてください。");
  }

  @Test
  void 受講生詳細の性別_異常系_入力チェックで空欄のときにエラーになること() {
    student.setGender("");
    Set<ConstraintViolation<Student>> violations = studentViolations(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("gender"));
    assertThat(violations).extracting("message").containsOnly("性別を入力してください。");
  }

  /**
   * 受講生コース情報のバリデーションチェックです。
   */
  private Set<ConstraintViolation<StudentCourse>> courseViolations(StudentCourse course) {
    return validator.validate(course);
  }

  @Test
  void 受講生コース情報_正常系_入力チェックで異常が発生しないこと() {
    Set<ConstraintViolation<StudentCourse>> violations = courseViolations(course);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生コース情報のID_異常系_入力チェックで1000以上のときにエラーになること() {
    course.setId(10000);
    Set<ConstraintViolation<StudentCourse>> violations = courseViolations(course);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("id"));
    assertThat(violations).extracting("message").containsOnly("999以下の数値にしてください。");
  }

  @Test
  void 受講生コース情報のコース名_異常系_入力チェックで空欄のときにエラーになること() {
    course.setCourseName(null);
    Set<ConstraintViolation<StudentCourse>> violations = courseViolations(course);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("courseName"));
    assertThat(violations).extracting("message").containsOnly("コース名を入力してください。");
  }

  @Test
  void 受講生コース情報の開始日_異常系_入力チェックで空欄のときにエラーになること() {
    course.setStartDate(null);
    Set<ConstraintViolation<StudentCourse>> violations = courseViolations(course);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("propertyPath")
        .containsExactlyInAnyOrder(PathImpl.createPathFromString("startDate"));
    assertThat(violations).extracting("message").containsOnly("開始日を入力してください。");
  }

}