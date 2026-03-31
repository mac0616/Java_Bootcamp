package com.wanted.di.section02.gateway;

public interface PaymentInterface {


    boolean processPayment(String orderId, double amount);

}
