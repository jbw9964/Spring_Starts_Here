package practice.autowire_annot.on_const;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(Config.class);

        Parrot parrot = context.getBean(Parrot.class);
        Person person = context.getBean(Person.class);

        System.out.println(parrot);
        System.out.println(person);
    }

    static void showProperties(Object... objs) {
        Arrays.stream(objs)
              .map(o -> String.format(
                      "[%s] \t: 0x%8x", o.getClass()
                                         .getSimpleName(), System.identityHashCode(o)))
              .forEach(System.out::println);
    }
}
