
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Sense;

@Component
@Transactional
public class SenseToStringConverter implements Converter<Sense, String> {

	@Override
	public String convert(final Sense sense) {
		String result;

		if (sense == null)
			result = null;
		else
			result = String.valueOf(sense.getId());
		return result;
	}

}
