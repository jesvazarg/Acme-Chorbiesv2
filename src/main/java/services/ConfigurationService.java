
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ConfigurationRepository	configurationRepository;

	@Autowired
	private AdministratorService	administratorService;


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

	//	public String RandomBanner() {
	//		Configuration aux;
	//		aux = this.findConfiguration();
	//		final List<String> banners = new ArrayList<String>(aux.getBanners());
	//		final Random randomNumber = new Random();
	//		final Integer i = randomNumber.nextInt(banners.size());
	//		//System.out.print(banners.get(i));
	//		return banners.get(i);
	//	}

}
