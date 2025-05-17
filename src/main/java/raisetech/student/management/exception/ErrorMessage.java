package raisetech.student.management.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {

  private int statusValue;
  private String statusName;
  private String message;

}
