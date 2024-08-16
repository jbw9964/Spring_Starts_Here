package practice.awareness.scope;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    Parrot parrot() {
        Parrot parrot = new Parrot();
        parrot.setName("I am parrot");
        return parrot;
    }

    @Bean
    Person person1(Parrot p)    {
        Person person = new Person();
        person.setParrot(p);
        person.setName("First person");
        return person;
    }

    @Bean
    Person person2(Parrot p)    {
        Person person = new Person();
        person.setParrot(p);
        person.setName("Second person");
        return person;
    }
}
