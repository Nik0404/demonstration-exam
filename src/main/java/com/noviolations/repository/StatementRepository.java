package com.noviolations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noviolations.model.Statement;

public interface StatementRepository extends JpaRepository<Statement, Long> {

}
