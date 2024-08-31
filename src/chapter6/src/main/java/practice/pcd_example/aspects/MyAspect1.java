package practice.pcd_example.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
//@Component    // it's too messy...
public class MyAspect1 {
    @Pointcut("execution(* *(..))")
    // Pointcut methods should have empty body
    public void onAnyBeanMethod()    {
        System.out.println("I WANT TO BE EXECUTED!!!!");
    }

    @Before("onAnyBeanMethod()")
    // Advice references `onAnyBeanMethod` pointcut
    public void before() {
        System.out.println("<== Before Advice Invoked!");
    }

    @After("onAnyBeanMethod()")
    // Advice references `onAnyBeanMethod` pointcut
    public void after() {
        System.out.println("<== After Advice Invoked!");
    }
}
