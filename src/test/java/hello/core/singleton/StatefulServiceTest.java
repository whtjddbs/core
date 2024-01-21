package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA: A사용자가 10000원 주문
        statefulService1.order("uesrA", 10000);
        //ThreadB: B사용자가 20000원 주문
        statefulService2.order("uesrB", 20000);

        //ThreadA: A사용자 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
        //위처럼 StatefulService 의 price 필드는 공유되는 필드인데, 특정 클라이언트가 값을 변경한다.
        //사용자A의 주문금액은 10000원이 되어야 하는데, 20000원이라는 결과가 나왔다.
        //큰 문제 발생!! 스프링 빈은 항상 무상태(stateless)로 설계해아 한다.
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}