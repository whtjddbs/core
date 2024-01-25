package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 구현체인 FixDiscountPolicy도 의존하고 있음 - DIP 위반
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // 정액할인 정책에서 정율할인 정책으로 변경할 경우 클라이언트(Service) 소스도 변경이 필요함 - OCP 위반
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    //AppConfig을 통해 구현체 주입을 하도록 변경하여 DIP 위반 문제 해결
    //@Autowired //필드 주입 : 순수 자바로 테스트가 불가하기 때문에 사용하지 않는다.
    private final MemberRepository memberRepository;
    //@Autowired //필드 주입
    private final DiscountPolicy discountPolicy;

    /*
    //수정자 주입
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //수정자 주입
    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
    */
    //생성자 주입
    @Autowired //생성자가 딱 1개만 있는 경우 @Autowired를 생략해도 자동으로 의존관계 주입을 한다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
