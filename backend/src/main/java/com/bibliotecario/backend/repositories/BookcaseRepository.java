package com.bibliotecario.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecario.backend.modal.entites.Bookcase;

public interface BookcaseRepository extends JpaRepository<Bookcase, Long> {

}
