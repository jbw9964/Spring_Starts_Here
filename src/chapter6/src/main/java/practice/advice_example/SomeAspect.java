package practice.advice_example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class SomeAspect {
    private static void showJoinPointProperties(JoinPoint joinPoint) {
        System.out.println("\t -----------------------------------------------------------------------------------------------");
        System.out.println("\t| JoinPoint Info\t----: " + joinPoint + "\t\t|");
        System.out.println("\t|\tJoinPoint Kind\t\t--: " + joinPoint.getKind()+ "\t\t\t\t\t\t\t\t\t\t\t\t\t|");
        System.out.println("\t|\tProxy Object\t\t--: " + joinPoint.getThis()+ "\t\t\t\t\t\t\t\t|");
        System.out.println("\t|\tTarget Object\t\t--: " + joinPoint.getTarget()+ "\t\t\t\t\t\t\t\t|");
        System.out.println("\t|\tMethod Arguments\t--: " + String.format("%-20s", Arrays.toString(joinPoint.getArgs()))+ "\t\t\t\t\t\t\t\t\t\t\t\t|");
        System.out.println("\t -----------------------------------------------------------------------------------------------");
    }

    @Before("execution(* practice.advice_example.Bean.*(*))")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("\n<== Before Advice Invoked!");
        showJoinPointProperties(joinPoint);
    }

    @AfterReturning("execution(* practice.advice_example.Bean.*(*))")
    public void afterReturningAdvice(JoinPoint joinPoint) {
        System.out.println("<== AfterReturning Advice Invoked!");
    }

    @AfterThrowing("execution(* practice.advice_example.Bean.*(*))")
    public void afterThrowAdvice(JoinPoint joinPoint) {
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
