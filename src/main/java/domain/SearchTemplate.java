
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class SearchTemplate extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public SearchTemplate() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String		relationship;
	private Integer		age;
	private String		genre;
	private Coordinate	coordinate;
	private String		keyword;
	private Date		updateMoment;


	public String getRelationship() {
		return this.relationship;
	}

	public void setRelationship(final String relationship) {
		this.relationship = relationship;
	}

	@Min(0)
	@Max(120)
	public Integer getAge() {
		return this.age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	public String getGenre() {
		return this.genre;
	}

	public void setGenre(final String genre) {
		this.genre = genre;
	}

	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(final Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getUpdateMoment() {
		return this.updateMoment;
	}

	public void setUpdateMoment(final Date updateMoment) {
		this.updateMoment = updateMoment;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Chorbi>	results;


	@Valid
	@NotNull
	@ManyToMany()
	public Collection<Chorbi> getResults() {
		return this.results;
	}

	public void setResults(final Collection<Chorbi> results) {
		this.results = results;
	}

}
