
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ActorRepository	actorRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Actor findOne(final int actorId) {
		Actor actor;

		actor = this.actorRepository.findOne(actorId);
		Assert.notNull(actor);

		return actor;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();

		return result;
	}

	public Actor save(Actor actor) {
		Assert.notNull(actor);

		Actor aux;
		Md5PasswordEncoder encoder;
		String hash;
		UserAccount userAccount;

		userAccount = actor.getUserAccount();
		Assert.notNull(userAccount.getUsername());
		Assert.notNull(userAccount.getPassword());

		aux = this.findOne(actor.getId());

		if (!(aux.getUserAccount().getPassword().equals(userAccount.getPassword()))) {
			encoder = new Md5PasswordEncoder();
			hash = encoder.encodePassword(actor.getUserAccount().getPassword(), null);

			actor.getUserAccount().setPassword(hash);
		}

		actor = this.actorRepository.save(actor);

		return actor;
	}

	// Other business methods -------------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Actor findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Actor result;

		result = this.actorRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Boolean checkAuthority(final Actor actor, final String authority) {
		Boolean result;
		Collection<Authority> authorities;

		authorities = actor.getUserAccount().getAuthorities();

		result = false;
		for (final Authority a : authorities)
			result = result || (a.getAuthority().equals(authority));

		return result;
	}

	public void updateProfile(final Actor a) {
		final Actor actor = this.findByPrincipal();
		actor.setName(a.getName());
		actor.setSurname(a.getName());
		actor.setEmail(a.getEmail());
		actor.setPhone(a.getPhone());
		this.actorRepository.save(actor);
	}

	/*
	 * public Actor reconstructProfile(final CreateActorForm createActorForm) {
	 * Assert.notNull(createActorForm);
	 * Actor actor;
	 * Md5PasswordEncoder encoder;
	 * String password;
	 * 
	 * actor = this.findByPrincipal();
	 * 
	 * password = createActorForm.getPassword();
	 * 
	 * encoder = new Md5PasswordEncoder();
	 * password = encoder.encodePassword(password, null);
	 * 
	 * actor.getUserAccount().setUsername(createActorForm.getUsername());
	 * actor.getUserAccount().setPassword(password);
	 * actor.setName(createActorForm.getName());
	 * actor.setEmail(createActorForm.getEmail());
	 * actor.setPhoneNumber(createActorForm.getPhoneNumber());
	 * 
	 * if (this.checkAuthority(actor, Authority.CUSTOMER)) {
	 * final Customer customer = (Customer) actor;
	 * 
	 * actor = customer;
	 * }
	 * 
	 * return actor;
	 * }
	 */

	/*
	 * public CreateActorForm desreconstructProfile(final Actor actor) {
	 * Assert.notNull(actor);
	 * 
	 * CreateActorForm createActorForm;
	 * 
	 * createActorForm = new CreateActorForm();
	 * 
	 * createActorForm.setUsername(actor.getUserAccount().getUsername());
	 * createActorForm.setPassword(actor.getUserAccount().getPassword());
	 * createActorForm.setName(actor.getName());
	 * createActorForm.setEmail(actor.getEmail());
	 * createActorForm.setPhoneNumber(actor.getPhoneNumber());
	 * 
	 * return createActorForm;
	 * }
	 */

}
