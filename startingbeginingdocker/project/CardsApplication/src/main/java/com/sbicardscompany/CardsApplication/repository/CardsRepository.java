package com.sbicardscompany.CardsApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbicardscompany.CardsApplication.entity.Cards;

public interface CardsRepository extends JpaRepository<Cards, Long> {
	
	Optional<Cards> findByMobileNumber(String mobileNumber);
    Optional<Cards> findByCardNumber(String cardNumber);
}
