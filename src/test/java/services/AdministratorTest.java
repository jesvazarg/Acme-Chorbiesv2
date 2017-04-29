
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.Configuration;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private ConfigurationService	configurationService;


	// Tests ------------------------------------------------------------------

	// REQUISITOS FUNCIONALES
	// Ban a chorbi, that is, to disable his or her account.
	// Unban a chorbi, which means that his or her account is re-enabled.

	//En este primer driver vamos a banear a un usuario.
	@Test
	public void driverBanearChorbi() {
		final Object testingData[][] = {
			{
				"admin", 273, null
			}, {
				"admin", 274, null
			}, {
				"", 273, IllegalArgumentException.class
			}, {
				"chorbi4", 274, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.banearUsuario((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void banearUsuario(final String user, final int idChorbi, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);

			final Chorbi chorbi = this.chorbiService.findOne(idChorbi);
			this.administratorService.banChorbi(chorbi);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En este test vamos a quitarle el baneo a un chorbi

	@Test
	public void driverQuitarBaneoChorbi() {
		final Object testingData[][] = {
			{
				"admin", 273, null
			}, {
				"admin", 274, null
			}, {
				"", 273, IllegalArgumentException.class
			}, {
				"chorbi4", 274, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.quitarBaneoUsuario((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void quitarBaneoUsuario(final String user, final int idChorbi, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);

			final Chorbi chorbi = this.chorbiService.findOne(idChorbi);
			this.administratorService.desBanChorbi(chorbi);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// REQUISITOS FUNCIONALES
	// Change the time that the results of search templates are cached. 
	// The time must be expressed in hours, minutes, and seconds.

	//En este test comprobaremos el caso de uso en el cual el administrador
	//cambia la hora que se mantiene una busqueda del SearchTemplate en cache

	@Test
	public void driverCambiarCache() {
		final Object testingData[][] = {
			{
				"admin", "12:00:00", null
			}, {
				"admin", "10:00:00", null
			}, {
				"", "", IllegalArgumentException.class
			}, {
				"chorbi4", "", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.cambiarCache((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void cambiarCache(final String user, final String hora, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);

			final Configuration configuration = this.configurationService.findConfiguration();
			configuration.setTime(hora);
			this.configurationService.save(configuration);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
