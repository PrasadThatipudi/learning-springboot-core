package org.learning.irctc;

import org.learning.di.ApplicationContext;
import org.learning.irctc.service.*;

public class IRCTCApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = ApplicationContext.getInstance();

        BookingService bookingService = applicationContext.getBean(BookingService.class);
        System.out.println("======================================================================");
        bookingService.bookTicket("user1", "Delhi", "Mumbai");
        System.out.println("======================================================================");
    }
}
