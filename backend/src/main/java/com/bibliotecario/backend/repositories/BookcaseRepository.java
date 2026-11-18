package com.bibliotecario.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecario.backend.modal.entites.Bookcase;

public interface BookcaseRepository extends JpaRepository<Bookcase, Long> {

	List<Bookcase> findByNumber(Integer number);

	List<Bookcase> findByHallwayContainingIgnoreCase(String hallway);
}
