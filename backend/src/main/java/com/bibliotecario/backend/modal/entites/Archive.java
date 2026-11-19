package com.bibliotecario.backend.modal.entites;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Archive")
public class Archive {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "bookcase_id", nullable = false)
	private Bookcase bookcase;

	@ManyToOne(optional = false)
	@JoinColumn(name = "publisher_id", nullable = false)
	private Publisher publisher;
	
	private Float quantity;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Integer year;
	
	@Column(nullable = false)
	private Integer edition;
	
	private String location;
	
	private Integer volume;
	
	@Column(nullable = false)
	private String author;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "typebook_id", nullable = false)
	private TypeBook typebook;
	
	public Archive() {
		
	}

	public Archive(Long id, Bookcase bookcase, Publisher publisher, Float quantity, String title,
			TypeBook typebook,Integer year, Integer edition, String location, Integer volume, String author) {
		this.id = id;
		this.bookcase = bookcase;
		this.publisher = publisher;
		this.quantity = quantity;
		this.title = title;
		this.typebook = typebook;
		this.year = year;
		this.edition = edition;
		this.location = location;
		this.volume = volume;
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public Bookcase getBookcase() {
		return bookcase;
	}

	public void setBookcase(Bookcase bookcase) {
		this.bookcase = bookcase;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TypeBook getTypebook() {
		return typebook;
	}

	public void setTypebook(TypeBook typebook) {
		this.typebook = typebook;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getEdition() {
		return edition;
	}

	public void setEdition(Integer edition) {
		this.edition = edition;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Archive other = (Archive) obj;
		return Objects.equals(id, other.id);
	}
	
	

}
