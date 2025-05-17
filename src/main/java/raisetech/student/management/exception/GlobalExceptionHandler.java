package raisetech.student.management.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * JSONリクエストボディのバリデーション失敗時に発生する例外を処理する。
   *
   * @param ex 発生した例外
   * @return HTTP 400 と例外メッセージ
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessage> handleException(MethodArgumentNotValidException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorMessage error = new ErrorMessage(
        status.value(),
        status.name(),
        ex.getMessage()
    );
    return ResponseEntity.status(status).body(error);
  }

  /**
   * メソッド引数のバリデーション違反時に発生する例外を処理する。
   *
   * @param ex 発生した例外
   * @return HTTP 400 と例外メッセージ
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorMessage> handleException(ConstraintViolationException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorMessage error = new ErrorMessage(
        status.value(),
        status.name(),
        ex.getMessage()
    );
    return ResponseEntity.status(status).body(error);
  }

  /**
   * データベースに重複するキーのレコードを挿入しようとした際の例外を処理する。
   *
   * @param ex 発生した例外
   * @return HTTP 409 と例外メッセージ
   */
  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<ErrorMessage> handleException(DuplicateKeyException ex) {
    HttpStatus status = HttpStatus.CONFLICT;
    ErrorMessage error = new ErrorMessage(
        status.value(),
        status.name(),
        ex.getMessage()
    );
    return ResponseEntity.status(status).body(error);
  }

  /**
   * データベースに重複するキーのレコードを挿入しようとした際の例外を処理する。
   *
   * @param ex 発生した例外
   * @return HTTP 500 と例外メッセージ
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> handleException(Exception ex) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ErrorMessage error = new ErrorMessage(
        status.value(),
        status.name(),
        ex.getMessage()
    );
    return ResponseEntity.status(status).body(error);
  }

  /**
   * TestExceptionがスローされたときに処理する。
   *
   * @param ex 発生した例外
   * @return HTTP 400 と例外メッセージ
   */
  @ExceptionHandler(TestException.class)
  public ResponseEntity<ErrorMessage> handleTestException(TestException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorMessage error = new ErrorMessage(
        status.value(),
        status.name(),
        ex.getMessage()
    );
    return ResponseEntity.status(status).body(error);
  }

}