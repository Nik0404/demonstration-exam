package com.noviolations.service;

import java.util.List;

import com.noviolations.model.Statement;
import com.noviolations.model.User;

public interface StatementService {
	
	public Statement create(Statement statement, User user);
	public List<Statement> findAllStatement() throws Exception;
	

}
