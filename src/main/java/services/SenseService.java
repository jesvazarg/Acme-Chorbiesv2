
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SenseRepository;
import security.Authority;
import domain.Chorbi;
import domain.Sense;

@Service
@Transactional
public class SenseService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SenseRepository	senseRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private ActorService	actorService;


	// Constructors------------------------------------------------------------
	public SenseService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Sense findOne(final int senseId) {
		Sense result;

		result = this.senseRepository.findOne(senseId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Sense> findAll() {
		Collection<Sense> result;

		result = this.senseRepository.findAll();

		return result;
	}

	public Sense create(final Chorbi chorbi) {
		Assert.notNull(chorbi);
		Sense result;
		Chorbi principal;
		Calendar today;

		principal = this.chorbiService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(chorbi.getId() != principal.getId());

		today = Calendar.getInstance();
		today.set(Calendar.MILLISECOND, -10);

		result = new Sense();
		result.setSender(principal);
		result.setRecipient(chorbi);
		result.setMoment(today.getTime());
		result.setStars(0);

		for (final Sense s : chorbi.getReciveSenses())
			Assert.isTrue(s.getSender().getId() != principal.getId());

		return result;
	}

	public Sense save(final Sense sense) {
		Assert.notNull(sense);
		Sense result;
		Chorbi principal;

		principal = this.chorbiService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(sense.getSender().getId() == principal.getId());

		result = this.senseRepository.save(sense);

		return result;
	}

	public void delete(final Sense sense) {
		Assert.notNull(sense);
		Assert.notNull(this.chorbiService.findByPrincipal());
		Assert.isTrue(sense.getId() != 0);

		this.senseRepository.delete(sense);
	}

	// Other business methods -------------------------------------------------
	public Sense findSense(final Chorbi sender, final Chorbi recipient) {
		Sense result = null;
		Collection<Sense> senses;

		senses = sender.getGiveSenses();
		for (final Sense s : senses)
			if (s.getRecipient().getId() == recipient.getId()) {
				result = s;
				break;
			}
		return result;
	}

	public Collection<Sense> filterSensesNotBanned(final Collection<Sense> senses) {
		final Collection<Sense> result = new ArrayList<Sense>();

		for (final Sense e : senses)
			if (this.actorService.checkAuthority(e.getSender(), Authority.CHORBI))
				result.add(e);
		return result;
	}
	/*
	 * public Collection<Chorbi> findChorbiesSender(final Collection<Sense> senses) {
	 * final Collection<Chorbi> result = new ArrayList<Chorbi>();
	 * 
	 * for (final Sense s : senses)
	 * result.add(s.getSender());
	 * return result;
	 * }
	 * 
	 * public Collection<Chorbi> findChorbiesRecipient(final Collection<Sense> senses) {
	 * final Collection<Chorbi> result = new ArrayList<Chorbi>();
	 * 
	 * for (final Sense s : senses)
	 * result.add(s.getRecipient());
	 * return result;
	 * }
	 */
	public Double[] minAvgMaxOfSenses() {
		final Double[] result = this.senseRepository.minAvgMaxOfSenses();
		return result;
	}

}
