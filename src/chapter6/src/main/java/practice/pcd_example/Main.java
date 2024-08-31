package practice.pcd_example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import practice.pcd_example.some_package.Bean1;
import practice.pcd_example.some_package.Bean2;
import practice.pcd_example.some_package.ServiceBean;


public class Main {
    public static void main(String[] args)  {
        var context
                = new AnnotationConfigApplicationContext(Config.class);

        Bean1 bean1 = context.getBean(Bean1.class);
        ServiceBean serviceBean = context.getBean(ServiceBean.class);
        Bean2 bean2 = context.getBean(Bean2.class);

        bean1.helloWorld();                 // with only MyAspect1

        System.out.println();
        serviceBean.weiredMethod();         // MyAspect2

        bean2.singleArgMethod(10);     // MyAspect3

        System.out.println();
        bean2.singleArgMethod(bean2);       // MyAspect3
    }
}
