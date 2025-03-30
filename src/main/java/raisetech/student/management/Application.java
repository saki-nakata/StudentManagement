package raisetech.student.management;

import java.util.HashMap;
import java.util.Map;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	private String name = "RaiseTech";
	private int age = 5;

	private Map<String, Integer> student = new HashMap<>();

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
	public void updateStudentName(String name) {
		this.name = name;
	}

	@PostMapping("/studentAge")
	public void updateStudentAge(int age) {
		this.age = age;
	}

	@GetMapping("/studentMap")
	public Map<String, Integer> studentMap() {
		return student;
	}

	@PostMapping("/studentMap")
	public void studentMap(String name, int age) {
		student.put(name, age);
	}

	@PostMapping("/studentMapName")
	public void studentMapName(String before, String after) {
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

}
