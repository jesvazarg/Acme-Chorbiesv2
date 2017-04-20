
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "birthDate")
})
public class Chorbi extends Person {

	// Constructors -----------------------------------------------------------
	public Chorbi() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String		picture;
	private String		description;
	private String		relationship;
	private Date		birthDate;
	private String		genre;
	private Coordinate	coordinate;


	@NotBlank
	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	public String getRelationship() {
		return this.relationship;
	}
	public void setRelationship(final String relationship) {
		this.relationship = relationship;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	@NotBlank
	public String getGenre() {
		return this.genre;
	}
	public void setGenre(final String genre) {
		this.genre = genre;
	}

	@NotNull
	@Valid
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
	public void setCoordinate(final Coordinate coordinate) {
		this.coordinate = coordinate;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Sense>	giveSenses;
	private Collection<Sense>	reciveSenses;
	private SearchTemplate		searchTemplate;
	private CreditCard			creditCard;
	private Collection<Event>	events;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "sender")
	public Collection<Sense> getGiveSenses() {
		return this.giveSenses;
	}
	public void setGiveSenses(final Collection<Sense> giveSenses) {
		this.giveSenses = giveSenses;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "recipient")
	public Collection<Sense> getReciveSenses() {
		return this.reciveSenses;
	}
	public void setReciveSenses(final Collection<Sense> reciveSenses) {
		this.reciveSenses = reciveSenses;
	}

	@Valid
	@OneToOne(optional = true)
	public SearchTemplate getSearchTemplate() {
		return this.searchTemplate;
	}
	public void setSearchTemplate(final SearchTemplate searchTemplate) {
		this.searchTemplate = searchTemplate;
	}

	@Valid
	@OneToOne(optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Valid
	@NotNull
	@ManyToMany()
	public Collection<Event> getEvents() {
		return this.events;
	}
	public void setEvents(final Collection<Event> events) {
		this.events = events;
	}

}
