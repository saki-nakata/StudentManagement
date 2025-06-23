package raisetech.student.management.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.domain.CourseDetail;
import raisetech.student.management.dto.SearchCondition;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

/**
 * 受講生の検索や登録、更新をおこなうREST APIとして受け付けるControllerです。
 */
@Tag(name = "Controller", description = "受講生の検索や登録、更新をおこなうREST API")
@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の条件検索です。条件を指定しない場合は全件検索を行います。
   *
   * @return 受講生詳細の一覧
   */
  @Operation(summary = "受講生詳細の条件検索", description = "条件に一致する受講生詳細の一覧を検索します。",
      responses = {@ApiResponse(responseCode = "200", description = "成功",
          content = @Content(schema = @Schema(implementation = StudentDetail.class)))
      })
  @GetMapping("/search")
  public List<StudentDetail> search(@ModelAttribute SearchCondition condition) {
    return service.search(condition);
  }

   /**
   * 受講生詳細(受講生、コース情報、申込状況)の更新を行います。キャンセルフラグの更新も行います(論理削除)。
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生詳細の更新", description = "受講生詳細の更新をします。",
      responses = {@ApiResponse(responseCode = "200", description = "成功",
          content = @Content(schema = @Schema(implementation = StudentDetail.class)))
      })
  @PutMapping("/updateStudent")
  public ResponseEntity<StudentDetail> updateStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok(studentDetail);
  }

  /**
   * コース詳細(コース情報、申込状況)の更新を行います。
   *
   * @param courseDetail コース詳細
   * @return 実行結果
   */
  @Operation(summary = "コース詳細の更新", description = "コース詳細の更新をします。",
      responses = {@ApiResponse(responseCode = "200", description = "成功",
          content = @Content(schema = @Schema(implementation = CourseDetail.class)))
      })
  @PutMapping("/updateCourse")
  public ResponseEntity<CourseDetail> updateCourse(
      @RequestBody @Valid CourseDetail courseDetail) {
    service.updateCourse(courseDetail);
    return ResponseEntity.ok(courseDetail);
  }

  /**
   * 受講生詳細(受講生、コース情報、申込状況)の登録を行います。
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生詳細の登録", description = "受講生詳細(受講生、コース情報、申込状況)を登録します。",
      responses = {@ApiResponse(responseCode = "200", description = "成功")})
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) throws TestException {
    service.registerStudent(studentDetail);
    return ResponseEntity.ok(studentDetail);
  }

  /**
   * コース詳細(コース情報、申込状況)の登録を行います。
   *
   * @param courseDetail コース詳細
   * @return 実行結果
   */
  @Operation(summary = "コース詳細の登録", description = "コース詳細(コース情報、申込状況)を登録します。",
      responses = {@ApiResponse(responseCode = "200", description = "成功")})
  @PostMapping("/registerCourse")
  public ResponseEntity<CourseDetail> registerCourse(
      @RequestBody @Valid CourseDetail courseDetail) throws TestException {
    service.registerCourse(courseDetail);
    return ResponseEntity.ok(courseDetail);
  }

  /**
   * 例外処理の動作確認をするためのテスト用メソッドです。
   *
   * @throws TestException テスト用の例外
   */
  @Hidden
  @Operation(summary = "例外をスロー", description = "例外処理の動作確認をします。")
  @GetMapping("/testException")
  public void testException() throws TestException {
    throw new TestException("意図的に例外をスロー");
  }

}