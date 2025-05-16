package raisetech.student.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * アプリケーション内で発生した例外を処理する例外ハンドラ。
   *
   * @param ex 発生した例外
   * @return HTTP 400 と例外メッセージ
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("例外発生:" + ex.getMessage());
  }

  /**
   * TestExceptionがスローされたときに処理する例外ハンドラ。
   *
   * @param ex 発生した例外
   * @return HTTP 400 と例外メッセージ
   */
  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

}