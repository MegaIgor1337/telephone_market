package market.config;

import lombok.RequiredArgsConstructor;
import market.entity.*;
import market.enums.*;
import market.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@ComponentScan(value = {"market.repository"})
@RequiredArgsConstructor
public class SpringConfig {

}
