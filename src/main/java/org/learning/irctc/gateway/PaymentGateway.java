package org.learning.irctc.gateway;

import org.learning.di.annotation.Component;

@Component
public class PaymentGateway {
    public PaymentGateway() {}
    public void processPayment() {
        System.out.println("PaymentGateway: processing payment");
    }
}
