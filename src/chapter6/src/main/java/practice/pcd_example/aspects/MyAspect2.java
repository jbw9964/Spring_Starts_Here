package practice.pcd_example.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect2 {
    @Pointcut("within(practice.pcd_example..*)")
    public void withInPackage() {}

    @Before("withInPackage()")
    public void inPackage() {
        System.out.println("<<== This method in under practice.pcd_example package!!");
    }

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void withInServiceAnnotatedBean() {}

    @Before("withInServiceAnnotatedBean()")
    public void beforeService() {
        System.out.println("<<== This method is under @Service annotated bean!!");
    }

    @Pointcut("@annotation(MyAnnotation)")
    public void onAnnotation()  {}

    @Before("onAnnotation()")
    public void beforeAnnot()   {
        System.out.println("<<== This method is annotated with @MyAnnotation!!");
    }
}
