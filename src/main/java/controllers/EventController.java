
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import domain.Event;

@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	// Service ---------------------------------------------------------------		
	@Autowired
	private EventService	eventService;


	// Constructors -----------------------------------------------------------

	public EventController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> events;

		events = this.eventService.findAll();

		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("requestURI", "event/list.do");

		return result;
	}

	// List Recently ---------------------------------------------------------------
	@RequestMapping(value = "/listRecently", method = RequestMethod.GET)
	public ModelAndView listRecently() {
		ModelAndView result;
		Collection<Event> events;

		events = this.eventService.findEventsLessOneMonth();

		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("requestURI", "event/listRecently.do");

		return result;
	}

	// Display ---------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int eventId) {
		ModelAndView result;
		Event event;

		event = this.eventService.findOne(eventId);

		result = new ModelAndView("event/display");
		result.addObject("event", event);
		result.addObject("requestURI", "event/display.do");

		return result;
	}

}
