package com.daniel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.data.vo.v1.PersonVO;
import com.daniel.services.PersonServices;

@RestController
@RequestMapping("/persons/v1")
public class PersonController {
	
	@Autowired
	private PersonServices services;
	
	@GetMapping
	public ResponseEntity<List<PersonVO>> findAll() {
		List<PersonVO> persons = services.findAll();
		return new ResponseEntity<>(persons, HttpStatus.OK);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<PersonVO> findById(@PathVariable Long id) {
		PersonVO person = services.findById(id);
		return ResponseEntity.ok(person);
	}
	
	@PostMapping
	public ResponseEntity<PersonVO> create(@RequestBody PersonVO person) {
		PersonVO newPerson = services.create(person);
		return new ResponseEntity<>(newPerson, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<PersonVO> update(@PathVariable Long id, @RequestBody PersonVO person) {
		PersonVO existingPersonOptional = services.findById(person.getKey());
		
		if(existingPersonOptional != null) {
			existingPersonOptional.setFirstName(person.getFirstName());
            existingPersonOptional.setLastName(person.getLastName());
            existingPersonOptional.setAddress(person.getAddress());
            existingPersonOptional.setGender(person.getGender());
            PersonVO updatedEntity = services.update(existingPersonOptional);
            return ResponseEntity.ok(updatedEntity);
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping("/{id}") 
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		PersonVO existingPersonOptional = services.findById(id);
		
		if (existingPersonOptional!= null) {
            services.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
	}
	
	
}
