package raisetech.student.management.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int id;
  private String fullName;  // 'full_name' フィールド
  private String furigana;
  private String nickname;
  private String emailAddress; // 'email_address'
  private String liveCity; // 'live_city'
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;
}




