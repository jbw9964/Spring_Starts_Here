package practice.using_stereotype;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        var context
                = new AnnotationConfigApplicationContext(Config.class);

        Parrot p = context.getBean(Parrot.class);
        String hello = context.getBean(String.class);

        System.out.println(p.getClass().getSimpleName() + " : " + p);
        System.out.println(hello);
    }
}