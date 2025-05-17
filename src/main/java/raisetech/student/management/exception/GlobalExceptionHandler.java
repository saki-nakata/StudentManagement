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
  public ResponseEntity<ErrorMessage> handleException(Exception ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorMessage error = new ErrorMessage(
        "例外発生:Exception",
        status.value(),
        status.name(),
        "例外発生\n" + ex.getMessage()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  /**
   * TestExceptionがスローされたときに処理する例外ハンドラ。
   *
   * @param ex 発生した例外
   * @return HTTP 400 と例外メッセージ
   */
  @ExceptionHandler(TestException.class)
  public ResponseEntity<ErrorMessage> handleTestException(TestException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorMessage error = new ErrorMessage(
        "例外発生:TestException",
        status.value(),
        status.name(),
        ex.getMessage()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

}