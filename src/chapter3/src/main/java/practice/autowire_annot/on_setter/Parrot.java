package practice.autowire_annot.on_setter;

import org.springframework.stereotype.Component;

@Component
public class Parrot {
    private String name = "setter parrot";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Parrot : " + name;
    }
}
