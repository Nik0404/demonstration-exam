package com.noviolations.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noviolations.model.Statement;
import com.noviolations.model.User;
import com.noviolations.repository.UserRepository;
import com.noviolations.service.StatementService;
import com.noviolations.service.UserService;

@RestController
@RequestMapping("/api/statement")
public class StatementController {
	@Autowired
	private StatementService statementService;

	@Autowired
	private UserService userService;

	@PostMapping()
	public Statement createStatement(@RequestBody Statement statement, @RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByJwt(jwt);

		Statement createdStatement = statementService.create(statement, user);
		return createdStatement;
	}

	@GetMapping()
	public List<Statement> getAllStatement() throws Exception {

		List<Statement> statement = statementService.findAllStatement();
		
		return statement;
	}

}
