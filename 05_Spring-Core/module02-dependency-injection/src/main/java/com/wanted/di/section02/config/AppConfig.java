package com.wanted.di.section02.config;

import com.wanted.di.section02.gateway.NaverPayGateway;
import com.wanted.di.section02.gateway.PaymentInterface;
import com.wanted.di.section02.service.PaymentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = "com.wanted.di.section02",
        excludeFilters = @ComponentScan.Filter(
//                type = FilterType.ASSIGNABLE_TYPE, classes = {PaymentService.class}
//                type = FilterType.ANNOTATION, classes = {org.springframework.stereotype.Service.class}
                // 서비스라는 어노테이션을 제외하겠다.
//                type = FilterType.REGEX, pattern = {"com.wanted.di.section01.service.*"}
        ))
//@ComponentScan // 굳이 경로 지정하지 않아도 같은 폴더 안에 있으면 찾을 수 있음
public class AppConfig {

    /* comment.
    *   @Qualifier 어노테이션은 특정 Bean 을 이름으로 지정을 한다.
    *   @Primary 설정으로 default 값이 KakaoPay로 되어 있지만,
    *   @Qualifier 어노테이션은 우리가 명시적으로 Interface 의 구현체를
    *   지정할 수 있다.
    *   의존성 주입 우선순위는 @Qualifier 가 @Primary 보다 높다.
    *   */


    @Bean("naverPay")
    public PaymentService naverPayment(
            @Qualifier("naverPayGateway")PaymentInterface paymentInterface
            ){
        return new PaymentService(paymentInterface);
    }

}
