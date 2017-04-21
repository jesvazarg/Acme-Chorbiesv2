
package controllers.chorbi;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.SenseService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.Sense;

@Controller
@RequestMapping("/sense/chorbi")
public class SenseChorbiController extends AbstractController {

	// Service ---------------------------------------------------------------		
	@Autowired
	private SenseService	senseService;

	@Autowired
	private ChorbiService	chorbiService;


	// Constructors -----------------------------------------------------------

	public SenseChorbiController() {
		super();
	}

	// Like ---------------------------------------------------------------		

	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int chorbiId) {
		ModelAndView result;
		Chorbi chorbi;
		Sense sense;

		chorbi = this.chorbiService.findOne(chorbiId);
		sense = this.senseService.create(chorbi);
		//sense = this.senseService.save(sense);

		//result = new ModelAndView("redirect:../../chorbi/list.do");
		result = this.createEditModelAndView(sense);

		return result;
	}

	// Dislike ---------------------------------------------------------------		

	@RequestMapping(value = "/dislike", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int chorbiId) {
		ModelAndView result;
		Chorbi chorbi;
		Chorbi principal;
		Sense sense;

		principal = this.chorbiService.findByPrincipal();
		chorbi = this.chorbiService.findOne(chorbiId);
		sense = this.senseService.findSense(principal, chorbi);
		Assert.notNull(sense);
		this.senseService.delete(sense);

		result = new ModelAndView("redirect:../../chorbi/list.do");

		return result;
	}

	// Comment ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView comment(@RequestParam final int chorbiId) {
		ModelAndView result;
		Chorbi chorbi;
		Chorbi principal;
		Sense sense;

		principal = this.chorbiService.findByPrincipal();
		chorbi = this.chorbiService.findOne(chorbiId);
		sense = this.senseService.findSense(principal, chorbi);
		Assert.notNull(sense);

		result = this.createEditModelAndView(sense);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView comment(@Valid final Sense sense) {
		ModelAndView result;

		if (sense.getComment().equals(""))
			sense.setComment(null);

		this.senseService.save(sense);

		result = new ModelAndView("redirect:../../chorbi/list.do");

		return result;
	}

	//Create --------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Sense sense) {
		final ModelAndView result = this.createEditModelAndView(sense, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sense sense, final String message) {
		ModelAndView result;

		result = new ModelAndView("sense/edit");
		result.addObject("sense", sense);
		result.addObject("message", message);
		return result;
	}

}
