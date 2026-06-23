package com.wanted.container.section04.gateway;

public interface PaymentInterface {

    boolean processPayment(String orderId, double amount);

}
