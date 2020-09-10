package com.example.demo.services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {
	
	public List<User> readFromJsonFile(){
		List<User> users = new ArrayList<>();
		
		try{
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = new FileInputStream(new File("D:\\users.json"));
			TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
			users = mapper.readValue(inputStream, typeReference);
			inputStream.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(JsonParseException e){
			e.printStackTrace();
		}catch(JsonMappingException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return users;
	}
	
	public User doLogin(String email, String password){
		List<User> users = this.readFromJsonFile();
		for(User u: users){
			if(u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)){
				return u;
			}
		}
		return null;
	}
	
	public Boolean addNewUser(User user){
		try{
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = new FileInputStream(new File("D:\\users.json"));
			TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
			
			List<User> users = new ArrayList<User>();
			try{
				users = mapper.readValue(inputStream, typeReference);
			}catch(JsonMappingException e){}
			
			if(users.size() != 0){
				int lastId = users.get(users.size() -1).getId();
				lastId++;
				user.setId(lastId);
			}else{	
				user.setId(1);
			}
			users.add(user);
			mapper.writeValue(new File("D:\\users.json"), users);
			inputStream.close();
			return true;
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(JsonParseException e){
			e.printStackTrace();
		}catch(JsonMappingException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return false;
	}

	public Boolean removeUser(int id){
		try{
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = new FileInputStream(new File("D:\\users.json"));
			TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
			
			List<User> users = new ArrayList<User>();
			try{
				users = mapper.readValue(inputStream, typeReference);
			}catch(JsonMappingException e){}
			
			int i = 0;
			for(User u : users){
				if(u.getId() == id){
					break;
				}
				i++;
			}
			users.remove(i);
			
			mapper.writeValue(new File("D:\\users.json"), users);
			inputStream.close();
			return true;
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(JsonParseException e){
			e.printStackTrace();
		}catch(JsonMappingException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public Boolean updateUser(User user, int idUser){
		try{
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = new FileInputStream(new File("D:\\users.json"));
			TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
			List<User> users = mapper.readValue(inputStream, typeReference);

			for(User u : users){
				if(u.getId() == idUser){
					u.setAddress(user.getAddress());
					u.setFirstName(user.getFirstName());
					u.setLastName(user.getLastName());
					u.setAge(user.getAge());
				}
			}
			
			mapper.writeValue(new File("D:\\users.json"), users);
			inputStream.close();
			return true;
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(JsonParseException e){
			e.printStackTrace();
		}catch(JsonMappingException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return false;
	}

}
