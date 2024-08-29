package practice.advice_example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SomeAspect {
    @Before("execution(* practice.advice_example.Bean.*(*))")
    public void beforeAdvice() {
        System.out.println("\n<== Before Advice Invoked!");
    }

    @AfterReturning("execution(* practice.advice_example.Bean.*(*))")
    public void afterAdvice() {
        System.out.println("<== AfterReturning Advice Invoked!");
    }

    @AfterThrowing("execution(* practice.advice_example.Bean.*(*))")
    public void afterThrowAdvice() {
        System.out.println("<== AfterThrowing Advice Invoked!");
    }

    @After("execution(* practice.advice_example.Bean.*(*))")
    public void afterAdvice(JoinPoint joinPoint) {
        System.out.println("<== After Advice Invoked!\n");
    }
}
