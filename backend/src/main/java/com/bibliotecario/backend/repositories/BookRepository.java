package com.bibliotecario.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bibliotecario.backend.modal.entites.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByCode(Integer code);

	List<Book> findByArchiveId(Long archiveId);

	long countByArchiveId(Long archiveId);

	// Maior codigo ja usado, base para gerar o proximo
	@Query("select max(b.code) from Book b")
	Integer findMaxCode();

}
