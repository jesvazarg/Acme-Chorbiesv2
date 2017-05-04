
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

public class CreateChorbiForm {

	//Solo meto los datos que quiero que el usuario tenga que rellenar
	//Las relaciones tambien se pueden meter
	//Cada atributo tendra su get y set con las mismas restricciones que en el dominio
	//Con SafeHtml es para seguridad que tambien se le puede meter en el dominio

	private String	username;
	private String	password;
	private String	confirmPassword;
	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	picture;
	private String	description;
	private String	relationship;
	private Date	birthDate;
	private String	genre;
	private String	city;				//Tengo que separar los 4 atributos de Coordinate aqui tambien
	private String	country;
	private String	state;
	private String	province;
	private Boolean	isAgree;


	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "(\\+\\d{1,3} )?(\\(\\d{1,3}\\) )?(\\w{4,})")
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
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

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getCity() {
		return this.city;
	}
	public void setCity(final String city) {
		this.city = city;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getCountry() {
		return this.country;
	}
	public void setCountry(final String country) {
		this.country = country;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getState() {
		return this.state;
	}
	public void setState(final String state) {
		this.state = state;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getProvince() {
		return this.province;
	}
	public void setProvince(final String province) {
		this.province = province;
	}

	public Boolean getIsAgree() {
		return this.isAgree;
	}
	public void setIsAgree(final Boolean isAgree) {
		this.isAgree = isAgree;
	}

}
