package raisetech.student.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 検索条件のオブジェクト
 */
@Schema(description = "検索条件")
@Data
@NoArgsConstructor
public class SearchCondition {

  @Schema(description = "受講生ID", minimum = "1", maximum = "999", example = "1")
  private Integer studentId;

  @Schema(description = "名前", example = "Raise Tech")
  private String name;

  @Schema(description = "ふりがな", example = "レイズテック")
  private String furigana;

  @Schema(description = "ニックネーム", example = "テッくん")
  private String nickname;

  @Schema(description = "住んでいる地域", example = "日本")
  private String liveCity;

  /**
   * ageとminAge/maxAgeとの併用不可。
   */
  @Schema(description = "年齢(minAge/maxAgeとの併用不可)", minimum = "18", maximum = "100", example = "50")
  private Integer age;

  @Schema(description = "年齢の下限(ageとの併用不可)", minimum = "18", example = "18")
  private Integer minAge;

  @Schema(description = "年齢の上限(ageとの併用不可)", maximum = "100", example = "100")
  private Integer maxAge;

  @Schema(description = "性別", example = "その他")
  private String gender;

  @Schema(description = "論理削除", example = "false")
  private Boolean isDeleted;

  @Schema(description = "コースID", minimum = "1", maximum = "999", example = "1")
  private Integer courseId;

  @Schema(description = "コース名", example = "Java")
  private String courseName;

  @Schema(description = "申込状況ID", minimum = "1", maximum = "999", example = "1")
  private Integer statusId;

  @Schema(description = "状況", example = "仮申込")
  private String status;

}
