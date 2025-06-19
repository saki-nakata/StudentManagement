package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.Student;
import raisetech.student.management.domain.CourseDetail;
import raisetech.student.management.domain.StudentDetail;

/**
 * 受講生、コース情報、申込状況を受講生詳細に変換を行うConverterです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づくコース詳細をマッピングします。コース情報と申込状況は受講生に対して複数存在するので、ループを回して受講生詳細情報を組み立てる。
   *
   * @param studentList      受講生一覧
   * @param courseDetailList コース情報と申込状況の一覧
   * @return 受講生詳細の一覧
   */
  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<CourseDetail> courseDetailList) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      List<CourseDetail> convertDetailList = courseDetailList.stream()
          .filter(detail -> student.getId() == detail.getStudentCourse().getStudentId())
          .collect(Collectors.toList());
      studentDetail.setCourseDetailList(convertDetailList);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

}