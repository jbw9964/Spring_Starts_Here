
# Spring Data JPA

---

설명해야 할 내용

1. 영속성 컨텍스트와 엔티티 생명주기
2. JPA 에서의 프록시 개체
3. 엔티티 매니저
4. N + 1 문제
5. 대표 어노테이션

---

몇일 전, 수업에서 Spring Data JPA 실습을 진행했다.

JPA 에 대해 잘 모르는 사실이 많아 이를 기록하고자 글을 작성하였다.

---


> https://www.ibm.com/docs/en/was-liberty/nd?topic=liberty-java-persistence-api-jpa

<details>

- Persistence unit 

    Defines a complete Object-Relational Model mapping Java classes (entities + supporting structures) with a relational database. The EntityManagerFactory uses this data to create a persistence context that can be accessed through the EntityManager.


- EntityManagerFactory

    Used to create an EntityManager for database interactions. The application server containers typically supply this function, but the EntityManagerFactory is required if you are using JPA application-managed persistence. An instance of an EntityManagerFactory represents a Persistence context.


- Persistence context

    Defines the set of active instances that the application is manipulating currently. You can create the persistence context manually or through injection.


- EntityManager

    The resource manager that maintains the active collection of entity objects that are being used by the application. The EntityManager handles the database interaction and metadata for object-relational mappings. An instance of an EntityManager represents a Persistence context. An application in a container can obtain the EntityManager through injection into the application or by looking it up in the Java component name-space. If the application manages its persistence, the EntityManager is obtained from the EntityManagerFactory.


- Entity objects

    A simple Java class that represents a row in a database table in its simplest form. Entities objects can be concrete classes or abstract classes. They maintain states by using properties or fields.

</details>

> https://www.youtube.com/watch?v=kJexMyaeHDs&t=621s
> 
> 엔티티 생명 주기 간단 설명

> https://www.youtube.com/watch?v=ni92wUkAmQI&t=218s
> 
> JPA N + 1 문제
