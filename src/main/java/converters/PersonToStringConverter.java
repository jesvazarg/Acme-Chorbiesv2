
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Person;

@Component
@Transactional
public class PersonToStringConverter implements Converter<Person, String> {

	@Override
	public String convert(final Person person) {
		String result;

		if (person == null)
			result = null;
		else
			result = String.valueOf(person.getId());
		return result;
	}

}
