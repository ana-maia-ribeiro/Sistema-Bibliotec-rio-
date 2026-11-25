package com.bibliotecario.backend.modal.entites;

import java.sql.Time;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Librarian")
public class Librarian {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotNull
	private String name;
	
	@NotNull
	private String email;
	
	@NotNull
	private Time entrance;
	
	@NotNull
	@Column(name = "`exit`")
	private Time exit;
	
	public Librarian() {
		
	}

	public Librarian(Long id, @NotNull String name, @NotNull String email, @NotNull Time entrance, @NotNull Time exit) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.entrance = entrance;
		this.exit = exit;
	}

	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Time getEntrance() {
		return entrance;
	}

	public void setEntrance(Time entrance) {
		this.entrance = entrance;
	}

	public Time getExit() {
		return exit;
	}

	public void setExit(Time exit) {
		this.exit = exit;
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
		Librarian other = (Librarian) obj;
		return Objects.equals(id, other.id);
	}
	
	

}
