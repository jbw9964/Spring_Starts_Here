package practice.multiple_beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Config {
    @Bean
    Parrot parrot1()    {
        Parrot p = new Parrot();
        p.setName("First parrot");
        return p;
    }

    @Bean
    Parrot parrot2()    {
        Parrot p = new Parrot();
        p.setName("Second parrot");
        return p;
    }

    @Bean
    @Primary
    Person person(Parrot parrot1)   {
        Person p = new Person();
        p.setName("Person");
        p.setParrot(parrot1);
        return p;
    }
}
