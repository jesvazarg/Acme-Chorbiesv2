
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import controllers.AbstractController;
import domain.Configuration;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ConfigurationAdministratorController() {
		super();
	}

	// List -------------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Configuration configuration;

		configuration = this.configurationService.findConfiguration();

		result = new ModelAndView("configuration/list");
		result.addObject(configuration);

		return result;
	}

	// Edition ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Configuration configuration;

		configuration = this.configurationService.findConfiguration();

		result = this.createEditModelAndView(configuration);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Configuration configuration, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(configuration);
		} else
			try {
				this.configurationService.save(configuration);
				result = new ModelAndView("redirect:../administrator/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(configuration, "configuration.commit.error");
			}

		return result;
	}

	// List -------------------------------------------------------------------		

	@RequestMapping(value = "/collect", method = RequestMethod.GET)
	public ModelAndView collect() {
		ModelAndView result;
		Configuration configuration;

		this.configurationService.cobrarAChorbies();
		configuration = this.configurationService.findConfiguration();

		result = new ModelAndView("configuration/list");
		result.addObject(configuration);

		//result = new ModelAndView("redirect:..configuration/administrator/list.do");

		return result;
	}

	// Ancillary methods ---------------------------------------------------------------		

	private ModelAndView createEditModelAndView(final Configuration configuration) {
		ModelAndView result;
		result = this.createEditModelAndView(configuration, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Configuration configuration, final String message) {
		ModelAndView result;
		result = new ModelAndView("configuration/edit");
		result.addObject("configuration", configuration);
		result.addObject("message", message);
		return result;
	}
}
