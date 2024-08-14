package practice.using_beans;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        var context /*
                    Create a new AnnotationConfigApplicationContext,
                    deriving bean definitions from the given component classes
                    and automatically refreshing the context.
                    */ = new AnnotationConfigApplicationContext(Config.class);

        /*
        Return the bean instance that uniquely matches the given object type, if any.
        */
        Parrot p = context.getBean(Parrot.class);
        // org.springframework.beans.factory.NoUniqueBeanDefinitionException
        // No qualifying bean of type 'practice.using_beans.Parrot' available:
        // expected single matching bean but found 2: parrot1,parrot2

        String str = context.getBean(String.class);
        // org.springframework.beans.factory.NoSuchBeanDefinitionException
        // No qualifying bean of type 'java.lang.String' available


    }
}