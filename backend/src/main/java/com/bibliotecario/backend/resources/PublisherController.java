package com.bibliotecario.backend.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping
	public ResponseEntity<Publisher> insert(@Valid @RequestBody Publisher publisher) {
		Publisher saved = publisherRepository.save(publisher);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId())
				.toUri();
		return ResponseEntity.created(location).body(saved);
	}
}
