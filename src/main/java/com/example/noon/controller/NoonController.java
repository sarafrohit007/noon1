package com.example.noon.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.noon.dto.Book;
import com.example.noon.dto.User;
import com.example.noon.services.INoonService;
import com.sun.research.ws.wadl.Method;

@Controller
@RequestMapping("/api")
public class NoonController {
	
	@Autowired
	private INoonService noonService;

	@RequestMapping("/result")
	@ResponseBody
	public String printResult() {
		System.out.println("Inside get result function.... analysis");
		return "Inside get result function.... analysis";
	}
	
	@RequestMapping("/addbook")
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	public String addBook(@RequestBody Book book) {
		System.out.println("Inside Add Book function of ");
		noonService.addBook(book);
		return "Inside add Book result function.... analysis";
	}
	
	@RequestMapping("/adduser")
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON)
	public String addUser(@RequestBody User user) {
		System.out.println("Inside Add User function of ");
		noonService.addUser(user);
		return "Inside add User result function.... analysis";
	}
	
	
	
	
}
