
package controllers.actor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChorbiService;
import services.CreditCardService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.CreditCard;
import forms.CreateCreditCardForm;

@Controller
@RequestMapping("/creditCard/actor")
public class CreditCardChorbiController extends AbstractController {

	// Service ---------------------------------------------------------------		
	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ChorbiService		chorbiService;


	// Constructors -----------------------------------------------------------
	public CreditCardChorbiController() {
		super();
	}

	// Display ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {

		ModelAndView result;
		CreditCard creditCard;

		creditCard = this.actorService.findByPrincipal().getCreditCard();

		result = new ModelAndView("creditCard/display");
		result.addObject("creditCard", creditCard);
		result.addObject("requestURI", "creditCard/display");

		return result;

	}
	// Creation ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreateCreditCardForm createCreditCardForm;

		createCreditCardForm = new CreateCreditCardForm();
		result = this.createEditModelAndView(createCreditCardForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final CreateCreditCardForm createCreditCardForm, final BindingResult binding) {

		ModelAndView result;
		CreditCard creditCard;

		if (binding.hasErrors())
			result = this.createEditModelAndView(createCreditCardForm);
		else
			try {
				creditCard = this.creditCardService.reconstructCreditCard(createCreditCardForm, "create");
				this.creditCardService.save(creditCard);
				result = new ModelAndView("redirect:/creditCard/actor/display.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(createCreditCardForm, "creditCard.commit.error");

			}
		return result;
	}

	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		CreateCreditCardForm createCreditCardForm;
		CreditCard creditCard;

		creditCard = this.actorService.findByPrincipal().getCreditCard();
		Assert.notNull(creditCard);
		createCreditCardForm = this.creditCardService.constructCreditCard(creditCard);
		result = this.editionEditModelAndView(createCreditCardForm);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final CreateCreditCardForm createCreditCardForm, final BindingResult binding) {

		ModelAndView result;
		CreditCard creditCard;

		if (binding.hasErrors())
			result = this.editionEditModelAndView(createCreditCardForm);
		else
			try {
				creditCard = this.creditCardService.reconstructCreditCard(createCreditCardForm, "edit");
				this.creditCardService.save(creditCard);
				result = new ModelAndView("redirect:/creditCard/actor/display.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(createCreditCardForm, "creditCard.commit.error");

			}
		return result;
	}

	// Delete ------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {

		ModelAndView result;
		Chorbi principal;
		CreditCard creditCard;

		principal = this.chorbiService.findByPrincipal();
		creditCard = principal.getCreditCard();
		this.creditCardService.delete(creditCard);
		result = new ModelAndView("redirect:/profile/myProfile.do");

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final CreateCreditCardForm createCreditCardForm) {
		ModelAndView result;

		result = this.createEditModelAndView(createCreditCardForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateCreditCardForm createCreditCardForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("creditCard/create");
		result.addObject("createCreditCardForm", createCreditCardForm);
		result.addObject("requestURI", "creditCard/actor/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateCreditCardForm createCreditCardForm) {
		ModelAndView result;

		result = this.editionEditModelAndView(createCreditCardForm, null);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateCreditCardForm createCreditCardForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("creditCard/edit");
		result.addObject("createCreditCardForm", createCreditCardForm);
		result.addObject("requestURI", "creditCard/actor/edit.do");
		result.addObject("message", message);

		return result;
	}

}
