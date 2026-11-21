package com.bibliotecario.backend.resources;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bibliotecario.backend.modal.entites.Archive;
import com.bibliotecario.backend.modal.entites.StockEntry;
import com.bibliotecario.backend.repositories.ArchiveRepository;
import com.bibliotecario.backend.repositories.StockEntryRepository;
import com.bibliotecario.backend.services.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/StockEntry")
public class StockEntryController {

	@Autowired
	private StockEntryRepository stockEntryRepository;

	@Autowired
	private ArchiveRepository archiveRepository;

	@Autowired
	private BookService bookService;

	@GetMapping
	public List<StockEntry> search(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(required = false) Long archive) {
		if (date != null) {
			return stockEntryRepository.findByDate(date);
		}
		if (archive != null) {
			return stockEntryRepository.findByArchiveId(archive);
		}
		return stockEntryRepository.findAll();
	}

	@PostMapping
	@Transactional
	public ResponseEntity<StockEntry> insert(@Valid @RequestBody StockEntry stockEntry) {
		Long archiveId = stockEntry.getArchive().getId();
		if (archiveId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o id do Archive");
		}
		Archive archive = archiveRepository.findById(archiveId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Archive " + archiveId + " nao encontrado"));


		float current = archive.getQuantity() != null ? archive.getQuantity() : 0f;
		archive.setQuantity(current + stockEntry.getQuantity());
		archiveRepository.save(archive);

		LocalDateTime now = LocalDateTime.now();
		stockEntry.setDate(now.toLocalDate());
		stockEntry.setHour(now.toLocalTime().withNano(0));
		stockEntry.setArchive(archive);
		StockEntry saved = stockEntryRepository.save(stockEntry);

		// Destrincha a entrada: cada livro que chegou ganha seu codigo unico
		bookService.generateBooks(archive, stockEntry.getQuantity());

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId())
				.toUri();
		return ResponseEntity.created(location).body(saved);
	}

}
