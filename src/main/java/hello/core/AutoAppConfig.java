package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
) //실제로는 보통 excludeFilters를 사용하지 않지만, 앞서 연습을 위해 사용한 AppCnofig, TestConfig 등 @Configuration 을 사용한 코드들의 중복을 피하기 위해 사용하였음.
public class AutoAppConfig {

    /* 빈 이름 중복 시 충돌 오류 발생 확인을 위한 코드 */
    /*
    @Bean(name = "memoryMemberRepository")
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    */
}
