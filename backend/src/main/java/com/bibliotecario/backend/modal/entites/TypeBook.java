package com.bibliotecario.backend.modal.entites;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Type")
public class TypeBook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(nullable = false, unique = true)
	private Integer code;
	
	@NotNull
	@Column(nullable = false)
	private String description;
	
	public TypeBook() {
		
	}
	

	public TypeBook(Long id, Integer code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}



	public Long getId() {
		return id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		TypeBook other = (TypeBook) obj;
		return Objects.equals(id, other.id);
	}
	
	
	

}
