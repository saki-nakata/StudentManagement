package raisetech.student.management.service;

import java.util.List;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {
  private StudentRepository repository;

  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    List<Student> student = repository.studentSearch();
    return student.stream()
        .filter(age -> age.getAge() >= 30 && age.getAge() <= 39)
        .toList();
  }

  public List<StudentCourse> searchCourseList() {
    List<StudentCourse> course = repository.courseSearch();
    return course.stream()
        .filter(name -> name.getCourseName().equals("Javaコース"))
        .toList();
  }

}
