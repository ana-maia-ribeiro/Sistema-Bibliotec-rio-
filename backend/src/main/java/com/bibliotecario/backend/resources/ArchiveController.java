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

import com.bibliotecario.backend.modal.entites.Archive;
import com.bibliotecario.backend.repositories.ArchiveRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Archive")
public class ArchiveController {
	
	@Autowired
	private ArchiveRepository archiveRepository;
	
	@GetMapping
	public List<Archive> search(@RequestParam(required = false) String title,
			@RequestParam(required = false) Integer edition, @RequestParam(required = false) Integer year, 
			@RequestParam(required = false) String publisher, @RequestParam(required = false) String author, @RequestParam(required = false) String subtitle) {
		if (title != null) {
			return archiveRepository.findByTitle(title);
		}
		if (edition != null) {
			return archiveRepository.findByEdition(edition);
		}
		if (year != null) {
			return archiveRepository.findByYear(year);
		}
		if (publisher != null) {
			return archiveRepository.findByPublisherDescriptionContainingIgnoreCase(publisher);
		}
		if (author != null) {
			return archiveRepository.findByAuthor(author);
		}
		if (subtitle != null) {
			return archiveRepository.findBySubtitle(subtitle);
		}
		return archiveRepository.findAll();
	}
	
	
	@PostMapping
	public ResponseEntity<Archive> insert(@Valid @RequestBody Archive archive) {
		Archive saved = archiveRepository.save(archive);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId())
				.toUri();
		return ResponseEntity.created(location).body(saved);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Archive> update(@PathVariable Long id, @Valid @RequestBody Archive archive) {
		return archiveRepository.findById(id)
				.map(existing -> {
					existing.setAuthor(archive.getAuthor());
					existing.setBookcase(archive.getBookcase());
					existing.setEdition(archive.getEdition());
					existing.setLocation(archive.getLocation());
					existing.setPublisher(archive.getPublisher());
					existing.setQuantity(archive.getQuantity());
					existing.setTitle(archive.getTitle());
					existing.setTypebook(archive.getTypebook());
					existing.setVolume(archive.getVolume());
					existing.setYear(archive.getYear());
					existing.setSubtitle(archive.getSubtitle());
					
					return ResponseEntity.ok(archiveRepository.save(existing));
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!archiveRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		archiveRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
