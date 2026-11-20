package com.bibliotecario.backend.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecario.backend.modal.entites.StockEntry;

public interface StockEntryRepository extends JpaRepository<StockEntry,Long> {

	List<StockEntry> findByDate(LocalDate date);

	List<StockEntry> findByArchiveId(Long archiveId);

}
