package wdm.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.util.Locale;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

//	@Bean
//	public Jackson2ObjectMapperBuilder jacksonBuilder() {
//		final Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
//		mapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//		mapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
//		// Set the dateformat to the ISO 8601 standard
//		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
//		mapperBuilder.dateFormat(dateFormat);
//
//		return mapperBuilder;
//	}
}
