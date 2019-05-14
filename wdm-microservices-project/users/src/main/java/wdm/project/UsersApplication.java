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
}
