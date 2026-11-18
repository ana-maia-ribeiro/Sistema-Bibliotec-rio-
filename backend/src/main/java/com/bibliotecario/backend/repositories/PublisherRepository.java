package com.bibliotecario.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecario.backend.modal.entites.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

	List<Publisher> findByCode(Integer code);

	List<Publisher> findByDescriptionContainingIgnoreCase(String description);
}
