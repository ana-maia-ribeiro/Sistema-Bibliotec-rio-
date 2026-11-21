package com.bibliotecario.backend.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bibliotecario.backend.modal.entites.Archive;
import com.bibliotecario.backend.modal.entites.Book;
import com.bibliotecario.backend.repositories.ArchiveRepository;
import com.bibliotecario.backend.repositories.BookRepository;
import com.bibliotecario.backend.services.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/BookCollection")
public class BookCollectionController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ArchiveRepository archiveRepository;

	@Autowired
	private BookService bookService;

	@GetMapping
	public List<Book> search(@RequestParam(required = false) Integer code,
			@RequestParam(required = false) Long archive) {
		if (code != null) {
			return bookRepository.findByCode(code);
		}
		if (archive != null) {
			return bookRepository.findByArchiveId(archive);
		}
		return bookRepository.findAll();
	}


	@PostMapping
	@Transactional
	public ResponseEntity<List<Book>> insert(@Valid @RequestBody Book request) {
		Long archiveId = request.getArchive().getId();
		if (archiveId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o id do Archive");
		}
		Archive archive = archiveRepository.findById(archiveId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Archive " + archiveId + " nao encontrado"));

		List<Book> created = bookService.generateMissingBooks(archive);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

}
