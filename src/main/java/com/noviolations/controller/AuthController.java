package com.noviolations.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.boot.model.source.spi.InLineViewSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noviolations.config.JwtProvider;
import com.noviolations.model.User;
import com.noviolations.repository.UserRepository;
import com.noviolations.request.LoginRequest;
import com.noviolations.response.AuthResponse;
import com.noviolations.service.CustomeUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomeUserDetailsService customeUserDetails;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody User user) throws Exception {

		String fullName = user.getFullName();
		String phone = user.getPhone();
		String email = user.getEmail();
		String login = user.getLogin();
		String password = user.getPassword();

		User isExistEmail = userRepository.findByEmail(email);

		if (isExistEmail != null) {
			throw new Exception("электронная почта уже используется с другой учетной записью");
		}

		User createdUser = new User();
		createdUser.setFullName(fullName);
		createdUser.setPhone(phone);
		createdUser.setEmail(email);
		createdUser.setLogin(login);
		createdUser.setPassword(passwordEncoder.encode(password));

		User saveUser = userRepository.save(createdUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse res = new AuthResponse();

		res.setJwt(token);
		res.setMessage("успешная регистрация");

		return res;
	}

	@PostMapping("/signin")
	public AuthResponse signinHandler(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		Authentication authentication = authentication(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse res = new AuthResponse();

		res.setJwt(token);
		res.setMessage("успешная авторизация");

		return res;

	}

	private Authentication authentication(String username, String password) {
		UserDetails userDetails = customeUserDetails.loadUserByUsername(username);

		if (userDetails == null) {
			throw new BadCredentialsException("пользователь не найден");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("неверный пароль");
		}

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
