package raisetech.student.management.controller.converter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

@ExtendWith(MockitoExtension.class)
class StudentConverterTest {

  @InjectMocks
  private StudentConverter sut;

  @Test
  void 受講生に紐づく受講生コース情報が正しく受講生詳細に変換できていること() {
    int studentId = 999;
    Student student = new Student();
    student.setId(studentId);
    List<Student> studentList = new ArrayList<>();
    studentList.add(student);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(studentId);
    List<StudentCourse> courseList = new ArrayList<>();
    courseList.add(studentCourse);

    List<StudentDetail> expected = List.of(new StudentDetail(student, courseList));

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, courseList);

    assertThat(actual.size()).isEqualTo(expected.size());
    assertThat(actual).isEqualTo(expected);
    assertThat(actual.get(0).getStudent().getId()).isEqualTo(
        actual.get(0).getStudentCourseList().get(0).getStudentId());
  }

}