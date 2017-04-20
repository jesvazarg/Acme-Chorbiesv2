/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

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

import security.Authority;
import services.ActorService;
import services.AdministratorService;
import services.ChorbiService;
import domain.Actor;
import domain.Administrator;
import domain.Chorbi;
import domain.Sense;
import forms.CreateChorbiForm;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {

	// Service ---------------------------------------------------------------		
	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public ChorbiController() {
		super();
	}

	// List ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chorbi> chorbies;
		Collection<Sense> sensesSent;
		Actor principal;
		Boolean isAdmin;

		principal = this.actorService.findByPrincipal();
		isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
		if (isAdmin) {
			chorbies = this.chorbiService.findAll();
			sensesSent = null;
		} else {
			chorbies = this.chorbiService.findAllNotBanned();
			sensesSent = this.chorbiService.findOne(principal.getId()).getGiveSenses();
		}

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbies);
		result.addObject("sensesSent", sensesSent);
		result.addObject("requestURI", "chorbi/list.do");

		return result;
	}
	// Creation ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreateChorbiForm createChorbiForm;

		createChorbiForm = new CreateChorbiForm();
		result = this.createEditModelAndView(createChorbiForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid final CreateChorbiForm createChorbiForm, final BindingResult binding) {

		ModelAndView result;
		Chorbi chorbi;

		if (binding.hasErrors())
			result = this.createEditModelAndView(createChorbiForm);
		else
			try {
				chorbi = this.chorbiService.reconstructProfile(createChorbiForm, "create");
				this.chorbiService.save(chorbi);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				System.out.println(oops);
				result = this.createEditModelAndView(createChorbiForm, "chorbi.commit.error");

			}
		return result;
	}

	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		CreateChorbiForm createChorbiForm;
		Chorbi chorbi;

		chorbi = this.chorbiService.findByPrincipal();
		createChorbiForm = this.chorbiService.constructProfile(chorbi);
		result = this.editionEditModelAndView(createChorbiForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final CreateChorbiForm createChorbiForm, final BindingResult binding) {

		ModelAndView result;
		Chorbi chorbi;

		if (binding.hasErrors())
			result = this.editionEditModelAndView(createChorbiForm);
		else
			try {
				chorbi = this.chorbiService.reconstructProfile(createChorbiForm, "edit");
				this.chorbiService.save(chorbi);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(createChorbiForm, "chorbi.commit.error");

			}
		return result;
	}

	// Ban ---------------------------------------------------------------	

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int chorbiId) {
		ModelAndView result;
		Administrator admin;
		Chorbi chorbi;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		chorbi = this.chorbiService.findOne(chorbiId);

		this.administratorService.banChorbi(chorbi);

		result = new ModelAndView("redirect:/chorbi/list.do");

		return result;
	}

	// Unban ---------------------------------------------------------------	

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final int chorbiId) {
		ModelAndView result;
		Administrator admin;
		Chorbi chorbi;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		chorbi = this.chorbiService.findOne(chorbiId);

		this.administratorService.desBanChorbi(chorbi);

		result = new ModelAndView("redirect:/chorbi/list.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final CreateChorbiForm createChorbiForm) {
		ModelAndView result;

		result = this.createEditModelAndView(createChorbiForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateChorbiForm createChorbiForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("chorbi/create");
		result.addObject("createChorbiForm", createChorbiForm);
		result.addObject("requestURI", "chorbi/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateChorbiForm createChorbiForm) {
		ModelAndView result;

		result = this.editionEditModelAndView(createChorbiForm, null);

		return result;
	}

	protected ModelAndView editionEditModelAndView(final CreateChorbiForm createChorbiForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("chorbi/edit");
		result.addObject("createChorbiForm", createChorbiForm);
		result.addObject("requestURI", "chorbi/edit.do");
		result.addObject("message", message);

		return result;
	}
}
