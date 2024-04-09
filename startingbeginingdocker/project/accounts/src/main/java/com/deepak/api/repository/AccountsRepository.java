package com.deepak.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.deepak.api.entity.Accounts;

import jakarta.transaction.Transactional;

public interface AccountsRepository extends JpaRepository<Accounts, Integer>{
	
	Optional<Accounts> findByCustomerId(int customerId);
	@Transactional
	@Modifying
	void deleteByCustomerId(int customerId);
	

}
