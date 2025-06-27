package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.CourseStatus;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.CourseDetail;
import raisetech.student.management.domain.StudentDetail;

/**
 * 受講生、コース情報、申込状況を受講生詳細に変換を行うConverterです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づくコース詳細をマッピングします。コース情報と申込状況は受講生に対して複数存在するので、ループを回して受講生詳細情報を組み立てます。
   * コース詳細の検索条件が指定されていない場合は、紐づくコース詳細がなくても受講生情報を含めます。
   *
   * @param studentList                  受講生の一覧
   * @param courseList                   　コース情報の一覧
   * @param statusList                   　申込状況の一覧
   * @param isCourseDetailConditionEmpty 　コース詳細に関する検索条件が未指定かどうか
   * @return 受講生にコース詳細を紐づけた受講生詳細の一覧
   */
  public List<StudentDetail> mapToStudentDetailList(List<Student> studentList,
      List<StudentCourse> courseList, List<CourseStatus> statusList,
      boolean isCourseDetailConditionEmpty) {
    List<CourseDetail> mapToCourseDetailList = mapToCourseDetailList(courseList, statusList);
    List<StudentDetail> studentDetailList = new ArrayList<>();
    studentList.forEach(student -> {
      List<CourseDetail> courseDetailList = mapToCourseDetailList.stream()
          .filter(detail -> student.getId() == detail.getStudentCourse().getStudentId())
          .collect(Collectors.toList());
      if (!courseDetailList.isEmpty()) {
        studentDetailList.add(new StudentDetail(student, courseDetailList));
      } else if (isCourseDetailConditionEmpty) {
        studentDetailList.add(new StudentDetail(student, null));
      }
    });
    return studentDetailList;
  }

  /**
   * コース情報と申込状況をマッピングし、コース詳細の一覧を作成します。
   *
   * @param courseList 　コース情報の一覧
   * @param statusList 　申込状況の一覧
   * @return コース情報に申込状況を紐づけたコース詳細の一覧
   */
  List<CourseDetail> mapToCourseDetailList(List<StudentCourse> courseList,
      List<CourseStatus> statusList) {
    List<CourseDetail> courseDetailList = new ArrayList<>();
    courseList.forEach(course -> {
      statusList.stream().filter(status -> course.getId() == status.getCourseId())
          .map(status -> new CourseDetail(course, status)).forEach(courseDetailList::add);
    });
    return courseDetailList;
  }

}