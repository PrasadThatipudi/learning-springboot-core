package org.learning.irctc.repository;

import org.learning.di.annotation.Component;

@Component
public class BookingRepositoryImpl implements BookingRepository {
    @Override
    public void saveBooking() {
        System.out.println("BookingRepository: saving booking");
    }
}
