# HISTORY

### 2024-01-05
스프링의 기본적인 핵심 원리를 학습하기 위해 스프링 부트를 통해 별도의 Dependencies 추가 없이 프로젝트 생성  
https://start.spring.io/
![image](https://github.com/whtjddbs/core/assets/39715626/0c12e536-ea0a-4e3f-aa19-f462c7f5c14f)
---
### 2024-01-07
####비즈니스 요구사항과 설계
* #####회원  
> 회원을 가입하고 조회할 수 있다.  
회원은 일반과 VIP 두 가지 등급이 있다.  
회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다. (미확정)
* #####주문과 할인 정책  
> 회원은 상품을 주문할 수 있다.  
회원 등급에 따라 할인 정책을 적용할 수 있다.  
할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용해달라. (나중에 변경 될 수 있다.)  
할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루
고 싶다. 최악의 경우 할인을 적용하지 않을 수 도 있다. (미확정)
---
### 2024-01-08
회원 도메인 분석/설계/개발/테스트
---
### 2024-01-09
주문, 할인 도메인 분석/설계/개발/테스트
---
### 2024-01-10
객체 지향 원리를 적용해서 요구사항 변경 반영  
- 새로운 할인 정책 적용 (정률 할인)  
- AppConfig를 통해 의존성 주입을 하여 각 서비스들은 역할(추상화)에만 의존하도록 수정 (DIP 위반 해결)
---
### 2024-01-11
3가지 SRP, DIP, OCP 적용<br/>

####<b>SRP 단일 책임 원칙</b><br/>
>"한 클래스는 하나의 책임만 가져야 한다."<br/>
* 클라이언트 객체는 직접 구현 객체를 생성하고, 연결하고, 실행하는 다양한 책임을 가지고 있음
* SRP 단일 책임 원칙을 따르면서 관심사를 분리함
* 구현 객체를 생성하고 연결하는 책임은 AppConfig가 담당
* 클라이언트 객체는 실행하는 책임만 담당

####<br>DIP 의존관계 역전 원칙</b><br/>
>"프로그래머는 “추상화에 의존해야지, 구체화에 의존하면 안된다.” 의존성 주입은 이 원칙을 따르는 방법 중 하나다."<br/>
* 새로운 할인 정책을 개발하고, 적용하려고 하니 클라이언트 코드도 함께 변경해야 했다. 왜냐하면 기존 클라이언
트 코드( OrderServiceImpl )는 DIP를 지키며 DiscountPolicy 추상화 인터페이스에 의존하는 것 같았
지만, FixDiscountPolicy 구체화 구현 클래스에도 함께 의존했다.
* 클라이언트 코드가 DiscountPolicy 추상화 인터페이스에만 의존하도록 코드를 변경했다.
하지만 클라이언트 코드는 인터페이스만으로는 아무것도 실행할 수 없다.
* AppConfig가 FixDiscountPolicy 객체 인스턴스를 클라이언트 코드 대신 생성해서 클라이언트 코드에 의
존관계를 주입했다. 이렇게해서 DIP 원칙을 따르면서 문제도 해결했다.

####<b>OCP</b><br/>
>"소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다"
* 다형성 사용하고 클라이언트가 DIP를 지킴
* 애플리케이션을 사용 영역과 구성 영역으로 나눔
* AppConfig가 의존관계를 FixDiscountPolicy RateDiscountPolicy 로 변경해서 클라이언트 코드
에 주입하므로 클라이언트 코드는 변경하지 않아도 됨
* 소프트웨어 요소를 새롭게 확장해도 사용 영역의 변경은 닫혀 있다
---
###2024-01-14
####스프링 컨테이너의 생성 과정
1. 스프링 컨테이너 생성<br/>
2. 스프링 빈 등록<br/>
3. 스프링 빈 의존관계 설정 - 준비<br/>
4. 스프링 빈 의존관계 설정 - 완료<br/>
---
###2024-01-15  
####스프링 빈 조회 해보기

---
###2024-01-16
1. 설정 정보를 AppConfig.java에서 xml설정으로 변경해보기  
2. 스프링은 BeanDefinition이라는 추상화를 통해 빈 설정 메타정보를 생성을 위한 역할을 만들고
   java 설정파일이나 xml 설정파일을 읽고 BeanDefinition을 실제로 생성하는 구현체를 통해 다양한 설정 형식을 지원한다.  
> ####BeanDefinition 정보  
* BeanClassName: 생성할 빈의 클래스 명(자바 설정 처럼 팩토리 역할의 빈을 사용하면 없음)  
* factoryBeanName: 팩토리 역할의 빈을 사용할 경우 이름, 예) appConfig  
* factoryMethodName: 빈을 생성할 팩토리 메서드 지정, 예) memberService  
* Scope: 싱글톤(기본값)  
* lazyInit: 스프링 컨테이너를 생성할 때 빈을 생성하는 것이 아니라, 실제 빈을 사용할 때 까지 최대한 생성을 지연
처리 하는지 여부  
* InitMethodName: 빈을 생성하고, 의존관계를 적용한 뒤에 호출되는 초기화 메서드 명  
* DestroyMethodName: 빈의 생명주기가 끝나서 제거하기 직전에 호출되는 메서드 명  
* Constructor arguments, Properties: 의존관계 주입에서 사용한다. (자바 설정 처럼 팩토리 역할의 빈을 사용
하면 없음)
---
###2024-01-17
####싱글톤 패턴  
1. 스프링 없는 순수한 DI컨테이너의 문제점 확인  
2. 싱글톤 패턴 : 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴  
---
###2024-01-18
####싱글톤 컨테이너
---
###2024-01-21
####싱글톤 방식의 문제점  
* 싱글톤 패턴이든, 스프링 같은 싱글톤 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는 싱글톤
방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(stateful)하
게 설계하면 안된다.
* 무상태(stateless)로 설계해야 한다!
  * 특정 클라이언트에 의존적인 필드가 있으면 안된다.
  * 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다!
  * 가급적 읽기만 가능해야 한다.
  * 필드 대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.
  * 스프링 빈의 필드에 공유 값을 설정하면 정말 큰 장애가 발생할 수 있다!!
####@Configuration
@Configuration은 스프링 빈이 싱글톤이 되도록 보장해준다.  
@Configuration을 사용하면 GCLIB라는 바이트 조작 라이브러리를 사용해서 클래스명+CGLIB이라는 클래스를 빈으로 등록하고 
@Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환, 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만든다.   
따라서, @Configuration없이 @Bean만 사용할 경우 스프링 빈에 등록은 되지만 싱글톤을 보장해주지는 않는다.
---
###2024-01-22
####컴포넌트 스캔
@ComponentScan 은 @Component 가 붙은 모든 클래스를 스프링 빈으로 등록한다.  
이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.  
* 빈 이름 기본 전략: MemberServiceImpl 클래스 memberServiceImpl  
* 빈 이름 직접 지정: 만약 스프링 빈의 이름을 직접 지정하고 싶으면 @Component("memberService2") 이런식으로 이름을 부여하면 된다
####@Autowired 의존관계 자동 주입
생성자에 @Autowired 를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.  
이때 기본 조회 전략은 타입이 같은 빈을 찾아서 주입한다.  
getBean(MemberRepository.class) 와 동일하다고 이해하면 된다
---
###2024-01-23
####컴포넌트 스캔의 탐색 위치와 기본 스캔 대상
1. 탐색 위치 
* basePackages : 탐색할 패키지의 시작 위치를 지정한다. 이 패키지를 포함해서 하위 패키지를 모두 탐색한다.
  * basePackages = {"hello.core", "hello.service"} 이렇게 여러 시작 위치를 지정할 수도 있다. 
* basePackageClasses : 지정한 클래스의 패키지를 탐색 시작 위치로 지정한다.
* 만약 지정하지 않으면 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.  
※ 권장하는 방법 : 
  패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 위치. 최근 스프링 부트도 이 방법을 기본으로 제공한다.  
  
2. 기본 스캔 대상
* 컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함한다.
  * @Component : 컴포넌트 스캔에서 사용
  * @Controller : 스프링 MVC 컨트롤러에서 사용
  * @Service : 스프링 비즈니스 로직에서 사용
  * @Repository : 스프링 데이터 접근 계층에서 사용
  * @Configuration : 스프링 설정 정보에서 사용
  
####컴포넌트 스캔 필터
includeFilters, excludeFilters
---
###2024-01-24
####스프링 빈 중복 등록과 충돌
> 컴포넌트 스캔을 통해 자동 등록된 빈의 이름이 같을 경우 충돌 오류 발생.  
> 자동 등록 빈과 @Bean을 통해 수동 등록된 빈의 이름이 중복될 경우 수동 빈으로 오버라이딩 한다.  
> 의도하지 않은 수동 빈 등록으로 인해 오버라이딩되는 상황을 피하기 위해 최근 스프링부트에서는 기본설정으로 충돌 오류를 발생시킨다.  

####의존관계 주입
의존관계 주입은 크게 4가지 방법이 있다.  
* 생성자 주입
* 수정자 주입(setter 주입)
* 필드 주입
* 일반 메서드 주입
---
###2024-01-26
####@Autowired 의존 관계 주입 옵션 처리 방법
* @Autowired(required=false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
* org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null이 입력된다.
* Optional<> : 자동 주입할 대상이 없으면 Optional.empty 가 입력된다
<!-- AutowiredOption.java 에서 테스트 -->
---  
###2024-01-27  
####생성자 주입을 선택한 코드  
####롬복과 최신 트랜드
--- 
###2024-01-29  
####조회 빈이 2개 이상일 때 문제 해결  
1. @Autowired 필드명 매칭
    1) 타입 매칭
    2) 타입 매칭의 결과가 2개 이상일 때 필드명, 파라미터명으로 빈이름 매칭
2. @Qualifier 사용
    1) 같은 @Qualifier끼리 매칭
    2) 같은 빈 이름 매칭
    3) NoSuchBeanDefinitionException 예외 발생
3. @Primary 사용
    1) 우선 순위를 부여한다.
> @Primary 는 기본값 처럼 동작하는 것이고, @Qualifier 는 매우 상세하게 동작한다. 이런 경우 어떤 것이 우선권을
가져갈까? 스프링은 자동보다는 수동이, 넒은 범위의 선택권 보다는 좁은 범위의 선택권이 우선 순위가 높다. 따라서 여
기서도 @Qualifier 가 우선권이 높다
####애노테이션 직접 만들기
* @Qualifier("mainDiscountPolicy") 처럼 문자를 적으면서 타이핑 오류를 했을 경우, 컴파일 시 타입 체크가 되지 않는다.
직접 애노테이션을 만들어서 이러한 문제를 해결 → @MainDiscountPolicy 생성
---  
###2024-01-30
####조회한 빈이 모두 사용
* 상황에 따라서 여러 할인 정책을 사용해야할 경우, 모든 빈(할인 정책 rate, fix)을 모두 사용해야 한다.  
* 이 경우 스프링을 사용하면 List, Map을 사용하여 간단하게 전략 패턴을 구현 가능
---  
### 2024-01-31  
#### 의존관계 주입 : 자동 vs 수동  
* 편리한 자동 기능을 기본으로 사용하자(스프링 부트는 자동으로 컴포넌트스캔을 함)
* 직접 등록하는 기술 지원 객체는 수동 빈 등록
* 다형성을 적극 활용하는 비즈니스 로직은 수동 등록을 고려  
```java
@Configuration
public class DiscountPolicyConfig {
    @Bean
    public DiscountPolicy rateDiscountPolicy() {
        return new RateDiscountPolicy();
    }
    @Bean
    public DiscountPolicy fixDiscountPolicy() {
        return new FixDiscountPolicy();
    }
}
```
---
## 빈 생명주기 콜백
### 빈 생명주기 시작  
#### 스프링 싱글톤 빈의 이벤트 라이프사이클  
스프링 컨테이너 생성 → 스프링 빈 생성 → 의존관계 주입 → 초기화 콜백 → 사용 → 소멸 전 콜백 → 스프링 종료  
> <b>객체의 생성과 초기화를 분리하자</b>  
> 생성자는 필수 정보(파라미터)를 받고, 메모리를 할당해서 객체를 생성하는 책임을 가진다.
> 반면에 초기화는 이렇게 생성된 값들을 활용해서 외부 커넥션을 연결하는 등 무거운 동작을 수행한다.
> <!-- BeanLifeCycleTest.java 에서 테스트 -->
###
#### 스프링은 크게 3가지 방법으로 빈 생명주기 콜백을 지원한다.    
* 인터페이스(InitializingBean, DisposableBean) 구현
* 설정 정보에 초기화 메서드, 종료 메서드 지정
* @PostConstruct, @PreDestroy 애노테이션
###
#### 인터페이스(InitializingBean, DisposableBean) 구현  
<h6>초기화, 소멸 인터페이스 단점</h6>
* 이 인터페이스는 스프링 전용 인터페이스다. 해당 코드가 스프링 전용 인터페이스에 의존한다.
* 초기화, 소멸 메서드의 이름을 변경할 수 없다.
* 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
> 참고: 인터페이스를 사용하는 초기화, 종료 방법은 스프링 초창기에 나온 방법들이고, 지금은 다음의 더 나은 방법
들이 있어서 거의 사용하지 않는다.  
###
#### 빈 등록 초기화, 소멸 메서드 지정  
설정 정보에 ```@Bean(initMethod = "init", destroyMethod = "close")``` 처럼 초기화, 소멸 메서드를 지
정할 수 있다  
<h6>설정 정보 사용 특징</h6>
* 메서드 이름을 자유롭게 줄 수 있다.
* 스프링 빈이 스프링 코드에 의존하지 않는다.
* 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적
용할 수 있다.
### 
#### @PostConstruct, @PreDestroy 애노테이션  
<h6>@PostConstruct, @PreDestroy 애노테이션 특징</h6>
* 최신 스프링에서 가장 권장하는 방법이다.
* 애노테이션 하나만 붙이면 되므로 매우 편리하다.
* 패키지를 잘 보면 javax.annotation.PostConstruct 이다. 스프링에 종속적인 기술이 아니라 JSR-250
라는 자바 표준이다. 따라서 스프링이 아닌 다른 컨테이너에서도 동작한다.
* 컴포넌트 스캔과 잘 어울린다.
* 유일한 단점은 외부 라이브러리에는 적용하지 못한다는 것이다. 외부 라이브러리를 초기화, 종료 해야 하면
@Bean의 기능을 사용하자.
#### 정리
* @PostConstruct, @PreDestroy 애노테이션을 사용하자
* 코드를 고칠 수 없는 외부 라이브러리를 초기화, 종료해야 하면 @Bean 의 initMethod , destroyMethod 를
사용하자
---
## 빈 스코프
* <b>싱글톤</b>: 기본 스코프, 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위의 스코프이다.
* <b>프로토타입</b> : 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는 매우
  짧은 범위의 스코프이다.
```java
@Scope("singleton") public class SingletonBean {}
@Scope("prototype") public class PrototypeBean {}
```

### 싱글톤 빈에서 프로토타입 빈 사용
싱글톤 빈에서 프로토타입 빈 사용 시 싱글톤 빈 생성 시점에 의존관계 주입을 하기 때문에, 의도와 다르게 최초 주입된 프로토타입 빈을 계속 사용하게 된다.  
이러한 문제를 해결하기 위해 Provider를 사용하여 의존관계 조회(DL) 한다.

### ObjectProvider
지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스를 제공하는 것이 바로 ObjectProvider 이다. 참고로 과거에는
ObjectFactory 가 있었는데, 여기에 편의 기능을 추가해서 ObjectProvider 가 만들어졌다.

### Provider
마지막 방법은 javax.inject.Provider 라는 JSR-330 자바 표준을 사용하는 방법이다.
스프링 부트 3.0은 jakarta.inject.Provider 사용한다

### 웹 스코프
웹 환경에서만 동작한다.<br/>
웹 스코프는 프로토타입과 다르게 스프링이 해당 스코프의 종료시점까지 관리한다. 따라서 종료 메서드가 호출된다.<br/><br/>
web 환경을 위해 라이브러리 추가 필요.<br/>
```groovy
implementation 'org.springframework.boot:spring-boot-starter-web'
```
* <b>request</b> : HTTP 요청 하나가 들어오고 나갈 때까지 유지되는 스코프.
* <b>session</b> : HTTP Session과 동일한 생명주기를 가지는 스코프.

### request 스코프 예제
!!! 주의점:  
request 스코프 빈은 http요청마다 생성되기 때문에 싱글톤 빈에서 DI할 경우 빈을 찾을 수 없어 에러가 발생한다.  
```
Error creating bean with name 'myLogger': Scope 'request' is not active for the 
current thread; consider defining a scoped proxy for this bean if you intend to 
refer to it from a singleton;
```
### 스코프와 Provider
첫번째 해결방안은 앞서 배운 Provider를 사용하는 것이다.  
```java
@Service
@RequiredArgsConstructor
public class LogDemoService {
 private final ObjectProvider<MyLogger> myLoggerProvider;
 public void logic(String id) {
 MyLogger myLogger = myLoggerProvider.getObject();
 myLogger.log("service id = " + id);
 }
}
```
* ```ObjectProvider``` 덕분에 ```ObjectProvider.getObject()``` 를 호출하는 시점까지 request scope 빈의
생성을 지연할 수 있다.
* ```ObjectProvider.getObject()``` 를 호출하시는 시점에는 HTTP 요청이 진행중이므로 request scope 빈
의 생성이 정상 처리된다.
  
### 스코프와 프록시
두번째 해결방안으로 프록시 방법이 있다.  
```java
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {
}
```
* 적용 대상이 인터페이스가 아닌 클래스면 ```TARGET_CLASS``` 를 선택  
* 적용 대상이 인터페이스면 ```INTERFACES``` 를 선택  

이렇게 하면 MyLogger의 가짜 프록시 클래스를 만들어두고 HTTP request와 상관 없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있다

---
