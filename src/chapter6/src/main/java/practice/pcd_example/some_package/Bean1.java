package practice.pcd_example.some_package;

import org.springframework.stereotype.Component;
import practice.pcd_example.aspects.MyAnnotation;

@Component
public class Bean1 {
    @MyAnnotation
    public void helloWorld()    {
        System.out.println("[AT BEAN1] : Hello World!");
    }
}
