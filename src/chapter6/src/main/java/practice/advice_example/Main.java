package practice.advice_example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context
                = new AnnotationConfigApplicationContext(
                Config.class
        );

        Bean bean = context.getBean(Bean.class);

        System.out.println("--------------------------------------");
        System.out.println("[ON MAIN] Before calling someMethod");
        String returned1 = bean.someMethod("given parameter");

        System.out.println("[ON MAIN] Returned: " + returned1);
        try {
            System.out.println("--------------------------------------");

            System.out.println("[ON MAIN] Before calling someMethod");
            String returned2 = bean.someMethod(null);

            System.out.println("[ON MAIN] Returned: " + returned2);
        }
        catch (Exception e) {
            System.out.println("[ON MAIN] Exception occurred in method!");
        }

        System.out.println("--------------------------------------");
    }
}
