
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
	private Double	feeManager;
	private Double	feeChorbi;


	@NotNull
	@Min(0)
	public Double getFeeManager() {
		return this.feeManager;
	}

	public void setFeeManager(final Double feeManager) {
		this.feeManager = feeManager;
	}

	@NotNull
	@Min(0)
	public Double getFeeChorbi() {
		return this.feeChorbi;
	}

	public void setFeeChorbi(final Double feeChorbi) {
		this.feeChorbi = feeChorbi;
	}

	@NotBlank
	@Pattern(regexp = "\\d{2}\\:\\d{2}\\:\\d{2}?")
	public String getTime() {
		return this.time;
	}

	public void setTime(final String time) {
		this.time = time;
	}

	// Relationships ----------------------------------------------------------

}
