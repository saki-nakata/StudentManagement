package raisetech.student.management.service;

import java.util.List;
import org.springframework.stereotype.Service;
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

  public void insertStudentList(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    int registeredStudent = repository.insertStudent(student);
    List<StudentCourse> studentCourseList = studentDetail.getStudentCourse();
    if (registeredStudent == 0) {
      throw new RuntimeException("studentsテーブルが登録できませんでした。");
    }
    StudentCourse course = studentCourseList.get(0);
    course.setStudentId(student.getId());
    course.setCourseName(course.getCourseName());
    course.setStartDate(course.getStartDate());
    course.setScheduledEndDate(course.getStartDate().plusMonths(6));
    int registeredCourse = repository.insertStudentCourse(course);
    if (registeredCourse == 0) {
      throw new RuntimeException("students_coursesテーブルが登録できませんでした。");
    }
  }

}
