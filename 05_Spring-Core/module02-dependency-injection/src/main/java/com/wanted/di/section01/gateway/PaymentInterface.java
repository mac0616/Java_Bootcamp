package com.wanted.di.section01.gateway;

public interface PaymentInterface {

    boolean processPayment(String orderId, double amount);

}
