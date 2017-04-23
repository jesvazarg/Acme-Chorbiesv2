
package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	// Create, Edit and Delete ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Event event;

		event = this.eventService.create();

		result = this.createEditModelAndView(event);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int eventId) {
		ModelAndView result;
		Event event;

		event = this.eventService.findOne(eventId);

		result = this.createEditModelAndView(event);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Event event, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(event);
		else
			try {
				this.eventService.save(event);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(event, "event.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Event event, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = new ModelAndView("redirect:../../welcome/index.do");
		else
			try {
				this.eventService.delete(event);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(event, "event.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Event event) {
		ModelAndView result;

		result = this.createEditModelAndView(event, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Event event, final String message) {
		ModelAndView result;

		result = new ModelAndView("event/edit");
		result.addObject("event", event);
		result.addObject("requestURI", "event/manager/edit.do");
		result.addObject("message", message);

		return result;
	}

}
