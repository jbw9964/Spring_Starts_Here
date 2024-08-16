package practice.bean_annot.direct_method_call;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        System.out.println("Un-Wired example : ");
        System.out.println("-------------------");
        showUnWiredBeans();
        System.out.println("-------------------");
        System.out.println();

        System.out.println("Wired example : ");
        System.out.println("-------------------");
        showWiredBeans();
        System.out.println("-------------------");
        System.out.println();
    }

    static void showUnWiredBeans()  {
        var context = new AnnotationConfigApplicationContext(UnWiredConfig.class);

        Parrot parrot = context.getBean(Parrot.class);
        Person person = context.getBean(Person.class);

        System.out.println(parrot);
        System.out.println(person);
    }

    static void showWiredBeans()    {
        var context = new AnnotationConfigApplicationContext(WiredConfig.class);

        Parrot parrot = context.getBean(Parrot.class);
        Person person = context.getBean(Person.class);

        System.out.println(parrot);
        System.out.println(person);
    }

    static void showProperties(Object ... objs) {
        Arrays.stream(objs)
              .map(o -> String.format(
                      "[%s] \t: 0x%8x", o.getClass()
                                         .getSimpleName(), System.identityHashCode(o)))
              .forEach(System.out::println);
    }
}
