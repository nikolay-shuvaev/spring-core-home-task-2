package configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import services.strategies.DiscountStrategy;

import java.util.List;

/**
 * Created by Nikolai_Shuvaev on 1/9/2017.
 */
@Configuration
@ComponentScan({"dao", "services"})
public class AppConfig {


}
