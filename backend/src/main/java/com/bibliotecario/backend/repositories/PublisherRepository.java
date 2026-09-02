package com.bibliotecario.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecario.backend.modal.entites.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher,Long>  {
	
	

}
