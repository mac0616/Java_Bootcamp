package com.wanted.di.section03.gateway;

public interface PaymentInterface {


    boolean processPayment(String orderId, double amount);

}
