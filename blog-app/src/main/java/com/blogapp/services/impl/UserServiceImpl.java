package com.blogapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.models.User;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.UserRepo;
import com.blogapp.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user=this.dtoToUser(userDto);
		User savedUser=this.userRepo.save(user);
		System.out.println("*****************"+savedUser.getUserId());
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));

		user.setUserName(userDto.getUserName());
		user.setUserEmail(userDto.getUserEmail());
		user.setUserPassword(userDto.getUserPassword());
		// user.setUserRole(userDto.getUserRole());
		
		User updatedUser=this.userRepo.save(user);
		UserDto updatedUserDto=this.userToDto(updatedUser);
		return updatedUserDto;
	}

	
	@Override
	public UserDto getUserById(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "Id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		
		List<User> users=this.userRepo.findAll();
		
		List<UserDto> userDtos=users.stream().map((user)->this.userToDto(user)).collect(Collectors.toList());
		for (UserDto userDto : userDtos) {
			System.out.println(userDto.getUserId());
		}
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {

		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "id", userId));
		this.userRepo.delete(user);
		
	}
	
	public User dtoToUser(UserDto userDto){
		User user=this.modelMapper.map(userDto, User.class);

		// User user=new User();
		// user.setUserId(userDto.getUserId());
		// user.setUserName(userDto.getUserName());
		// user.setUserEmail(userDto.getUserEmail());
		// user.setUserPassword(userDto.getUserPassword());
		// user.setUserRole(userDto.getUserRole());
		return user;
	}
	
	public UserDto userToDto(User user) {
		
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
	
		// userDto.setUserId(user.getUserId());
		// userDto.setUserName(user.getUserName());
		// userDto.setUserEmail(user.getUserEmail());
		// userDto.setUserPassword(user.getUserPassword());
		// userDto.setUserRole(user.getUserRole());
		return userDto;
	}
	
}
