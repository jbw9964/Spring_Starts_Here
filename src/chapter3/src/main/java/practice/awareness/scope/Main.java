package practice.awareness.scope;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        var context
                = new AnnotationConfigApplicationContext(
                Config.class
        );

        Parrot parrot = context.getBean(Parrot.class);
        Person person1 = context.getBean("person1", Person.class);
        Person person2 = context.getBean("person2", Person.class);

        System.out.println(parrot);
        System.out.println(person1);
        System.out.println(person2);

        System.out.println();
        showProperties(person1, person2);

        System.out.println();
        showProperties(parrot, person1.getParrot(), person2.getParrot());
    }

    static void showProperties(Object... objs) {
        Arrays.stream(objs)
              .map(o -> String.format("[%s] \t: 0x%8x",
                                      o.getClass().getSimpleName(),
                                      System.identityHashCode(o)))
              .forEach(System.out::println);
    }
}
