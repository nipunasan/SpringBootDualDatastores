package com.ns.dualds.controller;

import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ns.dualds.model.DB_One.User;
import com.ns.dualds.model.DB_Two.Product;
import com.ns.dualds.repository.DB_One.UserRepository;
import com.ns.dualds.repository.DB_Two.ProductRepository;

@RestController
@RequestMapping(value = { "/api/v1" })
public class MainController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@GetMapping("/user")
	@Transactional
	public ResponseEntity getUser() {
		Random rnd = new Random();
		User u = new User();
		u.setName("User-" + String.valueOf(rnd.nextInt()));
		u.setAge(rnd.nextInt());
		u.setEmail("user_" + String.valueOf(rnd.nextInt()) + "@email.com");
		return new ResponseEntity<>(userRepository.save(u), HttpStatus.OK);
	}

	@GetMapping("/product")
	@Transactional
	public ResponseEntity getProduct() {
		Random rnd = new Random();
		Product u = new Product();
		u.setName("User-" + String.valueOf(rnd.nextInt()));
		u.setPrice(rnd.nextInt());
		return new ResponseEntity<>(productRepository.save(u), HttpStatus.OK);
	}

}
