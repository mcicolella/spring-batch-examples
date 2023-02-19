package net.emmecilab.spring.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchCsvReadWriteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchCsvReadWriteApplication.class, args);
	}

}
