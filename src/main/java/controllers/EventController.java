
package controllers;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.EventService;
import domain.Actor;
import domain.Event;

@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	// Service ---------------------------------------------------------------		
	@Autowired
	private EventService	eventService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public EventController() {
		super();
	}

	// List ---------------------------------------------------------------
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> events;
		Actor principal = null;
		Long hoy;
		Long mes;
		Date mesAux;

		try {
			principal = this.actorService.findByPrincipal();
		} catch (final Throwable oops) {
		}

		events = this.eventService.findAll();
		hoy = Calendar.getInstance().getTime().getTime();
		mesAux = Calendar.getInstance().getTime();
		mesAux.setMonth(mesAux.getMonth() + 1);
		mes = mesAux.getTime();

		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("principal", principal);
		result.addObject("hoy", hoy);
		result.addObject("mes", mes);
		result.addObject("requestURI", "event/list.do");

		return result;
	}

	// List Recently ---------------------------------------------------------------
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/listRecently", method = RequestMethod.GET)
	public ModelAndView listRecently() {
		ModelAndView result;
		Collection<Event> events;
		Actor principal = null;
		Long hoy;
		Long mes;
		Date mesAux;

		try {
			principal = this.actorService.findByPrincipal();
		} catch (final Throwable oops) {
		}

		events = this.eventService.findEventsLessOneMonth();
		hoy = Calendar.getInstance().getTime().getTime();
		mesAux = Calendar.getInstance().getTime();
		mesAux.setMonth(mesAux.getMonth() + 1);
		mes = mesAux.getTime();

		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("principal", principal);
		result.addObject("hoy", hoy);
		result.addObject("mes", mes);
		result.addObject("requestURI", "event/listRecently.do");

		return result;
	}

	// Display ---------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int eventId) {
		ModelAndView result;
		Event event;
		Boolean isManager = false;
		final Actor actor = this.actorService.findByPrincipal();

		event = this.eventService.findOne(eventId);

		if (event.getManager().equals(actor))
			isManager = true;
		result = new ModelAndView("event/display");
		result.addObject("event", event);
		result.addObject("isManager", isManager);
		result.addObject("requestURI", "event/display.do");

		return result;
	}

}
