package practice.bean_annot.direct_method_call;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnWiredConfig {
    @Bean
    Parrot parrot() {
        Parrot parrot = new Parrot();
        parrot.setName("un-wired parrot");
        return parrot;
    }

    @Bean
    Person person() {
        Person person = new Person();
        person.setName("un-wired person");
        return person;
    }
}
