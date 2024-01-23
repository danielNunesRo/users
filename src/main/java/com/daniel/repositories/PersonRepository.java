package com.daniel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
