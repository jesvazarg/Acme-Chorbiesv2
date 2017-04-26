
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Manager;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManagerTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ManagerService		managerService;

	@Autowired
	private CreditCardService	creditCardService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	//Register to the system as a manager.
	//Login to the system using his or her credentials.

	//En este primer test vamos a registrarnos como manager
	//Los 3 casos negativos son debidos a que la contraseña y la confirmacion de este no son iguales,
	// no ha aceptado los terminos y condiciones y por ultimo ha introducido un numero de tarjeta de credito erronea.
	@Test
	public void driverRegistrarUnManager() {
		final Object testingData[][] = {
			{
				"managerNew", "managerNew", "managerNew", "pepe", "fernandez", "pepe@gmail.com", "1254", "Sevilla S.A.", "SVQ", "Pepe", "VISA", "5760651824445570", 10, 2019, 300, true, null
			}, {
				"managerPepa", "managerPepa", "managerPepa", "pepa", "juana", "pepe@gmail.com", "5854", "Jaen S.A.", "JA", "Juana", "VISA", "5732718459670965", 11, 2018, 350, true, null
			}, {
				"rambo", "rambo", "rambo", "Antonio", "Ramos", "tonio@gmail.com", "1287", "Cadiz S.A.", "CA", "Juana", "VISA", "5732718459670965", 11, 2017, 345, true, null
			}, {
				"rambo", "rambo", "rambo1", "Antonio", "Ramos", "tonio@gmail.com", "1287", "Cadiz S.A.", "CA", "Juana", "VISA", "5732718459670965", 11, 2017, 345, true, IllegalArgumentException.class
			}, {
				"rambo", "rambo", "rambo", "Antonio", "Ramos", "tonio@gmail.com", "1287", "Cadiz S.A.", "CA", "Juana", "VISA", "5732718459670965", 11, 2017, 345, false, IllegalArgumentException.class
			}, {
				"rambo", "rambo", "rambo", "Antonio", "Ramos", "tonio@gmail.com", "1287", "Cadiz S.A.", "CA", "Juana", "VISA", "0002718459670960", 11, 2017, 345, true, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.registrarUnManager((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (int) testingData[i][12], (int) testingData[i][13], (int) testingData[i][14],
				(Boolean) testingData[i][15], (Class<?>) testingData[i][16]);
	}

	protected void registrarUnManager(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final String phone, final String company, final String vat,
		final String holdername, final String brandname, final String number, final int expirationMonth, final int expirationYear, final int cvv, final Boolean isAgree, final Class<?> expected) {
		Class<?> caught;
		Md5PasswordEncoder encoder;
		String passwordEncoded;

		caught = null;
		try {
			final Manager manager = this.managerService.create();
			CreditCard creditCard = this.creditCardService.create();

			Assert.isTrue(password.equals(confirmPassword));
			manager.getUserAccount().setUsername(username);
			encoder = new Md5PasswordEncoder();
			passwordEncoded = encoder.encodePassword(password, null);
			manager.getUserAccount().setPassword(passwordEncoded);

			manager.setName(name);
			manager.setSurname(surname);
			manager.setEmail(email);
			manager.setPhone(phone);
			manager.setCompany(company);
			manager.setVat(vat);

			creditCard.setHolderName(holdername);
			creditCard.setBrandName(brandname);
			creditCard.setNumber(number);
			creditCard.setCvv(cvv);
			creditCard.setExpirationMonth(expirationMonth);
			creditCard.setExpirationYear(expirationYear);

			Assert.isTrue(isAgree);

			creditCard = this.creditCardService.saveRegister(creditCard);
			manager.setCreditCard(creditCard);
			this.managerService.save(manager);

			this.managerService.findAll();
			this.creditCardService.findAll();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//Con este test lo que hacemos es modificar el perfil del manager que esta logueado
	//Las pruebas que dan errores son porque la contraseña no es igual que la confirmacion de la contraseña,
	//intentamos loguearnos con un manager que no existe y ponemos un numero de tarjeta de credito no valida
	@Test
	public void driverEditarPerfilDeUnManager() {
		final Object testingData[][] = {
			{
				"manager1", "manager1", "manager1", "pepe", "fernandez", "pepe@gmail.com", "1254", "Sevilla S.A.", "SVQ", "Pepe", "VISA", "5760651824445570", 10, 2019, 300, null, null
			}, {
				"manager2", "manager2", "manager2", "pepa", "juana", "pepe@gmail.com", "5854", "Jaen S.A.", "JA", "Juana", "VISA", "5732718459670965", 11, 2018, 350, null, null
			}, {
				"manager3", "manager3", "manager3", "Antonio", "Ramos", "tonio@gmail.com", "1287", "Cadiz S.A.", "CA", "Juana", "VISA", "5732718459670965", 11, 2017, 345, null, null
			}, {
				"manager1", "manager150", "manager1", "Antonio", "Ramos", "tonio@gmail.com", "1287", "Cadiz S.A.", "CA", "Juana", "VISA", "5732718459670965", 11, 2017, 345, null, IllegalArgumentException.class
			}, {
				"managerNoExist", "managerNoExist", "managerNoExist", "Antonio", "Ramos", "tonio@gmail.com", "1287", "Cadiz S.A.", "CA", "Juana", "VISA", "5732718459670965", 11, 2017, 345, null, IllegalArgumentException.class
			}, {
				"manager2", "manager2", "manager2", "Antonio", "Ramos", "tonio@gmail.com", "1287", "Cadiz S.A.", "CA", "Juana", "VISA", "0002718459670960", 11, 2017, 345, null, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editarPerfilDeUnManager((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (int) testingData[i][12], (int) testingData[i][13], (int) testingData[i][14],
				(Boolean) testingData[i][15], (Class<?>) testingData[i][16]);
	}

	protected void editarPerfilDeUnManager(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final String phone, final String company, final String vat,
		final String holdername, final String brandname, final String number, final int expirationMonth, final int expirationYear, final int cvv, final Boolean isAgree, final Class<?> expected) {
		Class<?> caught;
		Md5PasswordEncoder encoder;
		String passwordEncoded;

		caught = null;
		try {
			this.authenticate(username);
			final Manager manager = this.managerService.findByPrincipal();
			CreditCard creditCard = manager.getCreditCard();

			Assert.isTrue(password.equals(confirmPassword));
			manager.getUserAccount().setUsername(username);
			encoder = new Md5PasswordEncoder();
			passwordEncoded = encoder.encodePassword(password, null);
			manager.getUserAccount().setPassword(passwordEncoded);

			manager.setName(name);
			manager.setSurname(surname);
			manager.setEmail(email);
			manager.setPhone(phone);
			manager.setCompany(company);
			manager.setVat(vat);

			creditCard.setHolderName(holdername);
			creditCard.setBrandName(brandname);
			creditCard.setNumber(number);
			creditCard.setCvv(cvv);
			creditCard.setExpirationMonth(expirationMonth);
			creditCard.setExpirationYear(expirationYear);

			creditCard = this.creditCardService.saveRegister(creditCard);
			manager.setCreditCard(creditCard);
			this.managerService.save(manager);

			this.managerService.findAll();
			this.creditCardService.findAll();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//Con este test comprobamos que nos logueamos correctamente
	@Test
	public void driverLoguearteComoManager() {
		final Object testingData[][] = {
			{
				"manager1", null
			}, {
				"manager2", null
			}, {
				"noName", IllegalArgumentException.class
			}, {
				"", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.loguearteComoManager((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void loguearteComoManager(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			final Manager manager = this.managerService.findByPrincipal();
			Assert.notNull(manager);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
