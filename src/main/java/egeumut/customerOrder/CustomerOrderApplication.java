package egeumut.customerOrder;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerOrderApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper(){	//IoC Service registration
		return new ModelMapper();
	}
}
