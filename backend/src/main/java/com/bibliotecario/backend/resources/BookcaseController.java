package com.bibliotecario.backend.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bibliotecario.backend.modal.entites.Bookcase;
import com.bibliotecario.backend.repositories.BookcaseRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookcase")
public class BookcaseController {

	@Autowired
	private BookcaseRepository bookcaseRepository;

	@GetMapping
	public List<Bookcase> search(@RequestParam(required = false) String hallway,
			@RequestParam(required = false) Integer number) {
		if (hallway != null) {
			return bookcaseRepository.findByHallwayContainingIgnoreCase(hallway);
		}
		if (number != null) {
			return bookcaseRepository.findByNumber(number);
		}
		return bookcaseRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Bookcase> insert(@Valid @RequestBody Bookcase bookcase) {
		Bookcase saved = bookcaseRepository.save(bookcase);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId())
				.toUri();
		return ResponseEntity.created(location).body(saved);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Bookcase> update(@PathVariable Long id, @Valid @RequestBody Bookcase bookcase) {
		return bookcaseRepository.findById(id)
				.map(existing -> {
					existing.setHallway(bookcase.getHallway());
					existing.setShelf(bookcase.getShelf());
					existing.setNumber(bookcase.getNumber());
					return ResponseEntity.ok(bookcaseRepository.save(existing));
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!bookcaseRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		bookcaseRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
