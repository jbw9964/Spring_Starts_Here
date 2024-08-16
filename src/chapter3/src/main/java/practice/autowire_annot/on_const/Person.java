package practice.autowire_annot.on_const;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Person {
    private String name = "const person";

    // parrot can be final with constructor injection
    private final Parrot parrot;

    @Autowired  // inject dependency on constructor
    public Person(Parrot parrot) {
        this.parrot = parrot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parrot getParrot() {
        return parrot;
    }

    @Override
    public String toString() {
        return "Person : " + name + ", " + parrot;
    }
}
