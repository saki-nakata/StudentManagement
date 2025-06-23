package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.CourseDetail;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.dto.SearchCondition;

/**
 * 受講生、コース情報、申込状況を受講生詳細に変換を行うConverterです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づくコース詳細(コース情報、申込状況)をマッピングします。
   * 条件の指定がされている場合は条件に一致する受講生詳細のみを返し、条件の指定がされていない場合は全件の受講生詳細の一覧を返します。
   *
   * @param studentList      　受講生の一覧
   * @param courseDetailList 　コース詳細の一覧
   * @param condition        　条件
   * @return studentDetailList　 受講生詳細の一覧
   */
  public List<StudentDetail> mapToStudentDetailList(List<Student> studentList,
      List<CourseDetail> courseDetailList, SearchCondition condition) {
    List<StudentDetail> studentDetailList = new ArrayList<>();
    studentList.stream().filter(student -> isMatchedStudent(condition, student))
        .forEachOrdered(student -> {
          List<CourseDetail> converterList = courseDetailList.stream()
              .filter(
                  courseDetail -> student.getId() == courseDetail.getStudentCourse().getStudentId())
              .collect(Collectors.toList());
          if (!converterList.isEmpty()) {
            List<CourseDetail> matchedList = converterList.stream()
                .filter(courseDetail -> isMatchedCourseDetail(condition, courseDetail))
                .collect(Collectors.toList());
            if (!matchedList.isEmpty()) {
              studentDetailList.add(new StudentDetail(student, matchedList));
            }
          } else {
            studentDetailList.add(new StudentDetail(student, null));
          }
        });
    return studentDetailList;
  }

  /**
   * 受講生が指定された条件に一致するかどうかを判定します。
   *
   * @param condition 　条件
   * @param student   　受講生
   * @return true:条件に一致する、または条件の指定がされていない場合/false:条件に一致しない場合
   */
  boolean isMatchedStudent(SearchCondition condition, Student student) {
    if (condition.getStudentId() != null && student.getId() != condition.getStudentId()) {
      return false;
    }
    if (condition.getName() != null && !student.getFullName().contains(condition.getName())) {
      return false;
    }
    if (condition.getFurigana() != null && !student.getFurigana()
        .contains(condition.getFurigana())) {
      return false;
    }
    if (condition.getNickname() != null && !student.getNickname()
        .contains(condition.getNickname())) {
      return false;
    }
    if (condition.getLiveCity() != null && !student.getLiveCity()
        .contains(condition.getLiveCity())) {
      return false;
    }
    if (condition.getAge() != null && student.getAge() != condition.getAge()) {
      return false;
    }
    if (condition.getMinAge() != null && student.getAge() < condition.getMinAge()) {
      return false;
    }
    if (condition.getMaxAge() != null && student.getAge() > condition.getMaxAge()) {
      return false;
    }
    if (condition.getGender() != null && !student.getGender().equals(condition.getGender())) {
      return false;
    }
    if (condition.getIsDeleted() != null && student.isDeleted() != condition.getIsDeleted()) {
      return false;
    }
    return true;
  }

  /**
   * コース詳細(コース情報、申込状況)が指定された条件に一致するかどうかを判定します。
   *
   * @param condition    条件
   * @param courseDetail コース詳細
   * @return true:条件に一致する、または条件の指定がされていない場合/false:条件に一致しない場合
   */
  boolean isMatchedCourseDetail(SearchCondition condition, CourseDetail courseDetail) {
    StudentCourse course = courseDetail.getStudentCourse();
    CourseStatus status = courseDetail.getCourseStatus();
    if (condition.getCourseId() != null && course.getId() != condition.getCourseId()) {
      return false;
    }
    if (condition.getCourseName() != null && !course.getCourseName()
        .contains(condition.getCourseName())) {
      return false;
    }
    if (condition.getStatusId() != null && status.getId() != condition.getStatusId()) {
      return false;
    }
    if (condition.getStatus() != null && !Objects.equals(status.getApplicationStatus(),
        condition.getStatus())) {
      return false;
    }
    return true;
  }

}