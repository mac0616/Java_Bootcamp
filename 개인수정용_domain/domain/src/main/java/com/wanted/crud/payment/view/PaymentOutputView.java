package com.wanted.crud.payment.view;

import java.util.List;

public class PaymentOutputView {
    public void printMessage(String s) {
        System.out.println(s);
    }

    public void printError(String message) {
        System.out.println("🚨🚨" + message);
    }


}
