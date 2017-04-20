
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "copy")
})
public class Chirp extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Chirp() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String				subject;
	private String				text;
	private Collection<String>	attachments;
	private Date				moment;
	private Boolean				copy;


	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public Boolean getCopy() {
		return this.copy;
	}

	public void setCopy(final Boolean copy) {
		this.copy = copy;
	}


	// Relationships ----------------------------------------------------------
	private Person				sender;
	private Collection<Person>	recipients;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Person getSender() {
		return this.sender;
	}

	public void setSender(final Person sender) {
		this.sender = sender;
	}

	@NotNull
	@Valid
	@ManyToMany()
	public Collection<Person> getRecipients() {
		return this.recipients;
	}

	public void setRecipients(final Collection<Person> recipients) {
		this.recipients = recipients;
	}

}
