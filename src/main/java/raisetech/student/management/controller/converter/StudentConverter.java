package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

/**
 * 受講生詳細を受講生やコース情報、またはその逆の変換を行うConverterです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報をマッピングする。 受講生コース情報は受講生に対して複数存在するので、ループを回して受講生詳細情報を組み立てる。
   *
   * @param studentList 受講生一覧
   * @param coursesList 受講生コース情報のリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentCourse> coursesList) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      List<StudentCourse> convertCourseList = coursesList.stream()
          .filter(studentCourse -> student.getId() == studentCourse.getStudentId())
          .collect(Collectors.toList());
      studentDetail.setStudentCourseList(convertCourseList);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

}