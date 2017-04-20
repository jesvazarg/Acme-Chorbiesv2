
package services;

import javax.transaction.Transactional;

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

	//
	@Test
	public void driverCreateAndDeleteSense() {
		final Object testingData[][] = {
			{
				"chorbi1", "Hola holita vecinito", 132, null
			}, {
				"chorbi2", null, 132, null
			}, {
				"chorbi1", null, 127, IllegalArgumentException.class
			}, {
				"chorbi1", null, 128, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createAndDeleteSense((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3], "create");

		testingData[0][2] = 128;
		testingData[1][2] = 130;
		testingData[3][2] = 131;
		for (int i = 0; i < testingData.length; i++)
			this.createAndDeleteSense((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3], "delete");
	}
	protected void createAndDeleteSense(final String sender, final String comment, final Integer recipient, final Class<?> expected, final String createOrDelete) {

		Class<?> caught = null;
		Chorbi chorbi = null;
		Sense sense = null;

		try {
			this.authenticate(sender);
			chorbi = this.chorbiService.findOne(recipient);

			if (createOrDelete.equals("create")) {
				sense = this.senseService.create(chorbi);
				sense.setComment(comment);
				this.senseService.save(sense);
			} else if (createOrDelete.equals("delete")) {
				sense = this.senseService.findSense(this.chorbiService.findByPrincipal(), chorbi);
				this.senseService.delete(sense);
			}

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
