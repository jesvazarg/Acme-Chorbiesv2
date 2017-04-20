
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Person extends Actor {

	// Constructors -----------------------------------------------------------
	public Person() {
		super();
	}


	// Attributes -------------------------------------------------------------

	// Relationships ---------------------------------------------------------- 
	private Collection<Chirp>	sentChirps;
	private Collection<Chirp>	reciveChirps;


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

}
