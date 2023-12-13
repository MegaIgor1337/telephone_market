package market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@EntityScan("market/model/entity")
public class  ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class);
    }
}
