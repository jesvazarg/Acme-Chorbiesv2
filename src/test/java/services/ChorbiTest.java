
package services;

import java.util.Calendar;

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
import domain.Chorbi;
import domain.Coordinate;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChorbiTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	// Register to the system as a chorbi. As of the time of registering, a chorbi is not re-quired to provide a credit card.
	// 		No person under 18 is allowed to register to the system.
	// Login to the system using his or her credentials.
	// Change his or her profile.

	//Registrar un nuevo chorbi o editar el perfil de un chorbi
	@Test
	public void driverRegisterAndEditChorbi() {
		final Object testingData[][] = {
			{
				"chorbi10", "chorbi10", "chorbi10", "Pepe", "Botella", "pepe@gmail.com", "1234", "http://web.com/imagen.jpg", "soy yo", "Love", "01/01/1990", "Man", "Lepe", "España", "Andalucia", "Huelva", true, null
			},
			{
				"chorbi11", "chorbi11", "chorbi11", "Sancho", "Panza", "sancho@gmail.com", "2345", "http://web.com/imagen.jpg", "eres tu", "Friendship", "25/12/1969", "Man", "Cuenca", null, null, null, true, null
			},
			{
				"chorbi12", "chorbi12", "chorbi12", "Juana", "La Loca", "juana@gmail.com", "3456", "http://web.com/imagen.jpg", "es él", "Love", "15/05/1974", "Woman", "Salamanca", null, null, null, false, IllegalArgumentException.class
			},
			{
				"chorbi13", "chorbi13", "chrb", "Stewie", "Grifin", "stewie@gmail.com", "4567", "http://web.com/imagen.jpg", "es ella", "Friendship", "02/03/1992", "Woman", "Santander", "España", null, null, true, IllegalArgumentException.class
			},
			{
				"chorbi15", "chorbi15", "chorbi15", "Paquito", "Chocolatero", "paquito.com", "6789", "http://web.com/imagen.jpg", "sois vosotros", "Friendship", "06/06/2011", "Man", "Dos Hermanas", null, "Andalucia", "Sevilla", true,
				IllegalArgumentException.class
			}, {
				"chorbi14", "chorbi14", "chorbi14", "", "Simpson", "bart@gmail.com", "5678", "http://web.com/imagen.jpg", "somos nosotros", "Love", "14/02/1965", "Man", "San Fernando", null, null, "Cádiz", true, ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length - 1; i++)
			this.registerAndEditChorbi((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (String) testingData[i][14],
				(String) testingData[i][15], (Boolean) testingData[i][16], (Class<?>) testingData[i][17], "register");

		testingData[2][17] = null;
		for (int i = 0; i < testingData.length; i++)
			this.registerAndEditChorbi((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (String) testingData[i][14],
				(String) testingData[i][15], (Boolean) testingData[i][16], (Class<?>) testingData[i][17], "edit");
	}

	protected void registerAndEditChorbi(final String username, final String password, final String confirmPassword, final String name, final String surname, final String email, final String phone, final String picture, final String description,
		final String relationship, final String birthDate, final String genre, final String city, final String country, final String state, final String province, final Boolean isAgree, final Class<?> expected, final String registerOrEdit) {

		Class<?> caught = null;
		Md5PasswordEncoder encoder;
		String passwordEncoded;
		final Calendar calendar = Calendar.getInstance();
		String[] fecha;
		final Coordinate coordinate = new Coordinate();

		try {
			Chorbi chorbi = null;
			if (registerOrEdit.equals("register")) {
				chorbi = this.chorbiService.create();
				chorbi.getUserAccount().setUsername(username);
			} else if (registerOrEdit.equals("edit"))
				chorbi = this.chorbiService.findOne(127);

			Assert.isTrue(password.equals(confirmPassword));
			encoder = new Md5PasswordEncoder();
			passwordEncoded = encoder.encodePassword(password, null);
			chorbi.getUserAccount().setPassword(passwordEncoded);

			chorbi.setName(name);
			chorbi.setSurname(surname);
			chorbi.setEmail(email);
			chorbi.setPhone(phone);
			chorbi.setPicture(picture);
			chorbi.setDescription(description);
			chorbi.setRelationship(relationship);

			fecha = birthDate.split("/");
			calendar.set(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
			chorbi.setBirthDate(calendar.getTime());

			chorbi.setGenre(genre);
			coordinate.setCity(city);
			coordinate.setCountry(country);
			coordinate.setState(state);
			coordinate.setProvince(province);
			chorbi.setCoordinate(coordinate);
			if (registerOrEdit.equals("register"))
				Assert.isTrue(isAgree);

			this.chorbiService.save(chorbi);
			this.chorbiService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
