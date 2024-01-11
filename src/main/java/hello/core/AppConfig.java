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

/**
 * AppConfig를 통해 구체 클래스 주입을 담당하게 하고 각 서비스들은 역할에만 집중할 수 있다.
 * 즉, "DI 컨테이너" 역할을 한다.
 */
public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    /* 할인 정책 변경 시 discountPolicy에서 구현체만 변경하면 된다. */
    private DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

}
