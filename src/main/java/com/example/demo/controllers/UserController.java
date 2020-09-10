package com.example.demo.controllers;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.*;
import com.example.demo.services.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	// /users
	@GetMapping("/users")
	public List<User> getUser(){
		List<User> users = userService.readFromJsonFile();
		return users;
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> addUser(@RequestBody User newUser){
		boolean response = userService.addNewUser(newUser);
		if(response){
			return ResponseEntity.accepted().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/user/{id}")
	public User findUser(@PathVariable final String id ){
		int castedId = Integer.parseInt(id);
		List<User> users = userService.readFromJsonFile();
		for(User u : users){
			if(u.getId() == castedId){
				return u;
			}
		}
		return null;
	}
	
	
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable final String id , @RequestBody User user){
		int castedId = Integer.parseInt(id);
		boolean response = userService.updateUser(user, castedId);
		if(response){
			return ResponseEntity.accepted().body(user);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable final String id){
		int castedId = Integer.parseInt(id);
		boolean response = userService.removeUser(castedId);
		if(response){
			return ResponseEntity.accepted().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody Map<String ,String> json){
		String email = json.get("email");
		String password = json.get("password");
		User user = userService.doLogin(email, password);
		return ResponseEntity.accepted().body(user);
	}
	
}
