
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.ManagerService;
import domain.CreditCard;
import domain.Manager;
import forms.CreateManagerForm;

@Controller
@RequestMapping("/managerUser")
public class ManagerController extends AbstractController {

	// Service ---------------------------------------------------------------

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------------------
	public ManagerController() {
		super();
	}

	// List -------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Manager> managers;

		managers = this.managerService.findAll();

		result = new ModelAndView("chorbi/list");
		result.addObject("managers", managers);
		result.addObject("requestURI", "managerUser/list.do");

		return result;
	}

	// Creation ---------------------------------------------------------------	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreateManagerForm createManagerForm;

		createManagerForm = new CreateManagerForm();
		result = this.createEditModelAndView(createManagerForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final CreateManagerForm createManagerForm, final BindingResult binding) {

		ModelAndView result;
		Manager manager;
		CreditCard creditCard;

		if (binding.hasErrors())
			result = this.createEditModelAndView(createManagerForm);
		else
			try {
				creditCard = (CreditCard) this.managerService.reconstructProfile(createManagerForm, "create")[0];
				creditCard = this.creditCardService.saveRegister(creditCard);
				manager = (Manager) this.managerService.reconstructProfile(createManagerForm, "create")[1];
				manager.setCreditCard(creditCard);
				this.managerService.save(manager);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				System.out.println(oops);
				result = this.createEditModelAndView(createManagerForm, "manager.commit.error");
			}
		return result;
	}

	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		CreateManagerForm createManagerForm;
		Manager manager;

		manager = this.managerService.findByPrincipal();
		createManagerForm = this.managerService.constructProfile(manager);
		result = this.editionEditModelAndView(createManagerForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final CreateManagerForm createManagerForm, final BindingResult binding) {

		ModelAndView result;
		Manager manager;
		CreditCard creditCard;

		if (binding.hasErrors())
			result = this.editionEditModelAndView(createManagerForm);
		else
			try {
				creditCard = (CreditCard) this.managerService.reconstructProfile(createManagerForm, "edit")[0];
				creditCard = this.creditCardService.saveRegister(creditCard);
				manager = (Manager) this.managerService.reconstructProfile(createManagerForm, "edit")[1];
				manager.setCreditCard(creditCard);
				this.managerService.save(manager);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				result = this.editionEditModelAndView(createManagerForm, "manager.commit.error");

			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final CreateManagerForm createManagerForm) {
		ModelAndView result;

		result = this.createEditModelAndView(createManagerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateManagerForm createManagerForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("managerUser/create");
		result.addObject("createManagerForm", createManagerForm);
		result.addObject("requestURI", "managerUser/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateManagerForm createManagerForm) {
		ModelAndView result;

		result = this.editionEditModelAndView(createManagerForm, null);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateManagerForm createManagerForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("managerUser/edit");
		result.addObject("createManagerForm", createManagerForm);
		result.addObject("requestURI", "managerUser/edit.do");
		result.addObject("message", message);

		return result;
	}

}
