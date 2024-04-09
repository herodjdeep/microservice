package com.sbicardscompany.CardsApplication.service;

import com.sbicardscompany.CardsApplication.dto.CardsDto;

public interface ICardsService {
	
	void createCard(String mobileNumber); //CREATE
	
	 CardsDto fetchCard(String mobileNumber); //READ
	 
	 boolean updateCard(CardsDto cardsDto); //UPDATE
	 
	 boolean deleteCard(String mobileNumber); //DELETE
	 
}
