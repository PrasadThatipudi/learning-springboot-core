package org.learning.irctc.service;

import org.learning.di.annotation.Component;

@Component
public class OfferService {
    public OfferService() {}
    public void applyOffer() {
        System.out.println("OfferService: applying offer");
    }
}
