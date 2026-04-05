package org.learning.irctc.service;

import org.learning.irctc.repository.BookingRepository;

public class BookingService {
    private final StationService stationService;
    private final PaymentService paymentService;
    private final DiscountService discountService;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    public BookingService(StationService stationService, PaymentService paymentService, DiscountService discountService, UserService userService, BookingRepository bookingRepository) {
        this.stationService = stationService;
        this.paymentService = paymentService;
        this.discountService = discountService;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
    }



    public void bookTicket(String user, String from, String to) {
        System.out.println("BookingService: booking ticket for " + user + " from " + from + " to " + to);
        // Simulate user validation or fetching user details
        System.out.println("BookingService: validating user " + user);
        userService.getUserDetails(user);
        stationService.searchStation(from);
        stationService.searchStation(to);
        discountService.applyDiscount();
        paymentService.pay();
        bookingRepository.saveBooking();
    }
}
