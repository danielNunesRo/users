package com.daniel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
