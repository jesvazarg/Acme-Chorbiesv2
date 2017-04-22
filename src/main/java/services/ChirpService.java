
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Actor;
import domain.Chirp;
import domain.Chorbi;
import domain.Event;

@Service
@Transactional
public class ChirpService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ChirpRepository	chirpRepository;

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ManagerService	managerService;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public ChirpService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Chirp findOne(final int chirpId) {
		Assert.isTrue(chirpId != 0);
		Chirp chirp;

		chirp = this.chirpRepository.findOne(chirpId);
		Assert.notNull(chirp);
		return chirp;
	}

	public Collection<Chirp> findAll() {
		Collection<Chirp> result;

		result = this.chirpRepository.findAll();

		return result;
	}

	public Chirp create() {
		Chirp result;
		//final Chorbi chorbi;

		Calendar calendar;

		result = new Chirp();
		final Actor actor = this.actorService.findByPrincipal();
		//chorbi = this.chorbiService.findByPrincipal();

		Assert.notNull(actor);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result.setAttachments(new ArrayList<String>());
		result.setMoment(calendar.getTime());
		result.setSender(actor);

		final Collection<Actor> recipients = new HashSet<Actor>();
		result.setRecipients(recipients);

		result.setCopy(false);
		return result;
	}

	public Chirp create(final Chorbi recipient) {
		Chirp result;
		//final Chorbi chorbi;

		Calendar calendar;

		result = new Chirp();
		//chorbi = this.chorbiService.findByPrincipal();
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result.setAttachments(new ArrayList<String>());
		result.setMoment(calendar.getTime());
		result.setSender(actor);
		final Collection<Actor> recipientsAux = new HashSet<Actor>();
		recipientsAux.add(recipient);
		result.setRecipients(recipientsAux);
		result.setCopy(false);
		return result;
	}

	public Chirp forward(final Chirp chirp) {
		Assert.notNull(chirp);
		Chirp result;
		//final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(chirp.getSender().equals(actor));
		result = new Chirp();
		result.setSubject(chirp.getSubject());
		result.setText(chirp.getText());
		result.setMoment(chirp.getMoment());
		result.setAttachments(chirp.getAttachments());
		result.setSender(actor);
		result.setCopy(false);
		return result;
	}

	public Chirp reply(final Chirp chirp) {
		Assert.notNull(chirp);
		Assert.isTrue(chirp.getRecipients().contains(this.actorService.findByPrincipal()));
		final Chirp result = this.create();
		final Collection<Actor> recipientsAux = result.getRecipients();
		recipientsAux.add(chirp.getSender());
		result.setRecipients(recipientsAux);
		//result.setRecipients(chirp.getSender());
		result.setSubject(chirp.getSubject());
		//result.setText(message.getText());
		//result.setAttachments(message.getAttachments());

		return result;
	}

	public Chirp broadcast(final Event event) {
		Assert.notNull(event);
		Assert.isTrue(event.getManager().equals(this.managerService.findByPrincipal()));
		final Chirp result = this.create();
		final Collection<Actor> recipientsAux = result.getRecipients();
		recipientsAux.addAll(event.getChorbies());
		result.setRecipients(recipientsAux);
		return result;
	}

	public void delete(final Chirp chirp) {
		Assert.notNull(chirp);
		//final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(chirp.getSender().equals(actor) || chirp.getRecipients().contains(actor));

		this.chirpRepository.delete(chirp);
	}

	public Chirp save(Chirp chirp) {
		Assert.notNull(chirp);
		Assert.isTrue(this.validatorURL(chirp.getAttachments()));
		Assert.notNull(chirp.getId() != 0);

		Chirp copy;
		copy = new Chirp();
		copy.setCopy(true);
		copy.setAttachments(chirp.getAttachments());
		copy.setMoment(chirp.getMoment());
		copy.setRecipients(chirp.getRecipients());
		copy.setSender(chirp.getSender());
		copy.setSubject(chirp.getSubject());
		copy.setText(chirp.getText());

		chirp = this.chirpRepository.save(chirp);
		this.chirpRepository.save(copy);

		return chirp;
	}

	public Chirp saveBroadcast(Chirp chirp) {
		Assert.notNull(chirp);
		Assert.isTrue(this.validatorURL(chirp.getAttachments()));
		Assert.notNull(chirp.getId() != 0);

		chirp = this.chirpRepository.save(chirp);

		for (final Actor a : chirp.getRecipients()) {

			Chirp copy;
			copy = new Chirp();
			copy.setCopy(true);
			copy.setAttachments(chirp.getAttachments());
			copy.setMoment(chirp.getMoment());
			final Collection<Actor> recipientsAux = copy.getRecipients();
			recipientsAux.add(a);
			copy.setRecipients(recipientsAux);
			copy.setSender(chirp.getSender());
			copy.setSubject(chirp.getSubject());
			copy.setText(chirp.getText());
			this.chirpRepository.save(copy);

		}

		return chirp;
	}

	// Other business methods -------------------------------------------------

	public Collection<Chirp> findChirpsSentByActorId(final int actorId) {

		Collection<Chirp> result;

		result = this.chirpRepository.findChirpsSentByActorId(actorId);

		return result;

	}

	public Collection<Chirp> findChirpsReceivedByActorId(final int actorId) {

		Collection<Chirp> result;

		result = this.chirpRepository.findChirpsReceivedByActorId(actorId);

		return result;

	}

	//Devuelve true si la collection esta vacia o si las URLs contenidas en ellas son URLs validas
	public Boolean validatorURL(final Collection<String> lista) {
		Boolean res = false;
		if (!lista.isEmpty()) {
			for (final String aux : lista)
				if (aux.length() > 11) {
					if ((aux.subSequence(0, 11).equals("http://www.") || (aux.subSequence(0, 12).equals("https://www."))))
						res = true;
					else {
						res = false;
						break;
					}
				} else {
					res = false;
					break;
				}
		} else
			res = true;

		return res;
	}

	public Double[] minAvgMaxChirpsSent() {
		final Double[] result = this.chirpRepository.minAvgMaxChirpsSent();
		return result;
	}

}
