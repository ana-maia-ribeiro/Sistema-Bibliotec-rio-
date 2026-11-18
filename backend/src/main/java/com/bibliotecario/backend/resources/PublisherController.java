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

import com.bibliotecario.backend.modal.entites.Publisher;
import com.bibliotecario.backend.repositories.PublisherRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/publisher")
public class PublisherController {

	@Autowired
	private PublisherRepository publisherRepository;

	@GetMapping
	public List<Publisher> search(@RequestParam(required = false) Integer code,
			@RequestParam(required = false) String description) {
		if (code != null) {
			return publisherRepository.findByCode(code);
		}
		if (description != null) {
			return publisherRepository.findByDescriptionContainingIgnoreCase(description);
		}
		return publisherRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Publisher> insert(@Valid @RequestBody Publisher publisher) {
		Publisher saved = publisherRepository.save(publisher);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId())
				.toUri();
		return ResponseEntity.created(location).body(saved);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Publisher> update(@PathVariable Long id, @Valid @RequestBody Publisher publisher) {
		return publisherRepository.findById(id)
				.map(existing -> {
					existing.setCode(publisher.getCode());
					existing.setDescription(publisher.getDescription());
					return ResponseEntity.ok(publisherRepository.save(existing));
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!publisherRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		publisherRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
