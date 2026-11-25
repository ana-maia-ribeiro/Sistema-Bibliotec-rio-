package com.bibliotecario.backend.modal.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Borrow")
public class Borrow {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@JoinColumn(name = "book_id", nullable = false)
	@ManyToOne(optional = false)
	private Book book;
	
	@JoinColumn(name = "librarian_id", nullable = false)
	@ManyToOne(optional = false)
	private Librarian librarian;

}
