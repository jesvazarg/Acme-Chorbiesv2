
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Chorbi;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ConfigurationRepository	configurationRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ChorbiService			chorbiService;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public ConfigurationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Configuration findOne(final int configurationId) {
		Assert.isTrue(configurationId != 0);
		Configuration configuration;
		configuration = this.configurationRepository.findOne(configurationId);
		Assert.notNull(configuration);
		return configuration;
	}

	public Collection<Configuration> findAll() {
		Collection<Configuration> result;
		result = this.configurationRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Configuration save(Configuration configuration) {
		Assert.notNull(configuration);
		Assert.notNull(this.administratorService.findByPrincipal());

		configuration = this.configurationRepository.save(configuration);

		return configuration;
	}

	// Other business methods -------------------------------------------------
	public Configuration findConfiguration() {
		final ArrayList<Configuration> aux = (ArrayList<Configuration>) this.configurationRepository.findAll();
		final Configuration configuration = aux.get(0);
		return configuration;
	}
	public Double horasCache() {
		Configuration configuration;
		String[] tiempo;
		Double result;
		configuration = this.findConfiguration();
		final String horas = configuration.getTime();
		tiempo = horas.split(":");
		result = Double.parseDouble(tiempo[0]) + (Double.parseDouble(tiempo[1]) / 60) + (Double.parseDouble(tiempo[2]) / 3600);
		return result;
	}

	public List<Integer> horasMinutosSegundosCache() {
		final List<Integer> result = new ArrayList<Integer>();
		Configuration configuration;
		String[] tiempo;
		configuration = this.findConfiguration();
		final String horas = configuration.getTime();
		tiempo = horas.split(":");
		result.add(Integer.parseInt(tiempo[0]));//Horas
		result.add(Integer.parseInt(tiempo[1]));//Minutos
		result.add(Integer.parseInt(tiempo[2]));//Segundos
		return result;

	}

	public void cobrarAChorbies() {
		Collection<Chorbi> chorbies;
		Assert.notNull(this.administratorService.findByPrincipal());
		chorbies = this.chorbiService.findAll();
		final Configuration confAux = this.findConfiguration();
		final Calendar fechaSistema = Calendar.getInstance();
		final Calendar chorbiMoment = Calendar.getInstance();

		for (final Chorbi chor : chorbies) {
			final Date date = chor.getMoment();
			chorbiMoment.setTime(date);

			final Integer auxD1 = fechaSistema.get(Calendar.DAY_OF_MONTH);
			final Integer auxD2 = chorbiMoment.get(Calendar.DAY_OF_MONTH);

			final Integer auxM1 = fechaSistema.get(Calendar.MONTH);
			final Integer auxM2 = chorbiMoment.get(Calendar.MONTH);

			final Integer auxY1 = fechaSistema.get(Calendar.YEAR);
			final Integer auxY2 = chorbiMoment.get(Calendar.YEAR);

			if (auxY1 > auxY2) {
				final Double am = chor.getAmount() + confAux.getFeeChorbi();
				chor.setAmount(am);

				final Calendar cl = Calendar.getInstance();
				cl.setTime(chor.getMoment());
				cl.add(Calendar.DAY_OF_YEAR, 30);
				chor.setMoment(cl.getTime());
				this.chorbiService.save(chor);

			} else if (auxY1.compareTo(auxY2) == 0)
				if (auxM1.compareTo(auxM2) > 0 && auxD1.compareTo(auxD2) > 0) {
					final Double am = chor.getAmount() + confAux.getFeeChorbi();
					chor.setAmount(am);

					final Calendar cl = Calendar.getInstance();
					cl.setTime(chor.getMoment());
					cl.add(Calendar.DAY_OF_YEAR, 30);
					chor.setMoment(cl.getTime());
					this.chorbiService.save(chor);
				}

		}
	}

}
