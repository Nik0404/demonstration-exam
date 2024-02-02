package com.noviolations.service;

import java.util.Optional;

import org.apache.catalina.servlets.CGIServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noviolations.config.JwtProvider;
import com.noviolations.model.User;
import com.noviolations.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserById(Long userId) throws Exception {
		Optional<User> user = userRepository.findById(userId);

		if (user.isPresent()) {
			return user.get();
		}

		throw new Exception("пользователь не найден " + userId);

	}

	@Override
	public User findUserByJwt(String jwt) throws Exception {
		String email = jwtProvider.getEmailFromJwtToke(jwt);
		
		if(email == null) {
			throw new Exception("предоставьте действительный токен jwt");
		}
		
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new Exception("пользователь не найден по электронной почте " + email);
		}
		
		return user;
	}

}
