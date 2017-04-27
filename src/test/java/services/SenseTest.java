
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.Sense;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SenseTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private SenseService	senseService;

	@Autowired
	private ChorbiService	chorbiService;


	// Tests ------------------------------------------------------------------
	// FUNCTIONAL REQUIREMENTS
	// Like another chorbi; a like may be cancelled at any time.

	// Like a un chorbi
	@Test
	public void driverCreateSense() {
		final Object testingData[][] = {
			{
				"chorbi1", 0, "Hola holita vecinito", 278, null
			}, {
				"chorbi2", 1, null, 278, null
			}, {
				"chorbi1", 2, null, 273, IllegalArgumentException.class
			}, {
				"chorbi1", 3, null, 274, IllegalArgumentException.class
			}, {
				"chorbi3", 4, null, 273, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createSense((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void createSense(final String sender, final Integer stars, final String comment, final Integer recipient, final Class<?> expected) {

		Class<?> caught = null;
		Chorbi chorbi = null;
		Sense sense = null;

		try {
			this.authenticate(sender);
			chorbi = this.chorbiService.findOne(recipient);

			sense = this.senseService.create(chorbi);
			sense.setStars(stars);
			sense.setComment(comment);
			this.senseService.save(sense);

			this.senseService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	// Disike a un chorbi
	@Test
	public void driverDeleteSense() {
		final Object testingData[][] = {
			{
				"chorbi1", 274, null
			}, {
				"chorbi2", 276, null
			}, {
				"chorbi1", 273, IllegalArgumentException.class
			}, {
				"chorbi1", 277, IllegalArgumentException.class
			}, {
				"manager1", 273, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length - 1; i++)
			this.deleteSense((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void deleteSense(final String sender, final Integer recipient, final Class<?> expected) {

		Class<?> caught = null;
		Chorbi chorbi = null;
		Sense sense = null;

		try {
			this.authenticate(sender);
			chorbi = this.chorbiService.findOne(recipient);

			sense = this.senseService.findSense(this.chorbiService.findByPrincipal(), chorbi);
			this.senseService.delete(sense);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
