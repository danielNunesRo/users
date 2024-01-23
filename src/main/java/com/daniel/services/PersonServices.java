package com.daniel.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.controllers.PersonController;
import com.daniel.data.vo.v1.PersonVO;
import com.daniel.exceptions.ResourceNotFoundException;
import com.daniel.mapper.DozerMapper;
import com.daniel.mapper.custom.PersonMapper;
import com.daniel.model.Person;
import com.daniel.repositories.PersonRepository;

@Service
public class PersonServices {
	
	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private PersonMapper mapper;
	
	public List<PersonVO> findAll() {
		var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
	}
	
	public PersonVO findById(Long id) {
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));;
		PersonVO vo =  DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo; 
	}
	
	public PersonVO create(PersonVO person) {
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		var entity = repository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
		
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		repository.delete(entity);
	}
	
	
	
	
}
