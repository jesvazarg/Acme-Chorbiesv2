
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Banner;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BannerTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private BannerService	bannerService;


	// Tests ------------------------------------------------------------------

	// REQUISITOS FUNCIONALES
	//Change the banners that are displayed on the welcome page

	//En este primer driver se comprueba que podemos añadir nuevos banners

	@Test
	public void driverAñadirBanner() {
		final Object testingData[][] = {
			{
				"admin", "https://www.mozilla.org/media/img/firefox/firefox-256.e2c1fc556816.jpg", null
			}, {
				"admin", "firefox", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.añadirBanner((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void añadirBanner(final String user, final String picture, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);
			final Banner banner = this.bannerService.create();
			banner.setPicture(picture);

			this.bannerService.save(banner);

			this.bannerService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En ester driver se comprueba que podemos eliminar un banner.

	@Test
	public void driverBorrarBanner() {
		final Object testingData[][] = {
			{
				"admin", 257, null
			}, {
				"admin", 130, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.borrarBanner((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void borrarBanner(final String user, final int bannerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);
			final Banner banner = this.bannerService.findOne(bannerId);

			this.bannerService.delete(banner);

			this.bannerService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//En este driver se comprueba que podemos editar un banner.

	@Test
	public void driverEditarBanner() {
		final Object testingData[][] = {
			{
				"admin", 257, "https://www.mozilla.org/media/img/firefox/firefox-256.e2c1fc556816.jpg", null
			}, {
				"admin", 257, "firefox", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editarBanner((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void editarBanner(final String user, final int bannerId, final String picture, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(user);
			final Banner banner = this.bannerService.findOne(bannerId);
			banner.setPicture(picture);

			this.bannerService.save(banner);

			this.bannerService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
