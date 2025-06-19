package raisetech.student.management.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.exception.ErrorMessage;
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
   * 受講生詳細一覧検索です。 全件検索です。
   *
   * @return 受講生詳細一覧(全件)
   */
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。",
      responses = {@ApiResponse(responseCode = "200", description = "成功",
          content = @Content(schema = @Schema(implementation = StudentDetail.class)))
      })
  @GetMapping("/studentList")
  public List<StudentDetail> getStudent() {
    return service.searchStudentList();
  }

  /**
   * 受講生検索です。 IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 　受講生ID
   * @return 単一の受講生情報
   */
  @Operation(
      summary = "受講生情報の取得",
      description = "IDを指定して1人の受講生情報を取得します。",
      parameters = {
          @Parameter(name = "id", description = "受講生ID", required = true, example = "1")
      },
      responses = {
          @ApiResponse(responseCode = "200", description = "成功"),
          @ApiResponse(responseCode = "400", description = "例外発生",
              content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
      }
  )
  @GetMapping("/student/{id}")
  public StudentDetail getStudentInfo(
      @PathVariable @Min(value = 1, message = "1以上の数値にしてください。") @Max(value = 999, message = "999以下の数値にしてください。") int id) {
    return service.getStudentInfo(id);
  }

  /**
   * 受講生詳細の更新を行います。キャンセルフラグの更新も行います(論理削除)。
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
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します。",
      responses = {@ApiResponse(responseCode = "200", description = "成功")})
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) throws TestException {
    service.registerStudent(studentDetail);
    return ResponseEntity.ok(studentDetail);
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