
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreditCardTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ChorbiService		chorbiService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	// Register to the system as a chorbi. As of the time of registering, a chorbi is not re-quired to provide a credit card.
	// 		No person under 18 is allowed to register to the system.
	// Login to the system using his or her credentials.

	//Crear una nueva tarjeta de crédito o editar la tarjeta de crédito de un chorbi ya existente
	@Test
	public void driverCreateAndEditCreditCard() {
		final Object testingData[][] = {
			{
				"Pablo Escobar", "VISA", "4928756507439105", "3", "2020", "745", 273, null
			}, {
				"Rick Grimes", "DISCOVER", "6420559532032202", "8", "2021", "156", 274, null
			}, {
				"Ragnar Lothbrok", "DANKORT", "5019767397639669", "1", "2019", "688", 273, IllegalArgumentException.class
			}, {
				"Phil Dunphy", "MASTERCARD", "5293764977707328", "1", "2015", "688", 274, IllegalArgumentException.class
			}, {
				"Sheldon Cooper", "VISA", "44952880865", "1", "2015", "688", 273, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createAndEditCreditCard((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Integer) testingData[i][6],
				(Class<?>) testingData[i][7], "create");

		testingData[0][6] = 277;
		testingData[1][6] = 278;
		testingData[1][7] = IllegalArgumentException.class;
		for (int i = 0; i < testingData.length; i++)
			this.createAndEditCreditCard((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Integer) testingData[i][6],
				(Class<?>) testingData[i][7], "edit");
	}
	protected void createAndEditCreditCard(final String holderName, final String brandName, final String number, final String expirationMonth, final String expirationYear, final String cvv, final Integer id, final Class<?> expected,
		final String createOrEdit) {

		Class<?> caught = null;
		Chorbi chorbi = null;
		final Integer expirationMonthInt = Integer.parseInt(expirationMonth);
		final Integer expirationYearInt = Integer.parseInt(expirationYear);
		final Integer cvvInt = Integer.parseInt(cvv);

		try {
			this.authenticate("chorbi1");
			CreditCard creditCard = null;
			if (createOrEdit.equals("create")) {
				chorbi = this.chorbiService.findOne(id);
				creditCard = this.creditCardService.create();
			} else if (createOrEdit.equals("edit")) {
				chorbi = this.chorbiService.findOne(id);
				creditCard = chorbi.getCreditCard();
				Assert.notNull(creditCard);
			}

			creditCard.setHolderName(holderName);
			creditCard.setBrandName(brandName);
			creditCard.setNumber(number);
			creditCard.setExpirationMonth(expirationMonthInt);
			creditCard.setExpirationYear(expirationYearInt);
			creditCard.setCvv(cvvInt);

			creditCard = this.creditCardService.save(creditCard);

			if (createOrEdit.equals("create")) {
				chorbi.setCreditCard(creditCard);
				this.chorbiService.save(chorbi);
			}
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
