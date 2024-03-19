package com.wc.weconnect.service;

import com.WeConnect.config.JwtProvider;
import com.WeConnect.exceptions.UserException;
import com.WeConnect.models.User;
import com.WeConnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	UserRepository userRepository;
	

	@Override
	public User registerUser(User user) {
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(user.getPassword());
		newUser.setId(user.getId());
		
		User savedUser = userRepository.save(newUser);
		return savedUser;
	}

	@Override
	public User findUserById(Integer userId) throws UserException {
		
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			return user.get();
		}
		
		throw new UserException("user not exist with userid " + userId);
	}

	@Override
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public User followUser(Integer reqUserId, Integer userId2) throws UserException {
		// here user1 wants to follow user2
		User reqUser = findUserById(reqUserId);
		User user2 = findUserById(userId2);
		user2.getFollowers().add(reqUser.getId());
		reqUser.getFollowings().add(user2.getId());
		userRepository.save(reqUser);
		userRepository.save(user2);
		return reqUser;
	}

	@Override
	public User updateUser(User user, Integer userId) throws UserException {
		// check for valid user
			Optional<User> user1 = userRepository.findById(userId);
				
			if(user1.isEmpty()) {
				throw new UserException("user not exist with id " + userId);
			}
				
			// if user exist then we have to update the user
				
			User oldUser = user1.get();// if it exist then it is inside in old user and we have to change it
				
			if(user.getFirstName()!=null) {
				oldUser.setFirstName(user.getFirstName());
			}
			if(user.getLastName()!=null) {
				oldUser.setLastName(user.getLastName());
			}
			if(user.getEmail()!=null) {
				oldUser.setEmail(user.getEmail());
			}
			
			if(user.getGender()!=null) {
				oldUser.setGender(user.getGender());
			}
				
			User updatedUser = userRepository.save(oldUser);
		return updatedUser;
	}

	@Override
	public List<User> searchUser(String query) {
		
		return userRepository.searchUser(query);
	}

	@Override
	public User findUserByJwt(String jwt) {
		// now i have token and i have to take out email
		String email = JwtProvider.getEmailFromJwtToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		return user;
	}	
}