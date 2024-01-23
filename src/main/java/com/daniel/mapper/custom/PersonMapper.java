package com.daniel.mapper.custom;

import org.springframework.stereotype.Service;

import com.daniel.data.vo.v1.PersonVO;
import com.daniel.model.Person;

@Service
public class PersonMapper {
	
	public PersonVO convertEntityToVo(Person person) {
		PersonVO vo = new PersonVO();
		vo.setKey(person.getId());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setAddress(person.getAddress());
		vo.setGender(person.getGender());
		return vo;
	}
	
	
	public Person convertVoToEntity(PersonVO person) {
		Person entity = new Person();
		entity.setId(person.getKey());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		return entity;
	}
}
