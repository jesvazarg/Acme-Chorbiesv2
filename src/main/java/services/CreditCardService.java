
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.Actor;
import domain.Chorbi;
import domain.CreditCard;
import forms.CreateCreditCardForm;

@Service
@Transactional
public class CreditCardService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private ActorService			actorService;


	// Constructors------------------------------------------------------------
	public CreditCardService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public CreditCard findOne(final int creditCardId) {
		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);

		return result;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result;

		result = this.creditCardRepository.findAll();

		return result;
	}

	public CreditCard create() {
		CreditCard result;

		result = new CreditCard();

		return result;
	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		CreditCard result;
		Actor principal;

		this.checkCreditCard(creditCard);

		result = this.creditCardRepository.save(creditCard);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		principal.setCreditCard(result);
		this.actorService.save(principal);

		return result;
	}

	public CreditCard saveRegister(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		CreditCard result;

		this.checkCreditCard(creditCard);

		result = this.creditCardRepository.save(creditCard);

		return result;
	}

	public void delete(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Chorbi principal;

		principal = this.chorbiService.findByPrincipal();
		Assert.notNull(principal);
		principal.setCreditCard(null);
		this.chorbiService.save(principal);
		Assert.isTrue(creditCard.getId() != 0);

		this.creditCardRepository.delete(creditCard);
	}

	// Other business methods -------------------------------------------------

	public void checkCreditCard(final CreditCard creditCard) {

		final Calendar expiryDate = Calendar.getInstance();
		expiryDate.set(creditCard.getExpirationYear(), creditCard.getExpirationMonth() - 1, 1);
		final Calendar today = Calendar.getInstance();
		expiryDate.add(Calendar.DAY_OF_YEAR, -1);
		Assert.isTrue(expiryDate.after(today));

		final String brandName = creditCard.getBrandName().toUpperCase();
		Assert.isTrue(brandName.equals("VISA") || brandName.equals("MASTERCARD") || brandName.equals("DISCOVER") || brandName.equals("DINNERS") || brandName.equals("AMEX"));

	}

	public Boolean checkCreditCardBoolean(final CreditCard creditCard) {
		Boolean result = true;
		final Calendar expiryDate = Calendar.getInstance();
		expiryDate.set(creditCard.getExpirationYear(), creditCard.getExpirationMonth() - 1, 1);
		final Calendar today = Calendar.getInstance();
		expiryDate.add(Calendar.DAY_OF_YEAR, -1);

		final String brandName = creditCard.getBrandName().toUpperCase();

		if (expiryDate.after(today) == false || (brandName.equals("VISA") || brandName.equals("MASTERCARD") || brandName.equals("DISCOVER") || brandName.equals("DINNERS") || brandName.equals("AMEX")) == false)
			result = false;
		return result;

	}

	public CreditCard reconstructCreditCard(final CreateCreditCardForm createCreditCardForm, final String type) {
		Assert.notNull(createCreditCardForm);
		CreditCard creditCard = null;
		Actor principal;

		principal = this.actorService.findByPrincipal();

		if (type.equals("create"))
			creditCard = this.create();
		else if (type.equals("edit"))
			creditCard = principal.getCreditCard();

		creditCard.setHolderName(createCreditCardForm.getHolderName());
		creditCard.setBrandName(createCreditCardForm.getBrandName());
		creditCard.setNumber(createCreditCardForm.getNumber());
		creditCard.setExpirationMonth(createCreditCardForm.getExpirationMonth());
		creditCard.setExpirationYear(createCreditCardForm.getExpirationYear());
		creditCard.setCvv(createCreditCardForm.getCvv());

		return creditCard;
	}

	public CreateCreditCardForm constructCreditCard(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		CreateCreditCardForm createCreditCardForm;

		createCreditCardForm = new CreateCreditCardForm();
		createCreditCardForm.setHolderName(creditCard.getHolderName());
		createCreditCardForm.setBrandName(creditCard.getBrandName());
		createCreditCardForm.setNumber(creditCard.getNumber());
		createCreditCardForm.setExpirationMonth(creditCard.getExpirationMonth());
		createCreditCardForm.setExpirationYear(creditCard.getExpirationYear());
		createCreditCardForm.setCvv(creditCard.getCvv());

		return createCreditCardForm;
	}

}
