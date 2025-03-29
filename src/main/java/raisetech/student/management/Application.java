package raisetech.student.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	private String name = "Raise Tech";
	private int age = 5;

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/studentInfo")
	public String getStudentInfo() {
		return name + "さん " + age + "歳";
	}

	@PostMapping("/studentInfo")
	public void studentInfo(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@PostMapping("/studentName")
	public void uppdateStudentName(String name) {
		this.name = name;
	}

	@PostMapping("/studentAge")
	public void uppdateStudentAge(int age) {
		this.age = age;
	}
}
