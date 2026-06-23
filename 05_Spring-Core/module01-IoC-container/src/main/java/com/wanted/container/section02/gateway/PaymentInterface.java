package com.wanted.container.section02.gateway;

public interface PaymentInterface {

    boolean processPayment(String orderId, double amount);

}
