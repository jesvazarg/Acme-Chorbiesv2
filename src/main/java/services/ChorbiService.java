
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chirp;
import domain.Chorbi;
import domain.Coordinate;
import domain.Sense;
import forms.CreateChorbiForm;

@Service
@Transactional
public class ChorbiService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ChorbiRepository	chorbiRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService		actorService;


	// Constructors------------------------------------------------------------
	public ChorbiService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Chorbi findOne(final int chorbiId) {
		Chorbi result;

		result = this.chorbiRepository.findOne(chorbiId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Chorbi> findAll() {
		Collection<Chorbi> result;

		result = this.chorbiRepository.findAll();

		return result;
	}

	public Chorbi create() {
		Chorbi result;
		UserAccount userAccount;
		Authority authority;
		Collection<Sense> giveSenses;
		Collection<Sense> reciveSenses;
		Collection<Chirp> sentChirps;
		Collection<Chirp> reciveChirps;

		userAccount = new UserAccount();
		authority = new Authority();
		giveSenses = new ArrayList<Sense>();
		reciveSenses = new ArrayList<Sense>();
		sentChirps = new ArrayList<Chirp>();
		reciveChirps = new ArrayList<Chirp>();

		authority.setAuthority(Authority.CHORBI);
		userAccount.addAuthority(authority);

		result = new Chorbi();
		result.setUserAccount(userAccount);
		result.setGiveSenses(giveSenses);
		result.setReciveSenses(reciveSenses);
		result.setSentChirps(sentChirps);
		result.setReciveChirps(reciveChirps);
		result.setAmount(0.0);

		return result;
	}

	@SuppressWarnings("deprecation")
	public Chorbi save(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		Chorbi result;
		Date nuevo;

		nuevo = Calendar.getInstance().getTime();
		nuevo.setYear(nuevo.getYear() - 18);
		Assert.isTrue(chorbi.getBirthDate().before(nuevo));

		result = this.chorbiRepository.save(chorbi);
		return result;
	}

	// Other business methods -------------------------------------------------
	public Chorbi findByPrincipal() {
		Chorbi result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Chorbi findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Chorbi result;

		result = this.chorbiRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Chorbi> findAllNotBanned() {
		Collection<Chorbi> allChorbies;
		final Collection<Chorbi> result = new ArrayList<Chorbi>();
		final Chorbi principal = this.findByPrincipal();

		allChorbies = this.findAll();
		for (final Chorbi c : allChorbies)
			if (this.actorService.checkAuthority(c, Authority.CHORBI) && principal.getId() != c.getId())
				result.add(c);
		return result;
	}

	public Collection<Chorbi> filterNotBanned(final Collection<Chorbi> chorbies, final Chorbi chorbi) {
		final Collection<Chorbi> result = new ArrayList<Chorbi>();

		for (final Chorbi c : chorbies)
			if (this.actorService.checkAuthority(c, Authority.CHORBI) && chorbi.getId() != c.getId())
				result.add(c);
		return result;
	}

	@SuppressWarnings("deprecation")
	public Integer edadChorbi(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		Integer result;
		Date calendarHoy;
		Date cumple;

		cumple = chorbi.getBirthDate();
		calendarHoy = Calendar.getInstance().getTime();

		result = calendarHoy.getYear() - cumple.getYear();
		return result;
	}

	public Chorbi reconstructProfile(final CreateChorbiForm createChorbiForm, final String type) {
		Assert.notNull(createChorbiForm);
		Chorbi chorbi = null;
		Md5PasswordEncoder encoder;
		String password;
		Coordinate coordinate;

		Assert.isTrue(createChorbiForm.getPassword().equals(createChorbiForm.getConfirmPassword()));

		//Creo uno nuevo vacio para meterle los datos del formulario a dicho chorbi
		if (type.equals("create")) {
			chorbi = this.create();
			Assert.isTrue(createChorbiForm.getIsAgree());
		} else if (type.equals("edit"))
			chorbi = this.findByPrincipal();

		password = createChorbiForm.getPassword();

		encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);

		chorbi.getUserAccount().setUsername(createChorbiForm.getUsername());
		chorbi.getUserAccount().setPassword(password);
		chorbi.setName(createChorbiForm.getName());
		chorbi.setSurname(createChorbiForm.getSurname());
		chorbi.setEmail(createChorbiForm.getEmail());
		chorbi.setPhone(createChorbiForm.getPhone());
		chorbi.setPicture(createChorbiForm.getPicture());
		chorbi.setDescription(createChorbiForm.getDescription());
		chorbi.setRelationship(createChorbiForm.getRelationship());
		chorbi.setBirthDate(createChorbiForm.getBirthDate());
		chorbi.setGenre(createChorbiForm.getGenre());
		coordinate = new Coordinate();
		coordinate.setCity(createChorbiForm.getCity());
		coordinate.setCountry(createChorbiForm.getCountry());
		coordinate.setState(createChorbiForm.getState());
		coordinate.setProvince(createChorbiForm.getProvince());
		chorbi.setCoordinate(coordinate);

		return chorbi;
	}
	public CreateChorbiForm constructProfile(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		CreateChorbiForm createChorbiForm;

		createChorbiForm = new CreateChorbiForm();
		createChorbiForm.setUsername(chorbi.getUserAccount().getUsername());
		createChorbiForm.setPassword(chorbi.getUserAccount().getPassword());
		createChorbiForm.setName(chorbi.getName());
		createChorbiForm.setSurname(chorbi.getSurname());
		createChorbiForm.setEmail(chorbi.getEmail());
		createChorbiForm.setPhone(chorbi.getPhone());
		createChorbiForm.setPicture(chorbi.getPicture());
		createChorbiForm.setDescription(chorbi.getDescription());
		createChorbiForm.setRelationship(chorbi.getRelationship());
		createChorbiForm.setBirthDate(chorbi.getBirthDate());
		createChorbiForm.setGenre(chorbi.getGenre());
		createChorbiForm.setCity(chorbi.getCoordinate().getCity());
		createChorbiForm.setCountry(chorbi.getCoordinate().getCountry());
		createChorbiForm.setState(chorbi.getCoordinate().getState());
		createChorbiForm.setProvince(chorbi.getCoordinate().getProvince());

		return createChorbiForm;
	}

	//C1: A listing with the number of chorbies per country and city.
	public Collection<Object[]> numberChorbiPerCountryAndCity() {
		final Collection<Object[]> query = this.chorbiRepository.numberChorbiPerCountryAndCity();
		return query;
	}

	//C4: The ratios of chorbies who search for activities, friendship, and love.
	public Double ratioPerChorbiAndSearchTemplateRelationship() {
		final Double result = this.chorbiRepository.ratioPerChorbiAndSearchTemplateRelationship();
		return result;
	}

	//B1: The list of chorbies, sorted by the number of likes they have got.
	public Collection<Chorbi> chorbiesSortedGotLikes() {
		final Collection<Chorbi> result = this.chorbiRepository.chorbiesSortedGotLikes();
		return result;
	}

	public Double[] minMaxAvgReciveChirps() {
		return this.chorbiRepository.minMaxAvgReciveChirps();
	}

	public Collection<Chorbi> findChorbiMoreReciveChirps() {
		return this.chorbiRepository.findChorbiMoreReciveChirps();
	}

	public Collection<Chorbi> findChorbiMoreSentChirps() {
		return this.chorbiRepository.findChorbiMoreSentChirps();
	}

	public Double ratioChorbiesWithNullOrInvalidCreditcard() {
		final Double res1 = this.chorbiRepository.ratioNotRegisteredCreditcardPerChorbi();
		final Double res2 = this.chorbiRepository.numberChorbiesWithInvalidCreditMonth();
		final Double res3 = this.chorbiRepository.numberChorbiesWithInvalidCreditYear();
		final Integer totalChorbies = this.chorbiRepository.findAll().size();
		final Double result = (res3 / totalChorbies) + (res2 / totalChorbies) + res1;
		return result;
	}

	public Double[] minMaxAvgAgeOfChorbi2() {
		final Double[] result = this.chorbiRepository.minMaxAvgAgeOfChorbi2();
		return result;
	}

	//----------------- Dashboard 2.0 -----------------------------------
	public String[] minMaxAvgStarsPerChorbi() {
		final Integer[] arrayMin = this.chorbiRepository.minStarsPerChorbi();
		final Integer[] arrayMax = this.chorbiRepository.maxStarsPerChorbi();
		final Double avg = this.chorbiRepository.avgStarsPerChorbi();
		final String[] result = {
			arrayMin[0].toString(), arrayMax[0].toString(), avg.toString()
		};
		return result;
	}

	public Collection<Chorbi> chorbiesOrderByEventRegistered() {
		return this.chorbiRepository.chorbiesOrderByEventRegistered();
	}

	public Collection<Object[]> chorbiesAmountDueFee() {
		return this.chorbiRepository.chorbiesAmountDueFee();
	}

	public Collection<Chorbi> chorbiesSortedByAvgNumberOfStars() {
		return this.chorbiRepository.chorbiesSortedByAvgNumberOfStars();
	}

}
