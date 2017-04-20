
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Configuration() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	time;


	//	private Collection<String>	banners;

	@NotBlank
	@Pattern(regexp = "\\d{2}\\:\\d{2}\\:\\d{2}?")
	public String getTime() {
		return this.time;
	}

	public void setTime(final String time) {
		this.time = time;
	}

	//	@NotNull
	//	//@URL
	//	@ElementCollection
	//	public Collection<String> getBanners() {
	//		return this.banners;
	//	}
	//
	//	public void setBanners(final Collection<String> banners) {
	//		this.banners = banners;
	//	}

	// Relationships ----------------------------------------------------------

}
