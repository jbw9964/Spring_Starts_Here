package practice.autowire_annot.on_const;

import org.springframework.stereotype.Component;

@Component
public class Parrot {
    private String name = "const parrot";

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
