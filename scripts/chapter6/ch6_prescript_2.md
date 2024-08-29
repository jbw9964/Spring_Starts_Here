# Chapter 6 : Using aspects with Spring AOP (2/2)

---

이전 글에서는 AOP 에 대한 필수적인 지식과 Spring-aop 가 

---

## Advices in Spring

이제 aspect 사용법에 대해 알아보자.

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

| **Advice**          | **Description**                                                 |
|---------------------|-----------------------------------------------------------------|
| `@Before`           | 메서드 실행 이전에 실행되고픈 Advice                                         |
| `@AfterReturning`   | 메서드가 정상적으로 실행 후, 호출된 곳으로 실행 결과 `(return)` 를 전달하기 전 실행되고픈 Advice |
| `@AfterThrowing`    | 메서드 실행 중 에러가 throw 되었을 때 실행되고픈 Advice                           |
| `@After, (finally)` | 메서드 실행 (throw 포함) 후, 실행되고픈 Advice (`try-finally` 와 동치)          |
| `@Around`           | 메서드 실행 이전, 에러 throw 후, 실행 후 등 위 모든 Advice 를 포괄할 수 있는 Advice     |

이를 직접 확인해보자.

<details><summary> [자세한 코드]</summary>

```java
import org.aspectj.lang.JoinPoint;

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

<== Before Advice Invoked!
[AT BEAN] someMethod in Process with arg : given parameter
<== AfterReturning Advice Invoked!
<== After Advice Invoked!

[ON MAIN] Returned: someMethod
--------------------------------------
[ON MAIN] Before calling someMethod

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

위 코드를 보면 `... ("execution(* practice.advice_example.Bean.*(*))")` 과 같은 표현식이 있는데, 이는 `AspectJ` 식 Pointcut 표현식으로, 프로그램상 모든 Join Point 중 특정한 것들을 지칭할 수 있다.

스프링 aspect 는 기본적으로 `AspectJ` 표현식으로 Pointcut 을 지칭하며 메서드의 이름, 메서드의 반환 타입, 심지어 메서드의 인자에 따라 Pointcut 을 추릴 수 있다.

---

### `@AfterThrowing`, `@After` Advice

---

### `@Around` Advice

---

아직 스프링 aop 에 관해 설명하지 않았지만, 먼저 미리보기로 위 개념을 코드에 합쳐서 보이면 다음과 같다.

[//]: # (TODO_imp spring-aop & aop terminology 합쳐서 코드 그림으로 보여주기)

---

> ?? `@EnableAspectJAutoProxy` 잘못 붙였는데도 실행 왜 됨???
> https://stackoverflow.com/questions/48625149/spring-aop-works-without-enableaspectjautoproxy
>
> 위 글에선 Spring Boot 일 때 `@SpringBootApplication` 에 섞여있다고 함. 여기까지는 나랑 상관 없는데 `AopAutoConfiguration.java` 보면
> 관련된 `@ConditionalOnClass` 어노테이션에 `Aspect.class` 가 있는 걸 볼 수 있음. `@ConditionalOnClass` 는 특정 class 파일이 존재하면 bean 을 등록하는
> 어노테이션이라고 함.
>
> 그래서 비록 boot 는 아니지만 뭔가 `@ConditionalOnClass` 비슷한 거 때문에 `@Aspect` 어노테이션 붙은 클래스 인식하고 자동으로 `@EnableAspectJAutoProxy` 붙여준
> 듯??


Spring AOP includes the following types of advice:

Before advice: Advice that runs before a join point but that does not have the ability to prevent execution flow
proceeding to the join point (unless it throws an exception).

After returning advice: Advice to be run after a join point completes normally (for example, if a method returns without
throwing an exception).

After throwing advice: Advice to be run if a method exits by throwing an exception.

After (finally) advice: Advice to be run regardless of the means by which a join point exits (normal or exceptional
return).

Around advice: Advice that surrounds a join point such as a method invocation. This is the most powerful kind of advice.
Around advice can perform custom behavior before and after the method invocation. It is also responsible for choosing
whether to proceed to the join point or to shortcut the advised method execution by returning its own return value or
throwing an exception.

---

## Reference

- [`[1] : Spring AOP works without @EnableAspectJAutoProxy? - StackOverflow`](https://stackoverflow.com/questions/48625149/spring-aop-works-without-enableaspectjautoproxy)