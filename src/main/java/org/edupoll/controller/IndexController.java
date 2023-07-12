package org.edupoll.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class IndexController {

	@GetMapping("/")
	public ResponseEntity<Void> indexHandle() {

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
