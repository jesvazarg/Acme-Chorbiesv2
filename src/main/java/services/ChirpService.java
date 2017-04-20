
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
import domain.Chirp;
import domain.Chorbi;
import domain.Person;

@Service
@Transactional
public class ChirpService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ChirpRepository	chirpRepository;

	@Autowired
	private ChorbiService	chorbiService;


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
		Chorbi chorbi;

		Calendar calendar;

		result = new Chirp();
		chorbi = this.chorbiService.findByPrincipal();
		Assert.notNull(chorbi);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result.setAttachments(new ArrayList<String>());
		result.setMoment(calendar.getTime());
		result.setSender(chorbi);

		final Collection<Person> recipients = new HashSet<Person>();
		result.setRecipients(recipients);

		result.setCopy(false);
		return result;
	}

	public Chirp create(final Chorbi recipient) {
		Chirp result;
		Chorbi chorbi;

		Calendar calendar;

		result = new Chirp();
		chorbi = this.chorbiService.findByPrincipal();
		Assert.notNull(chorbi);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result.setAttachments(new ArrayList<String>());
		result.setMoment(calendar.getTime());
		result.setSender(chorbi);
		final Collection<Person> recipientsAux = new HashSet<Person>();
		recipientsAux.add(recipient);
		result.setRecipients(recipientsAux);
		result.setCopy(false);
		return result;
	}

	public Chirp forward(final Chirp chirp) {
		Assert.notNull(chirp);
		Chirp result;
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		Assert.notNull(chorbi);
		Assert.isTrue(chirp.getSender().equals(chorbi));
		result = new Chirp();
		result.setSubject(chirp.getSubject());
		result.setText(chirp.getText());
		result.setMoment(chirp.getMoment());
		result.setAttachments(chirp.getAttachments());
		result.setSender(chorbi);
		result.setCopy(false);
		return result;
	}

	public Chirp reply(final Chirp chirp) {
		Assert.notNull(chirp);
		Assert.isTrue(chirp.getRecipients().contains(this.chorbiService.findByPrincipal()));
		final Chirp result = this.create();
		final Collection<Person> recipientsAux = result.getRecipients();
		recipientsAux.add(chirp.getSender());
		result.setRecipients(recipientsAux);
		//result.setRecipients(chirp.getSender());
		result.setSubject(chirp.getSubject());
		//result.setText(message.getText());
		//result.setAttachments(message.getAttachments());

		return result;
	}

	public void delete(final Chirp chirp) {
		Assert.notNull(chirp);
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		Assert.isTrue(chirp.getSender().equals(chorbi) || chirp.getRecipients().contains(chorbi));

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

	// Other business methods -------------------------------------------------

	public Collection<Chirp> findChirpsSentByChorbiId(final int chorbiId) {

		Collection<Chirp> result;

		result = this.chirpRepository.findChirpsSentByChorbiId(chorbiId);

		return result;

	}

	public Collection<Chirp> findChirpsReceivedByChorbiId(final int chorbiId) {

		Collection<Chirp> result;

		result = this.chirpRepository.findChirpsReceivedByChorbiId(chorbiId);

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
