package practice.pcd_example.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import practice.pcd_example.some_package.Bean2;

@Aspect
@Component
public class MyAspect3 {
    @Pointcut("args(*)")
    public void onSingleArg()   {}

    @Before("onSingleArg() && args(value)")
    public void beforeIntSingleArg(Integer value) {
        System.out.println("<== Before singleArg(int) Invoked!! : " + value);
    }

    @Before("onSingleArg() && args(practice.pcd_example.some_package.Bean2) && args(bean2)")
    public void beforeBeanSingleArg(Bean2 bean2)   {
        System.out.println("<== Before beanSingleArg() Invoked!!! : " + bean2);
    }

    @Pointcut("target(practice.pcd_example.some_package.Bean2) && !execution(* toString(..))")
    public void onTarget()  {}

    @After("onTarget()")
    public void afterTarget() {
        System.out.println("<== Target were USED!!");
    }
}
