package com.bibliotecario.backend.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bibliotecario.backend.modal.entites.Course;
import com.bibliotecario.backend.modal.entites.Student;
import com.bibliotecario.backend.repositories.CourseRepository;
import com.bibliotecario.backend.repositories.StudentRepository;
import com.bibliotecario.backend.services.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentService studentService;

	@GetMapping
	public List<Student> search(@RequestParam(required = false) String code,
			@RequestParam(required = false) String name, @RequestParam(required = false) String email) {
		if (code != null) {
			return studentRepository.findByCode(code);
		}
		if (name != null) {
			return studentRepository.findByName(name);
		}
		if (email != null) {
			return studentRepository.findByEmail(email);
		}
		return studentRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Student> insert(@Valid @RequestBody Student student ){
		student.setCourse(findCourse(student));
		// O RA e sempre gerado pelo sistema; o que vier no JSON e ignorado
		student.setCode(studentService.generateCode());
		Student saved = studentRepository.save(student);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId())
				.toUri();
		return ResponseEntity.created(location).body(saved);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Student> update(@PathVariable Long id, @Valid @RequestBody Student student) {
		return studentRepository.findById(id)
				.map(existing -> {
					existing.setName(student.getName());
					existing.setEmail(student.getEmail());
					existing.setCourse(findCourse(student));
					existing.setSemester(student.getSemester());
					return ResponseEntity.ok(studentRepository.save(existing));
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!studentRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		studentRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}


	private Course findCourse(Student student) {
		Long courseId = student.getCourse().getId();
		if (courseId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o id do Course");
		}
		return courseRepository.findById(courseId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Course " + courseId + " nao encontrado"));
	}
}
