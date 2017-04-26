
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EventRepository;
import domain.Chorbi;
import domain.Event;
import domain.Manager;

@Service
@Transactional
public class EventService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private EventRepository		eventRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ManagerService		managerService;

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ChorbiService		chorbiService;


	// Constructors------------------------------------------------------------
	public EventService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Event findOne(final int eventId) {
		Event result;

		result = this.eventRepository.findOne(eventId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Event> findAll() {
		Collection<Event> result;

		result = this.eventRepository.findAll();

		return result;
	}

	public Event create() {
		Event result;
		Manager principal;
		Collection<Chorbi> chorbies;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		chorbies = new ArrayList<Chorbi>();

		result = new Event();
		result.setManager(principal);
		result.setChorbies(chorbies);

		return result;
	}

	public Event save(final Event event) {
		Assert.notNull(event);
		Event result;
		Manager principal;
		Date hoy;

		hoy = Calendar.getInstance().getTime();
		Assert.isTrue(event.getMoment().after(hoy));

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(event.getManager().getId() == principal.getId());
		this.creditCardService.checkCreditCard(principal.getCreditCard());

		//Aquí va el cobro del fee al manager logeado

		//Aquí va el envio de los chirps a todos los chorbies registrados

		result = this.eventRepository.save(event);

		return result;
	}

	public void delete(final Event event) {
		Assert.notNull(event);
		Manager principal;
		Collection<Chorbi> chorbies;
		Collection<Event> events;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(event.getManager().getId() == principal.getId());

		chorbies = event.getChorbies();
		for (final Chorbi c : chorbies) {
			events = c.getEvents();
			events.remove(event);
			this.chorbiService.save(c);
		}

		//Aquí va el envio de los chirps a todos los chorbies registrados

		this.eventRepository.delete(event);
	}

	// Other business methods -------------------------------------------------

	public Event registerChorbiToEvent(final Event event) {
		Assert.notNull(event);
		Assert.isTrue(event.getSeats() - event.getChorbies().size() > 0);
		Chorbi principal;
		Collection<Event> events;
		Collection<Chorbi> chorbies;
		Date hoy;

		hoy = Calendar.getInstance().getTime();
		Assert.isTrue(event.getMoment().after(hoy));

		principal = this.chorbiService.findByPrincipal();
		Assert.notNull(principal);
		events = principal.getEvents();
		Assert.isTrue(!events.contains(event));
		events.add(event);
		principal.setEvents(events);
		this.chorbiService.save(principal);

		chorbies = event.getChorbies();
		Assert.isTrue(!chorbies.contains(principal));
		chorbies.add(principal);
		event.setChorbies(chorbies);
		this.eventRepository.save(event);

		return event;
	}

	public Event unregisterChorbiToEvent(final Event event) {
		Assert.notNull(event);
		Chorbi principal;
		Collection<Event> events;
		Collection<Chorbi> chorbies;
		Date hoy;

		hoy = Calendar.getInstance().getTime();
		Assert.isTrue(event.getMoment().after(hoy));

		principal = this.chorbiService.findByPrincipal();
		Assert.notNull(principal);
		events = principal.getEvents();
		Assert.isTrue(events.contains(event));
		events.remove(event);
		principal.setEvents(events);
		this.chorbiService.save(principal);

		chorbies = event.getChorbies();
		Assert.isTrue(chorbies.contains(principal));
		chorbies.remove(principal);
		event.setChorbies(chorbies);
		this.eventRepository.save(event);

		return event;
	}

	@SuppressWarnings("deprecation")
	public Collection<Event> findEventsLessOneMonth() {
		final Collection<Event> result = new ArrayList<Event>();
		Collection<Event> events;
		Date hoy;
		Date limite;

		events = this.eventRepository.findAll();
		hoy = Calendar.getInstance().getTime();
		limite = Calendar.getInstance().getTime();
		limite.setMonth(limite.getMonth() + 1);

		for (final Event e : events)
			if ((e.getMoment().before(limite)) && (e.getMoment().after(hoy)) && (e.getSeats() - e.getChorbies().size() > 0))
				result.add(e);

		return result;
	}

	public Collection<Event> findEventsPassed() {
		final Collection<Event> result = new ArrayList<Event>();
		Collection<Event> events;
		Date limite;

		events = this.eventRepository.findAll();
		limite = Calendar.getInstance().getTime();

		for (final Event e : events)
			if (e.getMoment().before(limite))
				result.add(e);

		return result;
	}

}
