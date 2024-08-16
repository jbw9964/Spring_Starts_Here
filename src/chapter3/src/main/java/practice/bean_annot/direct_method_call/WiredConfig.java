package practice.bean_annot.direct_method_call;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiredConfig {
    @Bean
    Parrot parrot() {
        Parrot parrot = new Parrot();
        parrot.setName("wired parrot");
        return parrot;
    }

    @Bean
    Person person() {
        Person person = new Person();
        person.setName("wired person");
        person.setParrot(parrot());
        return person;
    }
}
