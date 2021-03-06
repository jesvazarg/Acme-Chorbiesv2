
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChirpService;
import services.ChorbiService;
import services.EventService;
import services.ManagerService;
import domain.Actor;
import domain.Chirp;
import domain.Chorbi;
import domain.Event;
import domain.Manager;

@Controller
@RequestMapping("/chirp")
public class ChirpController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private EventService	eventService;


	// Constructors -----------------------------------------------------------

	public ChirpController() {
		super();
	}

	// List ----------------------------------------------------------------
	@RequestMapping(value = "/listOut", method = RequestMethod.GET)
	public ModelAndView listIn() {
		ModelAndView result;
		Collection<Chirp> chirps;
		//final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final Actor actor = this.actorService.findByPrincipal();

		final Manager manager = this.managerService.findOne(actor.getId());
		if (manager != null)
			chirps = this.chirpService.findChirpsSentByManagerId(actor.getId());
		else
			chirps = this.chirpService.findChirpsSentByActorId(actor.getId());

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);

		return result;
	}

	@RequestMapping(value = "/listIn", method = RequestMethod.GET)
	public ModelAndView listOut() {
		ModelAndView result;
		Collection<Chirp> chirps;
		//final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final Actor actor = this.actorService.findByPrincipal();

		chirps = this.chirpService.findChirpsReceivedByActorId(actor.getId());

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		//result.addObject("recipients", actor);
		//result.addObject("recipients", chorbi);

		return result;
	}

	@RequestMapping(value = "/listBroadcastSent", method = RequestMethod.GET)
	public ModelAndView listBroadcastSent() {
		ModelAndView result;
		Collection<Chirp> chirps;
		//final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final Actor actor = this.actorService.findByPrincipal();
		final Manager manager = this.managerService.findByUserAccount(actor.getUserAccount());
		if (manager == null)
			return result = new ModelAndView("redirect:../../welcome/index.do");
		else {
			chirps = this.chirpService.findBroadcastsSentByManagerId(actor.getId());

			result = new ModelAndView("chirp/listBroadcast");
			result.addObject("chirps", chirps);
			final String broadcast = "Broadcast";
			result.addObject("recipients", broadcast);

			return result;
		}

	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int chirpId) {
		ModelAndView result;
		Chirp chirp;
		Boolean isRecipient = false;
		Boolean isManager = false;
		//final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final Actor actor = this.actorService.findByPrincipal();
		final Manager manager = this.managerService.findOne(actor.getId());
		if (manager != null)
			isManager = true;
		chirp = this.chirpService.findOne(chirpId);
		//if (chirp.getRecipients().contains(actor))
		//isRecipient = true;
		/* Seguridad */
		if (!chirp.getRecipients().contains(actor) && !chirp.getSender().equals(actor))
			return result = new ModelAndView("redirect:../../welcome/index.do");
		/*----*/
		else {
			if (chirp.getRecipients().contains(actor))
				isRecipient = true;

			result = new ModelAndView("chirp/display");
			result.addObject("chirp", chirp);
			result.addObject("isRecipient", isRecipient);
			result.addObject("isManager", isManager);
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int chorbieId) {
		ModelAndView result;
		//final Chorbi recipient = this.chorbiService.findByPrincipal();
		final Chorbi recipient = this.chorbiService.findOne(chorbieId);

		final Chirp chirp = this.chirpService.create(recipient);
		result = this.createEditModelAndView(chirp);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveResponse(@Valid final Chirp Chirp, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(Chirp);
		else
			try {
				this.chirpService.save(Chirp);
				result = new ModelAndView("redirect:../chirp/listOut.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(Chirp, "chirp.commit.error");
			}

		return result;
	}

	//Reply ------------------------------------------------------------------------
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam final int chirpId) {
		ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		final Chirp chirpRequest = this.chirpService.findOne(chirpId);

		/* Seguridad */
		if (!chirpRequest.getRecipients().contains(actor) && !chirpRequest.getSender().equals(actor))
			return result = new ModelAndView("redirect:../../welcome/index.do");
		/*----*/
		else {
			final Chirp chirp = this.chirpService.reply(chirpRequest);
			result = this.createEditModelAndView(chirp);
		}

		return result;
	}

	//Reply ------------------------------------------------------------------------
	@RequestMapping(value = "/forward", method = RequestMethod.GET)
	public ModelAndView forward(@RequestParam final int chirpId) {
		ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		final Chirp chirpRequest = this.chirpService.findOne(chirpId);

		/* Seguridad */
		if (!chirpRequest.getRecipients().contains(actor) && !chirpRequest.getSender().equals(actor))
			return result = new ModelAndView("redirect:../../welcome/index.do");
		/*----*/
		else {

			final Chirp chirp = this.chirpService.forward(chirpRequest);
			System.out.print(chirp.getId() + chirp.getSubject() + chirp.getText() + chirp.getSender() + chirp.getRecipients());
			result = this.createEditModelAndViewForward(chirp);
		}

		return result;
	}

	@RequestMapping(value = "/forward", method = RequestMethod.POST, params = "save")
	public ModelAndView saveReply(@Valid final Chirp chirp, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewForward(chirp);
		else
			try {
				this.chirpService.save(chirp);
				result = new ModelAndView("redirect:../chirp/listOut.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(chirp, "chirp.commit.error");
			}

		return result;
	}

	//Reply ------------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Chirp chirp, final BindingResult binding) {
		ModelAndView result;
		final Chirp chirpToDel = this.chirpService.findOne(chirp.getId());
		final Actor actor = this.actorService.findByPrincipal();
		/* Seguridad */
		if (!chirp.getRecipients().contains(actor) && !chirp.getSender().equals(actor))
			return result = new ModelAndView("redirect:../../welcome/index.do");
		else
			try {
				this.chirpService.delete(chirpToDel);
				result = new ModelAndView("redirect:../chirp/listIn.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:../chirp/listIn.do");
			}
		return result;
	}

	// Broadcast ----------------------------------------------------------------
	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast(@RequestParam final int eventId) {
		ModelAndView result;
		//final Chorbi recipient = this.chorbiService.findByPrincipal();
		final Event event = this.eventService.findOne(eventId);

		if (event.getChorbies().size() < 1 || !event.getManager().equals(this.actorService.findByPrincipal()))
			return result = new ModelAndView("redirect:../../welcome/index.do");

		final Chirp chirp = this.chirpService.broadcast(event);
		result = this.createEditModelAndView(chirp);

		return result;
	}

	@RequestMapping(value = "/broadcast", method = RequestMethod.POST, params = "save")
	public ModelAndView saveBroadcast(@Valid final Chirp Chirp, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(Chirp);
		else
			try {
				this.chirpService.saveBroadcast(Chirp);
				result = new ModelAndView("redirect:../chirp/listOut.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(Chirp, "chirp.commit.error");
			}

		return result;
	}

	//Ancillary methods---------------------------------------------------------

	//Create --------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Chirp chirp) {
		final ModelAndView result = this.createEditModelAndView(chirp, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Chirp chirp, final String message) {
		ModelAndView result;
		//Actor chorbi;
		Collection<Chorbi> recipients;

		//chorbi = this.chorbiService.findByPrincipal();
		recipients = this.chorbiService.findAll();
		//recipients.remove(chorbi);

		result = new ModelAndView("chirp/create");
		result.addObject("chirp", chirp);
		result.addObject("recipients", recipients);
		result.addObject("message", message);
		return result;
	}

	//Reply ------------------------------------------------------------------------
	protected ModelAndView createEditModelAndViewForward(final Chirp chirp) {
		final ModelAndView result = this.createEditModelAndViewForward(chirp, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewForward(final Chirp chirp, final String message) {
		ModelAndView result;
		Actor actor;
		Collection<Actor> recipients;

		actor = this.actorService.findByPrincipal();
		recipients = this.actorService.findAll();
		recipients.remove(actor);

		result = new ModelAndView("chirp/forward");
		result.addObject("chirp", chirp);
		result.addObject("recipients", recipients);
		result.addObject("message", message);
		return result;
	}

}
