
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
import domain.Actor;
import domain.Chirp;
import domain.Chorbi;
import domain.Event;

@Controller
@RequestMapping("/chirp")
public class ChirpController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private ChorbiService	chorbiService;

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
		//result.addObject("recipients", chorbi);

		return result;
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int chirpId) {
		ModelAndView result;
		Chirp chirp;
		Boolean isRecipient = false;
		final Chorbi chorbi = this.chorbiService.findByPrincipal();

		chirp = this.chirpService.findOne(chirpId);
		if (chirp.getRecipients().contains(chorbi))
			isRecipient = true;
		/* Seguridad */
		if (!chirp.getRecipients().contains(chorbi) && !chirp.getSender().equals(chorbi))
			return result = new ModelAndView("redirect:../../welcome/index.do");
		/*----*/
		else {
			if (chirp.getRecipients().equals(chorbi))
				isRecipient = true;

			result = new ModelAndView("chirp/display");
			result.addObject("chirp", chirp);
			result.addObject("isRecipient", isRecipient);
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
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final Chirp chirpRequest = this.chirpService.findOne(chirpId);

		/* Seguridad */
		if (!chirpRequest.getRecipients().contains(chorbi) && !chirpRequest.getSender().equals(chorbi))
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
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final Chirp chirpRequest = this.chirpService.findOne(chirpId);

		/* Seguridad */
		if (!chirpRequest.getRecipients().contains(chorbi) && !chirpRequest.getSender().equals(chorbi))
			return result = new ModelAndView("redirect:../../welcome/index.do");
		/*----*/
		else {

			final Chirp chirp = this.chirpService.forward(chirpRequest);
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
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		/* Seguridad */
		if (!chirp.getRecipients().contains(chorbi) && !chirp.getSender().equals(chorbi))
			return result = new ModelAndView("redirect:../../welcome/index.do");
		else
			try {
				this.chirpService.delete(chirp);
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
		Chorbi chorbi;
		Collection<Chorbi> recipients;

		chorbi = this.chorbiService.findByPrincipal();
		recipients = this.chorbiService.findAll();
		recipients.remove(chorbi);

		result = new ModelAndView("chirp/forward");
		result.addObject("chirp", chirp);
		result.addObject("recipients", recipients);
		result.addObject("message", message);
		return result;
	}

}
