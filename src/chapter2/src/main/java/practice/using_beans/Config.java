package practice.using_beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/*
Indicates that a class declares one or more @Bean methods
and may be processed by the Spring container to generate bean definitions
and service requests for those beans at runtime,
*/
@Configuration
public class Config {

    /*
    Indicates that a method produces a bean to be managed
    by the Spring container.
     */
    @Bean
    Parrot parrot1()    {
        Parrot p = new Parrot();
        p.setName("I am First Parrot!!!");
        return p;
    }

    @Bean
    @Primary
    Parrot parrot2()    {
        Parrot p = new Parrot();
        p.setName("I am Second Parrot!!!");
        return p;
    }
}
