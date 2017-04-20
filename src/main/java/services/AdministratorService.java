
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Chorbi;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ChorbiService			chorbiService;


	// Constructors -----------------------------------------------------------
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------
	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Administrator result;

		result = this.administratorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public void banChorbi(final Chorbi chorbi) {
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority authority = new Authority();
		Assert.notNull(chorbi);
		Assert.notNull(this.findByPrincipal());

		authority.setAuthority(Authority.BANNED);
		authorities.add(authority);
		chorbi.getUserAccount().setAuthorities(authorities);

		this.chorbiService.save(chorbi);
	}

	public void desBanChorbi(final Chorbi chorbi) {
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority authority = new Authority();
		Assert.notNull(chorbi);
		Assert.notNull(this.findByPrincipal());

		authority.setAuthority(Authority.CHORBI);
		authorities.add(authority);
		chorbi.getUserAccount().setAuthorities(authorities);

		this.chorbiService.save(chorbi);
	}

}
