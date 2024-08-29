# Chapter 6 : Using aspects with Spring AOP (2/2)

---

이전 글에서는 AOP 에 대한 필수적인 지식과 Spring-aop 가 Proxy 를 이용해 작동하는 것을 알아보았다.

이번 글에서는 실제로 스프링에서 aspect 를 사용하는 방법에 대해 알아보자.

---

## Advices in Spring

스프링의 aspect 는 `spring-aspect` 혹은 `aspectj` 관련 라이브러리가 필요하다. (사실 `spring-aspect` 라이브러리가 `aspectjweaver` 라이브러리를 의존하고 있어서 뭐든 비슷하다.)

<!-- spring-aspect_dependency.png -->

<p align="center">
  <img src="../../images/chapter6/spring-aspect_dependency.png" width=80% height=80% />
</p>

또한 aspect 를 이용하기 위해선 스프링 설정 클래스에 `@EnableAspectJAutoProxy` 를 붙여야 한다.

```java
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class Config {}
```

또한 우리 관심사의 집합인 Aspect 에 해당하는 클래스를 만들어야 하는데, 해당 클래스는 스프링의 bean 이어야 하며 `@Aspect` 어노테이션이 붙어야 한다.

```java
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component  
// Aspect has to be bean so that spring invoke advice
public class SomeAspect {
    /* ... */
}
```

> `@EnableAspectJAutoProxy` 잘못 붙여도 실행 되던데요??
>
> 스프링 공식 문서를 보면 `@EnableAspectJAutoProxy` 는 스프링 설정 클래스에 붙어야 한다고 적혀있다.
>
> 하지만 찾아본 결과 Spring-boot 에서 `@Aspect` 어노테이션이 붙은 bean 이 발견되면, 스프링이 알아서 `@EnableAspectJAutoProxy` 를 붙여주는 것을 확인했다. [`[1]`](#reference)
>
> 때문에 비록 boot 가 아니지만 위와 비슷한 내부 처리로 스프링이 알아서 붙여주는 것 같다.

스프링은 대표적인 5 종류의 Advice 를 사용할 수 있다. [`[2]`](#reference)

| **Advice**          | **Description**                                                |
|---------------------|----------------------------------------------------------------|
| `@Before`           | 메서드 실행 이전에 실행되고픈 어드바이스                                         |
| `@AfterReturning`   | 메서드가 정상적으로 실행 후, 호출된 곳으로 실행 결과 `(return)` 를 전달하기 전 실행되고픈 어드바이스 |
| `@AfterThrowing`    | 메서드 실행 중 에러가 throw 되었을 때 실행되고픈 어드바이스                           |
| `@After, (finally)` | 메서드 실행 (throw 포함) 후, 실행되고픈 어드바이스 (`try-finally` 와 동치)          |
| `@Around`           | 메서드 실행 이전, 에러 throw 후, 실행 후 등 위 모든 어드바이스 를 포괄할 수 있는 어드바이스      |

이를 직접 확인해보자.

<details><summary> [자세한 코드]</summary>

```java
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

```

```java
@Component
public class Bean    {
    public String someMethod(Object arg)  {
        System.out.print("[AT BEAN] someMethod in Process with arg : ");
        System.out.println(arg.toString());
        return "someMethod";
    }
}
```

</details>

```java
var context
        = new AnnotationConfigApplicationContext(
        Config.class
);

Bean bean = context.getBean(Bean.class);

System.out.println("--------------------------------------");
System.out.println("[ON MAIN] Before calling someMethod");
try {
    String returned1 = bean.someMethod("given parameter");

    System.out.println("[ON MAIN] Returned: " + returned1);
    System.out.println("--------------------------------------");

    System.out.println("[ON MAIN] Before calling someMethod");
    String returned2 = bean.someMethod(null);

    System.out.println("[ON MAIN] Returned: " + returned2);
}
catch (Exception e) {
    System.out.println("[ON MAIN] Exception occurred in method!");
}

System.out.println("--------------------------------------");
```
```
--------------------------------------
[ON MAIN] Before calling someMethod
<== Around Advice Invoked!

<== Before Advice Invoked!
[AT BEAN] someMethod in Process with arg : given parameter
<== AfterReturning Advice Invoked!
<== After Advice Invoked!

<== Releasing Around Advice!

[ON MAIN] Returned: someMethod
--------------------------------------
[ON MAIN] Before calling someMethod
<== Around Advice Invoked!

<== Before Advice Invoked!
[AT BEAN] someMethod in Process with arg : <== AfterThrowing Advice Invoked!
<== After Advice Invoked!

[ON MAIN] Exception occurred in method!
--------------------------------------
```

---

### `@Before`, `@AfterReturning` Advice

`@Before` 어드바이스는 우리가 지정한 Pointcut 이 실행되기 전에 실행되며, `@AfterReturning` 은 실행 후, 호출된 곳으로 반환값을 전달하기 전 실행된다.

```java
@Before("execution(* practice.advice_example.Bean.*(*))")
public void beforeAdvice() {
    System.out.println("\n<== Before Advice Invoked!");
}

@AfterReturning("execution(* practice.advice_example.Bean.*(*))")
public void afterAdvice() {
    System.out.println("<== AfterReturning Advice Invoked!");
}
```

위 코드를 보면 `... ("execution(* practice.advice_example.Bean.*(*))")` 과 같은 표현식이 있는데, 이는 `AspectJ` 식 Pointcut 표현식으로, 표현식을 잉요해 프로그램상 모든 Join Point 중 특정한 것들을 지칭할 수 있다.

스프링 aspect 는 기본적으로 `AspectJ` 표현식으로 Pointcut 을 지칭하며 메서드의 이름, 메서드의 반환 타입, 심지어 메서드의 인자 타입에 따라 Pointcut 을 추릴 수 있다.

```
--------------------------------------
[ON MAIN] Before calling someMethod
<== Around Advice Invoked!

<== Before Advice Invoked!
[AT BEAN] someMethod in Process with arg : given parameter
<== AfterReturning Advice Invoked!
<== After Advice Invoked!

<== Releasing Around Advice!

[ON MAIN] Returned: someMethod
--------------------------------------
[ON MAIN] Before calling someMethod
<== Around Advice Invoked!

<== Before Advice Invoked!
[AT BEAN] someMethod in Process with arg : <== AfterThrowing Advice Invoked!
<== After Advice Invoked!

[ON MAIN] Exception occurred in method!
--------------------------------------
```

그래서 위 출력을 보면 `[AT BEAN]` 이 출력되기 전, `<== Before Advice Invoked!` 가 출력되고, `[ON MAIN]` 에서 메서드 반환값인 `someMethod` 가 출력되기 전, `<== After Advice Invoked!` 가 출력되는 것을 볼 수 있다.

---

### `@AfterThrowing`, `@After` Advice

만약 메서드가 실행 중 에러가 발생하면 어떨까?

```java
try {
    System.out.println("------------------------------");

    System.out.println("[ON MAIN] Before calling someMethod");
    String returned2 = bean.someMethod(null);

    System.out.println("[ON MAIN] Returned: " + returned2);
}
catch (Exception e) {
    System.out.println("[ON MAIN] Exception occurred in method!");
}

System.out.println("------------------------------");
```

위 코드는 `bean.someMethod(null)` 을 실행하여 의도적으로 `NullPointException` 을 일으킨다.

이전 `@Before` 어드바이스 경우, 메서드 실행 전에 사용되므로 아무런 문제가 없다.

하지만 `@AfterReturning` 어드바이스는 실행 중 에러로 인해 중단되었으므로, 해당 어드바이스가 실행되지 않는다.

---

### `@Around` Advice 와 `JoinPoint`, `ProceedingJoinPoint`

---

아직 스프링 aop 에 관해 설명하지 않았지만, 먼저 미리보기로 위 개념을 코드에 합쳐서 보이면 다음과 같다.

[//]: # (TODO_imp spring-aop & aop terminology 합쳐서 코드 그림으로 보여주기)

---

---

## Reference

- [`[1] : Spring AOP works without @EnableAspectJAutoProxy? - StackOverflow`](https://stackoverflow.com/questions/48625149/spring-aop-works-without-enableaspectjautoproxy)