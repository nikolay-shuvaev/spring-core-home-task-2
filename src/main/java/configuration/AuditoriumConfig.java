package configuration;

import entities.Auditorium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by NICK on 10.01.2017.
 */
@Configuration
@PropertySource(value = {"classpath:auditoriums/auditoriums-center.properties", "classpath:auditoriums/auditoriums-east.properties"})
public class AuditoriumConfig {

    @Value("${center.auditorium.1.vipSeats}")
    private Set<Integer> vipSeats;

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySource() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public List<Auditorium> auditoriumList(Environment environment) {
        String name = environment.getProperty("east.auditorium.1.name");
        Integer numberOfSeats = environment.getProperty("east.auditorium.1.numberOfSeats", Integer.class);

        return Arrays.asList(new Auditorium(name, numberOfSeats, vipSeats));
    }

    @Bean
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        return new ConversionServiceFactoryBean();
    }
}
