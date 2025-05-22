package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生情報")
@Getter
@Setter
public class Student {

  @Schema(description = "受講生ID", minimum = "1", maximum = "999", example = "1", required = true)
  @Max(value = 999, message = "999以下の数値にしてください。")
  private int id;

  @Schema(description = "名前", example = "Raise Tech", required = true)
  @NotBlank(message = "名前を入力してください。")
  @Size(max = 30, message = "30桁以内で入力してください。")
  private String fullName;// 'full_name' フィールド

  @Schema(description = "ふりがな", example = "れいず てっく", required = true)
  @NotBlank(message = "ふりがなを入力してください。")
  @Size(max = 50, message = "50桁以内で入力してください。")
  private String furigana;

  @Schema(description = "ニックネーム", example = "テッくん", required = false)
  @Size(max = 30, message = "30桁以内で入力してください。")
  private String nickname;

  @Schema(description = "メールアドレス", format = "email", example = "raisetech@gmail.com", required = true)
  @Email(message = "有効なメールアドレスの形式ではありません。")
  @NotBlank(message = "メールアドレスを入力してください。")
  @Size(min = 10, max = 50, message = "30桁以内で入力してください。")
  private String emailAddress; // 'email_address'

  @Schema(description = "住んでいる場所", example = "大阪府大阪市", required = false)
  @Size(max = 50, message = "50桁以内で入力してください。")
  private String liveCity; // 'live_city'

  @Schema(description = "年齢", example = "30", required = true)
  @Min(value = 18, message = "18以上の数値にしてください。")
  @Max(value = 100, message = "100以下の数値にしてください。")
  private int age;

  @Schema(description = "性別", example = "その他", required = true)
  @NotBlank(message = "性別を入力してください。")
  private String gender;

  @Schema(description = "備考", required = false)
  @Size(max = 100, message = "100桁以内で入力してください。")
  private String remark;

  @Schema(description = "論理削除", defaultValue = "false", required = true)
  private boolean isDeleted; // 'is_deleted'

}