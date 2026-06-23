package com.wanted.container.section03.gateway;

public interface PaymentInterface {

    boolean processPayment(String orderId, double amount);

}
