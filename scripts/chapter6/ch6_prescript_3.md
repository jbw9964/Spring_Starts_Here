# Chapter 6 : Using aspects with Spring AOP (3/3)

---

이전 글은 스프링에서 사용할 수 있는 어드바이스들에 대해 알아보았다.

이번 글은 어드바이스의 Pointcut 을 지칭하는 _**PCD (Pointcut Designators)**_ 와 어드바이스에 매개변수를 전달하는 방법에 대해 알아보자.

---

## Supported Pointcut Designators

포인트 컷 지시자 _(PCD)_ 는 `@Pointcut` 어노테이션 혹은 `@Before`, `@Around` 등의 어드바이스 선언부에 같이 사용된다.

이 때 `@Pointcut` 은 편의를 위해 특정 Pointcut 을 지칭하는 어노테이션으로, 아래 예시처럼 정의 후 어드바이스에 참조하여 사용할 수 있다.

```java
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Component
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
```
```java
@Component
public class Bean3 {
    public void helloWorld()    {
        System.out.println("[AT BEAN1] : Hello World!");
    }
}

@Service
public class ServiceBean {
    public String weiredMethod()    {
        System.out.println(
                "[At ServiceBean] : " +
                "Service method in progress"
        );
        return "SERVICE!!";
    }
}

/* ... */

bean1.helloWorld();     System.out.println();

serviceBean.weiredMethod();
```
```
<== Before Advice Invoked!
[AT BEAN1] : Hello World!
<== After Advice Invoked!

<== Before Advice Invoked!
[At ServiceBean] : Service method in progress
<== After Advice Invoked!
```

`@Pointcut` 어노테이션은 이름 그대로 특정 Pointcut 을 지칭할 뿐, 어드바이스 처럼 어떤 행동 (관심사) 를 실행하지 않는다. 때문에 위 출력을 보면 `onAnyBeanMethod()` 의 `"I WANT TO BE EXECUTED!!!!"` 가 출력되지 않는 것을 볼 수 있다.

PCD 는 이름 그대로 Join Point 중 특정 Join Point 를 지시하는 역할을 담당해, 우리의 어드바이스 혹은 Aspect 가 적용될 Join Point _(When + Where)_ 를 지시할 수 있다.

스프링 공식문서는 다음 9 개의 PCD 에 대해 설명하고 있다. [`[1]`](#reference)

| **PCD**                                 | **Description**                                                                                                                                                                               |
|-----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **`execution`**                         | 특정 메서드 실행 Join Point 를 지칭하는 PCD. Spring-aop PCD 중 가장 기본적인 _(Primary)_ PCD 이다.                                                                                                                 |
| **`within`**, **`@within`**             | _"특정 타입 속으로 Join Point 를 제한하는 **(Limits matching to join point within certain types)**"_ PCD 이다. <br/>특정 타입은 패키지, 혹은 클래스가 될 수 있으며, 특히 `@within` 으로 특정 어노테이션이 붙은 클래스 내 Join Point 를 지칭할 수도 있다. |
| **`this`**, **`target`**, **`@target`** | 특정 개체에 대한 Join Point 를 지칭할 수 있는 PCD 로, _Target Object_ 의 타입을 지칭해 제한하는 PCD 라 생각하면 편하다.  <br>특히 `@target` PCD 는 여러 개체 중, 특정 어노테이션 (`Ex : @Service`) 가 붙은 개체로 제한할 수 있다.                          |
| **`args`**, **`@args`**                 | 메서드 호출 Join Point 중, 특정 타입의 인자가 전달된 Join Point 로 제한하는 PCD 이다. `@args` 를 이용해 특정 어노테이션이 붙은 인자로 제한할 수도 있다.                                                                                       |
| **`@annotation`**                       | 특정 어노테이션이 붙은 메서드 Join Point 로 제한하는 PCD 이다.                                                                                                                                                    |

이들은 모두 `&& (AND)`, `|| (OR)`, `! (NOT)` 같은 논리연산자와 함께 사용되어 다수의 PCD 와 함꼐 조건을 제한할 수 있다.

특히 `this`, `(@)target`, `(@)args` 는 각자의 대상을 어드바이스의 매개변수로 제공할 수 있다. 때문에 이들은 [`Advice Parameter`](#advice-parameter) 에서 알아보겠다.

지금은 `execution`, `(@)within`, `@annotation` 에 대해 알아보자.

---

### `execution`

`execution` PCD 는 가장 주된 PCD 로, 특정 메서드 호출 Join Point 를 지칭할 수 있다.

`execution` PCD 로 Join Point 를 지칭하려면 아래의 표현에 따라 작성해야 한다. [`[2]`](#reference)

```
execution( 
 (접근 제한자) __ [리턴 타입 패턴] __ 
 (패키지 or 클래스 경로 패턴, 존재하면 . 로 메서드 이름과 구분 필요)
 [메서드 이름 패턴(파라미터 패턴)] __
 (throws 예외 타입 패턴)
)
```

위 표현 중 `__` 는 공백을 넣어야 함을 의미하고, `(접근 제한자)`, `(패키지 or 클래스 경로 패턴)`, `(throws 예외 타입 패턴)` 는 생략할 수 있다.

즉, `execution` PCD 는 반드시 `[리턴 타입]`, `[메서드]`, `[메서드 파라미터]` 패턴을 표기해야 한다.

`execution` PCD 의 각 패턴은 `*` 또는 `..` 를 이용해 포괄적으로 표현할 수 있는데, 이들 모두 **정규식의 _Wildcard_ 와 비슷한** 역할을 제공한다.

다만 `*` 의 경우 **"반드시 하나 이상을 포함하는 의미" (아닌 패턴도 있음!!)** 로 사용되는 반면, `..` 는 **"0 개 이상을 포함하는 의미"** 로 사용된다. 

또한 `*` 는 **`정규 표현식의 Wildcard` 처럼 거의 제한 없이** 사용될 수 있지만 `..` 는 대부분 패키지 관련 `Wildcard` 로 사용된다.

아래는 이들을 활용한 `execution` PCD 의 예시로, `Wildcard` 가 얼마나 자유분방한지 알 수 있다.

```java
// public void [아무 메서드](인자 1 개)
execution(public void *(*))

// public String [아무 메서드](아무 인자, 존재하지 않아도 됨)
execution(public String *(..))

// [practice 패키지 하위에 존재하는 모든 메서드](아무 인자)
execution(* practice..*(..))

// String [p 로 시작하는 패키지에 속한 모든 메서드](인자 2 개)
execution(String p*.*(*, *))

// [java.lang 하위 패키지의 아무 타입] 
// [p 로 시작하는 패키지에 속한 모든 메서드](인자 1 개 이상)
execution(java.lang..* p*.*(*, ..))

// [반환 타입의 이름이 St 로 시작] [아무 메서드](아무 인자)
execution(St* *(..))

// [반환 타입의 이름 중 rin 가 들어간 타입] [아무 메서드](아무 인자)
execution(*rin* *(..))

// [반환 타입의 이름이 g 로 끝나는 타입] [아무 메서드](아무 인자)
execution(*g *(..))

// void [ (아무 패키지)/practice/(아무 패키지) 에 존재하는 모든 메서드]
// (인자 0 개)
execution(void *.practice.*.*())

// [반환 타입의 이름이 v 로 시작하는 타입] [아무 메서드](인자 0 개)
execution(v* *())
```

---

### `within`

`within` PCD 는 특정 패키지, 혹은 클래스 하위 영역을 지칭할 수 있다.

앞서 `execution` 의 `(패키지 or 클래스 경로 패턴)` 과 동치라 생각하면 편할 것이다.

또한 `@within` PCD 는 특정 어노테이션이 붙은 패키지, 클래스 하위 영역을 지정할 수 있어, 다음 처럼 사용할 수 있다.

(자바 9 부터 패키지에 직접적으로 어노테이션을 붙이는 기능이 추가됨.)

```java
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
```
```
<<== This method in under practice.pcd_example package!!
[AT BEAN1] : Hello World!

<<== This method is under @Service annotated bean!!
<<== This method in under practice.pcd_example package!!
[At ServiceBean] : Service method in progress
```

---

### `@annotation`

`@annotation` 은 특정 어노테이션이 붙은 Join Point 를 지칭하는 PCD 이다.

스프링의 Join Point 는 Proxy 로 인해 메서드 호출로 제한되므로, `@annotation` PCD 는 쉽게 말해 `특정 어노테이션이 붙은 메서드` 를 지칭하는 PCD 라 할 수 있다.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAnnotation {}

/* ... */

@Component
public class Bean1 {
    @MyAnnotation
    public void helloWorld()    {
        System.out.println("[AT BEAN1] : Hello World!");
    }
}

/* ... */

@Pointcut("@annotation(MyAnnotation)")
public void onAnnotation()  {}

@Before("onAnnotation()")
public void beforeAnnot()   {
    System.out.println("<<== This method is annotated with @MyAnnotation!!");
}
```
```
<<== This method is annotated with @MyAnnotation!!
[AT BEAN1] : Hello World!

[At ServiceBean] : Service method in progress
```

---

## Advice Parameter

앞서 말했듯이 `(@)args`, `this`, `(@)target` PCD 는 그들의 대상을 어드바이스의 매개변수로 제공할 수 있다.

```java
@Component
public class Bean2 {
    public void singleArgMethod(Integer num)    {
        System.out.println("[AT BEAN2] : singleArgMethod (Integer) = " + num);
    }

    public void singleArgMethod(Bean2 bean2) {
        System.out.println("[AT BEAN2] : singleArgMethod (Bean2) = " + bean2);
    }
}

/* ... */

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

/* ... */

bean2.singleArgMethod(10);

System.out.println();
bean2.singleArgMethod(bean2);
```
```
<== Before singleArg(int) Invoked!! : 10
[AT BEAN2] : singleArgMethod (Integer) = 10
<== Target were USED!!

<== Before beanSingleArg() Invoked!!! : practice.pcd_example.some_package.Bean2@239105a8
[AT BEAN2] : singleArgMethod (Bean2) = practice.pcd_example.some_package.Bean2@239105a8
<== Target were USED!!
```

위 코드를 보면 `args(value)` 와 같이 **변수의 이름을 미리 예약** 하고 매개변수에 `Integer value` 처럼 제공하여 **변수의 타입을 제한** 하는 것을 볼 수 있고, 이는 사실 `beforeBeanSingleArg` 의 `args` PCD 방식과 동일하다.

`(args(practice.pcd_example.some_package.Bean2) && args(bean2))`

당연히 이들에도 `*`, `..` 의 `Wildcard` 와 논리 연산자를 사용할 수 있다.

---

## Summary

- 포인트 컷 지시자 _**(PCD)**_ 를 이용해 어드바이스가 적용될 Join Point 를 제한할 수 있다.
- 각 PCD 는 `*`, `..` 와일드카드를 사용할 수 있으며, 여러 PCD 를 논리 연산자를 이용해 조합할 수 있다.
- `execution` PCD 표현식에는 **반드시 `[접근 제어자 패턴] [메서드 이름 패턴](파라미터 패턴)` 3 가지가 존재**해야 한다.
- `(@)args`, `(@)target`, `this` PCD 는 어드바이스에 PCD 대상을 인자로 제공할 수 있다.

---

## Reference

- [`[1] : Supported Pointcut Designators - Spring documentation`](https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/pointcuts.html#aop-pointcuts-designators)
- [`[2] : 포인트 컷의 다양한 표현식 - gmoon92's github.io`](https://gmoon92.github.io/spring/aop/2019/05/06/pointcut.html)