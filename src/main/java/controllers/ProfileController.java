/*
 * ProfileController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ChorbiService;
import services.SenseService;
import domain.Actor;
import domain.Chorbi;
import domain.Sense;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	// Services ---------------------------------------------------------------	

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private SenseService	senseService;


	// Constructor ---------------------------------------------------------------

	public ProfileController() {
		super();
	}

	// Display -------------------------------------------------------------------
	@RequestMapping(value = "myProfile", method = RequestMethod.GET)
	public ModelAndView displayMyProfile() {
		ModelAndView result;
		Actor actor;
		Boolean isAdmin = false;
		Boolean isManager = false;
		Boolean isChorbi = false;
		final Boolean sameActor = true;
		Collection<Sense> likeThem;
		String account = "";

		actor = this.actorService.findByPrincipal();

		result = new ModelAndView("profile/display");
		result.addObject("profile", actor);

		isAdmin = this.actorService.checkAuthority(actor, Authority.ADMIN);
		if (isAdmin)
			account = "admin";
		isManager = this.actorService.checkAuthority(actor, Authority.MANAGER);
		if (isManager)
			account = "manager";
		isChorbi = this.actorService.checkAuthority(actor, Authority.CHORBI);
		if (isChorbi)
			account = "chorbi";

		if (isChorbi == true) {
			final Chorbi chorbi = this.chorbiService.findByUserAccount(actor.getUserAccount());
			likeThem = this.senseService.filterSensesNotBanned(chorbi.getReciveSenses());
			result.addObject("description", chorbi.getDescription());
			result.addObject("likeThem", likeThem);
		}

		result.addObject("phone", actor.getPhone());
		result.addObject("email", actor.getEmail());
		result.addObject("account", account);
		result.addObject("isAdmin", isAdmin);
		result.addObject("sameActor", sameActor);
		result.addObject("requestURI", "profile/display");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int actorId) {
		ModelAndView result;
		Actor actor;
		final Boolean isAdmin = false;
		Boolean sameActor = false;
		Collection<Sense> likeThem = new ArrayList<Sense>();
		String maskPhone = null;
		String maskEmail = null;
		String maskDescription = null;
		String account = "";

		actor = this.actorService.findOne(actorId);

		if (this.actorService.checkAuthority(actor, Authority.CHORBI)) {
			final Chorbi chorbi = this.chorbiService.findByUserAccount(actor.getUserAccount());
			likeThem = this.senseService.filterSensesNotBanned(chorbi.getReciveSenses());
			maskPhone = actor.maskEmailAndPhone(chorbi.getPhone());
			maskEmail = actor.maskEmailAndPhone(chorbi.getEmail());
			maskDescription = actor.maskEmailAndPhone(chorbi.getDescription());
			account = "chorbi";
		} else if (this.actorService.checkAuthority(actor, Authority.MANAGER)) {
			maskPhone = actor.maskEmailAndPhone(actor.getPhone());
			maskEmail = actor.maskEmailAndPhone(actor.getEmail());
			maskDescription = "";
			account = "manager";
		}

		if (actor.equals(this.actorService.findByPrincipal()))
			sameActor = true;

		result = new ModelAndView("profile/display");
		result.addObject("account", account);
		result.addObject("profile", actor);
		result.addObject("phone", maskPhone);
		result.addObject("email", maskEmail);
		result.addObject("description", maskDescription);
		result.addObject("isAdmin", isAdmin);
		result.addObject("sameActor", sameActor);
		result.addObject("likeThem", likeThem);
		result.addObject("requestURI", "profile/display.do?actorId=" + actor.getId());

		return result;
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("profile/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("profile/action-2");

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/action-3")
	public ModelAndView action3() {
		throw new RuntimeException("Oops! An *expected* exception was thrown. This is normal behaviour.");
	}

}
