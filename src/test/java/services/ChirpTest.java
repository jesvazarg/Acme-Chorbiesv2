
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Actor;
import domain.Chirp;
import domain.Event;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChirpTest extends AbstractTest {

	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private EventService	eventService;


	// Tests ------------------------------------------------------------------

	// REQUISITOS FUNCIONALES
	//Chirp to another actor.
	//Browse the list of chirps that he or she's got, and reply to any of them.
	//Browse the list of chirps that he or she's sent, and re-send any of them.
	//Erase any of the chirps that he or she's got or sent.

	//En este primer driver se comprueba que un actor pueda enviar un chirp a otro actor

	@Test
	public void driverEnvioDeChirpAChorbi() {
		final Object testingData[][] = {
			{
				"chorbi1", "Envio1", "text1", this.actorService.findOne(274), null
			}, {
				"chorbi1", "Envio2", "text2", this.actorService.findOne(270), null
			}, {
				"manager2", "Envio3", "text3", this.actorService.findOne(276), null
			}, {
				"", "Envio4", "text4", this.actorService.findOne(274), IllegalArgumentException.class
			}, {
				"chorbi1", "Envio1", "", this.actorService.findOne(273), ConstraintViolationException.class
			}, {
				"chorbi1", "", "text1", this.actorService.findOne(270), ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.envioDeChirpAChorbi((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Actor) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void envioDeChirpAChorbi(final String sender, final String subject, final String text, final Actor recipient, final Class<?> expected) {
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

	//En este driver se comprueba que un actor puede responder un determinado actor.

	@Test
	public void driverRespuestaDeChirp() {
		final Object testingData[][] = {
			{
				"chorbi1", "Envio1", "text1", 287, null
			}, {
				"manager1", "Envio4", "text4", 279, IllegalArgumentException.class
			}, {
				"chorbi1", "Envio1", "", 287, ConstraintViolationException.class
			}, {
				"chorbi1", "Envio2", "text1", 280, IllegalArgumentException.class
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

	//En este driver se comprueba que un actor puede reenviar un chirp.

	@Test
	public void driverReenvioDeChirpAChorbi() {
		final Object testingData[][] = {
			{
				"chorbi1", "Envio1", "text1", this.actorService.findOne(277), 279, null
			}, {
				"chorbi2", "Envio2", "text2", this.actorService.findOne(273), 280, null
			}, {
				"chorbi1", "Envio4", "text4", this.actorService.findOne(276), 280, IllegalArgumentException.class
			}, {
				"chorbi1", "", "text1", this.actorService.findOne(275), 279, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.reenvioDeChirpAChorbi((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Actor) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void reenvioDeChirpAChorbi(final String sender, final String subject, final String text, final Actor recipient, final int chirp, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);
			final Chirp aux1 = this.chirpService.findOne(chirp);
			final Chirp aux2 = this.chirpService.forward(aux1);

			aux2.setSubject(subject);
			aux2.setText(text);
			final Collection<Actor> recipientAux = new HashSet<Actor>();
			recipientAux.add(recipient);
			aux2.setRecipients(recipientAux);

			this.chirpService.save(aux2);

			this.chirpService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En este driver se comprueba que un actor puede borrar un chirp suyo.

	@Test
	public void driverBorrarhirp() {
		final Object testingData[][] = {
			{
				"chorbi1", 279, null
			}, {
				"", 279, IllegalArgumentException.class
			}, {
				"admin", 280, IllegalArgumentException.class
			}, {
				"manager1", 281, IllegalArgumentException.class
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

	//En este driver se comprueba que un manager puede enviar un broadcast.

	@Test
	public void driverEnvioDeBroadcastAChorbi() {
		final Object testingData[][] = {
			{
				"manager1", "broadcastTitle1", "text1", this.eventService.findOne(297), null
			}, {
				"", "broadcastTitle2", "text4", this.eventService.findOne(297), IllegalArgumentException.class
			}, {
				"manager1", "broadcastTitle3", "", this.eventService.findOne(297), ConstraintViolationException.class
			}, {
				"manager1", "broadcastTitle4", "text2", this.eventService.findOne(300), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.envioDeBroadcastAChorbi((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Event) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void envioDeBroadcastAChorbi(final String sender, final String subject, final String text, final Event event, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(sender);

			final Chirp chirp = this.chirpService.broadcast(event);

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
}
