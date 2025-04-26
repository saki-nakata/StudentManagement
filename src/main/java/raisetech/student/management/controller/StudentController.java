package raisetech.student.management.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudent(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentCourse> courses = service.searchCourseList();
    model.addAttribute("studentList", converter.convertStudentDetails(students, courses));
    return "studentList";
  }

  @GetMapping("/courseList")
  public List<StudentCourse> getCourseList() {
    return service.searchCourseList();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    Student student = new Student();
    student.setAge(30);
    student.setGender("その他");

    StudentCourse course = new StudentCourse();
    course.setCourseName("");
    List<StudentCourse> courseList = new ArrayList<>();
    courseList.add(course);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourse(courseList);

    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute("studentDetail") StudentDetail studentDetail,
      BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }
    try {
      service.insertStudentList(studentDetail);
    } catch (DuplicateKeyException e) {
      System.out.println("重複エラー " + e.getMessage());
      return "registerStudent";
    } catch (Exception e) {
      System.out.println("例外が発生 " + e.getMessage());
      return "registerStudent";
    }
    System.out.println("登録成功しました!");
    return "redirect:/studentList";

  }

}
