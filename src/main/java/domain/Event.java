
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Event extends DomainEntity {

	// Constructors -----------------------------------------------------------
	public Event() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	title;
	private Date	moment;
	private String	description;
	private String	picture;
	private Integer	seats;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotNull
	@Min(0)
	public Integer getSeats() {
		return this.seats;
	}
	public void setSeats(final Integer seats) {
		this.seats = seats;
	}


	// Relationships ----------------------------------------------------------
	private Manager				manager;
	private Collection<Chorbi>	chorbies;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}
	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "events")
	public Collection<Chorbi> getChorbies() {
		return this.chorbies;
	}
	public void setChorbies(final Collection<Chorbi> chorbies) {
		this.chorbies = chorbies;
	}

}
