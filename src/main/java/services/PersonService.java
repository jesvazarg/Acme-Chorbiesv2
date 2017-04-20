
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonRepository;
import security.LoginService;
import security.UserAccount;
import domain.Person;

@Service
@Transactional
public class PersonService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PersonRepository	personRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public PersonService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Person findOne(final int personId) {
		Assert.isTrue(personId != 0);
		Person person;
		person = this.personRepository.findOne(personId);
		return person;
	}

	public Collection<Person> findAll() {
		return this.personRepository.findAll();
	}

	public Person save(final Person person) {
		Assert.notNull(person);

		final Person result = this.personRepository.save(person);

		return result;
	}

	// Other business methods -------------------------------------------------

	public Person findByPrincipal() {
		Person result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Person findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		Person result;

		result = this.personRepository.findByUserAccountId(userAccountId);

		return result;
	}

}
