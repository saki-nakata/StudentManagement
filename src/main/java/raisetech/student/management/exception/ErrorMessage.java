package raisetech.student.management.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "エラー内容")
@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {

  @Schema(description = "HTTPステータスコード", example = "400")
  private int statusValue;

  @Schema(description = "HTTPステータスの名前", example = "Bad Request")
  private String statusName;

  @Schema(description = "エラーメッセージ")
  private String message;

}