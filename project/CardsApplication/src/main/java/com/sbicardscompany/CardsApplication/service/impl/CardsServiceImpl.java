package com.sbicardscompany.CardsApplication.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.sbicardscompany.CardsApplication.constants.CardsConstants;
import com.sbicardscompany.CardsApplication.dto.CardsDto;
import com.sbicardscompany.CardsApplication.entity.Cards;
import com.sbicardscompany.CardsApplication.exception.CardAlreadyExistsException;
import com.sbicardscompany.CardsApplication.exception.ResourceNotFoundException;
import com.sbicardscompany.CardsApplication.mapper.CardsMapper;
import com.sbicardscompany.CardsApplication.repository.CardsRepository;
import com.sbicardscompany.CardsApplication.service.ICardsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class CardsServiceImpl implements ICardsService {

	private CardsRepository cardsRepository;

	@Override
	public void createCard(String mobileNumber) {

		Optional<Cards> optionalCheckViaMobileNumber = cardsRepository.findByMobileNumber(mobileNumber);
		if (optionalCheckViaMobileNumber.isPresent()) {

			throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);

		}

		cardsRepository.save(createNewCard(mobileNumber));

	}

	private Cards createNewCard(String mobileNumber) {

		Cards newCard = new Cards();
		long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
		newCard.setCardNumber(Long.toString(randomCardNumber));
		newCard.setMobileNumber(mobileNumber);
		newCard.setCardType(CardsConstants.CREDIT_CARD);
		newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
		newCard.setAmountUsed(0);
		newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
		return newCard;

	}

	@Override
	public CardsDto fetchCard(String mobileNumber) {

		Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(

				() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)

		);

		return CardsMapper.mapToCardsDto(cards, new CardsDto());

	}

	@Override
	public boolean updateCard(CardsDto cardsDto) {

		Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(

				() -> new ResourceNotFoundException("Card", "CardsNumber", cardsDto.getCardNumber())

		);
		Cards cardss = CardsMapper.mapToCards(cardsDto, cards);
		cardsRepository.save(cardss);

		return true;
	}

	@Override
	public boolean deleteCard(String mobileNumber) {

		Cards cards = cardsRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
		cardsRepository.deleteById(cards.getCardId());
		return true;

	}

}
