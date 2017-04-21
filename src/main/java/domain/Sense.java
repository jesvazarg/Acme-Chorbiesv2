
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Sense extends DomainEntity {

	// Constructors -----------------------------------------------------------
	public Sense() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private Date	moment;
	private String	comment;
	private Integer	stars;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public String getComment() {
		return this.comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
	}

	@NotNull
	@Range(min = 0, max = 3)
	public Integer getStars() {
		return this.stars;
	}
	public void setStars(final Integer stars) {
		this.stars = stars;
	}


	// Relationships ----------------------------------------------------------
	private Chorbi	sender;
	private Chorbi	recipient;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Chorbi getSender() {
		return this.sender;
	}
	public void setSender(final Chorbi sender) {
		this.sender = sender;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Chorbi getRecipient() {
		return this.recipient;
	}
	public void setRecipient(final Chorbi recipient) {
		this.recipient = recipient;
	}

}
