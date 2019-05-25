package wdm.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class StocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksApplication.class, args);
	}
}
