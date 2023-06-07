package market.config;

import lombok.RequiredArgsConstructor;
import market.mapper.address.CreateAddressMapper;
import market.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"market.repository"})
@RequiredArgsConstructor
public class SpringConfig {

}
