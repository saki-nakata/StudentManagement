package raisetech.student.management.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.searchStudent();
  }

  public List<StudentCourse> searchCourseList() {
    return repository.searchCourse();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    //todo；新規受講生情報の登録
    int registerStudent = repository.registerStudent(student);
    if (registerStudent == 0) {
      throw new RuntimeException("studentsテーブルでの登録処理");
    }
    //todo；受講生コース情報の登録
    for (StudentCourse course : studentDetail.getStudentCourse()) {
      course.setStudentId(student.getId());
      course.setCourseName(course.getCourseName());
      course.setStartDate(course.getStartDate());
      course.setScheduledEndDate(course.getStartDate().plusMonths(6));
      int registerCourse = repository.registerCourse(course);
      if (registerCourse == 0) {
        throw new RuntimeException("students_coursesテーブルでの登録処理");
      }
    }
  }

  public StudentDetail getStudentInfo(int id) {
    Student student = repository.getStudentInfo(id);
    List<StudentCourse> courseList = repository.getCourseInfo(id);
    return new StudentDetail(student,courseList);
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    //todo；新規受講生情報の更新
    int updateStudent = repository.updateStudent(student);
    if (updateStudent == 0) {
      throw new RuntimeException("studentsテーブルでの更新処理");
    }
    //todo；受講生コース情報の更新
    for (StudentCourse course : studentDetail.getStudentCourse()) {
      course.setStudentId(student.getId());
      course.setCourseName(course.getCourseName());
      course.setStartDate(course.getStartDate());
      course.setScheduledEndDate(course.getStartDate().plusMonths(6));
      int updateCourse = repository.updateCourse(course);
      if (updateCourse == 0) {
        throw new RuntimeException("students_coursesテーブルでの更新処理");
      }
    }
  }

}
