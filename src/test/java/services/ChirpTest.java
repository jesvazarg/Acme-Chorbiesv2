
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chirp;
import domain.Chorbi;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChirpTest extends AbstractTest {

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private ChirpService	chirpService;


	// Tests ------------------------------------------------------------------

	// REQUISITOS FUNCIONALES
	//Chirp to another chorbi.
	//Browse the list of chirps that he or she's got, and reply to any of them.
	//Browse the list of chirps that he or she's sent, and re-send any of them.
	//Erase any of the chirps that he or she's got or sent.

	//En este primer driver se comprueba que un chorbi pueda enviar un chirp a otro chorbi

	@Test
	public void driverEnvioDeChirpAChorbi() {
		final Object testingData[][] = {
			{
				"chorbi1", "Envio1", "text1", this.chorbiService.findOne(128), null
			}, {
				"chorbi1", "Envio2", "text2", this.chorbiService.findOne(129), null
			}, {
				"chorbi2", "Envio3", "text3", this.chorbiService.findOne(130), null
			}, {
				"", "Envio4", "text4", this.chorbiService.findOne(128), IllegalArgumentException.class
			}, {
				"admin", "Envio1", "text1", this.chorbiService.findOne(128), IllegalArgumentException.class
			}, {
				"chorbi1", "", "text1", this.chorbiService.findOne(128), ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.envioDeChirpAChorbi((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Chorbi) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void envioDeChirpAChorbi(final String sender, final String subject, final String text, final Chorbi recipient, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);
			final Chirp chirp = this.chirpService.create(recipient);

			chirp.setSubject(subject);
			chirp.setText(text);

			this.chirpService.save(chirp);

			this.chirpService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En este driver se comprueba que un chorbi puede responder un determinado chirp.

	@Test
	public void driverRespuestaDeChirp() {
		final Object testingData[][] = {
			{
				"chorbi1", "Envio1", "text1", 134, null
			}, {
				"chorbi1", "Envio4", "text4", 133, IllegalArgumentException.class
			}, {
				"admin", "Envio1", "text1", 134, IllegalArgumentException.class
			}, {
				"chorbi1", "", "text1", 134, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.respuestaDeChirp((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void respuestaDeChirp(final String sender, final String subject, final String text, final int chirp, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);
			final Chirp aux1 = this.chirpService.findOne(chirp);
			final Chirp aux2 = this.chirpService.reply(aux1);

			aux2.setSubject(subject);
			aux2.setText(text);

			this.chirpService.save(aux2);

			this.chirpService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En este driver se comprueba que un chorbi puede reenviar un chirp.

	@Test
	public void driverReenvioDeChirpAChorbi() {
		final Object testingData[][] = {
			{
				"chorbi1", "Envio1", "text1", this.chorbiService.findOne(130), 133, null
			}, {
				"chorbi1", "Envio2", "text2", this.chorbiService.findOne(131), 133, null
			}, {
				"chorbi1", "Envio4", "text4", this.chorbiService.findOne(130), 134, IllegalArgumentException.class
			}, {
				"admin", "Envio1", "text1", this.chorbiService.findOne(130), 133, IllegalArgumentException.class
			}, {
				"chorbi1", "", "text1", this.chorbiService.findOne(130), 133, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.reenvioDeChirpAChorbi((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Chorbi) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void reenvioDeChirpAChorbi(final String sender, final String subject, final String text, final Chorbi recipient, final int chirp, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);
			final Chirp aux1 = this.chirpService.findOne(chirp);
			final Chirp aux2 = this.chirpService.forward(aux1);

			aux2.setSubject(subject);
			aux2.setText(text);
			aux2.setRecipient(recipient);

			this.chirpService.save(aux2);

			this.chirpService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En este primer driver se comprueba que un chorbi puede borrar un chirp suyo.

	@Test
	public void driverBorrarhirp() {
		final Object testingData[][] = {
			{
				"chorbi1", 133, null
			}, {
				"", 133, IllegalArgumentException.class
			}, {
				"admin", 133, IllegalArgumentException.class
			}, {
				"chorbi5", 133, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.borrarChirp((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void borrarChirp(final String user, final int chirp, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);
			final Chirp aux = this.chirpService.findOne(chirp);
			this.chirpService.delete(aux);

			//this.chirpService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
