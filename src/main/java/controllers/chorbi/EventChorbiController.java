
package controllers.chorbi;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.EventService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.Event;

@Controller
@RequestMapping("/event/chorbi")
public class EventChorbiController extends AbstractController {

	// Service ---------------------------------------------------------------		
	@Autowired
	private EventService	eventService;

	@Autowired
	private ChorbiService	chorbiService;


	// Constructors -----------------------------------------------------------

	public EventChorbiController() {
		super();
	}

	// List ---------------------------------------------------------------
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> events;
		Chorbi principal;
		Long hoy;
		Long mes;
		Date mesAux;

		principal = this.chorbiService.findByPrincipal();
		events = principal.getEvents();
		hoy = Calendar.getInstance().getTime().getTime();
		mesAux = Calendar.getInstance().getTime();
		mesAux.setMonth(mesAux.getMonth() + 1);
		mes = mesAux.getTime();

		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("principal", principal);
		result.addObject("hoy", hoy);
		result.addObject("mes", mes);
		result.addObject("requestURI", "event/chorbi/list.do");

		return result;
	}

	// Register ---------------------------------------------------------------		

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam final int eventId) {
		ModelAndView result;
		Event event;

		event = this.eventService.findOne(eventId);
		event = this.eventService.registerChorbiToEvent(event);

		result = new ModelAndView("redirect:/event/list.do");

		return result;
	}

	// Unregister ---------------------------------------------------------------		

	@RequestMapping(value = "/unregister", method = RequestMethod.GET)
	public ModelAndView unregister(@RequestParam final int eventId) {
		ModelAndView result;
		Event event;

		event = this.eventService.findOne(eventId);
		event = this.eventService.unregisterChorbiToEvent(event);

		result = new ModelAndView("redirect:/event/list.do");

		return result;
	}

}
