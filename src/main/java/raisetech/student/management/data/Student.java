package raisetech.student.management.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int id;

  @NotNull(message = "名前を入力してください。")
  @Size(max = 30, message = "30桁以内で入力してください。")
  private String fullName;// 'full_name' フィールド

  @NotNull(message = "ふりがなを入力してください。")
  @Size(max = 50, message = "50桁以内で入力してください。")
  private String furigana;

  @Size(max = 30, message = "30桁以内で入力してください。")
  private String nickname;

  @Email(message = "有効なメールアドレスの形式ではありません。")
  @NotNull(message = "メールアドレスを入力してください。")
  @Size(max = 30, message = "30桁以内で入力してください。")
  private String emailAddress; // 'email_address'

  @Size(max = 50, message = "50桁以内で入力してください。")
  private String liveCity; // 'live_city'

  @Min(value = 18, message = "18以上の数値にしてください。")
  @Max(value = 100, message = "100以下の数値にしてください。")
  private int age;

  @NotNull(message = "性別を入力してください。")
  private String gender;

  @Size(max = 100, message = "100桁以内で入力してください。")
  private String remark;

  private boolean isDeleted; // 'is_deleted'

}




