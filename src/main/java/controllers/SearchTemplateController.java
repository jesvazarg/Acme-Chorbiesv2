
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.SearchTemplateService;
import domain.Chorbi;
import domain.SearchTemplate;

@Controller
@RequestMapping("/searchTemplate/chorbi")
public class SearchTemplateController extends AbstractController {

	// Constructors -----------------------------------------------------------
	public SearchTemplateController() {
		super();
	}


	// Services ---------------------------------------------------------------
	@Autowired
	private SearchTemplateService	searchTemplateService;

	@Autowired
	private ChorbiService			chorbiService;


	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Chorbi chorbi;
		SearchTemplate searchTemplate;

		chorbi = this.chorbiService.findByPrincipal();

		searchTemplate = chorbi.getSearchTemplate();

		result = new ModelAndView("searchTemplate/display");
		result.addObject("principal", chorbi);
		result.addObject("searchTemplate", searchTemplate);

		return result;
	}

	// Creation ---------------------------------------------------------------		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SearchTemplate searchTemplate;

		searchTemplate = this.searchTemplateService.create();

		result = this.createEditModelAndView(searchTemplate);

		return result;
	}

	// Edition ----------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int searchTemplateId) {
		ModelAndView result;
		SearchTemplate searchTemplate;

		searchTemplate = this.searchTemplateService.findOne(searchTemplateId);
		Assert.notNull(searchTemplate);

		result = this.createEditModelAndView(searchTemplate);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SearchTemplate searchTemplate, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(searchTemplate);
		else
			try {
				this.searchTemplateService.save(searchTemplate);
				result = new ModelAndView("redirect:display.do");

			} catch (final Throwable oops) {
				System.out.println(oops.toString());
				result = this.createEditModelAndView(searchTemplate, "searchTemplate.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/findBySearchTemplate", method = RequestMethod.GET)
	public ModelAndView buscador(@RequestParam final int searchTemplateId) {
		ModelAndView result;
		SearchTemplate searchTemplate;
		Chorbi chorbi;

		chorbi = this.chorbiService.findByPrincipal();
		searchTemplate = this.searchTemplateService.findOne(searchTemplateId);
		Assert.notNull(searchTemplate);
		try {
			final Collection<Chorbi> chorbies = this.searchTemplateService.findBySearchResult(searchTemplate);
			chorbies.remove(chorbi);

			result = new ModelAndView("chorbi/list");
			result.addObject("chorbies", chorbies);
			result.addObject("requestURI", "searchTemplate/chorbi/findBySearchTemplate.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../profile/myProfile.do");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------		
	protected ModelAndView createEditModelAndView(final SearchTemplate searchTemplate) {
		ModelAndView result;

		result = this.createEditModelAndView(searchTemplate, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SearchTemplate searchTemplate, final String message) {
		ModelAndView result;

		result = new ModelAndView("searchTemplate/edit");
		result.addObject("searchTemplate", searchTemplate);
		//result.addObject("creditCardError", creditCardError);
		result.addObject("message", message);

		return result;
	}

}
