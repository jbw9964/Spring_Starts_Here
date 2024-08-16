package practice.bean_annot.method_param;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    Parrot parrot() {
        Parrot parrot = new Parrot();
        parrot.setName("Param Parrot");
        return parrot;
    }

    @Bean
    Person person(Parrot p) {
        Person person = new Person();
        person.setName("Param Person");
        person.setParrot(p);
        return person;
    }
}
