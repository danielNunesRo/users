package com.daniel.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.controllers.BookController;
import com.daniel.data.vo.v1.BookVO;
import com.daniel.exceptions.RequiredObjectIsNullException;
import com.daniel.exceptions.ResourceNotFoundException;
import com.daniel.mapper.DozerMapper;
import com.daniel.model.Book;
import com.daniel.repositories.BookRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@Service
@Tag(name = "Book", description = "Endpoints for Managing Book")
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;	
	
	public List<BookVO> findAll() {
		var books = DozerMapper.parseListObjects(bookRepository.findAll(), BookVO.class);
		books.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		return books;
		
	}
	
	public BookVO findById(Long id) {
		
		var entity = bookRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public BookVO create(BookVO book) {

		if (book == null) throw new RequiredObjectIsNullException();
		
		var entity = DozerMapper.parseObject(book, Book.class);
		var vo =  DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public BookVO update(BookVO book) {

		if (book == null) throw new RequiredObjectIsNullException();
		
		
		var entity = bookRepository.findById(book.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var vo =  DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		var entity = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		bookRepository.delete(entity);
	}
}
