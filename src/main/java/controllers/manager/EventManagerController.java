
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Event;
import domain.Manager;

@Controller
@RequestMapping("/event/manager")
public class EventManagerController extends AbstractController {

	// Service ---------------------------------------------------------------		
	@Autowired
	private EventService	eventService;

	@Autowired
	private ManagerService	managerService;


	// Constructors -----------------------------------------------------------

	public EventManagerController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Event> events;
		Manager principal;

		principal = this.managerService.findByPrincipal();
		events = principal.getEvents();

		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("principal", principal);
		result.addObject("requestURI", "event/manager/list.do");

		return result;
	}

}
