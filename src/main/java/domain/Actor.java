
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Actor() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private Date	moment;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "(\\+\\d{1,3} )?(\\(\\d{1,3}\\) )?(\\w{4,})?")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	// Relationships ----------------------------------------------------------
	private UserAccount			userAccount;
	private Collection<Chirp>	sentChirps;
	private Collection<Chirp>	reciveChirps;
	private CreditCard			creditCard;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "sender")
	public Collection<Chirp> getSentChirps() {
		return this.sentChirps;
	}
	public void setSentChirps(final Collection<Chirp> sentChirps) {
		this.sentChirps = sentChirps;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "recipients")
	public Collection<Chirp> getReciveChirps() {
		return this.reciveChirps;
	}
	public void setReciveChirps(final Collection<Chirp> reciveChirps) {
		this.reciveChirps = reciveChirps;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String maskEmailAndPhone(final String string) {

		final String masked = string.replaceAll("[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]", "***");
		final String masked2 = masked.replaceAll("(\\+\\d{1,3})?(\\(\\d{1,3}\\) )?(\\d{3,})", "***");

		return masked2;
	}

	// Relationships ----------------------------------------------------------

	@Valid
	@OneToOne(optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
