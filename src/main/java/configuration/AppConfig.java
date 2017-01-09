package configuration;

import dao.OccupiedSeatsDao;
import dao.ScheduleTableDao;
import entities.Auditorium;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import services.AuditoriumService;
import services.impl.AuditoriumServiceImpl;
import services.strategies.BirthdayDiscountStrategy;
import services.strategies.DiscountStrategy;
import services.strategies.SoldTicketDiscountStrategy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Nikolai_Shuvaev on 1/9/2017.
 */
@Configuration
@ComponentScan({"dao", "services"})
@Import(AuditoriumConfig.class)
public class AppConfig {
    @Bean
    public BirthdayDiscountStrategy birthdayDiscountStrategy() {
        return new BirthdayDiscountStrategy(10, 5);
    }

    @Bean
    public SoldTicketDiscountStrategy soldTicketDiscountStrategy() {
        return new SoldTicketDiscountStrategy(50, 10);
    }

    @Bean
    public List<DiscountStrategy> discountStrategies() {
        return Arrays.asList(birthdayDiscountStrategy(), soldTicketDiscountStrategy());
    }

}