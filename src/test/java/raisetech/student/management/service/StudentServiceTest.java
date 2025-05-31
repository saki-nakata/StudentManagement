package raisetech.student.management.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;
  @Mock
  private StudentConverter converter;
  @InjectMocks
  private StudentService sut;

  private Student student;
  private StudentCourse studentCourse;
  private List<StudentCourse> courseList;

  @BeforeEach
  void before() {
    student = mock(Student.class);
    studentCourse = mock(StudentCourse.class);
    courseList = new ArrayList<>();
    courseList.add(studentCourse);
  }

  @Test
  void 受講生詳細の全件検索_リポジトリとコンバーターの検索処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList();
    List<StudentCourse> courseList = new ArrayList();
    Mockito.when(repository.searchStudent()).thenReturn(studentList);
    Mockito.when(repository.searchCourseList()).thenReturn(courseList);

    List<StudentDetail> actual = sut.searchStudentList();

    Mockito.verify(repository, times(1)).searchStudent();
    Mockito.verify(repository, times(1)).searchCourseList();
    Mockito.verify(converter, times(1)).convertStudentDetails(studentList, courseList);
  }

  @Test
  void 新規受講生詳細の登録_リポジトリの登録処理が呼び出されていること() {
    StudentDetail studentDetail = mock(StudentDetail.class);
    Mockito.when(studentDetail.getStudent()).thenReturn(student);
    Mockito.when(studentDetail.getStudentCourseList()).thenReturn(courseList);

    StudentDetail actual = sut.registerStudent(studentDetail);

    Mockito.verify(repository, times(1)).registerStudent(any(Student.class));
    Mockito.verify(repository, times(1)).registerCourse(any(StudentCourse.class));
  }

  @Test
  void 受講生詳細の単一検索_リポジトリのIDに紐づく検索処理が呼び出されていること() {
    int studentId = 1;
    Mockito.when(repository.getStudentInfo(studentId)).thenReturn(student);
    Mockito.when(repository.getCourseInfo(studentId)).thenReturn(courseList);

    StudentDetail actual = sut.getStudentInfo(studentId);

    Mockito.verify(repository, times(1)).getStudentInfo(studentId);
    Mockito.verify(repository, times(1)).getCourseInfo(studentId);
    Assertions.assertEquals(student, actual.getStudent());
    Assertions.assertEquals(courseList, actual.getStudentCourseList());
  }

  @Test
  void 受講生詳細の更新_リポジトリの更新処理が呼び出されていること() {
    StudentDetail studentDetail = mock(StudentDetail.class);
    Mockito.when(studentDetail.getStudent()).thenReturn(student);
    Mockito.when(student.getId()).thenReturn(1);
    Mockito.when(studentDetail.getStudentCourseList()).thenReturn(courseList);

    sut.updateStudent(studentDetail);

    Mockito.verify(repository, times(1)).updateStudent(student);
    Mockito.verify(studentCourse, times(1)).setStudentId(1);
    Mockito.verify(repository, times(1)).updateCourse(studentCourse);
  }

}