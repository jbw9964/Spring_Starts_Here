package practice.awareness.circular_dep;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        var context
                = new AnnotationConfigApplicationContext(Config.class);
    }

    static void showProperties(Object... objs) {
        Arrays.stream(objs)
              .map(o -> String.format("[%s] \t: 0x%8x",
                                      o.getClass().getSimpleName(),
                                      System.identityHashCode(o)))
              .forEach(System.out::println);
    }
}
