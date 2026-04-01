package com.wanted.di.section03.gateway;

import com.wanted.di.section01.gateway.PaymentInterface;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class KakaoPayGateway implements PaymentInterface {

    /* comment.
    *   @Primary 어노테이션은 동일한 타입의 Bean이 여러개일 때, 기본으로 주입될 Bean을 지정할 수 있다.
    *  */

    @Override
    public boolean processPayment(String orderId, double amount) {
        System.out.println("카카오페이로 결제 진행 시작 : 주문ID = " + orderId + ", 금액 = " + amount);
        return true;
    }
}
