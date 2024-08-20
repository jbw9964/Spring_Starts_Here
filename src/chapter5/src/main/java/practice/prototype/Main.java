package practice.prototype;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        var context
                = new AnnotationConfigApplicationContext(Config.class);

        var bean1 = context.getBean(PrototypeBean.class);
        var bean2 = context.getBean(PrototypeBean.class);
        var bean3 = context.getBean(PrototypeBean.class);

        showProperties(bean1, bean2, bean3);
    }

    static void showProperties(Object... objs) {
        Arrays.stream(objs)
              .map(o -> String.format("[%s] \t: 0x%8x",
                                      o.getClass().getSimpleName(),
                                      System.identityHashCode(o)))
              .forEach(System.out::println);
    }
}
