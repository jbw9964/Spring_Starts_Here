package practice.using_stereotype;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "practice.using_stereotype")
public class Config {
    @Bean
    String hello()    {
        return "Hello World";
    }

    @Bean
    List<?> list()  {
        return new ArrayList<>();
    }
}
