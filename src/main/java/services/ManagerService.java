
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chirp;
import domain.CreditCard;
import domain.Event;
import domain.Manager;
import forms.CreateManagerForm;

@Service
@Transactional
public class ManagerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ManagerRepository	managerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService		actorService;

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors------------------------------------------------------------
	public ManagerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Manager findOne(final int managerId) {
		Manager result;

		result = this.managerRepository.findOne(managerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Manager> findAll() {
		Collection<Manager> result;

		result = this.managerRepository.findAll();

		return result;
	}

	public Manager create() {
		Manager result;
		UserAccount userAccount;
		Authority authority;
		Collection<Chirp> sentChirps;
		Collection<Chirp> reciveChirps;
		Collection<Event> events;
		Double amount;

		userAccount = new UserAccount();
		authority = new Authority();
		sentChirps = new ArrayList<Chirp>();
		reciveChirps = new ArrayList<Chirp>();
		events = new ArrayList<Event>();
		amount = 0.0;

		authority.setAuthority(Authority.MANAGER);
		userAccount.addAuthority(authority);

		result = new Manager();
		result.setUserAccount(userAccount);
		result.setSentChirps(sentChirps);
		result.setReciveChirps(reciveChirps);
		result.setEvents(events);
		result.setAmount(amount);

		return result;
	}

	public Manager save(final Manager manager) {
		Assert.notNull(manager);
		Manager result;

		result = this.managerRepository.save(manager);
		return result;
	}

	// Other business methods -------------------------------------------------
	public Manager findByPrincipal() {
		Manager result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Manager findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Manager result;

		result = this.managerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Object[] reconstructProfile(final CreateManagerForm createManagerForm, final String type) {
		Assert.notNull(createManagerForm);
		Manager manager = null;
		Md5PasswordEncoder encoder;
		String password;
		final Object[] result = new Object[2];

		Assert.isTrue(createManagerForm.getPassword().equals(createManagerForm.getConfirmPassword()));

		manager = this.create();
		Assert.isTrue(createManagerForm.getIsAgree());
		final CreditCard creditCard = this.creditCardService.create();
		//		//Creo uno nuevo vacio para meterle los datos del formulario a dicho chorbi
		//		if (type.equals("create")) {
		//			manager = this.create();
		//			Assert.isTrue(createManagerForm.getIsAgree());
		//		} else if (type.equals("edit"))
		//			manager = this.findByPrincipal();

		password = createManagerForm.getPassword();

		encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);

		manager.getUserAccount().setUsername(createManagerForm.getUsername());
		manager.getUserAccount().setPassword(password);
		manager.setName(createManagerForm.getName());
		manager.setSurname(createManagerForm.getSurname());
		manager.setEmail(createManagerForm.getEmail());
		manager.setPhone(createManagerForm.getPhone());
		manager.setCompany(createManagerForm.getCompany());
		manager.setVat(createManagerForm.getVat());

		creditCard.setBrandName(createManagerForm.getBrandName());
		creditCard.setCvv(createManagerForm.getCvv());
		creditCard.setExpirationMonth(createManagerForm.getExpirationMonth());
		creditCard.setExpirationYear(createManagerForm.getExpirationYear());
		creditCard.setHolderName(createManagerForm.getHolderName());
		creditCard.setNumber(createManagerForm.getNumber());

		result[0] = creditCard;
		result[1] = manager;

		return result;
	}
	public CreateManagerForm constructProfile(final Manager manager) {
		Assert.notNull(manager);
		CreateManagerForm createManagerForm;

		createManagerForm = new CreateManagerForm();
		createManagerForm.setUsername(manager.getUserAccount().getUsername());
		createManagerForm.setPassword(manager.getUserAccount().getPassword());
		createManagerForm.setName(manager.getName());
		createManagerForm.setSurname(manager.getSurname());
		createManagerForm.setEmail(manager.getEmail());
		createManagerForm.setPhone(manager.getPhone());
		createManagerForm.setCompany(manager.getCompany());
		createManagerForm.setVat(manager.getVat());
		createManagerForm.setBrandName(manager.getCreditCard().getBrandName());
		createManagerForm.setCvv(manager.getCreditCard().getCvv());
		createManagerForm.setExpirationMonth(manager.getCreditCard().getExpirationMonth());
		createManagerForm.setExpirationYear(manager.getCreditCard().getExpirationYear());
		createManagerForm.setHolderName(manager.getCreditCard().getHolderName());
		createManagerForm.setNumber(manager.getCreditCard().getNumber());

		return createManagerForm;
	}

	//----------------- Dashboard 2.0 -----------------------------------
	public Collection<Manager> managesSortedEvents() {
		return this.managerRepository.managesSortedEvents();
	}

}
