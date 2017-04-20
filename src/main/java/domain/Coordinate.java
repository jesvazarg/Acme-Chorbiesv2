
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Coordinate {

	// Constructors -----------------------------------------------------------
	public Coordinate() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	city;
	private String	country;
	private String	state;
	private String	province;


	@NotBlank
	public String getCity() {
		return this.city;
	}
	public void setCity(final String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}
	public void setCountry(final String country) {
		this.country = country;
	}

	public String getState() {
		return this.state;
	}
	public void setState(final String state) {
		this.state = state;
	}

	public String getProvince() {
		return this.province;
	}
	public void setProvince(final String province) {
		this.province = province;
	}

}
