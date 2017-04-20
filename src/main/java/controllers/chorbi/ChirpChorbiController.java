
package controllers.chorbi;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.ChorbiService;
import controllers.AbstractController;
import domain.Chirp;
import domain.Chorbi;

@Controller
@RequestMapping("/chirp/chorbi")
public class ChirpChorbiController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private ChorbiService	chorbiService;


	// Constructors -----------------------------------------------------------

	public ChirpChorbiController() {
		super();
	}

	// List ----------------------------------------------------------------
	@RequestMapping(value = "/listOut", method = RequestMethod.GET)
	public ModelAndView listIn() {
		ModelAndView result;
		Collection<Chirp> chirps;
		final Chorbi chorbi = this.chorbiService.findByPrincipal();

		chirps = this.chirpService.findChirpsSentByChorbiId(chorbi.getId());

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);

		return result;
	}

	@RequestMapping(value = "/listIn", method = RequestMethod.GET)
	public ModelAndView listOut() {
		ModelAndView result;
		Collection<Chirp> chirps;
		final Chorbi chorbi = this.chorbiService.findByPrincipal();

		chirps = this.chirpService.findChirpsReceivedByChorbiId(chorbi.getId());

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);

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
		if (chirp.getRecipients().equals(chorbi))
			isRecipient = true;
		/* Seguridad */
		if (!chirp.getRecipients().equals(chorbi) && !chirp.getSender().equals(chorbi))
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
				result = new ModelAndView("redirect:../chorbi/listOut.do");
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
		if (!chirpRequest.getRecipients().equals(chorbi) && !chirpRequest.getSender().equals(chorbi))
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
		if (!chirpRequest.getRecipients().equals(chorbi) && !chirpRequest.getSender().equals(chorbi))
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
				result = new ModelAndView("redirect:../chorbi/listOut.do");
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
		if (!chirp.getRecipients().equals(chorbi) && !chirp.getSender().equals(chorbi))
			return result = new ModelAndView("redirect:../../welcome/index.do");
		else
			try {
				this.chirpService.delete(chirp);
				result = new ModelAndView("redirect:../chorbi/listIn.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:../chorbi/listIn.do");
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
		Chorbi chorbi;
		Collection<Chorbi> recipients;

		chorbi = this.chorbiService.findByPrincipal();
		recipients = this.chorbiService.findAll();
		recipients.remove(chorbi);

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
