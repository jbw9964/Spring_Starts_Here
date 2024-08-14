package practice.using_stereotype;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Parrot {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @PostConstruct
    public void init() {
        System.out.println("post constructing parrot...");
        name = "I am post-constructed Parrot!!!";
    }
}
