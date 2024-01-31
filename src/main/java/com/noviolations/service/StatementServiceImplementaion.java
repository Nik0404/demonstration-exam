package com.noviolations.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noviolations.model.Statement;
import com.noviolations.model.User;
import com.noviolations.repository.StatementRepository;

@Service
public class StatementServiceImplementaion implements StatementService {

	@Autowired
	private StatementRepository statementRepository;

	@Override
	public Statement create(Statement statement, User user) {
		Statement createdStatement = new Statement();
		createdStatement.setDescription(statement.getDescription());
		createdStatement.setCarNumber(statement.getCarNumber());
		createdStatement.setRegion(statement.getRegion());
		createdStatement.setStatus(false);
		createdStatement.setUser(user);

		return statementRepository.save(createdStatement);
	}

	@Override
	public List<Statement> findAllStatement() throws Exception {
		return statementRepository.findAll();
	}

}
