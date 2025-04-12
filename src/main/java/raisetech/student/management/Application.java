package raisetech.student.management;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@GetMapping("/studentList")
	public List<Student> getStudentList() {
		return repository.studentSearch();

	}

	@GetMapping("/courseList")
	public List<StudentCourse> getCourseList() {
		return repository.courseSearch();
	}

	@GetMapping("/student")
	public String getStudent(@RequestParam String name) {
		Student student = repository.searchByName(name);
		return student.getName() + "さん " + student.getAge() + "歳";
	}

	@GetMapping("/studentList")
	public List<Student> studentList() {
		return repository.studentList();
	}

	@PostMapping("/student")
	public void registerStudent(String name, int age) {
		repository.registerStudent(name, age);
	}

	@PatchMapping("/student")
	public void updateStudent(String name, int age) {
		repository.updateStudent(name, age);
	}

	@DeleteMapping("/student")
	public void deleteStudent(String name) {
		repository.deleteStudent(name);
	}

}
