package practice.advice_example;

import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

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

    @Around("execution(* practice.advice_example.Bean.*(*))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("<== Around Advice Invoked!");
        Object proceed = joinPoint.proceed();
        System.out.println("<== Releasing Around Advice!\n");
        return proceed;
    }
}
