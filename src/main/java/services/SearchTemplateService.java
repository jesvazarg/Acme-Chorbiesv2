
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

import repositories.SearchTemplateRepository;
import domain.Chorbi;
import domain.SearchTemplate;

@Service
@Transactional
public class SearchTemplateService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SearchTemplateRepository	searchTemplateRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ChorbiService				chorbiService;

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private CreditCardService			creditCardService;


	// Constructors -----------------------------------------------------------
	public SearchTemplateService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public SearchTemplate findOne(final int searchTemplateId) {
		Assert.isTrue(searchTemplateId != 0);
		Assert.isTrue(this.chorbiService.findByPrincipal().getSearchTemplate().getId() == searchTemplateId);
		SearchTemplate result;

		result = this.searchTemplateRepository.findOne(searchTemplateId);

		return result;
	}

	public Collection<SearchTemplate> findAll() {
		Collection<SearchTemplate> result;

		result = this.searchTemplateRepository.findAll();

		return result;
	}

	public SearchTemplate create() {
		SearchTemplate result;
		result = new SearchTemplate();
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		Assert.notNull(chorbi);

		Calendar calendar;
		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);
		result.setUpdateMoment(calendar.getTime());

		final Collection<Chorbi> chorbies = new ArrayList<Chorbi>();
		result.setResults(chorbies);

		return result;
	}

	public SearchTemplate save(final SearchTemplate searchTemplate) {
		Assert.notNull(searchTemplate, "SearchTemplate es null");
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		Assert.notNull(chorbi.getCreditCard());
		this.creditCardService.checkCreditCard(chorbi.getCreditCard());
		if (searchTemplate.getId() != 0)
			Assert.isTrue(searchTemplate.getId() == chorbi.getSearchTemplate().getId(), "No es el dueño");
		final Collection<Chorbi> empty = new ArrayList<Chorbi>();
		searchTemplate.setResults(empty);
		//searchTemplate.getResults().clear();

		SearchTemplate result;
		result = this.searchTemplateRepository.save(searchTemplate);

		chorbi.setSearchTemplate(result);

		//if (chorbi.getSearchTemplate() == null) {

		this.chorbiService.save(chorbi);
		//}

		return result;
	}

	public SearchTemplate saveWithoutClearResults(final SearchTemplate searchTemplate) {
		Assert.notNull(searchTemplate, "search is null");
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		Assert.notNull(chorbi.getCreditCard());
		this.creditCardService.checkCreditCard(chorbi.getCreditCard());
		if (searchTemplate.getId() != 0)
			Assert.isTrue(chorbi.getSearchTemplate().equals(searchTemplate), "id no es igual");

		SearchTemplate result;
		result = this.searchTemplateRepository.save(searchTemplate);
		chorbi.setSearchTemplate(result);

		//if (chorbi.getSearchTemplate() == null) {

		this.chorbiService.save(chorbi);
		//}

		return result;
	}

	public void delete(final SearchTemplate searchTemplate) {
		Assert.notNull(searchTemplate);
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		Assert.isTrue(chorbi.getSearchTemplate().equals(searchTemplate));

		chorbi.setSearchTemplate(null);
		this.searchTemplateRepository.delete(searchTemplate);
	}

	// Other business methods -------------------------------------------------

	@SuppressWarnings("deprecation")
	public Collection<Chorbi> findBySearchResult(SearchTemplate searchTemplate) {
		Assert.notNull(searchTemplate);
		Assert.notNull(this.chorbiService.findByPrincipal());
		Collection<Chorbi> chorbies;
		Collection<Chorbi> result = new ArrayList<Chorbi>();
		Date calendarDate;
		Calendar calendar;
		List<Integer> horasMinSeg = new ArrayList<Integer>();
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		Assert.notNull(chorbi.getCreditCard());

		horasMinSeg = this.configurationService.horasMinutosSegundosCache();

		calendarDate = Calendar.getInstance().getTime();
		calendarDate.setHours(calendarDate.getHours() - horasMinSeg.get(0));
		calendarDate.setMinutes(calendarDate.getMinutes() - horasMinSeg.get(1));
		calendarDate.setSeconds(calendarDate.getSeconds() - horasMinSeg.get(2));

		if (calendarDate.before(searchTemplate.getUpdateMoment()) && !searchTemplate.getResults().isEmpty())
			result = searchTemplate.getResults();
		else {
			calendar = Calendar.getInstance();
			calendar.set(Calendar.MILLISECOND, -10);
			chorbies = this.chorbiService.findAll();
			for (final Chorbi c : chorbies) {
				Boolean aux = true;

				if (!searchTemplate.getRelationship().equals(""))
					aux = aux && (c.getRelationship().equals(searchTemplate.getRelationship()));

				if (searchTemplate.getAge() != null)
					aux = aux && (((searchTemplate.getAge() - 5) <= this.chorbiService.edadChorbi(c)) && ((searchTemplate.getAge() + 5) >= this.chorbiService.edadChorbi(c)));

				if (!searchTemplate.getGenre().equals(""))
					aux = aux && (c.getGenre().equals(searchTemplate.getGenre()));

				if (!searchTemplate.getCoordinate().getCity().equals(""))
					aux = aux && (c.getCoordinate().getCity().equals(searchTemplate.getCoordinate().getCity()));

				if (!searchTemplate.getCoordinate().getCountry().equals(""))
					aux = aux && (c.getCoordinate().getCountry().equals(searchTemplate.getCoordinate().getCountry()));

				if (!searchTemplate.getCoordinate().getState().equals(""))
					aux = aux && (c.getCoordinate().getState().equals(searchTemplate.getCoordinate().getState()));

				if (!searchTemplate.getCoordinate().getProvince().equals(""))
					aux = aux && (c.getCoordinate().getProvince().equals(searchTemplate.getCoordinate().getProvince()));

				if (!searchTemplate.getKeyword().isEmpty())
					aux = aux && (c.getDescription().contains(searchTemplate.getKeyword()));

				if (aux)
					result.add(c);
			}

			searchTemplate.setUpdateMoment(calendar.getTime());
			searchTemplate.setResults(result);

			searchTemplate = this.saveWithoutClearResults(searchTemplate);
		}
		return result;

	}
	public SearchTemplate mySearch() {
		SearchTemplate result;
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		result = chorbi.getSearchTemplate();
		return result;
	}

}
