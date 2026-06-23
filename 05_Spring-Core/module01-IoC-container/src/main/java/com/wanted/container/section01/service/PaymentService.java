package com.wanted.container.section01.service;

import com.wanted.container.section01.gateway.KakaoPayGateway;
import com.wanted.container.section01.gateway.NaverPayGateway;

public class PaymentService {

    private NaverPayGateway payGateway; // 네이버로 바꾸려면 여기

    public PaymentService(){
        this.payGateway = new NaverPayGateway(); // 여기
    }

    public boolean processPayment(String orderId, double amount){
        System.out.println("결제 비즈니스 로직 시작. 주문 ID = " + orderId + ", 금액 = "+ amount);

        // 결제 게이트웨이를 통한 결제 처리
        boolean result = payGateway.processPayment(orderId, amount); // 여기를 다 고쳐야됨
        if(result){
            System.out.println("결제 처리가 성공적으로 마무리!");
        } else{
            System.out.println("결제 처리 실패..🚨🚨🚨");
        }
        return result;
    }

}
