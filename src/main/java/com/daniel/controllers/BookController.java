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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.data.vo.v1.BookVO;
import com.daniel.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/books/v1")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping
	@Operation(summary = "Finds all people", description = "Finds all people", tags = {"People"}, responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", content = @Content)
			})
	public ResponseEntity<List<BookVO>> findAll() {
		List<BookVO> books = bookService.findAll();
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "Finds a Book", description = "Finds a Book",
			tags = {"Book"},
			responses = {
				@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = BookVO.class))
				),
				@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
		)
		public BookVO findById(@PathVariable(value = "id") Long id) {
			return bookService.findById(id);
		}
	
		@PostMapping
		@Operation(summary = "Add a new Book", description = "Adds a new Book by passing in a JSON, XML or YML representation of the book!", 
		tags = {"books"}, responses = {
				@ApiResponse(description = "Success", responseCode = "200",
						content = @Content(schema = @Schema(implementation = BookVO.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		})
		public BookVO create(@RequestBody BookVO book) {
			return bookService.create(book);
		}
		
		@PutMapping
		@Operation(summary = "Updates a Book",
			description = "Updates a Book by passing in a JSON, XML or YML representation of the book!",
			tags = {"Book"},
			responses = {
				@ApiResponse(description = "Updated", responseCode = "200",
					content = @Content(schema = @Schema(implementation = BookVO.class))
				),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
		)
		
		public BookVO update(@RequestBody BookVO book) {
			return bookService.update(book);
		}
		
		@DeleteMapping(value = "/{id}")
		@Operation(summary = "Deletes a Book",
			description = "Deletes a Book by passing in a JSON, XML or YML representation of the book!",
			tags = {"Book"},
			responses = {
				@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
		)
		public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
			bookService.delete(id);
			return ResponseEntity.noContent().build();
		}
	
}
