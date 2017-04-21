
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chirp;
import domain.Event;
import domain.Manager;

@Service
@Transactional
public class ManagerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ManagerRepository	managerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService		actorService;


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

		userAccount = new UserAccount();
		authority = new Authority();
		sentChirps = new ArrayList<Chirp>();
		reciveChirps = new ArrayList<Chirp>();
		events = new ArrayList<Event>();

		authority.setAuthority(Authority.MANAGER);
		userAccount.addAuthority(authority);

		result = new Manager();
		result.setUserAccount(userAccount);
		result.setSentChirps(sentChirps);
		result.setReciveChirps(reciveChirps);
		result.setEvents(events);

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

}
