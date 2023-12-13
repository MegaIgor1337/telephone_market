package market.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"market.model.repository"})
@RequiredArgsConstructor
public class SpringConfig {

}
