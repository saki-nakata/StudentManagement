package raisetech.student.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private StudentRepository repository;
	private Map<String, Integer> student = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/studentMap")
	public Map<String, Integer> studentMap() {
		return student;
	}

	@PostMapping("/studentMap")
	public void studentMap(String name, int age) {
		student.put(name, age);
	}

	@PostMapping("/updateStudentMap")
	public void updateStudentMapName(String before, String after) {
		if (student.containsKey(before)) {
			int age = student.get(before);
			student.remove(before);
			student.put(after, age);
		}
	}

	@PostMapping("/studentMapAge")
	public void studentMapAge(String name, Integer age) {
		if (student.containsKey(name)) {
			student.replace(name, age);
		}
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
