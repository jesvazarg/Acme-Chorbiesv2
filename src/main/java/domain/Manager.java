
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Person {

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
	private CreditCard			creditCard;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "manager")
	public Collection<Event> getEvents() {
		return this.events;
	}
	public void setEvents(final Collection<Event> events) {
		this.events = events;
	}

	@Valid
	@OneToOne(optional = false)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
