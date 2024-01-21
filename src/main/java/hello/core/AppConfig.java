package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig를 통해 구체 클래스 주입을 담당하게 하고 각 서비스들은 역할에만 집중할 수 있다.
 * 즉, "DI 컨테이너" 역할을 한다.
 *
 * 2024-01-12
 * 지금까지 순수 JAVA 코드를 통해 DI를 적용.
 * 스프링을 통해 의존관계 주입을 하도록 변경한다.
 */
@Configuration
public class AppConfig {

    /*
    예상 시나리오
    @Bean memberService -> memberRepository()
    call AppConfig.memberService
    call AppConfig.memberRepository

    @Bean memberRepository
    call AppConfig.memberRepository

    @Bean orderService -> memberRepository(), discountPolicy()
    call AppConfig.orderService
    call AppConfig.memberRepository
    call AppConfig.discountPolicy

    @Bean discountPolicy
    call AppConfig.discountPolicy
     */

    /*
    실제 결과는 한번씩만 호출됨
    call AppConfig.memberService
    call AppConfig.memberRepository
    call AppConfig.orderService
    call AppConfig.discountPolicy
     */

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    /* 할인 정책 변경 시 discountPolicy에서 구현체만 변경하면 된다. */
    @Bean
    public DiscountPolicy discountPolicy() {
        System.out.println("call AppConfig.discountPolicy");
        return new RateDiscountPolicy();
    }

}
