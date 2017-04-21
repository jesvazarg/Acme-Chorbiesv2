
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	// Constructors ----------------------------------------------------------
	public Manager() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	company;
	private Integer	vat;


	@NotBlank
	public String getCompany() {
		return this.company;
	}

	public void setCompany(final String company) {
		this.company = company;
	}

	@NotNull
	public Integer getVat() {
		return this.vat;
	}

	public void setVat(final Integer vat) {
		this.vat = vat;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Event>	events;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "manager")
	public Collection<Event> getEvents() {
		return this.events;
	}
	public void setEvents(final Collection<Event> events) {
		this.events = events;
	}

}
