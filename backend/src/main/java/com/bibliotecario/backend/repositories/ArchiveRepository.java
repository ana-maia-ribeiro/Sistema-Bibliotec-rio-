package com.bibliotecario.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecario.backend.modal.entites.Archive;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {
	
	List<Archive> findByTitle(String title);
	
	List<Archive> findByEdition(Integer edition);
	
	List<Archive> findByYear (Integer year);
	
	List<Archive> findByPublisherDescriptionContainingIgnoreCase(String description);
	
	List<Archive> findByAuthor (String author);
	
	List<Archive> findBySubtitle (String subtitle);

}
