package practice.singleton;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        var context
                = new AnnotationConfigApplicationContext(Config.class);

        for (String names : context.getBeanDefinitionNames())
            System.out.println(names);

        SingletonBean bean1 = context.getBean(SingletonBean.class);
        SingletonBean bean2 = context.getBean(SingletonBean.class);
        SingletonBean bean3 = context.getBean(SingletonBean.class);

        showProperties(bean1, bean2, bean3);

        System.out.println("------------------------------");
        for (String names : context.getBeanDefinitionNames())
            System.out.println(names);
    }

    static void showProperties(Object... objs) {
        Arrays.stream(objs)
              .map(o -> String.format("[%s] \t: 0x%8x",
                      o.getClass().getSimpleName(),
                      System.identityHashCode(o)))
              .forEach(System.out::println);
    }
}
